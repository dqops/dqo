/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.status;

import com.dqops.core.synchronization.contract.DqoRoot;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Test stub for a synchronization status tracker. Does not publish notifications.
 */
public class SynchronizationStatusTrackerStub implements SynchronizationStatusTracker {
    private Map<String, CloudSynchronizationFoldersStatusModel> domainFolderStatuses = new LinkedHashMap<>();

    /**
     * Updates the folder synchronization status.
     *
     * @param folderRoot Folder type.
     * @param newStatus  New synchronization status.
     */
    @Override
    public synchronized void changeFolderSynchronizationStatus(DqoRoot folderRoot, String dataDomain, FolderSynchronizationStatus newStatus) {
        CloudSynchronizationFoldersStatusModel currentStatus = this.domainFolderStatuses.get(dataDomain);
        if (currentStatus == null) {
            currentStatus = new CloudSynchronizationFoldersStatusModel();
            this.domainFolderStatuses.put(dataDomain, currentStatus);
        }

        CloudSynchronizationFoldersStatusModel newFolderStatus = currentStatus.clone();
        newFolderStatus.setFolderStatus(folderRoot, newStatus);
        this.domainFolderStatuses.put(dataDomain, newFolderStatus);
    }

    /**
     * Returns the current folder synchronization status for each folder that is synchronized do DQOps Cloud.
     *
     * @return Current folder synchronization status.
     */
    @Override
    public synchronized CloudSynchronizationFoldersStatusModel getCurrentSynchronizationStatus(String dataDomain) {
        CloudSynchronizationFoldersStatusModel currentStatus = this.domainFolderStatuses.get(dataDomain);
        if (currentStatus == null) {
            currentStatus = new CloudSynchronizationFoldersStatusModel();
            this.domainFolderStatuses.put(dataDomain, currentStatus);
        }

        return currentStatus;
    }
}
