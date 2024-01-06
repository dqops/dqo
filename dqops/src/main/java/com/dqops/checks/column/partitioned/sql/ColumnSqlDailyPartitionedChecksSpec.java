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
package com.dqops.checks.column.partitioned.sql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.sql.ColumnSqlAggregateExpressionCheckSpec;
import com.dqops.checks.column.checkspecs.sql.ColumnSqlConditionFailedCountCheckSpec;
import com.dqops.checks.column.checkspecs.sql.ColumnSqlConditionPassedPercentCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSqlDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sql_condition_passed_percent_on_column", o -> o.dailyPartitionSqlConditionPassedPercentOnColumn);
            put("daily_partition_sql_condition_failed_count_on_column", o -> o.dailyPartitionSqlConditionFailedCountOnColumn);

            put("daily_partition_sql_aggregate_expression_on_column", o -> o.dailyPartitionSqlAggregateExpressionOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnColumn() {
        return dailyPartitionSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnColumn, dailyPartitionSqlConditionPassedPercentOnColumn));
        this.dailyPartitionSqlConditionPassedPercentOnColumn = dailyPartitionSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnColumn, "daily_partition_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnColumn() {
        return dailyPartitionSqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnColumn Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnColumn, dailyPartitionSqlConditionFailedCountOnColumn));
        this.dailyPartitionSqlConditionFailedCountOnColumn = dailyPartitionSqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnColumn, "daily_partition_sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getDailyPartitionSqlAggregateExpressionOnColumn() {
        return dailyPartitionSqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregateExpressionOnColumn Check specification.
     */
    public void setDailyPartitionSqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregateExpressionOnColumn, dailyPartitionSqlAggregateExpressionOnColumn));
        this.dailyPartitionSqlAggregateExpressionOnColumn = dailyPartitionSqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlAggregateExpressionOnColumn, "daily_partition_sql_aggregate_expression_on_column");
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
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
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
}