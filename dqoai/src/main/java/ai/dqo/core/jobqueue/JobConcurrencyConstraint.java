package ai.dqo.core.jobqueue;

/**
 * Configuration of the concurrency limit (degree of parallelism) for each job, configured at a level that has a concurrency limit.
 */
public class JobConcurrencyConstraint {
    private final JobConcurrencyTarget concurrencyTarget;
    private Integer concurrentJobsLimit;

    /**
     * Creates a concurrency constraint.
     * @param concurrencyTarget Concurrency target that identifies the job type and the node on which the limit is appliede.
     * @param concurrentJobsLimit Number of concurrent jobs that could be executed on that node.
     */
    public JobConcurrencyConstraint(JobConcurrencyTarget concurrencyTarget, Integer concurrentJobsLimit) {
        this.concurrencyTarget = concurrencyTarget;
        this.concurrentJobsLimit = concurrentJobsLimit;
    }

    /**
     * Returns the concurrency target.
     * @return Concurrency target.
     */
    public JobConcurrencyTarget getConcurrencyTarget() {
        return concurrencyTarget;
    }

    /**
     * Returns the number of concurrent jobs that can execute on the node.
     * @return Concurrency limit.
     */
    public Integer getConcurrentJobsLimit() {
        return concurrentJobsLimit;
    }
}
