package ai.dqo.core.jobqueue;

/**
 * Exception thrown when the job queue cannot accept a job.
 */
public class JobQueuePushFailedException extends RuntimeException {
    public JobQueuePushFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
