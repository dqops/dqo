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

/**
 * Tracks the synchronization status for each type of folder. Knows that there are unsaved changes in a given folder.
 */
public interface SynchronizationStatusTracker {
    /**
     * Updates the folder synchronization status.
     *
     * @param folderRoot Folder type.
     * @param dataDomain Data domain name.
     * @param newStatus  New synchronization status.
     */
    void changeFolderSynchronizationStatus(DqoRoot folderRoot, String dataDomain, FolderSynchronizationStatus newStatus);

    /**
     * Returns the current folder synchronization status for each folder that is synchronized do DQOps Cloud.
     *
     * @param dataDomain Data domain name.
     * @return Current folder synchronization status.
     */
    CloudSynchronizationFoldersStatusModel getCurrentSynchronizationStatus(String dataDomain);
}
