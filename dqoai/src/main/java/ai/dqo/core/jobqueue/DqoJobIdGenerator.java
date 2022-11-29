package ai.dqo.core.jobqueue;

/**
 * Job ID generation service, creates increasing job ids using the current time.
 */
public interface DqoJobIdGenerator {
    /**
     * Creates a new job id, assigning a new incremental id.
     * @return New job id.
     */
    DqoQueueJobId createNewJobId();

    /**
     * Creates a next incremental id.
     * @return Next incremental id.
     */
    long generateNextIncrementalId();
}
