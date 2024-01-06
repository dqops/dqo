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
package com.dqops.checks.table.partitioned.sql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.sql.TableSqlAggregateExpressionCheckSpec;
import com.dqops.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import com.dqops.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSqlDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sql_condition_passed_percent_on_table", o -> o.dailyPartitionSqlConditionPassedPercentOnTable);
            put("daily_partition_sql_condition_failed_count_on_table", o -> o.dailyPartitionSqlConditionFailedCountOnTable);

            put("daily_partition_sql_aggregate_expression_on_table", o -> o.dailyPartitionSqlAggregateExpressionOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.")
    private TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.")
    private TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private TableSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return dailyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnTable() {
        return dailyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnTable Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnTable, dailyPartitionSqlConditionPassedPercentOnTable));
        this.dailyPartitionSqlConditionPassedPercentOnTable = dailyPartitionSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnTable, "daily_partition_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnTable() {
        return dailyPartitionSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnTable Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnTable, dailyPartitionSqlConditionFailedCountOnTable));
        this.dailyPartitionSqlConditionFailedCountOnTable = dailyPartitionSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnTable, "daily_partition_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getDailyPartitionSqlAggregateExpressionOnTable() {
        return dailyPartitionSqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregateExpressionOnTable Check specification.
     */
    public void setDailyPartitionSqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregateExpressionOnTable, dailyPartitionSqlAggregateExpressionOnTable));
        this.dailyPartitionSqlAggregateExpressionOnTable = dailyPartitionSqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(dailyPartitionSqlAggregateExpressionOnTable, "daily_partition_sql_aggregate_expression_on_table");
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
        return CheckTarget.table;
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