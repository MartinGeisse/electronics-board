package name.martingeisse.electronics_board.backend.application.logic.editor;

public final class EditEvent {

    private final long id;
    private final EditEventBody body;

    public EditEvent(long id, EditEventBody body) {
        this.id = id;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public EditEventBody getBody() {
        return body;
    }

}
