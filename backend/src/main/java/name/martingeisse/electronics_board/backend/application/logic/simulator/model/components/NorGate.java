package name.martingeisse.electronics_board.backend.application.logic.simulator.model.components;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Component;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Driver;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.LogicValue;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class NorGate extends Component {

    private final Net net1;
    private final Net net2;
    private final Driver driver;

    public NorGate(ItemBasedSimulationModel simulationModel, String boardObjectId, Net net1, Net net2, Net outputNet) {
        super(simulationModel, boardObjectId);
        this.net1 = net1;
        this.net2 = net2;
        listenTo(net1, net2);
        this.driver = new Driver(outputNet);
    }

    @Override
    public void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue) {
        driver.setValueAfter(net1.getValue().nor(net2.getValue()), GateConstants.STANDARD_GATE_DELAY_TICKS);
    }

}
