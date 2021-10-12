package name.martingeisse.electronics_board.backend.application.logic.editor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import name.martingeisse.electronics_board.backend.application.logic.editor.events.*;
import name.martingeisse.electronics_board.backend.application.logic.editor.snapshot.MutableBoardSnapshot;

/**
 * All subclasses must be JSONable.
 *
 * For now, we keep the subclass type-id-to-class mapping hardcoded here because we don't want to expose class names,
 * so a more flexible system will be more complex.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateObjectEventBody.class, name = "CreateObject"),
        @JsonSubTypes.Type(value = DeleteObjectEventBody.class, name = "DeleteObject"),
        @JsonSubTypes.Type(value = MoveObjectEventBody.class, name = "MoveObject"),
        @JsonSubTypes.Type(value = ResizeObjectEventBody.class, name = "ResizeObject"),
        @JsonSubTypes.Type(value = ChangeLabelTextEventBody.class, name = "ChangeLabelText"),
        @JsonSubTypes.Type(value = ConfigureClockSourceEventBody.class, name = "ConfigureClockSource"),
        @JsonSubTypes.Type(value = ClearBoardEventBody.class, name = "ClearBoard"),
        @JsonSubTypes.Type(value = BoardSnapshotEventBody.class, name = "BoardSnapshot"),
})
public abstract class EditEventBody {

    public EditEventBody transformIncomingEventBody(EditEventBody otherBody) {
        return otherBody;
    }

    public abstract void apply(MutableBoardSnapshot snapshot);

}
