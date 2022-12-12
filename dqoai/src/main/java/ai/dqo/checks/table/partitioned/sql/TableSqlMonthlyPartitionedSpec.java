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
package ai.dqo.checks.table.partitioned.sql;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.sql.TableMaxSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableMinSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionMaxValueCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionMinValueCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class TableSqlMonthlyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlMonthlyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_min_sql_condition_passed_percent_on_table_1", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnTable_1);
            put("monthly_partition_min_sql_condition_passed_percent_on_table_2", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnTable_2);
            put("monthly_partition_min_sql_condition_passed_percent_on_table_3", o -> o.monthlyPartitionMinSqlConditionPassedPercentOnTable_3);

            put("monthly_partition_max_sql_condition_failed_count_on_table_1", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnTable_1);
            put("monthly_partition_max_sql_condition_failed_count_on_table_2", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnTable_2);
            put("monthly_partition_max_sql_condition_failed_count_on_table_3", o -> o.monthlyPartitionMaxSqlConditionFailedCountOnTable_3);

            put("monthly_partition_min_sql_aggregated_expression_value_on_table", o -> o.monthlyPartitionMinSqlAggregatedExpressionValueOnTable);
            put("monthly_partition_max_sql_aggregated_expression_value_on_table", o -> o.monthlyPartitionMaxSqlAggregatedExpressionValueOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionMinValueCheckSpec monthlyPartitionMinSqlAggregatedExpressionValueOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionMaxValueCheckSpec monthlyPartitionMaxSqlAggregatedExpressionValueOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return monthlyPartitionMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnTable_1() {
        return monthlyPartitionMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnTable_1(TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnTable_1, monthlyPartitionMinSqlConditionPassedPercentOnTable_1));
        this.monthlyPartitionMinSqlConditionPassedPercentOnTable_1 = monthlyPartitionMinSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnTable_1, "monthly_partition_min_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnTable_2() {
        return monthlyPartitionMinSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnTable_2(TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnTable_2, monthlyPartitionMinSqlConditionPassedPercentOnTable_2));
        this.monthlyPartitionMinSqlConditionPassedPercentOnTable_2 = monthlyPartitionMinSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnTable_1, "monthly_partition_min_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyPartitionMinSqlConditionPassedPercentOnTable_3() {
        return monthlyPartitionMinSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setMonthlyPartitionMinSqlConditionPassedPercentOnTable_3(TableMinSqlConditionPassedPercentCheckSpec monthlyPartitionMinSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlConditionPassedPercentOnTable_3, monthlyPartitionMinSqlConditionPassedPercentOnTable_3));
        this.monthlyPartitionMinSqlConditionPassedPercentOnTable_3 = monthlyPartitionMinSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(monthlyPartitionMinSqlConditionPassedPercentOnTable_3, "monthly_partition_min_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnTable_1() {
        return monthlyPartitionMaxSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnTable_1(TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnTable_1, monthlyPartitionMaxSqlConditionFailedCountOnTable_1));
        this.monthlyPartitionMaxSqlConditionFailedCountOnTable_1 = monthlyPartitionMaxSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnTable_1, "monthly_partition_max_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnTable_2() {
        return monthlyPartitionMaxSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnTable_2(TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnTable_2, monthlyPartitionMaxSqlConditionFailedCountOnTable_2));
        this.monthlyPartitionMaxSqlConditionFailedCountOnTable_2 = monthlyPartitionMaxSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnTable_2, "monthly_partition_max_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyPartitionMaxSqlConditionFailedCountOnTable_3() {
        return monthlyPartitionMaxSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setMonthlyPartitionMaxSqlConditionFailedCountOnTable_3(TableMaxSqlConditionFailedCountCheckSpec monthlyPartitionMaxSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlConditionFailedCountOnTable_3, monthlyPartitionMaxSqlConditionFailedCountOnTable_3));
        this.monthlyPartitionMaxSqlConditionFailedCountOnTable_3 = monthlyPartitionMaxSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlConditionFailedCountOnTable_3, "monthly_partition_max_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMinValueCheckSpec getMonthlyPartitionMinSqlAggregatedExpressionValueOnTable() {
        return monthlyPartitionMinSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMinSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMonthlyPartitionMinSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMinValueCheckSpec monthlyPartitionMinSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinSqlAggregatedExpressionValueOnTable, monthlyPartitionMinSqlAggregatedExpressionValueOnTable));
        this.monthlyPartitionMinSqlAggregatedExpressionValueOnTable = monthlyPartitionMinSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(monthlyPartitionMinSqlAggregatedExpressionValueOnTable, "monthly_partition_min_sql_aggregated_expression_value_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMaxValueCheckSpec getMonthlyPartitionMaxSqlAggregatedExpressionValueOnTable() {
        return monthlyPartitionMaxSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionMaxSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMonthlyPartitionMaxSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMaxValueCheckSpec monthlyPartitionMaxSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxSqlAggregatedExpressionValueOnTable, monthlyPartitionMaxSqlAggregatedExpressionValueOnTable));
        this.monthlyPartitionMaxSqlAggregatedExpressionValueOnTable = monthlyPartitionMaxSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(monthlyPartitionMaxSqlAggregatedExpressionValueOnTable, "monthly_partition_max_sql_aggregated_expression_value_on_table");
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