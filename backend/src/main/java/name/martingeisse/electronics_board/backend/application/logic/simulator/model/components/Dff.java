package name.martingeisse.electronics_board.backend.application.logic.simulator.model.components;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Component;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Driver;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.LogicValue;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Net;

public final class Dff extends Component {

    private final Net clockNet;
    private final Net clockEnableNet;
    private final Net dataInputNet;
    private final Driver dataOutputDriver;

    public Dff(ItemBasedSimulationModel simulationModel,
               String boardObjectId,
               Net clockNet,
               Net clockEnableNet,
               Net dataInputNet,
               Net dataOutputNet) {
        super(simulationModel, boardObjectId);
        this.clockNet = clockNet;
        this.clockEnableNet = clockEnableNet;
        this.dataInputNet = dataInputNet;
        this.dataOutputDriver = new Driver(dataOutputNet, LogicValue.LOW);
        listenTo(clockNet, clockEnableNet, dataInputNet);
    }

    @Override
    public void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue) {
        if (net == clockNet && oldValue == LogicValue.LOW && newValue == LogicValue.HIGH) {
            if (clockEnableNet.getValue() != LogicValue.LOW) {
                dataOutputDriver.setValueAfter(dataInputNet.getValue().sense(), GateConstants.REGISTER_CLOCK_TO_OUT_DELAY_TICKS);
            }
        }
    }

}
