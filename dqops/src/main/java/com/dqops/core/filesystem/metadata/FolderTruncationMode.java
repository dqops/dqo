/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.metadata;

/**
 * Folder truncation mode for limiting the number of local files and folders that will be synchronized to the cloud.
 */
public enum FolderTruncationMode {
    ASCENDING_SORTED_FIRST,

    DESCENDING_SORTED_FIRST
}
