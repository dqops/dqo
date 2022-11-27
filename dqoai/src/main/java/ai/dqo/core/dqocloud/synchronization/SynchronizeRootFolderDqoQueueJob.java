package ai.dqo.core.dqocloud.synchronization;

import ai.dqo.core.jobqueue.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQO queue job that runs synchronization with DQO Cloud in the background for one user home's root folder (sources, sensors, etc.).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SynchronizeRootFolderDqoQueueJob extends DqoQueueJob<Void> {
    private DqoCloudSynchronizationService cloudSynchronizationService;
    private SynchronizeRootFolderDqoQueueJobParameters parameters;

    /**
     * Creates a synchronization job.
     * @param cloudSynchronizationService DQO Cloud synchronization service to use (provided as a dependency).
     */
    @Autowired
    public SynchronizeRootFolderDqoQueueJob(
            DqoCloudSynchronizationService cloudSynchronizationService) {
        this.cloudSynchronizationService = cloudSynchronizationService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public SynchronizeRootFolderDqoQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(SynchronizeRootFolderDqoQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.cloudSynchronizationService.synchronizeFolder(
                this.parameters.getRootType(),
                this.parameters.getFileSystemSynchronizationListener());
        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.SYNCHRONIZE_FOLDER;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint getConcurrencyConstraint() {
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.SYNCHRONIZE_FOLDER, this.parameters.getRootType());
        return new JobConcurrencyConstraint(concurrencyTarget, 1);
    }
}
