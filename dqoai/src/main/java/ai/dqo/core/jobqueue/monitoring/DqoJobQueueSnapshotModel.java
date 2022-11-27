package ai.dqo.core.jobqueue.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Returns the current snapshot of running jobs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueSnapshotModel {
    private List<DqoJobHistoryEntryModel> jobs;
    private long lastChangeId;

    /**
     * Creates a new job snapshot.
     * @param jobs List of jobs.
     * @param lastChangeId Last change id.
     */
    public DqoJobQueueSnapshotModel(List<DqoJobHistoryEntryModel> jobs, long lastChangeId) {
        this.jobs = jobs;
        this.lastChangeId = lastChangeId;
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
     * @return Last change id.
     */
    public long getLastChangeId() {
        return lastChangeId;
    }
}
