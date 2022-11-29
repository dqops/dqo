package ai.dqo.core.jobqueue;

/**
 * Context object passed to a job when it is executed. Provides access to the job id.
 */
public class DqoJobExecutionContext {
    private DqoQueueJobId jobId;

    /**
     * Creates a job execution context.
     * @param jobId Job id.
     */
    public DqoJobExecutionContext(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the job id. The job id also contains job ids of its parent jobs.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
