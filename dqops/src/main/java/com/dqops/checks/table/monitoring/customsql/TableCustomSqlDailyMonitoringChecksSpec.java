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
package com.dqops.checks.table.monitoring.customsql;

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
public class TableCustomSqlDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCustomSqlDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_sql_condition_failed_on_table", o -> o.dailySqlConditionFailedOnTable);
            put("daily_sql_condition_passed_percent_on_table", o -> o.dailySqlConditionPassedPercentOnTable);
            put("daily_sql_aggregate_expression_on_table", o -> o.dailySqlAggregateExpressionOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between columns: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores the most recent count of failed rows for each day when the data quality check was evaluated.")
    private TableSqlConditionFailedCheckSpec dailySqlConditionFailedOnTable;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores the most recent captured percentage for each day when the data quality check was evaluated.")
    private TableSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private TableSqlAggregateExpressionCheckSpec dailySqlAggregateExpressionOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCheckSpec getDailySqlConditionFailedOnTable() {
        return dailySqlConditionFailedOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionFailedOnTable Check specification.
     */
    public void setDailySqlConditionFailedOnTable(TableSqlConditionFailedCheckSpec dailySqlConditionFailedOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionFailedOnTable, dailySqlConditionFailedOnTable));
        this.dailySqlConditionFailedOnTable = dailySqlConditionFailedOnTable;
        propagateHierarchyIdToField(dailySqlConditionFailedOnTable, "daily_sql_condition_failed_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailySqlConditionPassedPercentOnTable() {
        return dailySqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionPassedPercentOnTable Check specification.
     */
    public void setDailySqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionPassedPercentOnTable, dailySqlConditionPassedPercentOnTable));
        this.dailySqlConditionPassedPercentOnTable = dailySqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(dailySqlConditionPassedPercentOnTable, "daily_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getDailySqlAggregateExpressionOnTable() {
        return dailySqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlAggregateExpressionOnTable Check specification.
     */
    public void setDailySqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec dailySqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlAggregateExpressionOnTable, dailySqlAggregateExpressionOnTable));
        this.dailySqlAggregateExpressionOnTable = dailySqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(dailySqlAggregateExpressionOnTable, "daily_sql_aggregate_expression_on_table");
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