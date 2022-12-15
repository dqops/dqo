package ai.dqo.utils.datetime;

import ai.dqo.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when the given text was not a supported duration text, such as 10s, 10m, 5h
 */
public class InvalidDurationFormatException extends DqoRuntimeException {
    public InvalidDurationFormatException(String message) {
        super(message);
    }

    public InvalidDurationFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
