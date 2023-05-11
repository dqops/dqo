/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.synchronization.status;

import ai.dqo.core.synchronization.contract.DqoRoot;

/**
 * Tracks the synchronization status for each type of folder. Knows that there are unsaved changes in a given folder.
 */
public interface SynchronizationStatusTracker {
    /**
     * Updates the folder synchronization status.
     *
     * @param folderRoot Folder type.
     * @param newStatus  New synchronization status.
     */
    void changeFolderSynchronizationStatus(DqoRoot folderRoot, FolderSynchronizationStatus newStatus);

    /**
     * Returns the current folder synchronization status for each folder that is synchronized do DQO Cloud.
     *
     * @return Current folder synchronization status.
     */
    CloudSynchronizationFoldersStatusModel getCurrentSynchronizationStatus();
}
