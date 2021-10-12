package name.martingeisse.electronics_board.backend.application.technical.event_store;

import com.google.common.collect.ImmutableList;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEvent;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;

public interface EventStore {

    ImmutableList<EditEvent> getAllEvents();

    ImmutableList<EditEvent> getAllEventsSinceId(long id);

    void insertEvent(EditEventBody body);

}
