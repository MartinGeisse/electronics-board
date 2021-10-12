package name.martingeisse.electronics_board.backend.application.logic.simulator.worker;

import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;

import java.util.function.Consumer;

public interface SimulationModel {

    /**
     * Sets the consumer that should receive output events from the model during simulation.
     */
    void setOutputEventConsumer(Consumer<SimulatorOutputEvent> consumer);

    /**
     * The argument is the maximum number of ticks to proceed. Returns the actual number of ticks, which may be less
     * as long as progress is made. This avoids locking up the simulator thread in a single long simulation step for
     * too long.
     */
    long step(long maximumTicks);

    /**
     * Returns the number of ticks until something interesting happens. While the simulator will run correctly if
     * this method just returns 0 all the time, it will cause the simulator to "actively wait" if nothing actually
     * happens, so this should be avoided. May return a negative number to indicate "don't know", which will cause
     * the simulator to wait for a specific real-world time instead so it doesn't lock up if some unexpected event
     * causes the return value of this method to get smaller.
     */
    long getSkippableTicks();

    /**
     * No-op model implementation.
     */
    SimulationModel NULL_MODEL = new SimulationModel() {

        @Override
        public void setOutputEventConsumer(Consumer<SimulatorOutputEvent> consumer) {
        }

        @Override
        public long getSkippableTicks() {
            return Long.MAX_VALUE;
        }

        @Override
        public long step(long maximumTicks) {
            return maximumTicks;
        }

    };

}
