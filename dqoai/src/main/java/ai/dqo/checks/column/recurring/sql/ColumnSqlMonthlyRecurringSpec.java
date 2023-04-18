/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.recurring.sql;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlAggregateExprCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlConditionPassedPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnSqlMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_passed_percent_on_column", o -> o.monthlySqlConditionPassedPercentOnColumn);
            put("monthly_sql_condition_failed_count_on_column", o -> o.monthlySqlConditionFailedCountOnColumn);

            put("monthly_sql_aggregate_expr_column", o -> o.monthlySqlAggregateExprColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSqlAggregateExprCheckSpec monthlySqlAggregateExprColumn;

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
    public ColumnSqlAggregateExprCheckSpec getMonthlySqlAggregateExprColumn() {
        return monthlySqlAggregateExprColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExprColumn Check specification.
     */
    public void setMonthlySqlAggregateExprColumn(ColumnSqlAggregateExprCheckSpec monthlySqlAggregateExprColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExprColumn, monthlySqlAggregateExprColumn));
        this.monthlySqlAggregateExprColumn = monthlySqlAggregateExprColumn;
        propagateHierarchyIdToField(monthlySqlAggregateExprColumn, "monthly_sql_aggregated_expression_value_on_column_min");
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
}