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
package ai.dqo.core.synchronization.filesystems.dqocloud;

import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.contract.SynchronizationRoot;

/**
 * Factory for a DQO Cloud remote file system.
 */
public interface DqoCloudRemoteFileSystemServiceFactory {
    /**
     * Creates a remote file system that accesses a remote DQO Cloud bucket to read and write the tenant's data.
     * @param rootType Root type.
     * @return DQO Cloud remote file system.
     */
    SynchronizationRoot createRemoteDqoCloudFSRW(DqoRoot rootType);
}
