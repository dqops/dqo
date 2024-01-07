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
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionFailedCountCheckSpec;
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
public class TableCustomSqlMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCustomSqlMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_passed_percent_on_table", o -> o.monthlySqlConditionPassedPercentOnTable);
            put("monthly_sql_condition_failed_count_on_table", o -> o.monthlySqlConditionFailedCountOnTable);
            put("monthly_sql_aggregate_expression_on_table", o -> o.monthlySqlAggregateExpressionOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return monthlySqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlySqlConditionPassedPercentOnTable() {
        return monthlySqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionPassedPercentOnTable Check specification.
     */
    public void setMonthlySqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionPassedPercentOnTable, monthlySqlConditionPassedPercentOnTable));
        this.monthlySqlConditionPassedPercentOnTable = monthlySqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(monthlySqlConditionPassedPercentOnTable, "monthly_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlySqlConditionFailedCountOnTable() {
        return monthlySqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionFailedCountOnTable Check specification.
     */
    public void setMonthlySqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionFailedCountOnTable, monthlySqlConditionFailedCountOnTable));
        this.monthlySqlConditionFailedCountOnTable = monthlySqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(monthlySqlConditionFailedCountOnTable, "monthly_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getMonthlySqlAggregateExpressionOnTable() {
        return monthlySqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExpressionOnTable Check specification.
     */
    public void setMonthlySqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExpressionOnTable, monthlySqlAggregateExpressionOnTable));
        this.monthlySqlAggregateExpressionOnTable = monthlySqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(monthlySqlAggregateExpressionOnTable, "monthly_sql_aggregate_expression_on_table");
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
        return CheckTimeScale.monthly;
    }
}