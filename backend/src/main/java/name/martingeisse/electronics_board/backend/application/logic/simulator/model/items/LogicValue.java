package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

public enum LogicValue {
    HIGH,
    LOW,
    UNKNOWN,
    HIGH_IMPEDANCE;

    public LogicValue shareNetWith(LogicValue other) {
        if (other == this) {
            return this;
        } else if (this == HIGH_IMPEDANCE) {
            return other;
        } else if (other == HIGH_IMPEDANCE) {
            return this;
        } else {
            return UNKNOWN;
        }
    }

    public LogicValue sense() {
        return this == HIGH_IMPEDANCE ? UNKNOWN : this;
    }

    public LogicValue not() {
        return (this == HIGH ? LOW : this == LOW ? HIGH : UNKNOWN);
    }

    public LogicValue and(LogicValue other) {
        if (this == LOW || other == LOW) {
            return LOW;
        } else if (this == HIGH && other == HIGH) {
            return HIGH;
        } else {
            return UNKNOWN;
        }
    }

    public LogicValue nand(LogicValue other) {
        if (this == LOW || other == LOW) {
            return HIGH;
        } else if (this == HIGH && other == HIGH) {
            return LOW;
        } else {
            return UNKNOWN;
        }
    }

    public LogicValue or(LogicValue other) {
        if (this == HIGH || other == HIGH) {
            return HIGH;
        } else if (this == LOW && other == LOW) {
            return LOW;
        } else {
            return UNKNOWN;
        }
    }

    public LogicValue nor(LogicValue other) {
        if (this == HIGH || other == HIGH) {
            return LOW;
        } else if (this == LOW && other == LOW) {
            return HIGH;
        } else {
            return UNKNOWN;
        }
    }

    public LogicValue xor(LogicValue other) {
        return (this == LOW ? other.sense() : this == HIGH ? other.not() : UNKNOWN);
    }

    public LogicValue xnor(LogicValue other) {
        return (this == LOW ? other.not() : this == HIGH ? other.sense() : UNKNOWN);
    }

}
