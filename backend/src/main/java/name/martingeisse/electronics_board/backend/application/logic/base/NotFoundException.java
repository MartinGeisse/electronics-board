package name.martingeisse.electronics_board.backend.application.logic.base;

import name.martingeisse.electronics_board.backend.application.util.ClientVisibleException;

/**
 * This is not a logic exception because IDs cannot be entered by the user, so whenever an entity cannot be
 * found, something went wrong on the technical level.
 */
public class NotFoundException extends ClientVisibleException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
