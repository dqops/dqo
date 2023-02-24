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
package ai.dqo.core.filesystem.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;

/**
 * Service that is called on startup, detects if there are any unsynchronized chages in any DQO User Home folder that should be synchronized to the DQO Cloud.
 */
public interface FileSynchronizationChangeDetectionService {
    /**
     * Detects if there are any unsynchronized changes in a given DQO User home folder.
     *
     * @param dqoRoot User home folder to be analyzed.
     * @return True when there are local unsynchronized changes, false otherwise.
     */
    boolean detectUnsynchronizedChangesInFolder(DqoRoot dqoRoot);

    /**
     * Detects changes in a folder. Optionally publishes a folder change status to "changed" if a change is detected.
     *
     * @param dqoRoot Folder to be analyzed.
     */
    void detectAndPublishLocalFolderStatus(DqoRoot dqoRoot);

    /**
     * Starts a background job that checks all folders and tries to detect local changes that were not yet synchronized to DQO Cloud.
     */
    void detectUnsynchronizedChangesInBackground();
}
