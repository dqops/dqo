package ai.dqo.execution.checks;

/**
 * Exception thrown when a single data quality check cannot be executed.
 */
public class CheckExecutionFailed extends RuntimeException {
    public CheckExecutionFailed() {
    }

    public CheckExecutionFailed(String message) {
        super(message);
    }

    public CheckExecutionFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
