package name.martingeisse.electronics_board.backend.application.logic.simulator.commands;

import name.martingeisse.electronics_board.backend.application.logic.simulator.worker.SimulationModel;

public interface SimulatorCommandContext {

    void setModel(SimulationModel model);

    void resume();

    void pause();

}
