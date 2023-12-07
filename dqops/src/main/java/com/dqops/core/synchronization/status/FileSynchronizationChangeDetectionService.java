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

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.contract.DqoRoot;

/**
 * Service that is called on startup, detects if there are any unsynchronized chages in any DQOps User Home folder that should be synchronized to the DQOps Cloud.
 */
public interface FileSynchronizationChangeDetectionService {
    /**
     * Detects if there are any unsynchronized changes in a given DQOps User home folder.
     *
     * @param dqoRoot User home folder to be analyzed.
     * @param principal User principal that identifies the data domain.
     * @return True when there are local unsynchronized changes, false otherwise.
     */
    boolean detectNotSynchronizedChangesInFolder(DqoRoot dqoRoot, DqoUserPrincipal principal);

    /**
     * Detects changes in a folder. Optionally publishes a folder change status to "changed" if a change is detected.
     *
     * @param principal User principal that identifies the data domain.
     * @param dqoRoot Folder to be analyzed.
     */
    void detectAndPublishLocalFolderStatus(DqoRoot dqoRoot, DqoUserPrincipal principal);

    /**
     * Starts a background job that checks all folders and tries to detect local changes that were not yet synchronized to DQOps Cloud.
     */
    void detectNotSynchronizedChangesInBackground();
}
