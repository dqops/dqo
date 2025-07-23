/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.services;

/**
 * The mode of loading a list of detailed check results: the most recent values for each data group, or all results of the first group.
 */
public enum CheckResultsDetailedLoadMode {
    /**
     * Find the first result from any data group and load results only for that group that was picked.
     */
    first_data_group,

    /**
     * Return the most recent result for each data group, not returning older values.
     */
    most_recent_per_group
}
