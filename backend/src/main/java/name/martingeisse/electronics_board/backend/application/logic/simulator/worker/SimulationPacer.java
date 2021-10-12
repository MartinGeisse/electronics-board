package name.martingeisse.electronics_board.backend.application.logic.simulator.worker;

/**
 * Real time gets converted to simulated time based on "chunks". A chunk is defined by its length in both real
 * milliseconds and simulated ticks. Each time a chunk has passed in real time, it gets added to a backlog of
 * simulated time that has to be processed by the simulation model.
 *
 * The relation between real and simulated time is therefore defined by a fraction-of-integers,
 * (realMillisecondsPerChunk / simulatedTicksPerChunk). Note though that only full chunks will be transferred to the
 * backlog, so making a chunk too large will cause the simulation to run in "choppy bursts".
 */
public final class SimulationPacer {

    private int realMillisecondsPerChunk = 100;
    private int simulatedTicksPerChunk = 1;
    private int maximumBacklogTicks = 10;
    private long previousRealTimestamp;
    private long backlogTicks;

    public SimulationPacer() {
        reset();
    }

    public int getRealMillisecondsPerChunk() {
        return realMillisecondsPerChunk;
    }

    public void setRealMillisecondsPerChunk(int realMillisecondsPerChunk) {
        this.realMillisecondsPerChunk = realMillisecondsPerChunk;
    }

    public int getSimulatedTicksPerChunk() {
        return simulatedTicksPerChunk;
    }

    public void setSimulatedTicksPerChunk(int simulatedTicksPerChunk) {
        this.simulatedTicksPerChunk = simulatedTicksPerChunk;
    }

    public int getMaximumBacklogTicks() {
        return maximumBacklogTicks;
    }

    public void setMaximumBacklogTicks(int maximumBacklogTicks) {
        this.maximumBacklogTicks = maximumBacklogTicks;
    }

    public void reset() {
        previousRealTimestamp = System.currentTimeMillis();
        backlogTicks = 0;
    }

    void senseRealTime() {
        long now = System.currentTimeMillis();
        long chunks = (now - previousRealTimestamp) / realMillisecondsPerChunk;
        previousRealTimestamp += chunks * realMillisecondsPerChunk; // less than (now) because only complete chunks are added
        backlogTicks += chunks * simulatedTicksPerChunk;
    }

    public long getBacklogTicks() {
        return backlogTicks;
    }

    void markSimulatedTimePassed(long passedTicks) {
        backlogTicks = Math.max(0, backlogTicks - passedTicks);
    }

    long convertTicksToMilliseconds(long ticks) {
        return ticks * realMillisecondsPerChunk / simulatedTicksPerChunk;
    }

}
