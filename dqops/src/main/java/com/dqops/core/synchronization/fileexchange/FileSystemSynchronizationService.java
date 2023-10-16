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
package com.dqops.core.synchronization.fileexchange;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;

/**
 * File system synchronization service that synchronizes files between two file systems. It could synchronize local files
 * with the target file system.
 */
public interface FileSystemSynchronizationService {
    /**
     * Synchronizes changes between two file systems.
     * @param local Source file system, the changes on the source (the local files) will overwrite changes in the target (remote DQOps Cloud or similar).
     * @param remote Target file system to send the changes in the source and download new changes.
     * @param dqoRoot User Home folder type to synchronize.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param apiKey API key with the license limits.
     * @param synchronizationListener Synchronization listener that is informed about the progress.
     * @return Synchronization result with two new file indexes after the file synchronization.
     */
    SynchronizationResult synchronize(FileSystemChangeSet local,
                                      FileSystemChangeSet remote,
                                      DqoRoot dqoRoot,
                                      FileSynchronizationDirection synchronizationDirection,
                                      DqoCloudApiKey apiKey,
                                      FileSystemSynchronizationListener synchronizationListener);
}
