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
package ai.dqo.core.synchronization.listeners;

import ai.dqo.core.filesystem.metadata.FileDifference;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.contract.SynchronizationRoot;

/**
 * Base class that is a progress handler to learn which files are synchronized.
 */
public abstract class FileSystemSynchronizationListener {
    // TODO: create a logger and log all operations in the debug severity mode

    /**
     * Called when the synchronization is about to begin. The synchronization is from the source to the target.
     * @param dqoRoot DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationBegin(DqoRoot dqoRoot, SynchronizationRoot sourceFileSystem, SynchronizationRoot targetFileSystem) {
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     * @param dqoRoot DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationFinished(DqoRoot dqoRoot, SynchronizationRoot sourceFileSystem, SynchronizationRoot targetFileSystem) {
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     * @param dqoRoot DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    public void onSourceChangeAppliedToTarget(DqoRoot dqoRoot,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     * @param dqoRoot DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    public void onTargetChangeAppliedToSource(DqoRoot dqoRoot,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
    }
}
