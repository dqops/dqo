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
package com.dqops.core.synchronization.fileexchange;

import com.dqops.core.filesystem.metadata.FolderMetadata;
import com.dqops.core.synchronization.contract.SynchronizationRoot;

import java.util.Optional;

/**
 * File system change set that should be applied.
 */
public class FileSystemChangeSet {
    private SynchronizationRoot fileSystem;
    private FolderMetadata storedFileIndex;
    private Optional<FolderMetadata> currentFileIndex;

    /**
     * Creates a file system change set.
     * @param fileSystem File system reference.
     * @param storedFileIndex Last known file index from the last synchronization.
     * @param currentFileIndex Current file index just retrieved by iterating over files.
     */
    public FileSystemChangeSet(SynchronizationRoot fileSystem, FolderMetadata storedFileIndex, Optional<FolderMetadata> currentFileIndex) {
        this.fileSystem = fileSystem;
        this.storedFileIndex = storedFileIndex;
        this.currentFileIndex = currentFileIndex;
    }

    /**
     * Returns the DQOps file system reference.
     * @return File system reference.
     */
    public SynchronizationRoot getFileSystem() {
        return fileSystem;
    }

    /**
     * Returns a file index of the files that were already synchronized.
     * @return Index of files that were synchronized before.
     */
    public FolderMetadata getStoredFileIndex() {
        return storedFileIndex;
    }

    /**
     * Returns the current, most recent file index of the files that are present in the file system.
     * When this file index is compared to the <code>storedFileIndex</code>, then the changes are identified.
     * This index may be null, so the synchronization system will retrieve the file list from the source file system.
     * @return Current file index that describes all files that are present right now in the file system.
     */
    public Optional<FolderMetadata> getCurrentFileIndex() {
        return currentFileIndex;
    }
}
