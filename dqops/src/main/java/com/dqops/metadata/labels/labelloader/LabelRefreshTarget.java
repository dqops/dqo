/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.labels.labelloader;

/**
 * Identifies the target object that was modified, and whose labels should be loaded. The choices is a connection and a table.
 */
public enum LabelRefreshTarget {
    /**
     * Labels from a connection YAML object.
     */
    CONNECTION,

    /**
     * Labels from a TableSpec object.
     */
    TABLE
}
