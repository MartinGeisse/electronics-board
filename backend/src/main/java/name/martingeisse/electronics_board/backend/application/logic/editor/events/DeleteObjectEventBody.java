package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public final class DeleteObjectEventBody extends EditEventBody {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        snapshot.removeObject(id);
    }

}
