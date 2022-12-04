package ai.dqo.core.jobqueue;

/**
 * Exception thrown when it was not possible to retrieve a result from a job that was pushed to a job queue.
 * It could be caused by an {@link InterruptedException} or an {@link java.util.concurrent.ExecutionException}.
 */
public class DqoQueueJobExecutionException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DqoQueueJobExecutionException(String message) {
        super(message);
    }

    public DqoQueueJobExecutionException(Throwable cause) {
        super(cause);
    }
}
