package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;

public class WireEdge extends BoardObject {

    private WireNodeId node1;
    private WireNodeId node2;

    public WireNodeId getNode1() {
        return node1;
    }

    public void setNode1(WireNodeId node1) {
        this.node1 = node1;
    }

    public WireNodeId getNode2() {
        return node2;
    }

    public void setNode2(WireNodeId node2) {
        this.node2 = node2;
    }

    @Override
    public void moveBy(int dx, int dy) {
        // cannot move the edge itself
    }

    @Override
    public boolean existenceDependsOn(BoardObject other) {
        String otherId = other.getId();
        return otherId.equals(node1.getObjectId()) || otherId.equals(node2.getObjectId());
    }

}
