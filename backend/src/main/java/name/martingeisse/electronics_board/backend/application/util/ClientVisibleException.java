package name.martingeisse.electronics_board.backend.application.util;

/**
 * An exception that gets returned to the client. All exceptions that do not inherit from this exception will be
 * hidden behind more generic error messages, typically "500 internal server error".
 *
 * The stack trace will NOT be returned to the client, but is included anyway so it is available for logging etc.
 */
public class ClientVisibleException extends RuntimeException {

    public ClientVisibleException() {
    }

    public ClientVisibleException(String message) {
        super(message);
    }

    public ClientVisibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientVisibleException(Throwable cause) {
        super(cause);
    }

}
