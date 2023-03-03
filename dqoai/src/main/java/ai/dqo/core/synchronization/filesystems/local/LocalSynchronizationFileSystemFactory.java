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
package ai.dqo.core.synchronization.filesystems.local;

import ai.dqo.core.synchronization.contract.SynchronizationRoot;
import ai.dqo.core.synchronization.contract.DqoRoot;

/**
 * DQO file system that accesses local files (selected folders) in the DQO_USER_HOME.
 */
public interface LocalSynchronizationFileSystemFactory {
    /**
     * Creates a DQO file system that accesses physical files on the local file system.
     * @param rootType Root type (folder type).
     * @return DQO file system that can manage local files in a selected folder.
     */
    SynchronizationRoot createUserHomeFolderFileSystem(DqoRoot rootType);
}
