package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.PointShapedBoardObject;

public class AuxiliaryWireNode extends PointShapedBoardObject implements Wirable {

    private final ImmutableSet<String> PORT_IDS = ImmutableSet.of("0");

    @Override
    @JsonIgnore
    public ImmutableSet<String> getPortIds() {
        return PORT_IDS;
    }

}
