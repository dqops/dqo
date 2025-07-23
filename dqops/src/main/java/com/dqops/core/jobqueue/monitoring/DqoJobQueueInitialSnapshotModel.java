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

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import com.dqops.utils.docs.generators.GeneratorUtility;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Returns the current snapshot of running jobs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DqoJobQueueInitialSnapshotModel {
    private List<DqoJobHistoryEntryModel> jobs;
    private CloudSynchronizationFoldersStatusModel folderSynchronizationStatus;
    private long lastSequenceNumber;

    /**
     * Creates a new job snapshot.
     * @param jobs List of jobs.
     * @param folderSynchronizationStatus Current folder synchronization status.
     * @param lastSequenceNumber Last change id (sequence number).
     */
    public DqoJobQueueInitialSnapshotModel(List<DqoJobHistoryEntryModel> jobs,
                                           CloudSynchronizationFoldersStatusModel folderSynchronizationStatus,
                                           long lastSequenceNumber) {
        this.jobs = jobs;
        this.folderSynchronizationStatus = folderSynchronizationStatus;
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
     * Returns the current DQOps Cloud folder synchronization status.
     * @return Folder synchronization status.
     */
    public CloudSynchronizationFoldersStatusModel getFolderSynchronizationStatus() {
        return folderSynchronizationStatus;
    }

    /**
     * Returns the last change id. This value must be used in a follow-up parked rest api call to get changes after this change id.
     * @return Last change id (sequence number).
     */
    public long getLastSequenceNumber() {
        return lastSequenceNumber;
    }

    public static class DqoJobQueueInitialSnapshotModelSampleFactory implements SampleValueFactory<DqoJobQueueInitialSnapshotModel> {
        @Override
        public DqoJobQueueInitialSnapshotModel createSample() {
            return new DqoJobQueueInitialSnapshotModel(
                    SampleListUtility.generateList(DqoJobHistoryEntryModel.class, 0),
                    new CloudSynchronizationFoldersStatusModel(),
                    SampleLongsRegistry.getSequenceNumber()
            );
        }
    }
}
