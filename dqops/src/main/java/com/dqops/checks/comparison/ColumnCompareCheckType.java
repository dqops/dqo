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
 * Enumeration of compare check on a column level. Identifies the check type (such as the min_match), to enable retrieving the same checks
 * with slightly different names from profiling, daily monitoring, and other check types.
 */
public enum ColumnCompareCheckType {
    /**
     * Min value match check.
     */
    min_match,

    /**
     * Max value match check.
     */
    max_match,

    /**
     * Sum of values match check.
     */
    sum_match,

    /**
     * Mean of values match check.
     */
    mean_match,

    /**
     * Count of nulls match check.
     */
    null_count_match,

    /**
     * Count of not nulls match check.
     */
    not_null_count_match
}
