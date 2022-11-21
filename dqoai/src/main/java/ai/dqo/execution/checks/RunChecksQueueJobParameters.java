package ai.dqo.execution.checks;

import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Parameters object for the run checks job.
 */
public class RunChecksQueueJobParameters {
    private CheckSearchFilters checkSearchFilters;
    @JsonIgnore
    private CheckExecutionProgressListener progressListener;
    private boolean dummySensorExecution;

    /**
     * Creates a check run parameters.
     * @param checkSearchFilters Check search filters.
     * @param progressListener Progress listener to receive events during the check execution.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public RunChecksQueueJobParameters(CheckSearchFilters checkSearchFilters,
                                       CheckExecutionProgressListener progressListener,
                                       boolean dummySensorExecution) {
        this.checkSearchFilters = checkSearchFilters;
        this.progressListener = progressListener;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the checks search filters.
     * @return Check search filters.
     */
    public CheckSearchFilters getCheckSearchFilters() {
        return checkSearchFilters;
    }

    /**
     * Progress listener that should be used to run the checks.
     * @return Check progress listener.
     */
    public CheckExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummySensorExecution() {
        return dummySensorExecution;
    }
}
