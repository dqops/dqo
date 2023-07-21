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

/**
 * File synchronization result - returns new file indexes.
 */
public class SynchronizationResult {
    private FolderMetadata sourceFileIndex;
    private FolderMetadata targetFileIndex;
    private TargetTableModifiedPartitions targetTableModifiedPartitions;

    /**
     * Creates a synchronization result.
     * @param sourceFileIndex New source file index (will be a local file index).
     * @param targetFileIndex New target file index (of the remote file system).
     * @param targetTableModifiedPartitions List of modified connections, tables. Only for data folders (parquet files).
     */
    public SynchronizationResult(FolderMetadata sourceFileIndex,
                                 FolderMetadata targetFileIndex,
                                 TargetTableModifiedPartitions targetTableModifiedPartitions) {
        this.sourceFileIndex = sourceFileIndex;
        this.targetFileIndex = targetFileIndex;
        this.targetTableModifiedPartitions = targetTableModifiedPartitions;
    }

    /**
     * Returns a new source file index.
     * @return New source file index.
     */
    public FolderMetadata getSourceFileIndex() {
        return sourceFileIndex;
    }

    /**
     * Returns a new target file index.
     * @return New target file index.
     */
    public FolderMetadata getTargetFileIndex() {
        return targetFileIndex;
    }

    /**
     * Returns the list of modified connections, tables, dates.
     * @return Modified partitions of a table.
     */
    public TargetTableModifiedPartitions getTargetTableModifiedPartitions() {
        return targetTableModifiedPartitions;
    }
}
