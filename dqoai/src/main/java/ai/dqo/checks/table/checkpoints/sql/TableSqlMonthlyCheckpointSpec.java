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
package ai.dqo.checks.table.checkpoints.sql;

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
public class TableSqlMonthlyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlMonthlyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_sql_condition_passed_percent_on_table_1", o -> o.monthlyCheckpointSqlConditionPassedPercentOnTable_1);
            put("monthly_checkpoint_sql_condition_passed_percent_on_table_2", o -> o.monthlyCheckpointSqlConditionPassedPercentOnTable_2);
            put("monthly_checkpoint_sql_condition_passed_percent_on_table_3", o -> o.monthlyCheckpointSqlConditionPassedPercentOnTable_3);

            put("monthly_checkpoint_sql_condition_failed_count_on_table_1", o -> o.monthlyCheckpointSqlConditionFailedCountOnTable_1);
            put("monthly_checkpoint_sql_condition_failed_count_on_table_2", o -> o.monthlyCheckpointSqlConditionFailedCountOnTable_2);
            put("monthly_checkpoint_sql_condition_failed_count_on_table_3", o -> o.monthlyCheckpointSqlConditionFailedCountOnTable_3);

            put("monthly_checkpoint_sql_aggregated_expression_value_on_table_min", o -> o.monthlyCheckpointSqlAggregatedExpressionValueOnTableMin);
            put("monthly_checkpoint_max_sql_aggregated_expression_value_on_table", o -> o.monthlyCheckpointSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyCheckpointSqlConditionPassedPercentOnTable_1() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionPassedPercentOnTable_1(TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionPassedPercentOnTable_1, monthlyCheckpointSqlConditionPassedPercentOnTable_1));
        this.monthlyCheckpointSqlConditionPassedPercentOnTable_1 = monthlyCheckpointSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionPassedPercentOnTable_1, "monthly_checkpoint_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyCheckpointSqlConditionPassedPercentOnTable_2() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionPassedPercentOnTable_2(TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionPassedPercentOnTable_2, monthlyCheckpointSqlConditionPassedPercentOnTable_2));
        this.monthlyCheckpointSqlConditionPassedPercentOnTable_2 = monthlyCheckpointSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionPassedPercentOnTable_2, "monthly_checkpoint_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyCheckpointSqlConditionPassedPercentOnTable_3() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionPassedPercentOnTable_3(TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionPassedPercentOnTable_3, monthlyCheckpointSqlConditionPassedPercentOnTable_3));
        this.monthlyCheckpointSqlConditionPassedPercentOnTable_3 = monthlyCheckpointSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionPassedPercentOnTable_3, "monthly_checkpoint_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyCheckpointSqlConditionFailedCountOnTable_1() {
        return monthlyCheckpointSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionFailedCountOnTable_1(TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionFailedCountOnTable_1, monthlyCheckpointSqlConditionFailedCountOnTable_1));
        this.monthlyCheckpointSqlConditionFailedCountOnTable_1 = monthlyCheckpointSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionFailedCountOnTable_1, "monthly_checkpoint_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyCheckpointSqlConditionFailedCountOnTable_2() {
        return monthlyCheckpointSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionFailedCountOnTable_2(TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionFailedCountOnTable_2, monthlyCheckpointSqlConditionFailedCountOnTable_2));
        this.monthlyCheckpointSqlConditionFailedCountOnTable_2 = monthlyCheckpointSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionFailedCountOnTable_2, "monthly_checkpoint_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyCheckpointSqlConditionFailedCountOnTable_3() {
        return monthlyCheckpointSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setMonthlyCheckpointSqlConditionFailedCountOnTable_3(TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionFailedCountOnTable_3, monthlyCheckpointSqlConditionFailedCountOnTable_3));
        this.monthlyCheckpointSqlConditionFailedCountOnTable_3 = monthlyCheckpointSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionFailedCountOnTable_3, "monthly_checkpoint_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMinCheckSpec getMonthlyCheckpointSqlAggregatedExpressionValueOnTableMin() {
        return monthlyCheckpointSqlAggregatedExpressionValueOnTableMin;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlAggregatedExpressionValueOnTableMin Check specification.
     */
    public void setMonthlyCheckpointSqlAggregatedExpressionValueOnTableMin(TableSqlAggregatedExpressionValueMinCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMin) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlAggregatedExpressionValueOnTableMin, monthlyCheckpointSqlAggregatedExpressionValueOnTableMin));
        this.monthlyCheckpointSqlAggregatedExpressionValueOnTableMin = monthlyCheckpointSqlAggregatedExpressionValueOnTableMin;
        propagateHierarchyIdToField(monthlyCheckpointSqlAggregatedExpressionValueOnTableMin, "monthly_checkpoint_sql_aggregated_expression_value_on_table_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMaxCheckSpec getMonthlyCheckpointSqlAggregatedExpressionValueOnTableMax() {
        return monthlyCheckpointSqlAggregatedExpressionValueOnTableMax;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlAggregatedExpressionValueOnTableMax Check specification.
     */
    public void setMonthlyCheckpointSqlAggregatedExpressionValueOnTableMax(TableSqlAggregatedExpressionValueMaxCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMax) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlAggregatedExpressionValueOnTableMax, monthlyCheckpointSqlAggregatedExpressionValueOnTableMax));
        this.monthlyCheckpointSqlAggregatedExpressionValueOnTableMax = monthlyCheckpointSqlAggregatedExpressionValueOnTableMax;
        propagateHierarchyIdToField(monthlyCheckpointSqlAggregatedExpressionValueOnTableMax, "monthly_checkpoint_sql_aggregated_expression_value_on_table_max");
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