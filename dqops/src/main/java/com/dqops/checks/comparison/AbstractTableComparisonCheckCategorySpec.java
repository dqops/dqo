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

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Base class for comparison (accuracy) check category at a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTableComparisonCheckCategorySpec extends AbstractComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractTableComparisonCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractComparisonCheckCategorySpec.FIELDS) {
        {
        }
    };

    /**
     * Returns the check specification for the given check type or null when it is not present and <code>createWhenMissing</code> is false.
     * @param tableCompareCheckType Compare check type.
     * @param createWhenMissing When true and the check specification is not present, it is created, added to the check compare container and returned.
     * @return Check specification or null (when <code>createWhenMissing</code> is false).
     */
    public abstract ComparisonCheckRules getCheckSpec(TableCompareCheckType tableCompareCheckType, boolean createWhenMissing);

    /**
     * Removes the check specification for the given check.
     * @param tableCompareCheckType Check type.
     */
    public abstract void removeCheckSpec(TableCompareCheckType tableCompareCheckType);

    /**
     * Returns true if this type of comparison checks support a column count comparison.
     * Profiling and monitoring checks that compare the whole table support also comparing the column count.
     * Partitioned checks do not support comparing row count and their comparison check containers return false.
     * @return True - the column count match check is supported for this type of checks, false when it is not supported.
     */
    public abstract boolean supportsColumnComparisonCheck();

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }
}
