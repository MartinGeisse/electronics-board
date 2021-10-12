package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SetOutputLogicValueEvent;

public final class Led extends Component {

    private final String description;

    public Led(ItemBasedSimulationModel simulationModel, String boardObjectId, Net inputNet, String description) {
        super(simulationModel, boardObjectId);
        listenTo(inputNet);
        this.description = description;
    }

    @Override
    public void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue) {
        System.out.println("LED value (" + description + "): " + newValue);
        output(new SetOutputLogicValueEvent(getBoardObjectId(), newValue));
    }

}
