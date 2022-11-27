package ai.dqo.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Identifies a single job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoQueueJobId implements Comparable<DqoQueueJobId> {
    private long jobId;
    private List<Long> parentJobs;
    private Instant createdAt;

    /**
     * Creates a new job id.
     */
    public DqoQueueJobId() {
        this.createdAt = Instant.now();
    }

    /**
     * Creates a new job, given a job id.
     * @param jobId Job id.
     */
    public DqoQueueJobId(long jobId) {
        this();
        this.jobId = jobId;
    }

    /**
     * Returns a unique job ID  that was assigned to the job.
     * @return Unique job id.
     */
    public long getJobId() {
        return jobId;
    }

    /**
     * Sets the job id. Should be used only for deserialization.
     * @param jobId Job id.
     */
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the timestamp when the job was created.
     * @return Job created timestamp.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created timestamp. Should be used only for deserialization.
     * @param createdAt Created at timestamp.
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a list of parent job ids.
     * @return List of parent job ids or null, when no parent jobs are present.
     */
    public List<Long> getParentJobs() {
        return parentJobs;
    }

    /**
     * Sets a list orf parent job ids. Should be used only for deserialization.
     * @param parentJobs List of parent job ids.
     */
    public void setParentJobs(List<Long> parentJobs) {
        this.parentJobs = parentJobs;
    }

    /**
     * Registers a parent job id. If a parent job is cancelled, we can find all child jobs and also cancel them.
     * @param parentJobId Job id of a parent job.
     */
    public void addParentJobId(Long parentJobId) {
        if (this.parentJobs == null) {
            this.parentJobs = new ArrayList<>();
        }

        this.parentJobs.add(parentJobId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoQueueJobId that = (DqoQueueJobId) o;

        return jobId == that.jobId;
    }

    @Override
    public int hashCode() {
        return (int) (jobId ^ (jobId >>> 32));
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoQueueJobId o) {
        return Long.compare(this.jobId, o.jobId);
    }

    @Override
    public String toString() {
        return "DqoQueueJobId{" +
                "jobId=" + jobId +
                ", createdAt=" + createdAt +
                '}';
    }
}
