package ai.dqo.execution.checks;

import ai.dqo.core.jobqueue.BaseDqoQueueJob;
import ai.dqo.core.jobqueue.DqoJobType;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run checks queue job.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunChecksQueueJob extends BaseDqoQueueJob<CheckExecutionSummary> {
    private CheckExecutionContextFactory checkExecutionContextFactory;
    private CheckExecutionService checkExecutionService;
    private RunChecksQueueJobParameters parameters;

    @Autowired
    public RunChecksQueueJob(
            CheckExecutionContextFactory checkExecutionContextFactory,
            CheckExecutionService checkExecutionService) {
        this.checkExecutionContextFactory = checkExecutionContextFactory;
        this.checkExecutionService = checkExecutionService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public RunChecksQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(RunChecksQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public CheckExecutionSummary onExecute() {
        CheckExecutionContext checkExecutionContext = this.checkExecutionContextFactory.create();
        CheckExecutionSummary checkExecutionSummary = this.checkExecutionService.executeChecks(
                checkExecutionContext,
                this.parameters.getCheckSearchFilters(),
                this.parameters.getProgressListener(),
                this.parameters.isDummySensorExecution());
        return checkExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.RUN_CHECKS;
    }
}
