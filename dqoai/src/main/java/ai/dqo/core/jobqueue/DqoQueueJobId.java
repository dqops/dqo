package ai.dqo.core.jobqueue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Identifies a single job.
 */
public class DqoQueueJobId {
    private UUID jobUuid;
    private List<UUID> parentJobs;
    private Instant createdAt;

    /**
     * Creates a new job id.
     */
    public DqoQueueJobId() {
    }

    /**
     * Creates a new job id, assigning a new ID. The job creation time is now.
     * @return New job id.
     */
    public static DqoQueueJobId createNew() {
        DqoQueueJobId dqoQueueJobId = new DqoQueueJobId();
        dqoQueueJobId.jobUuid = UUID.randomUUID();
        dqoQueueJobId.createdAt = Instant.now();

        return dqoQueueJobId;
    }

    /**
     * Returns a unique job ID (UUID) that was assigned to the job.
     * @return Unique job id (UUID).
     */
    public UUID getJobUuid() {
        return jobUuid;
    }

    /**
     * Sets the job UUID. Should be used only for deserialization.
     * @param jobUuid Job UUID.
     */
    public void setJobUuid(UUID jobUuid) {
        this.jobUuid = jobUuid;
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
    public List<UUID> getParentJobs() {
        return parentJobs;
    }

    /**
     * Sets a list orf parent job ids. Should be used only for deserialization.
     * @param parentJobs List of parent job ids.
     */
    public void setParentJobs(List<UUID> parentJobs) {
        this.parentJobs = parentJobs;
    }

    /**
     * Registers a parent job id. If a parent job is cancelled, we can find all child jobs and also cancel them.
     * @param parentJobId Job id of a parent job.
     */
    public void addParentJobId(UUID parentJobId) {
        if (this.parentJobs == null) {
            this.parentJobs = new ArrayList<>();
        }

        this.parentJobs.add(parentJobId);
    }
}
