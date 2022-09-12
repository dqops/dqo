package ai.dqo.execution.checks.progress;

/**
 * Returns a correct implementation of the check run progress listener that prints the progress to the screen.
 */
public interface CheckExecutionProgressListenerProvider {
    /**
     * Returns a check execution progress listener for the requested reporting level.
     *
     * @param reportingMode Reporting level.
     * @return Check execution progress listener.
     */
    CheckExecutionProgressListener getProgressListener(CheckRunReportingMode reportingMode);
}
