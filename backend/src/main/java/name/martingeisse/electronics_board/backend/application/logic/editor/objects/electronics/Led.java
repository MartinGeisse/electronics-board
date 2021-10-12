package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.RectangularBoardObject;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.ComponentFactory;

public class Led extends RectangularBoardObject implements Wirable, ComponentFactory {

    private final ImmutableSet<String> PORT_IDS = ImmutableSet.of("0");

    @Override
    @JsonIgnore
    public ImmutableSet<String> getPortIds() {
        return PORT_IDS;
    }

    @Override
    public void createComponents(Context context) {
        new name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.Led(
                context.getSimulationModel(),
                getId(),
                context.getNet(getId(), "0"),
                "LED " + getId()
        );
    }

}
