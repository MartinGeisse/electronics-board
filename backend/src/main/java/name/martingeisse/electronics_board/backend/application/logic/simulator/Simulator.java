package name.martingeisse.electronics_board.backend.application.logic.simulator;

import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.PauseCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.ResumeCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SetModelCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SimulatorControlCommand;
import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;
import name.martingeisse.electronics_board.backend.application.logic.simulator.worker.SimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.worker.SimulatorWorker;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public final class Simulator {

    private final BlockingQueue<SimulatorControlCommand> commandQueue = new LinkedBlockingQueue<>();
    private final Set<Consumer<SimulatorOutputEvent>> outputEventConsumers = new CopyOnWriteArraySet<>();

    public Simulator() {
        Consumer<SimulatorOutputEvent> outputEventReplicator = outputEvent -> {
            for (Consumer<SimulatorOutputEvent> consumer : outputEventConsumers) {
                consumer.accept(outputEvent);
            }
        };
        new SimulatorWorker(commandQueue, outputEventReplicator).startThread();
    }

    public void setModel(SimulationModel model) {
        commandQueue.add(new SetModelCommand(model));
    }

    public void resume() {
        commandQueue.add(new ResumeCommand());
    }

    public void pause() {
        commandQueue.add(new PauseCommand());
    }

    public void addOutputEventConsumer(Consumer<SimulatorOutputEvent> consumer) {
        this.outputEventConsumers.add(consumer);
    }

    public void removeOutputEventConsumer(Consumer<SimulatorOutputEvent> consumer) {
        this.outputEventConsumers.remove(consumer);
    }

}
