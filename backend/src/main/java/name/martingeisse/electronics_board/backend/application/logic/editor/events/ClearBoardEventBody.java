package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public class ClearBoardEventBody extends EditEventBody {

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        snapshot.clear();
    }

}
