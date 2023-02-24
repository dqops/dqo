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
package ai.dqo.core.dqocloud.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.synchronization.FileSynchronizationDirection;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;

/**
 * File synchronization service. Performs a full synchronization of a given category of files to the DQO Cloud.
 */
public interface DqoCloudSynchronizationService {
    /**
     * Performs synchronization of a given user home folder to the DQO Cloud.
     * @param dqoRoot User Home folder type to synchronize.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeFolder(DqoRoot dqoRoot, FileSynchronizationDirection synchronizationDirection, FileSystemSynchronizationListener synchronizationListener);

    /**
     * Synchronizes all roots (sources, check definitions, data).
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeAll(FileSynchronizationDirection synchronizationDirection, FileSystemSynchronizationListener synchronizationListener);

    /**
     * Synchronizes only the data roots (sensor readouts, rule results).
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeData(FileSynchronizationDirection fileSynchronizationDirection, FileSystemSynchronizationListener synchronizationListener);
}
