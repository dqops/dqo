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
