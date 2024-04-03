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
package com.dqops.core.synchronization.filesystems.dqocloud;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;

/**
 * Factory for a DQOps Cloud remote file system.
 */
public interface DqoCloudRemoteFileSystemServiceFactory {
    /**
     * Creates a remote file system that accesses a remote DQOps Cloud bucket to read and write the tenant's data.
     * @param rootType Root type.
     * @param userIdentity User identity that identifies the data domain that will be synchronized.
     * @return DQOps Cloud remote file system.
     */
    SynchronizationRoot createRemoteDqoCloudFSRW(DqoRoot rootType, UserDomainIdentity userIdentity);
}
