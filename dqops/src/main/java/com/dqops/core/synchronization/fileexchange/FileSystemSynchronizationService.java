/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.fileexchange;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.principal.UserDomainIdentity;
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
     * @param userIdentity User identity, also specifies the data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param apiKey API key with the license limits.
     * @param synchronizationListener Synchronization listener that is informed about the progress.
     * @return Synchronization result with two new file indexes after the file synchronization.
     */
    SynchronizationResult synchronize(FileSystemChangeSet local,
                                      FileSystemChangeSet remote,
                                      DqoRoot dqoRoot,
                                      UserDomainIdentity userIdentity,
                                      FileSynchronizationDirection synchronizationDirection,
                                      DqoCloudApiKey apiKey,
                                      FileSystemSynchronizationListener synchronizationListener);
}
