package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.Item;

/**
 * A {@link Driver} pushes a {@link LogicValue} into a {@link Net}. A driver can only drive a single net to simplify
 * things -- use multiple drivers to drive multiple, otherwise independent nets.
 */
public final class Driver extends Item {

    private final Net net;
    private LogicValue initialValue;
    private LogicValue value;
    private DriverChangeEvent latestChangeEvent;

    public Driver(Net net) {
        this(net, LogicValue.HIGH_IMPEDANCE);
    }

    public Driver(Net net, LogicValue initialValue) {
        super(net.getSimulationModel());
        this.net = net;
        this.value = LogicValue.HIGH_IMPEDANCE;
        this.initialValue = initialValue;
        this.latestChangeEvent = null;
        net.registerDriver(this);
    }

    @Override
    protected void initializeSimulation() {
        // We cannot just initialize this.value to the initialValue because then no change event would be generated,
        // and the initialValue would not propagate through logic gates.
        setValue(initialValue);
    }

    public Net getNet() {
        return net;
    }

    public LogicValue getValue() {
        return value;
    }

    public void setValue(LogicValue value) {
        disarmLatestChangeEvent();
        this.value = value;
        net.onDriverValueChanged();
    }

    public void setValueAfter(LogicValue value, long delay) {
        if (delay == 0) {
            setValue(value);
        } else {
            disarmLatestChangeEvent();
            DriverChangeEvent event = new DriverChangeEvent();
            event.newValue = value;
            fire(event, delay);
        }
    }

    private void disarmLatestChangeEvent() {
        if (latestChangeEvent != null) {
            latestChangeEvent.newValue = null;
            latestChangeEvent = null;
        }
    }

    // when such an event gets overridden later by another driver change, we set newValue to null to "disarm" the event
    private final class DriverChangeEvent implements Runnable {

        LogicValue newValue;

        @Override
        public void run() {
            if (newValue != null) {
                setValue(newValue);
            }
        }

    }

}
