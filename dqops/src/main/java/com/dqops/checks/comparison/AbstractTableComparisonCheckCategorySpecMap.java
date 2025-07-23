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
 * Base class for comparison check container maps that contain comparison checks for each defined data comparison to a reference table on a table level.
 */
public abstract class AbstractTableComparisonCheckCategorySpecMap<V extends AbstractTableComparisonCheckCategorySpec> extends AbstractComparisonCheckCategorySpecMap<V> {
}
