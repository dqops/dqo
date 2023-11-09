/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.checks.table.monitoring.comparison;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractTableComparisonCheckCategorySpec;
import com.dqops.checks.comparison.ComparisonCheckRules;
import com.dqops.checks.comparison.TableCompareCheckType;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonColumnCountMatchCheckSpec;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
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
 * Contains the daily monitoring comparison checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableComparisonDailyMonitoringChecksSpec extends AbstractTableComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableComparisonDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractTableComparisonCheckCategorySpec.FIELDS) {
        {
            put("daily_row_count_match", o -> o.dailyRowCountMatch);
            put("daily_column_count_match", o -> o.dailyColumnCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private TableComparisonRowCountMatchCheckSpec dailyRowCountMatch;

    @JsonPropertyDescription("Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private TableComparisonColumnCountMatchCheckSpec dailyColumnCountMatch;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableComparisonRowCountMatchCheckSpec getDailyRowCountMatch() {
        return dailyRowCountMatch;
    }

    /**
     * Sets a new row count match check.
     * @param dailyRowCountMatch Row count match check.
     */
    public void setDailyRowCountMatch(TableComparisonRowCountMatchCheckSpec dailyRowCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountMatch, dailyRowCountMatch));
        this.dailyRowCountMatch = dailyRowCountMatch;
        propagateHierarchyIdToField(dailyRowCountMatch, "daily_row_count_match");
    }

    /**
     * Returns the column count match check.
     * @return Column count match check.
     */
    public TableComparisonColumnCountMatchCheckSpec getDailyColumnCountMatch() {
        return dailyColumnCountMatch;
    }

    /**
     * Sets a new column count match check.
     * @param dailyColumnCountMatch Column count match check.
     */
    public void setDailyColumnCountMatch(TableComparisonColumnCountMatchCheckSpec dailyColumnCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnCountMatch, dailyColumnCountMatch));
        this.dailyColumnCountMatch = dailyColumnCountMatch;
        propagateHierarchyIdToField(dailyColumnCountMatch, "daily_column_count_match");
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
                if (this.dailyRowCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyRowCountMatch(new TableComparisonRowCountMatchCheckSpec());
                    }
                }

                return this.dailyRowCountMatch;
            }

            case column_count_match: {
                if (this.dailyColumnCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyColumnCountMatch(new TableComparisonColumnCountMatchCheckSpec());
                    }
                }

                return this.dailyColumnCountMatch;
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
                this.setDailyRowCountMatch(null);
                break;

            case column_count_match:
                this.setDailyColumnCountMatch(null);
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
        return true;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.monitoring;
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
}
