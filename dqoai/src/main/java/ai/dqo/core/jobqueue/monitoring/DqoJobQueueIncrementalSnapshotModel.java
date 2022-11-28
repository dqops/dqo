package ai.dqo.core.jobqueue.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Job history snapshot model that returns only changes after a given change sequence.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueIncrementalSnapshotModel {
    private List<DqoJobChangeModel> jobChanges;
    private long lastChangeId;

    /**
     * Creates an incremental change history model.
     * @param jobChanges List of changes.
     * @param lastChangeId Last change id.
     */
    public DqoJobQueueIncrementalSnapshotModel(List<DqoJobChangeModel> jobChanges, long lastChangeId) {
        this.jobChanges = jobChanges;
        this.lastChangeId = lastChangeId;
    }

    /**
     * A list of changes to the history.
     * @return List of changes.
     */
    public List<DqoJobChangeModel> getJobChanges() {
        return jobChanges;
    }

    /**
     * Last change id. Use this id in the next call to get more changes.
     * @return Last change id.
     */
    public long getLastChangeId() {
        return lastChangeId;
    }
}
