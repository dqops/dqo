package ai.dqo.core.jobqueue.jobs.metadata;

/**
 * Exception thrown by {@link ImportSchemaQueueJob} when importing tables failed.
 */
public class ImportSchemaQueueJobException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ImportSchemaQueueJobException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     */
    public ImportSchemaQueueJobException(String message, Throwable cause) {
        super(message, cause);
    }
}
