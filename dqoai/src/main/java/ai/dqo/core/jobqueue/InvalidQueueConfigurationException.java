package ai.dqo.core.jobqueue;

/**
 * Exception thrown when the configuration of the job queue is invalid.
 */
public class InvalidQueueConfigurationException extends RuntimeException {
    public InvalidQueueConfigurationException(String message) {
        super(message);
    }
}
