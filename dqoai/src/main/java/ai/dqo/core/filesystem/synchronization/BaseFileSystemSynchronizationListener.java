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
package ai.dqo.core.filesystem.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.metadata.FileDifference;

/**
 * Base class that is a progress handler to learn which files are synchronized.
 */
public class BaseFileSystemSynchronizationListener {
    /**
     * Called when the synchronization is about to begin. The synchronization is from the source to the target.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationBegin(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    public void onSynchronizationFinished(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    public void onSourceChangeAppliedToTarget(DqoFileSystem sourceFileSystem,
                                              DqoFileSystem targetFileSystem,
                                              FileDifference fileDifference) {
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    public void onTargetChangeAppliedToSource(DqoFileSystem sourceFileSystem,
                                              DqoFileSystem targetFileSystem,
                                              FileDifference fileDifference) {
    }
}
