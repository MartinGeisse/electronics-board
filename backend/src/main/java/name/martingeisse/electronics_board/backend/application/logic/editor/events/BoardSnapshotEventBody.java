package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public class BoardSnapshotEventBody extends EditEventBody {

    private BoardObject[] objects;

    public BoardObject[] getObjects() {
        return objects;
    }

    public void setObjects(BoardObject[] objects) {
        this.objects = objects;
    }

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        snapshot.clear();
        for (BoardObject object : objects) {
            snapshot.addObject(object);
        }
    }

}
