/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.virtual;

/**
 * File status in the file system tree.
 */
public enum FileTreeNodeStatus {
    /**
     * The file was not loaded and the file tree node is just a virtual node with the file name.
     */
    NOT_LOADED,

    /**
     * The file was loaded and was not modified.
     */
    LOADED_NOT_MODIFIED,

    /**
     * The file was added as a new file and should be saved to the file system on flush.
     */
    NEW,

    /**
     * An existing file that was loaded and is modified.
     */
    MODIFIED,

    /**
     * An existing file that is marked for deletion on flush.
     */
    TO_BE_DELETED,

    /**
     * A file was deleted and it is present in the file tree system only as an orphaned node.
     */
    DELETED
}
