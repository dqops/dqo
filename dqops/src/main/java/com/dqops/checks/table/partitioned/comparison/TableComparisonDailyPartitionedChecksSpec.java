/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.partitioned.comparison;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractTableComparisonCheckCategorySpec;
import com.dqops.checks.comparison.ComparisonCheckRules;
import com.dqops.checks.comparison.TableCompareCheckType;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 * Contains the daily partitioned comparison checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableComparisonDailyPartitionedChecksSpec extends AbstractTableComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableComparisonDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractTableComparisonCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_row_count_match", o -> o.dailyPartitionRowCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.")
    private TableComparisonRowCountMatchCheckSpec dailyPartitionRowCountMatch;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableComparisonRowCountMatchCheckSpec getDailyPartitionRowCountMatch() {
        return dailyPartitionRowCountMatch;
    }

    /**
     * Sets a new row count match check.
     * @param dailyPartitionRowCountMatch Row count match check.
     */
    public void setDailyPartitionRowCountMatch(TableComparisonRowCountMatchCheckSpec dailyPartitionRowCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountMatch, dailyPartitionRowCountMatch));
        this.dailyPartitionRowCountMatch = dailyPartitionRowCountMatch;
        propagateHierarchyIdToField(dailyPartitionRowCountMatch, "daily_partition_row_count_match");
    }

    /**
     * Returns the check specification for the given check type or null when it is not present and <code>createWhenMissing</code> is false.
     *
     * @param tableCompareCheckType Compare check type.
     * @param createWhenMissing     When true and the check specification is not present, it is created, added to the check compare container and returned.
     * @return Check specification or null (when <code>createWhenMissing</code> is false).
     */
    @Override
    public ComparisonCheckRules getCheckSpec(TableCompareCheckType tableCompareCheckType, boolean createWhenMissing) {
        switch (tableCompareCheckType) {
            case row_count_match: {
                if (this.dailyPartitionRowCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionRowCountMatch(new TableComparisonRowCountMatchCheckSpec());
                    }
                }

                return this.dailyPartitionRowCountMatch;
            }
            default:
                return null;
        }
    }

    /**
     * Removes the check specification for the given check.
     *
     * @param tableCompareCheckType Check type.
     */
    @Override
    public void removeCheckSpec(TableCompareCheckType tableCompareCheckType) {
        switch (tableCompareCheckType) {
            case row_count_match:
                this.setDailyPartitionRowCountMatch(null);
                break;
        }
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns true if this type of comparison checks support a column count comparison.
     * Profiling and monitoring checks that compare the whole table support also comparing the column count.
     * Partitioned checks do not support comparing row count and their comparison check containers return false.
     *
     * @return True - the column count match check is supported for this type of checks, false when it is not supported.
     */
    @Override
    public boolean supportsColumnComparisonCheck() {
        return false;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}
