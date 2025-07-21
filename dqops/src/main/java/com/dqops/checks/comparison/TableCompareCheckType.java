/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.comparison;

/**
 * Enumeration of compare check on a table level. Identifies the check type (such as the row_count_match), to enable retrieving the same checks
 * with slightly different names from profiling, daily monitoring, and other check types.
 */
public enum TableCompareCheckType {
    /**
     * Row count match check.
     */
    row_count_match,

    /**
     * Column count match check.
     */
    column_count_match
}
