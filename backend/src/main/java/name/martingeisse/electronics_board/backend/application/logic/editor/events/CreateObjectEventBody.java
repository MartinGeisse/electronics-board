package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public final class CreateObjectEventBody extends EditEventBody {

    private BoardObject object;

    public BoardObject getObject() {
        return object;
    }

    public void setObject(BoardObject object) {
        this.object = object;
    }

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        snapshot.addObject(object);
    }

}
