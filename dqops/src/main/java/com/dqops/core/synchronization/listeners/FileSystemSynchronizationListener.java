/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.listeners;

import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;

/**
 * Base class that is a progress handler to learn which files are synchronized.
 */
public abstract class FileSystemSynchronizationListener {
    // TODO: create a logger and log all operations in the debug severity mode

    /**
     * Called when the synchronization is about to begin. The synchronization is from the source to the target.
     * @param dqoRoot DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationBegin(DqoRoot dqoRoot,
                                       UserDomainIdentity userDomainIdentity,
                                       SynchronizationRoot sourceFileSystem,
                                       SynchronizationRoot targetFileSystem) {
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     * @param dqoRoot DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationFinished(DqoRoot dqoRoot,
                                          UserDomainIdentity userDomainIdentity,
                                          SynchronizationRoot sourceFileSystem,
                                          SynchronizationRoot targetFileSystem) {
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     * @param dqoRoot DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    public void onSourceChangeAppliedToTarget(DqoRoot dqoRoot,
                                              UserDomainIdentity userDomainIdentity,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     * @param dqoRoot DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    public void onTargetChangeAppliedToSource(DqoRoot dqoRoot,
                                              UserDomainIdentity userDomainIdentity,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
    }
}
