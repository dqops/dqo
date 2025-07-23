/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.factory;

/**
 * Enumeration of possible statistics scopes. "table" - a whole table was profiled, "data_groupings" - groups of rows were profiled.
 */
public enum StatisticsDataScope {
    /**
     * The statistics (profile) is analyzed for the whole table.
     */
    table,

    /**
     * The statistics (profile) is analyzed for each group of data.
     */
    data_group
}
