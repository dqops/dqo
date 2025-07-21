/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.filesystems.local;

import com.dqops.core.principal.UserDomainIdentity;
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
    SynchronizationRoot createUserHomeFolderFileSystem(DqoRoot rootType, UserDomainIdentity userIdentity);
}
