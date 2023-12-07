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
package com.dqops.core.synchronization.filesystems.local;

import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;

/**
 * DQOps file system that accesses local files (selected folders) in the DQO_USER_HOME.
 */
public interface LocalSynchronizationFileSystemFactory {
    /**
     * Creates a DQOps file system that accesses physical files on the local file system.
     * @param rootType Root type (folder type).
     * @param userIdentity User identity that selects the synchronized data domain.
     * @return DQOps file system that can manage local files in a selected folder.
     */
    SynchronizationRoot createUserHomeFolderFileSystem(DqoRoot rootType, DqoUserIdentity userIdentity);
}
