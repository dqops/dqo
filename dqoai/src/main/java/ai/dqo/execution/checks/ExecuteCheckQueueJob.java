package ai.dqo.execution.checks;

import ai.dqo.core.jobqueue.BaseDqoQueueJob;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;

/**
 * Execute checks queue job.
 */
public class ExecuteCheckQueueJob extends BaseDqoQueueJob<CheckExecutionSummary> {
    private CheckExecutionService checkExecutionService;
    private CheckExecutionContext checkExecutionContext;
    private CheckSearchFilters checkSearchFilters;
    private CheckExecutionProgressListener progressListener;
    private boolean dummySensorExecution;

    public ExecuteCheckQueueJob(
            CheckExecutionService checkExecutionService,
            CheckExecutionContext checkExecutionContext,
            CheckSearchFilters checkSearchFilters,
            CheckExecutionProgressListener progressListener,
            boolean dummySensorExecution) {
        this.checkExecutionService = checkExecutionService;
        this.checkExecutionContext = checkExecutionContext;
        this.checkSearchFilters = checkSearchFilters;
        this.progressListener = progressListener;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public CheckExecutionSummary onExecute() {
        CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecks(
                checkExecutionContext, checkSearchFilters, this.progressListener, this.dummySensorExecution);
        return checkExecutionSummary;
    }
}
