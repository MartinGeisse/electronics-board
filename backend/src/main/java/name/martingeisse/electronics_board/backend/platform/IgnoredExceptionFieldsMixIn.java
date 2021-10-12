package name.martingeisse.electronics_board.backend.platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class exists as a container for Jackson annotations to be applied to exception classes. We cannot write
 * these annotations into the exception classes themselves because they must affect methods that are final in the
 * Throwable base class.
 */
public abstract class IgnoredExceptionFieldsMixIn {

    @JsonProperty("error")
    public abstract String getMessage();

    @JsonIgnore
    public abstract String getLocalizedMessage();

    @JsonIgnore
    public abstract Throwable getCause();

    @JsonIgnore
    public abstract StackTraceElement[] getStackTrace();

    @JsonIgnore
    public abstract Throwable[] getSuppressed();

}
