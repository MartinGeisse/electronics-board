package name.martingeisse.electronics_board.backend.application.logic.simulator.worker;

import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SimulatorCommandContext;
import name.martingeisse.electronics_board.backend.application.logic.simulator.commands.SimulatorControlCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Implementation note: We use the input queue and a separate execution list because BlockingQueue does not allow us
 * to wait for an incoming element without also removing it. The execution list captures this removed element (and
 * any others) and commands get executed from there.
 */
class CommandQueue {

    private final BlockingQueue<SimulatorControlCommand> inputQueue;
    private final List<SimulatorControlCommand> executionList;

    public CommandQueue(BlockingQueue<SimulatorControlCommand> inputQueue) {
        this.inputQueue = inputQueue;
        this.executionList = new ArrayList<>();
    }

    public void waitForCommand() throws InterruptedException {
        executionList.add(inputQueue.take());
    }

    public void waitForCommand(long maxMilliseconds) throws InterruptedException {
        SimulatorControlCommand command = inputQueue.poll(maxMilliseconds, TimeUnit.MILLISECONDS);
        if (command != null) {
            executionList.add(command);
        }
    }

    public void executePendingCommands(SimulatorCommandContext commandContext) {
        while (true) {
            inputQueue.drainTo(executionList);
            if (executionList.isEmpty()) {
                break;
            }
            executionList.forEach(command -> command.execute(commandContext));
            executionList.clear();
        }
    }
    
}
