package name.martingeisse.electronics_board.backend.application.logic.simulator.commands;

public final class PauseCommand extends SimulatorControlCommand {

    @Override
    public void execute(SimulatorCommandContext context) {
        context.pause();
    }

}
