package ai.dqo.core.jobqueue.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Returns the current snapshot of running jobs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueInitialSnapshotModel {
    private List<DqoJobHistoryEntryModel> jobs;
    private long lastSequenceNumber;

    /**
     * Creates a new job snapshot.
     * @param jobs List of jobs.
     * @param lastSequenceNumber Last change id (sequence number).
     */
    public DqoJobQueueInitialSnapshotModel(List<DqoJobHistoryEntryModel> jobs, long lastSequenceNumber) {
        this.jobs = jobs;
        this.lastSequenceNumber = lastSequenceNumber;
    }

    /**
     * Returns a list of jobs on the queue. Finished jobs are also returned.
     * @return List of jobs.
     */
    public List<DqoJobHistoryEntryModel> getJobs() {
        return jobs;
    }

    /**
     * Returns the last change id. This value must be used in a follow-up parked rest api call to get changes after this change id.
     * @return Last change id (sequence number).
     */
    public long getLastSequenceNumber() {
        return lastSequenceNumber;
    }
}
