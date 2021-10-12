package name.martingeisse.electronics_board.backend.application.technical.event_store;

import com.google.common.collect.ImmutableList;
import name.martingeisse.electronics_board.backend.application.logic.base.NotFoundException;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEvent;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MemoryEventStore implements EventStore {

    private final ConcurrentLinkedQueue<EditEvent> events = new ConcurrentLinkedQueue<>();

    @Override
    public ImmutableList<EditEvent> getAllEvents() {
        return ImmutableList.copyOf(events);
    }

    @Override
    public ImmutableList<EditEvent> getAllEventsSinceId(long id) {
        if (id < 0 || id >= events.size()) {
            throw new NotFoundException();
        }
        // return ImmutableList.copyOf(events.subList((int)id, events.size()));
        System.out.println("TODO!!!!");
        throw new UnsupportedOperationException("TODO"); // TODO
    }

    @Override
    public void insertEvent(EditEventBody body) {
        events.add(new EditEvent(events.size(), body));
    }

}
