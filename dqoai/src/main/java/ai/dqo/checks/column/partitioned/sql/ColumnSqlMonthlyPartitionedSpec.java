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
import ai.dqo.checks.column.checkspecs.sql.ColumnMaxSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnMinSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlAggregatedExpressionMaxValueCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlAggregatedExpressionMinValueCheckSpec;
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
            put("monthly_partition_min_sql_condition_passed_percent_on_column_1", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnColumn_1);
            put("monthly_partition_min_sql_condition_passed_percent_on_column_2", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnColumn_2);
            put("monthly_partition_min_sql_condition_passed_percent_on_column_3", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnColumn_3);

            put("monthly_partition_max_sql_condition_failed_count_on_column_1", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnColumn_1);
            put("monthly_partition_max_sql_condition_failed_count_on_column_2", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnColumn_2);
            put("monthly_partition_max_sql_condition_failed_count_on_column_3", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnColumn_3);

            put("monthly_partition_min_sql_aggregated_expression_value_on_column", o -> o.monthlyPartitionMinSqlAggregatedExpressionValueOnColumn);
            put("monthly_partition_max_sql_aggregated_expression_value_on_column", o -> o.monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionMinValueCheckSpec monthlyPartitionMinSqlAggregatedExpressionValueOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionMaxValueCheckSpec monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnColumn_1() {
        return monthlyPartitionMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnColumn_1 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnColumn_1(ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_1, monthlyPartitionMinSqlConditionPassedPercentOnColumn_1));
        this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_1 = monthlyPartitionMinSqlConditionPassedPercentOnColumn_1;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnColumn_1, "monthly_partition_min_sql_condition_passed_percent_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnColumn_2() {
        return monthlyPartitionMinSqlConditionPassedPercentOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnColumn_2 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnColumn_2(ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_2, monthlyPartitionMinSqlConditionPassedPercentOnColumn_2));
        this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_2 = monthlyPartitionMinSqlConditionPassedPercentOnColumn_2;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnColumn_1, "monthly_partition_min_sql_condition_passed_percent_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnColumn_3() {
        return monthlyPartitionMinSqlConditionPassedPercentOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnColumn_3 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnColumn_3(ColumnMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_3, monthlyPartitionMinSqlConditionPassedPercentOnColumn_3));
        this.monthlyPartitionMinSqlConditionPassedPercentOnColumn_3 = monthlyPartitionMinSqlConditionPassedPercentOnColumn_3;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnColumn_3, "monthly_partition_min_sql_condition_passed_percent_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnColumn_1() {
        return monthlyPartitionMaxSqlConditionFailedCountOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnColumn_1 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnColumn_1(ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_1, monthlyPartitionMaxSqlConditionFailedCountOnColumn_1));
        this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_1 = monthlyPartitionMaxSqlConditionFailedCountOnColumn_1;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnColumn_1, "monthly_partition_max_sql_condition_failed_count_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnColumn_2() {
        return monthlyPartitionMaxSqlConditionFailedCountOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnColumn_2 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnColumn_2(ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_2, monthlyPartitionMaxSqlConditionFailedCountOnColumn_2));
        this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_2 = monthlyPartitionMaxSqlConditionFailedCountOnColumn_2;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnColumn_2, "monthly_partition_max_sql_condition_failed_count_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnColumn_3() {
        return monthlyPartitionMaxSqlConditionFailedCountOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnColumn_3 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnColumn_3(ColumnMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_3, monthlyPartitionMaxSqlConditionFailedCountOnColumn_3));
        this.monthlyPartitionMaxSqlConditionFailedCountOnColumn_3 = monthlyPartitionMaxSqlConditionFailedCountOnColumn_3;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnColumn_3, "monthly_partition_max_sql_condition_failed_count_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMinValueCheckSpec getMonthlyPartitionMinSqlAggregatedExpressionValueOnColumn() {
        return monthlyPartitionMinSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMonthlyPartitionMinSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMinValueCheckSpec monthlyPartitionMinSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlAggregatedExpressionValueOnColumn, monthlyPartitionMinSqlAggregatedExpressionValueOnColumn));
        this.monthlyPartitionMinSqlAggregatedExpressionValueOnColumn = monthlyPartitionMinSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(monthlyPartitionMinSqlAggregatedExpressionValueOnColumn, "monthly_partition_min_sql_aggregated_expression_value_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMaxValueCheckSpec getMonthlyPartitionMaxSqlAggregatedExpressionValueOnColumn() {
        return monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMonthlyPartitionMaxSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMaxValueCheckSpec monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn, monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn));
        this.monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn = monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlAggregatedExpressionValueOnColumn, "monthly_partition_max_sql_aggregated_expression_value_on_column");
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