/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.tables.sparse;

/**
 * Column type for sparse columns.
 */
public enum SparseColumnType {
    /**
     * A regular column that contains various values, or was not compressed to a sparse column.
     */
    regular,

    /**
     * An empty column.
     */
    empty,

    /**
     * A single value column that has only one value, applicable for all records.
     */
    single_value
}
