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
package com.dqops.checks.column.monitoring.customsql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlAggregateExpressionCheckSpec;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlConditionFailedCountCheckSpec;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlConditionPassedPercentCheckSpec;
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
public class ColumnCustomSqlMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_passed_percent_on_column", o -> o.monthlySqlConditionPassedPercentOnColumn);
            put("monthly_sql_condition_failed_count_on_column", o -> o.monthlySqlConditionFailedCountOnColumn);
            put("monthly_sql_aggregate_expression_on_column", o -> o.monthlySqlAggregateExpressionOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getMonthlySqlConditionPassedPercentOnColumn() {
        return monthlySqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionPassedPercentOnColumn Check specification.
     */
    public void setMonthlySqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionPassedPercentOnColumn, monthlySqlConditionPassedPercentOnColumn));
        this.monthlySqlConditionPassedPercentOnColumn = monthlySqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(monthlySqlConditionPassedPercentOnColumn, "monthly_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCountCheckSpec getMonthlySqlConditionFailedCountOnColumn() {
        return monthlySqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionFailedCountOnColumn Check specification.
     */
    public void setMonthlySqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionFailedCountOnColumn, monthlySqlConditionFailedCountOnColumn));
        this.monthlySqlConditionFailedCountOnColumn = monthlySqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(monthlySqlConditionFailedCountOnColumn, "monthly_sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getMonthlySqlAggregateExpressionOnColumn() {
        return monthlySqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExpressionOnColumn Check specification.
     */
    public void setMonthlySqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExpressionOnColumn, monthlySqlAggregateExpressionOnColumn));
        this.monthlySqlAggregateExpressionOnColumn = monthlySqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(monthlySqlAggregateExpressionOnColumn, "monthly_sql_aggregate_expression_on_column");
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