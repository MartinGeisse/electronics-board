package name.martingeisse.electronics_board.backend.application.logic.simulator.commands;

public abstract class SimulatorControlCommand {

    /**
     * This method must be called by the simulator, inside the simulator thread!
     */
    public abstract void execute(SimulatorCommandContext context);

}
