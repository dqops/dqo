/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.sqltemplates.grouping;

/**
 * Enumeration of SQL query component types.
 */
public enum SqlQueryFragmentType {
    /**
     * Static SQL fragment that must be the same for all similar queries that could be merged.
     */
    STATIC_FRAGMENT,

    /**
     * The SQL query fragment that computes the sensor value (the actual_value result column).
     */
    ACTUAL_VALUE,

    /**
     * The SQL query fragment that computes expected value.
     */
    EXPECTED_VALUE
}
