package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.Item;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;

public abstract class Component extends Item implements Net.Listener {

    private final String boardObjectId;

    public Component(ItemBasedSimulationModel simulationModel, String boardObjectId) {
        super(simulationModel);
        this.boardObjectId = boardObjectId;
    }

    public String getBoardObjectId() {
        return boardObjectId;
    }

    protected void listenTo(Net... nets) {
        for (Net net : nets) {
            net.registerListener(this);
        }
    }

}
