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
