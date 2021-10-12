package name.martingeisse.electronics_board.backend.application.logic.editor.objects;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;

public abstract class RectangularBoardObject extends BoardObject {

    public static final int MINIMUM_WIDTH = 300;

    private int x;
    private int y;
    private int width;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void moveBy(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

}
