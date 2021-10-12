package name.martingeisse.electronics_board.backend.application.logic.simulator.model;

import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;
import name.martingeisse.electronics_board.backend.application.logic.simulator.worker.SimulationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;

/**
 * This object contains both the items that make up the design to be simulated, as well as the event queue that drives
 * simulation. We do not separate these two concerns because they never appear individually in this application.
 * Furthermore, items will want to schedule events during simulation, and keeping the design and event queue together
 * greatly reduces the managment overhead needed for this.
 *
 * To obtain at least some degree of reproducible behavior, this simulation implements delta-cycles in a similar
 * way as HDLs do: A "batch" of events that are scheduled for "now" is taken off the queue as a whole, then processed
 * as a whole. If this schedules new events for "now", then the simulation still processes the whole "old" batch
 * before looking at those new events. When the batch is finished, a new batch is taken and processed, again not looking
 * at further events which get scheduled for "now" by the second batch.
 *
 * No batching is done for events scheduled for later. This means, for example, that an event scheduled by the second
 * "now"-batch for one second in the future may actually be processed *before* an event scheduled by the first
 * "now"-batch for one second in the future.
 */
public final class ItemBasedSimulationModel implements SimulationModel {

    private final List<Item> items = new ArrayList<>();
    private final PriorityQueue<ScheduledEvent> eventQueue = new PriorityQueue<>();
    private Consumer<SimulatorOutputEvent> outputEventConsumer;
    private long now = 0; // total number of ticks since the simulation started
    private long cycleIndex = 0; // increases even for delta cycles, used by nets to de-bounce value changes

    // region item management

    void register(Item item) {
        items.add(item);
    }

    public Iterable<Item> getItems() {
        return items;
    }

    public <T extends Item> List<T> findItems(Class<T> itemClass) {
        List<T> result = new ArrayList<>();
        for (Item item : items) {
            if (itemClass.isInstance(item)) {
                result.add(itemClass.cast(item));
            }
        }
        return result;
    }

    // endregion

    // region simulation

    @Override
    public void setOutputEventConsumer(Consumer<SimulatorOutputEvent> consumer) {
        this.outputEventConsumer = consumer;
    }

    public long getNow() {
        return now;
    }

    public long getCycleIndex() {
        return cycleIndex;
    }

    public void initializeSimulation() {
        for (Item item : items) {
            item.initializeSimulation();
        }
    }

    public void fire(Runnable eventCallback, long ticks) {
        if (eventCallback == null) {
            throw new IllegalArgumentException("eventCallback cannot be null");
        }
        if (ticks < 0) {
            throw new IllegalArgumentException("ticks cannot be negative");
        }
        eventQueue.add(new ScheduledEvent(now + ticks, eventCallback));
    }

    public void output(SimulatorOutputEvent event) {
        outputEventConsumer.accept(event);
    }

    @Override
    public long getSkippableTicks() {
        return eventQueue.isEmpty() ? -1 : Math.max(0, eventQueue.iterator().next().when - now);
    }

    @Override
    public long step(long maximumTicks) {
        cycleIndex++;

        // Check if the event queue is empty. Note that this does not imply that the simulation has effectively ended
        // since input devices can inject new events from the outside.
        if (eventQueue.isEmpty()) {
            now += maximumTicks;
            return maximumTicks;
        }

        // determine the length of this step and return immediately if it would be larger than maximumTicks
        ScheduledEvent firstEvent = eventQueue.element();
        if (firstEvent.when - now > maximumTicks) {
            now += maximumTicks;
            return maximumTicks;
        }
        maximumTicks = firstEvent.when - now;
        now += maximumTicks;

        // build a batch of events to process
        List<ScheduledEvent> batch = new ArrayList<>();
        while (!eventQueue.isEmpty() && eventQueue.peek().when == now) {
            batch.add(eventQueue.remove());
        }

        // execute the batch
        for (ScheduledEvent event : batch) {
            event.callback.run();
        }

        // return the actual length (in ticks) of this step
        return maximumTicks;

    }

    // endregion

    private static class ScheduledEvent implements Comparable<ScheduledEvent> {

        final long when;
        final Runnable callback;

        ScheduledEvent(long when, Runnable callback) {
            this.when = when;
            this.callback = callback;
        }

        @Override
        public int compareTo(ScheduledEvent o) {
            return Long.compare(when, o.when);
        }

    }

}
