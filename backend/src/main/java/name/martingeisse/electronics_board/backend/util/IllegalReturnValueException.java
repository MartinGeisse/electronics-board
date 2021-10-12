package name.martingeisse.electronics_board.backend.util;

import javax.annotation.Nonnull;

/**
 * Originally copied from GitHub repo MartinGeisse/trading-game
 *
 * This exception type indicates that a method returned a value that it is not supposed to return. It is the
 * equivalent to {@link IllegalArgumentException} for return values.
 */
public class IllegalReturnValueException extends RuntimeException {

    /**
     * Constructor.
     */
    public IllegalReturnValueException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public IllegalReturnValueException(@Nonnull String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the exception that caused this exception
     */
    public IllegalReturnValueException(@Nonnull Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the exception that caused this exception
     */
    public IllegalReturnValueException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

}