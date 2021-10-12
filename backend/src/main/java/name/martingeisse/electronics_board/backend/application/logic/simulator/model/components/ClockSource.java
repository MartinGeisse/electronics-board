package name.martingeisse.electronics_board.backend.application.logic.simulator.model.components;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.*;

public final class ClockSource extends Component {

    private final Driver driver;
    private LogicValue value = LogicValue.LOW;

    public ClockSource(ItemBasedSimulationModel simulationModel, String boardObjectId, Net outputNet, long initialDelay, long period) {
        super(simulationModel, boardObjectId);
        this.driver = new Driver(outputNet);
        new IntervalItem(simulationModel, initialDelay, period, () -> {
            value = value.not();
            driver.setValue(value);
            System.out.println("clock: " + value);
        });
    }

    @Override
    public void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue) {
    }

}
