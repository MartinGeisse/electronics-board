package name.martingeisse.electronics_board.backend.application.logic.base;

import name.martingeisse.electronics_board.backend.application.util.ClientVisibleException;

public class LogicException extends ClientVisibleException {

    public LogicException() {
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }

}
