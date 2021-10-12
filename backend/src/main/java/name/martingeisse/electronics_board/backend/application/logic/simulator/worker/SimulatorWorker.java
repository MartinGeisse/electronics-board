package name.martingeisse.electronics_board.backend.application.logic.simulator.worker;

import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.ResumeCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SetModelCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SimulatorCommandContext;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SimulatorControlCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.output.ResetOutputStateEvent;
import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;
import name.martingeisse.electronics_board.backend.util.ParameterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * This is a long-living object that creates and controls the simulation thread. In contrast, the
 * {@link ItemBasedSimulationModel} is contained in this object, is short-living, and can be replaced at will when the user
 * changes the design.
 *
 * The outputEventConsumer gets called in the worker thread! It must make sure that events are properly transferred
 * to the other threads, and it must not block for long since that would block the simulation.
 */
public class SimulatorWorker {

    private final CommandQueue commandQueue;
    private final Consumer<SimulatorOutputEvent> outputEventConsumer;
    private final SimulationPacer pacer;
    private SimulationModel model;
    private boolean running;

    public SimulatorWorker(
            BlockingQueue<SimulatorControlCommand> commandQueue,
            Consumer<SimulatorOutputEvent> outputEventConsumer
    ) {
        this.commandQueue = new CommandQueue(commandQueue);
        this.outputEventConsumer = outputEventConsumer;
        this.pacer = new SimulationPacer();
        this.model = SimulationModel.NULL_MODEL;
        this.running = false;
    }

    public void startThread() {
        new Thread(this::threadBody).start();
    }

    private void threadBody() {
        try {
            while (true) {
                loopIteration();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void loopIteration() throws InterruptedException {
        commandQueue.executePendingCommands(commandContext);
        if (running) {
            pacer.senseRealTime();
            for (int i = 0; i < 5; i++) { // handle a few commands before checking the command queue again
                if (pacer.getBacklogTicks() <= 0) {
                    sleepUntilModelOrCommandReady();
                    break;
                }
                pacer.markSimulatedTimePassed(model.step(pacer.getBacklogTicks()));
            }
        } else {
            // Wait for at least one command to be ready -- if the simulator is not running, it won't start without
            // a command. If the command does not start it, we'll end up here again.
            commandQueue.waitForCommand();
        }
    }

    private void sleepUntilModelOrCommandReady() throws InterruptedException {
        long skippableTicks = model.getSkippableTicks();
        // +1 so we don't sleep just too shortly because of rounding errors
        long skippableMilliseconds = skippableTicks < 0 ? Long.MAX_VALUE :
                (pacer.convertTicksToMilliseconds(skippableTicks) + 1);
        // max 10 to be able to react to unexpected events without stuttering
        commandQueue.waitForCommand(Math.min(10, skippableMilliseconds));
    }

    public static void executeAsMain(SimulationModel model) throws InterruptedException {
        BlockingQueue<SimulatorControlCommand> commandQueue = new LinkedBlockingQueue<>();
        commandQueue.add(new SetModelCommand(model));
        commandQueue.add(new ResumeCommand());
        SimulatorWorker worker = new SimulatorWorker(commandQueue, event -> {});
        do {
            worker.loopIteration();
        } while (worker.running);
    }

    private final SimulatorCommandContext commandContext = new SimulatorCommandContext() {

        @Override
        public void setModel(SimulationModel model) {
            ParameterUtil.ensureNotNull(model, "model");
            SimulatorWorker.this.model.setOutputEventConsumer(null);
            SimulatorWorker.this.model = model;
            model.setOutputEventConsumer(outputEventConsumer);
            outputEventConsumer.accept(new ResetOutputStateEvent());
        }

        @Override
        public void resume() {
            if (!running) {
                pacer.reset();
            }
            running = true;
        }

        @Override
        public void pause() {
            running = false;
        }

    };

}
