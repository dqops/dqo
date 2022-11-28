package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.jobqueue.DqoQueueJobId;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Describes a change to the job status or the job queue (such as a new job was added).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobChangeModel implements Comparable<DqoJobChangeModel> {
    private DqoJobStatus status;
    private DqoQueueJobId jobId;
    private long changeSequence;
    private DqoJobHistoryEntryModel updatedModel;
    private Instant statusChangedAt;

    /**
     * Creates a job change model by assigning a change sequence number.
     * @param jobChange Job change entry that is copied to the new object.
     * @param changeSequence Change sequence number.
     */
    public DqoJobChangeModel(DqoJobChange jobChange, long changeSequence) {
        this.status = jobChange.getStatus();
        this.jobId = jobChange.getJobId();
        this.updatedModel = jobChange.getUpdatedModel();
        this.changeSequence = changeSequence;
        if (jobChange.getUpdatedModel() != null) {
            this.statusChangedAt = jobChange.getUpdatedModel().getStatusChangedAt();
        } else {
            this.statusChangedAt = Instant.now();
        }
    }

    /**
     * Returns the most recent job status.
     * @return Job status.
     */
    public DqoJobStatus getStatus() {
        return status;
    }

    /**
     * Returns the job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns a change sequence that is used to sort job changes.
     * @return Change sequence.
     */
    public long getChangeSequence() {
        return changeSequence;
    }

    /**
     * Returns a job model for a new job or an updated job.
     * @return Job model.
     */
    public DqoJobHistoryEntryModel getUpdatedModel() {
        return updatedModel;
    }

    /**
     * Returns the timestamp when the job was added or changed.
     * @return Job status change timestamp.
     */
    public Instant getStatusChangedAt() {
        return statusChangedAt;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull DqoJobChangeModel o) {
        return Long.compare(this.changeSequence, o.changeSequence);
    }
}
