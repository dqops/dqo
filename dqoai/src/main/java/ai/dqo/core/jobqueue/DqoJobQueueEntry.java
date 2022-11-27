package ai.dqo.core.jobqueue;

import java.time.Instant;

/**
 * Holder of a single DQO job on the queue.
 */
public class DqoJobQueueEntry {
    private DqoQueueJob<?> job;
    private DqoQueueJobId jobId;
    private JobConcurrencyConstraint jobConcurrencyConstraint;

    /**
     * Creates a dqo job queue entry.
     * @param job Job.
     * @param jobId Job id.
     * @param jobConcurrencyConstraint Optional job concurrency constraint retrieved from the job. It is cached for the whole time when the job is awaiting on the queue.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId, JobConcurrencyConstraint jobConcurrencyConstraint) {
        this.job = job;
        this.jobId = jobId;
        this.jobConcurrencyConstraint = jobConcurrencyConstraint;
    }

    /**
     * Creates a dqo job queue entry for a job that has no concurrency constrains (parallel execution limits).
     * @param job Job.
     * @param jobId Job id.
     */
    public DqoJobQueueEntry(DqoQueueJob<?> job, DqoQueueJobId jobId) {
        this(job, jobId, job.getConcurrencyConstraint());
    }

    /**
     * Returns the dqo job object instance.
     * @return DQO Job instance.
     */
    public DqoQueueJob<?> getJob() {
        return job;
    }

    /**
     * Returns the dqo job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns an optional job concurrency constraint that identifies the concurrency target and the DOP.
     * @return Job concurrency constraint or null, when the job has no concurrency limits.
     */
    public JobConcurrencyConstraint getJobConcurrencyConstraint() {
        return jobConcurrencyConstraint;
    }
}
