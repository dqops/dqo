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
import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import com.dqops.core.synchronization.status.FolderSynchronizationStatus;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;

/**
 * Test stub for a synchronization status tracker. Does not publish notifications.
 */
public class SynchronizationStatusTrackerStub implements SynchronizationStatusTracker {
    private volatile CloudSynchronizationFoldersStatusModel currentFolderStatus = new CloudSynchronizationFoldersStatusModel();

    /**
     * Updates the folder synchronization status.
     *
     * @param folderRoot Folder type.
     * @param newStatus  New synchronization status.
     */
    @Override
    public void changeFolderSynchronizationStatus(DqoRoot folderRoot, FolderSynchronizationStatus newStatus) {
        CloudSynchronizationFoldersStatusModel newFolderStatus = currentFolderStatus.clone();
        newFolderStatus.setFolderStatus(folderRoot, newStatus);
        this.currentFolderStatus = newFolderStatus;
    }

    /**
     * Returns the current folder synchronization status for each folder that is synchronized do DQOps Cloud.
     *
     * @return Current folder synchronization status.
     */
    @Override
    public CloudSynchronizationFoldersStatusModel getCurrentSynchronizationStatus() {
        return this.currentFolderStatus;
    }
}
