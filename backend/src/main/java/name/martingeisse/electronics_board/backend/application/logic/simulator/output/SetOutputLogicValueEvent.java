package name.martingeisse.electronics_board.backend.application.logic.simulator.output;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.items.LogicValue;

public final class SetOutputLogicValueEvent extends BoardObjectOutputEvent {

    private final LogicValue value;

    public SetOutputLogicValueEvent(String boardObjectId, LogicValue value) {
        super(boardObjectId);
        this.value = value;
    }

    public LogicValue getValue() {
        return value;
    }

}
