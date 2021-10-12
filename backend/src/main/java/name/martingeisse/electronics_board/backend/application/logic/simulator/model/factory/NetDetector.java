package name.martingeisse.electronics_board.backend.application.logic.simulator.model.factory;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.Wirable;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.WireEdge;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.WireNodeId;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class allows to add nodes, mark nodes as neighbors, then build nets from them.
 *
 * There is a reason to add all nodes: We want to build a net even for an isolated node that is not connected to
 * any edges. Because of this, we can simplify the code by demanding that all nodes be added first -- so marking two
 * nodes as neighbors can assume that both nodes have been added before.
 */
public final class NetDetector {

    private final Map<String, Set<String>> map = new HashMap<>();

    public static String getInternalNodeName(String boardObjectId, String portId) {
        return boardObjectId + '/' + portId;
    }

    public static String getInternalNodeName(BoardObject boardObject, String portId) {
        return getInternalNodeName(boardObject.getId(), portId);
    }

    public static String getInternalNodeName(WireNodeId nodeId) {
        return getInternalNodeName(nodeId.getObjectId(), nodeId.getPortId());
    }

    public void addNode(String internalNodeName) {
        map.put(internalNodeName, new HashSet<>());
    }

    public void addNodesFrom(MutableBoardSnapshot snapshot) {
        for (BoardObject boardObject : snapshot.getObjects()) {
            if (boardObject instanceof Wirable) {
                Wirable wirable = (Wirable)boardObject;
                for (String portId : wirable.getPortIds()) {
                    addNode(getInternalNodeName(boardObject, portId));
                }
            }
        }
    }

    public void markNeighbors(String x, String y) {
        map.get(x).add(y);
        map.get(y).add(x);
    }

    public void markNeighborsFrom(MutableBoardSnapshot snapshot) {
        for (BoardObject boardObject : snapshot.getObjects()) {
            if (boardObject instanceof WireEdge) {
                WireEdge wireEdge = (WireEdge)boardObject;
                markNeighbors(getInternalNodeName(wireEdge.getNode1()), getInternalNodeName(wireEdge.getNode2()));
            }
        }
    }

    public Set<String> detectAndRemoveNet() {
        if (map.isEmpty()) {
            return null;
        }
        Set<String> netNodeIds = new HashSet<>();
        Set<String> pendingNodeIds = new HashSet<>();
        pendingNodeIds.add(map.keySet().iterator().next());
        while (!pendingNodeIds.isEmpty()) {
            String nodeId = pendingNodeIds.iterator().next();
            pendingNodeIds.remove(nodeId);
            if (netNodeIds.add(nodeId)) {
                pendingNodeIds.addAll(map.remove(nodeId));
            }
        }
        return netNodeIds;
    }

}
