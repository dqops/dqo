/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.monitoring;

import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Job history snapshot model that returns only changes after a given change sequence.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueIncrementalSnapshotModel {
    private List<DqoJobChangeModel> jobChanges;
    private CloudSynchronizationFoldersStatusModel folderSynchronizationStatus;
    private long lastSequenceNumber;

    /**
     * Creates an incremental change history model.
     * @param jobChanges List of changes.
     * @param folderSynchronizationStatus Current folder synchronization status.
     * @param lastSequenceNumber Last change id (sequence number).
     */
    public DqoJobQueueIncrementalSnapshotModel(List<DqoJobChangeModel> jobChanges,
                                               CloudSynchronizationFoldersStatusModel folderSynchronizationStatus,
                                               long lastSequenceNumber) {
        this.jobChanges = jobChanges;
        this.lastSequenceNumber = lastSequenceNumber;
        this.folderSynchronizationStatus = folderSynchronizationStatus;
    }

    /**
     * A list of changes to the history.
     * @return List of changes.
     */
    public List<DqoJobChangeModel> getJobChanges() {
        return jobChanges;
    }

    /**
     * Returns the current folder synchronization status.
     * @return Current folder synchronization status.
     */
    public CloudSynchronizationFoldersStatusModel getFolderSynchronizationStatus() {
        return folderSynchronizationStatus;
    }

    /**
     * Last change id (sequence number). Use this id in the next call to get more changes.
     * @return Last change id.
     */
    public long getLastSequenceNumber() {
        return lastSequenceNumber;
    }

    public static class DqoJobQueueIncrementalSnapshotModelSampleFactory implements SampleValueFactory<DqoJobQueueIncrementalSnapshotModel> {
        @Override
        public DqoJobQueueIncrementalSnapshotModel createSample() {
            return new DqoJobQueueIncrementalSnapshotModel(
                    SampleListUtility.generateList(DqoJobHistoryEntryModel.class, 0),
                    new CloudSynchronizationFoldersStatusModel(),
                    SampleLongsRegistry.getSequenceNumber()
            );
        }
    }
}
