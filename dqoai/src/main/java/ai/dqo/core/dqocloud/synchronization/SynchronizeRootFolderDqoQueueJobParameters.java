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
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Parameters object for a job that synchronizes one folder with DQO Cloud.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SynchronizeRootFolderDqoQueueJobParameters {
    private DqoRoot rootType;
    @JsonIgnore
    private FileSystemSynchronizationListener fileSystemSynchronizationListener;

    /**
     * Creates a new parameters object for a synchronize folder job.
     * @param rootType User home's root folder type to synchronize.
     * @param fileSystemSynchronizationListener File synchronization progress listener. Must be thread save because will be called from another thread (but ony one thread, no concurrent modifications).
     */
    public SynchronizeRootFolderDqoQueueJobParameters(DqoRoot rootType,
                                                      FileSystemSynchronizationListener fileSystemSynchronizationListener) {
        this.rootType = rootType;
        this.fileSystemSynchronizationListener = fileSystemSynchronizationListener;
    }

    /**
     * Returns the user home folder type to synchronize.
     * @return User home folder type.
     */
    public DqoRoot getRootType() {
        return rootType;
    }

    /**
     * Returns the file system synchronization listener that will receive progress messages during the synchronization process.
     * @return Synchronization listener.
     */
    public FileSystemSynchronizationListener getFileSystemSynchronizationListener() {
        return fileSystemSynchronizationListener;
    }
}
