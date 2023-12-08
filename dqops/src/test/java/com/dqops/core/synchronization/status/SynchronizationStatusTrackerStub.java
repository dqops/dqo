/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
