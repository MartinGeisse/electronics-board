package name.martingeisse.electronics_board.backend.application.logic.editor.objects;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;

public final class Label extends BoardObject {

    private int x;
    private int y;
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void moveBy(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

}
