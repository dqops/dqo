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
package com.dqops.core.synchronization.jobs;

import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;
import com.dqops.core.synchronization.listeners.SilentFileSystemSynchronizationListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;

/**
 * Parameters object for a job that synchronizes one folder with DQOps Cloud.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class SynchronizeRootFolderDqoQueueJobParameters {
    private SynchronizeRootFolderParameters synchronizationParameter;
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private FileSystemSynchronizationListener fileSystemSynchronizationListener = new SilentFileSystemSynchronizationListener();

    /**
     * Default constructor.
     */
    public SynchronizeRootFolderDqoQueueJobParameters() {
    }

    /**
     * Creates a new parameters object for a synchronize folder job.
     * @param synchronizationParameter Synchronization parameter that is a serializable object with the folder (DQOps Root) and direction to be synchronized.
     * @param fileSystemSynchronizationListener File synchronization progress listener. Must be thread save because will be called from another thread (but ony one thread, no concurrent modifications).
     */
    public SynchronizeRootFolderDqoQueueJobParameters(SynchronizeRootFolderParameters synchronizationParameter,
                                                      FileSystemSynchronizationListener fileSystemSynchronizationListener) {
        this.synchronizationParameter = synchronizationParameter;
        this.fileSystemSynchronizationListener = fileSystemSynchronizationListener;
    }

    /**
     * Returns the synchronization parameter with the folder to be synchronized and the synchronization direction.
     * @return Synchronization parameter.
     */
    public SynchronizeRootFolderParameters getSynchronizationParameter() {
        return synchronizationParameter;
    }

    /**
     * Returns the file system synchronization listener that will receive progress messages during the synchronization process.
     * @return Synchronization listener.
     */
    public FileSystemSynchronizationListener getFileSystemSynchronizationListener() {
        return fileSystemSynchronizationListener;
    }
}
