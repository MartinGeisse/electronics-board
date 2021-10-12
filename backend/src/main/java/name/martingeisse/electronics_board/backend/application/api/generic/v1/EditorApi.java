package name.martingeisse.electronics_board.backend.application.api.generic.v1;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEvent;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.technical.event_store.EventStore;

import java.util.ArrayList;
import java.util.List;

@Path("/generic/v1/editor")
public class EditorApi {

    @Inject
    private EventStore eventStore;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EditEvent> getAll(@QueryParam("sinceId") Long sinceId) {
        if (sinceId == null) {
            return new ArrayList<>(eventStore.getAllEvents());
        } else {
            return eventStore.getAllEventsSinceId(sinceId);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void addEvent(@QueryParam("parentId") long parentId, EditEventBody body) {
        // TODO transform event
        eventStore.insertEvent(body);
    }

}
