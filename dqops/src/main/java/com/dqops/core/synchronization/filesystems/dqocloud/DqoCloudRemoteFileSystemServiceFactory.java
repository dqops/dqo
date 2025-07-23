/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
