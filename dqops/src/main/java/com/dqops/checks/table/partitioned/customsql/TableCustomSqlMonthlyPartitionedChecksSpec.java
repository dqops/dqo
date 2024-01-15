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
package com.dqops.checks.table.partitioned.customsql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.customsql.TableSqlAggregateExpressionCheckSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionFailedCheckSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionPassedPercentCheckSpec;
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
public class TableCustomSqlMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCustomSqlMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_sql_condition_failed_on_table", o -> o.monthlyPartitionSqlConditionFailedOnTable);
            put("monthly_partition_sql_condition_passed_percent_on_table", o -> o.monthlyPartitionSqlConditionPassedPercentOnTable);
            put("monthly_partition_sql_aggregate_expression_on_table", o -> o.monthlyPartitionSqlAggregateExpressionOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between columns: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores a separate data quality check result for each monthly partition.")
    private TableSqlConditionFailedCheckSpec monthlyPartitionSqlConditionFailedOnTable;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores a separate data quality check result for each monthly partition.")
    private TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private TableSqlAggregateExpressionCheckSpec monthlyPartitionSqlAggregateExpressionOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCheckSpec getMonthlyPartitionSqlConditionFailedOnTable() {
        return monthlyPartitionSqlConditionFailedOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedOnTable Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedOnTable(TableSqlConditionFailedCheckSpec monthlyPartitionSqlConditionFailedOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedOnTable, monthlyPartitionSqlConditionFailedOnTable));
        this.monthlyPartitionSqlConditionFailedOnTable = monthlyPartitionSqlConditionFailedOnTable;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedOnTable, "monthly_partition_sql_condition_failed_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnTable() {
        return monthlyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnTable Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnTable, monthlyPartitionSqlConditionPassedPercentOnTable));
        this.monthlyPartitionSqlConditionPassedPercentOnTable = monthlyPartitionSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnTable, "monthly_partition_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getMonthlyPartitionSqlAggregateExpressionOnTable() {
        return monthlyPartitionSqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregateExpressionOnTable Check specification.
     */
    public void setMonthlyPartitionSqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec monthlyPartitionSqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregateExpressionOnTable, monthlyPartitionSqlAggregateExpressionOnTable));
        this.monthlyPartitionSqlAggregateExpressionOnTable = monthlyPartitionSqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregateExpressionOnTable, "monthly_partition_sql_aggregate_expression_on_table");
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
        return CheckTimeScale.monthly;
    }
}