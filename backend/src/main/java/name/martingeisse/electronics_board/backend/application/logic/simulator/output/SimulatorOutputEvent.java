package name.martingeisse.electronics_board.backend.application.logic.simulator.output;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * All subclasses must be JSON-serializable, but de-serialization from JSON is not needed.
 *
 * For now, we keep the subclass type-id-to-class mapping hardcoded here because we don't want to expose class names,
 * so a more flexible system will be more complex.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResetOutputStateEvent.class, name = "ResetOutputState"),
        @JsonSubTypes.Type(value = SetOutputLogicValueEvent.class, name = "SetOutputLogicValue"),
})
public abstract class SimulatorOutputEvent {
}
