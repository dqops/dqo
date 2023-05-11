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
package ai.dqo.checks.column.partitioned.sql;

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
public class ColumnSqlMonthlyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlMonthlyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_sql_condition_passed_percent_on_column", o -> o.monthlyPartitionSqlConditionPassedPercentOnColumn);
            put("monthly_partition_sql_condition_failed_count_on_column", o -> o.monthlyPartitionSqlConditionFailedCountOnColumn);

            put("monthly_partition_sql_aggregate_expr_column", o -> o.monthlyPartitionSqlAggregateExprColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSqlAggregateExprCheckSpec monthlyPartitionSqlAggregateExprColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnColumn() {
        return monthlyPartitionSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnColumn, monthlyPartitionSqlConditionPassedPercentOnColumn));
        this.monthlyPartitionSqlConditionPassedPercentOnColumn = monthlyPartitionSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnColumn, "monthly_partition_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCountCheckSpec getMonthlyPartitionSqlConditionFailedCountOnColumn() {
        return monthlyPartitionSqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedCountOnColumn Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedCountOnColumn, monthlyPartitionSqlConditionFailedCountOnColumn));
        this.monthlyPartitionSqlConditionFailedCountOnColumn = monthlyPartitionSqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedCountOnColumn, "monthly_partition_sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExprCheckSpec getMonthlyPartitionSqlAggregateExprColumn() {
        return monthlyPartitionSqlAggregateExprColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregateExprColumn Check specification.
     */
    public void setMonthlyPartitionSqlAggregateExprColumn(ColumnSqlAggregateExprCheckSpec monthlyPartitionSqlAggregateExprColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregateExprColumn, monthlyPartitionSqlAggregateExprColumn));
        this.monthlyPartitionSqlAggregateExprColumn = monthlyPartitionSqlAggregateExprColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregateExprColumn, "monthly_partition_sql_aggregate_expr_column");
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