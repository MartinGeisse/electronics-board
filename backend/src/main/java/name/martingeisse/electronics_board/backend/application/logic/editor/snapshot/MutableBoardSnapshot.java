package name.martingeisse.electronics_board.backend.application.logic.editor.snapshot;

import name.martingeisse.electronics_board.backend.application.logic.editor.BoardObject;

import java.util.*;

public class MutableBoardSnapshot {

    private final Map<String, BoardObject> objects;

    public MutableBoardSnapshot() {
        this.objects = new HashMap<>();
    }

    public void addObject(BoardObject object) {
        objects.put(object.getId(), object);
    }

    public BoardObject getObject(String id) {
        return objects.get(id);
    }

    public void removeObject(String id) {
        Set<String> idsToRemove = new HashSet<>();
        idsToRemove.add(id);
        while (!idsToRemove.isEmpty()) {
            {
                Iterator<String> iterator = idsToRemove.iterator();
                id = iterator.next();
                iterator.remove();
            }
            BoardObject boardObject = objects.remove(id);
            if (boardObject == null) {
                continue;
            }
            for (BoardObject otherObject : objects.values()) {
                if (otherObject.existenceDependsOn(boardObject)) {
                    idsToRemove.add(otherObject.getId());
                }
            }
        }
    }

    public Iterable<BoardObject> getObjects() {
        return objects.values();
    }

    public void clear() {
        objects.clear();
    }

}
