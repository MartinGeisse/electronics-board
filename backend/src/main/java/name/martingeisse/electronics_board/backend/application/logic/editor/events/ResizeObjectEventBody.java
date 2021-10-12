package name.martingeisse.electronics_board.backend.application.logic.editor.events;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.EditEventBody;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.RectangularBoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

public final class ResizeObjectEventBody extends EditEventBody {

    private String id;
    private int dx;
    private int dy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    @Override
    public void apply(MutableBoardSnapshot snapshot) {
        BoardObject object = snapshot.getObject(id);
        if (object instanceof RectangularBoardObject) {
            RectangularBoardObject rectangularBoardObject = (RectangularBoardObject)object;
            int width = rectangularBoardObject.getWidth() + dx;
            if (width < RectangularBoardObject.MINIMUM_WIDTH) {
                width = RectangularBoardObject.MINIMUM_WIDTH;
            }
            rectangularBoardObject.setWidth(width);
        }
    }

}
