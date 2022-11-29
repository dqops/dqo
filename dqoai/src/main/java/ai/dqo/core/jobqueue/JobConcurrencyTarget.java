package ai.dqo.core.jobqueue;

import java.util.Objects;

/**
 * Identifies a target object on which a limit of concurrent jobs is configured. This object is used as a key in a dictionary
 * that maintains the limits.
 */
public class JobConcurrencyTarget {
    private final ConcurrentJobType jobType;
    private final Object target;

    /**
     * Creates a job concurrency target object.
     * @param jobType Job type.
     * @param target Hierarchy id that identifies the object that has a concurrency limit.
     */
    public JobConcurrencyTarget(ConcurrentJobType jobType, Object target) {
        this.jobType = jobType;
        this.target = target;
    }

    /**
     * Returns the job type on which a concurrency limit is imposed.
     * @return Job type with a concurrency limit.
     */
    public ConcurrentJobType getJobType() {
        return jobType;
    }

    /**
     * Returns the hierarchy node ID of the node that has a concurrency limit.
     * @return Concurrency target hierarchy id.
     */
    public Object getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobConcurrencyTarget that = (JobConcurrencyTarget) o;

        if (jobType != that.jobType) return false;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        int result = jobType != null ? jobType.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }
}
