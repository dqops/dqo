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
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionValueMaxCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionValueMinCheckSpec;
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
            put("monthly_partition_sql_condition_passed_percent_on_table_1", o -> o.monthlyPartitionSqlConditionPassedPercentOnTable_1);
            put("monthly_partition_sql_condition_passed_percent_on_table_2", o -> o.monthlyPartitionSqlConditionPassedPercentOnTable_2);
            put("monthly_partition_sql_condition_passed_percent_on_table_3", o -> o.monthlyPartitionSqlConditionPassedPercentOnTable_3);

            put("monthly_partition_sql_condition_failed_count_on_table_1", o -> o.monthlyPartitionSqlConditionFailedCountOnTable_1);
            put("monthly_partition_sql_condition_failed_count_on_table_2", o -> o.monthlyPartitionSqlConditionFailedCountOnTable_2);
            put("monthly_partition_sql_condition_failed_count_on_table_3", o -> o.monthlyPartitionSqlConditionFailedCountOnTable_3);

            put("monthly_partition_sql_aggregated_expression_value_on_table_min", o -> o.monthlyPartitionSqlAggregatedExpressionValueOnTableMin);
            put("monthly_partition_sql_aggregated_expression_value_on_table_max", o -> o.monthlyPartitionSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec monthlyPartitionSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec monthlyPartitionSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return monthlyPartitionSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnTable_1() {
        return monthlyPartitionSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnTable_1(TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnTable_1, monthlyPartitionSqlConditionPassedPercentOnTable_1));
        this.monthlyPartitionSqlConditionPassedPercentOnTable_1 = monthlyPartitionSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnTable_1, "monthly_partition_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnTable_2() {
        return monthlyPartitionSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnTable_2(TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnTable_2, monthlyPartitionSqlConditionPassedPercentOnTable_2));
        this.monthlyPartitionSqlConditionPassedPercentOnTable_2 = monthlyPartitionSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnTable_2, "monthly_partition_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnTable_3() {
        return monthlyPartitionSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnTable_3(TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnTable_3, monthlyPartitionSqlConditionPassedPercentOnTable_3));
        this.monthlyPartitionSqlConditionPassedPercentOnTable_3 = monthlyPartitionSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnTable_3, "monthly_partition_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyPartitionSqlConditionFailedCountOnTable_1() {
        return monthlyPartitionSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedCountOnTable_1(TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedCountOnTable_1, monthlyPartitionSqlConditionFailedCountOnTable_1));
        this.monthlyPartitionSqlConditionFailedCountOnTable_1 = monthlyPartitionSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedCountOnTable_1, "monthly_partition_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyPartitionSqlConditionFailedCountOnTable_2() {
        return monthlyPartitionSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedCountOnTable_2(TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedCountOnTable_2, monthlyPartitionSqlConditionFailedCountOnTable_2));
        this.monthlyPartitionSqlConditionFailedCountOnTable_2 = monthlyPartitionSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedCountOnTable_2, "monthly_partition_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyPartitionSqlConditionFailedCountOnTable_3() {
        return monthlyPartitionSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedCountOnTable_3(TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedCountOnTable_3, monthlyPartitionSqlConditionFailedCountOnTable_3));
        this.monthlyPartitionSqlConditionFailedCountOnTable_3 = monthlyPartitionSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedCountOnTable_3, "monthly_partition_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMinCheckSpec getMonthlyPartitionSqlAggregatedExpressionValueOnTableMin() {
        return monthlyPartitionSqlAggregatedExpressionValueOnTableMin;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregatedExpressionValueOnTableMin Check specification.
     */
    public void setMonthlyPartitionSqlAggregatedExpressionValueOnTableMin(TableSqlAggregatedExpressionValueMinCheckSpec monthlyPartitionSqlAggregatedExpressionValueOnTableMin) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregatedExpressionValueOnTableMin, monthlyPartitionSqlAggregatedExpressionValueOnTableMin));
        this.monthlyPartitionSqlAggregatedExpressionValueOnTableMin = monthlyPartitionSqlAggregatedExpressionValueOnTableMin;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregatedExpressionValueOnTableMin, "monthly_partition_sql_aggregated_expression_value_on_table_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMaxCheckSpec getMonthlyPartitionSqlAggregatedExpressionValueOnTableMax() {
        return monthlyPartitionSqlAggregatedExpressionValueOnTableMax;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregatedExpressionValueOnTableMax Check specification.
     */
    public void setMonthlyPartitionSqlAggregatedExpressionValueOnTableMax(TableSqlAggregatedExpressionValueMaxCheckSpec monthlyPartitionSqlAggregatedExpressionValueOnTableMax) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregatedExpressionValueOnTableMax, monthlyPartitionSqlAggregatedExpressionValueOnTableMax));
        this.monthlyPartitionSqlAggregatedExpressionValueOnTableMax = monthlyPartitionSqlAggregatedExpressionValueOnTableMax;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregatedExpressionValueOnTableMax, "monthly_partition_sql_aggregated_expression_value_on_table_max");
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