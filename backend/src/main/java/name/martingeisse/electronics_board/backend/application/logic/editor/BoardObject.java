package name.martingeisse.electronics_board.backend.application.logic.editor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.Dot;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.Label;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.AuxiliaryWireNode;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.ClockSource;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.Led;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.WireEdge;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.binary.*;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.unary.BufferChip;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.gate.unary.InverterChip;
import name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics.register.FourBitDffChip;

/**
 * All subclasses must be JSONable.
 *
 * For now, we keep the subclass type-id-to-class mapping hardcoded here because we don't want to expose class names,
 * so a more flexible system will be more complex.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({

        @JsonSubTypes.Type(value = Dot.class, name = "Dot"),
        @JsonSubTypes.Type(value = Label.class, name = "Label"),

        @JsonSubTypes.Type(value = ClockSource.class, name = "ClockSource"),
        @JsonSubTypes.Type(value = Led.class, name = "Led"),
        @JsonSubTypes.Type(value = WireEdge.class, name = "WireEdge"),
        @JsonSubTypes.Type(value = AuxiliaryWireNode.class, name = "AuxiliaryWireNode"),

        @JsonSubTypes.Type(value = BufferChip.class, name = "BufferChip"),
        @JsonSubTypes.Type(value = InverterChip.class, name = "InverterChip"),

        @JsonSubTypes.Type(value = AndGateChip.class, name = "AndGateChip"),
        @JsonSubTypes.Type(value = OrGateChip.class, name = "OrGateChip"),
        @JsonSubTypes.Type(value = XorGateChip.class, name = "XorGateChip"),
        @JsonSubTypes.Type(value = NandGateChip.class, name = "NandGateChip"),
        @JsonSubTypes.Type(value = NorGateChip.class, name = "NorGateChip"),
        @JsonSubTypes.Type(value = XnorGateChip.class, name = "XnorGateChip"),

        @JsonSubTypes.Type(value = FourBitDffChip.class, name = "FourBitDffChip"),
})
public abstract class BoardObject {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract void moveBy(int dx, int dy);

    public boolean existenceDependsOn(BoardObject other) {
        return false;
    }

}
