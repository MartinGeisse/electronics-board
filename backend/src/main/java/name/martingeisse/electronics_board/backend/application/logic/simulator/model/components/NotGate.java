package name.martingeisse.electronics_board.backend.application.logic.simulator.model.components;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Component;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Driver;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.LogicValue;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class NotGate extends Component {

    private final Net inputNet;
    private final Driver driver;

    public NotGate(ItemBasedSimulationModel simulationModel, String boardObjectId, Net inputNet, Net outputNet) {
        super(simulationModel, boardObjectId);
        this.inputNet = inputNet;
        listenTo(inputNet);
        this.driver = new Driver(outputNet);
    }

    @Override
    public void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue) {
        driver.setValueAfter(inputNet.getValue().not(), GateConstants.STANDARD_GATE_DELAY_TICKS);
    }

}
