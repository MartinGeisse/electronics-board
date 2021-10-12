package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.Item;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;

import java.util.ArrayList;
import java.util.List;

public final class Net extends Item {

    private final List<Driver> drivers;
    private final List<Listener> listeners;
    private LogicValue value;
    private LogicValue nextValue;
    private long cycleWhenValueChangeEventFired;

    public Net(ItemBasedSimulationModel simulationModel) {
        super(simulationModel);
        this.drivers = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.value = LogicValue.HIGH_IMPEDANCE;
        this.nextValue = this.value;
        this.cycleWhenValueChangeEventFired = -1;
    }

    void registerDriver(Driver driver) {
        drivers.add(driver);
    }

    void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public LogicValue getValue() {
        return value;
    }

    void onDriverValueChanged() {
        nextValue = LogicValue.HIGH_IMPEDANCE;
        for (Driver driver : drivers) {
            nextValue = nextValue.shareNetWith(driver.getValue());
        }
        if (nextValue != value && cycleWhenValueChangeEventFired != getSimulationModel().getCycleIndex()) {
            // We must not schedule multiple events! If we did, an event could not tell if it is the first event and
            // perform notification of all listeners. It cannot use (nextValue != value) to determine that since
            // by the time the second event gets processed, nextValue could have changed again.
            cycleWhenValueChangeEventFired = getSimulationModel().getCycleIndex();
            fire(this::updateValue, 0);
        }
    }

    private void updateValue() {
        if (value != nextValue) {
            LogicValue oldValue = value;
            value = nextValue;
            for (Listener listener : listeners) {
                listener.onNetValueChanged(this, oldValue, value);
            }
        }
    }

    public interface Listener {
        // TODO consider providing a SensorListener that does not distinguish X and Z values -- but first check
        // if this is really needed in practice
        void onNetValueChanged(Net net, LogicValue oldValue, LogicValue newValue);
    }

}
