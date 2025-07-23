/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.factory;

/**
 * Enumeration of possible error samples collection scopes. "table" - a whole table is analyzed for error samples, "data_groupings" - error samples are collected for each data grouping.
 */
public enum ErrorSamplesDataScope {
    /**
     * The error samples are collected for the whole table.
     */
    table,

    /**
     * The error samples are collected for each data grouping.
     */
    data_group
}
