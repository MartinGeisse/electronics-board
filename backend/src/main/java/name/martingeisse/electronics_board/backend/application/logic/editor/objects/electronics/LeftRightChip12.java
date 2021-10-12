package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.RectangularBoardObject;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.ComponentFactory;

public abstract class LeftRightChip12 extends RectangularBoardObject implements Wirable, ComponentFactory {

    @Override
    @JsonIgnore
    public ImmutableSet<String> getPortIds() {
        return ImmutableSet.of("L0", "L1", "L2", "L3", "L4", "L5", "R0", "R1", "R2", "R3", "R4", "R5");
    }

}
