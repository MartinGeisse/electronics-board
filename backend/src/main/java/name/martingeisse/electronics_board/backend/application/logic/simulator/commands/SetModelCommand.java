package name.martingeisse.electronics_board.backend.application.logic.simulator.commands;

import name.martingeisse.electronics_board.backend.application.logic.simulator.worker.SimulationModel;

public final class SetModelCommand extends SimulatorControlCommand {

    private final SimulationModel model;

    public SetModelCommand(SimulationModel model) {
        this.model = model;
    }

    public SimulationModel getModel() {
        return model;
    }

    @Override
    public void execute(SimulatorCommandContext context) {
        context.setModel(model);
    }

}
