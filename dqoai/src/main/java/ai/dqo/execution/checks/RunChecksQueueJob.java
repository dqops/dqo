package ai.dqo.execution.checks;

import ai.dqo.core.jobqueue.DqoJobExecutionContext;
import ai.dqo.core.jobqueue.DqoQueueJob;
import ai.dqo.core.jobqueue.DqoJobType;
import ai.dqo.core.jobqueue.JobConcurrencyConstraint;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run checks queue job.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunChecksQueueJob extends DqoQueueJob<CheckExecutionSummary> {
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
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public CheckExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
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

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint getConcurrencyConstraint() {
        return null; // user can start any number of "run check" operations, the concurrency will be applied later on a table level
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
            setRunChecksParameters(parameters);
        }};
    }
}
