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
public class TableSqlMonthlyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlMonthlyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_table_1", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnTable_1);
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_table_2", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnTable_2);
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_table_3", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnTable_3);

            put("monthly_checkpoint_max_sql_condition_failed_count_on_table_1", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnTable_1);
            put("monthly_checkpoint_max_sql_condition_failed_count_on_table_2", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnTable_2);
            put("monthly_checkpoint_max_sql_condition_failed_count_on_table_3", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnTable_3);

            put("monthly_checkpoint_min_sql_aggregated_expression_value_on_table", o -> o.monthlyCheckpointMinSqlAggregatedExpressionValueOnTable);
            put("monthly_checkpoint_max_sql_aggregated_expression_value_on_table", o -> o.monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionMinValueCheckSpec monthlyCheckpointMinSqlAggregatedExpressionValueOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionMaxValueCheckSpec monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnTable_1() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnTable_1(TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_1, monthlyCheckpointMinSqlConditionPassedPercentOnTable_1));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_1 = monthlyCheckpointMinSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnTable_1, "monthly_checkpoint_min_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnTable_2() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnTable_2(TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_2, monthlyCheckpointMinSqlConditionPassedPercentOnTable_2));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_2 = monthlyCheckpointMinSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnTable_2, "monthly_checkpoint_min_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnTable_3() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnTable_3(TableMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_3, monthlyCheckpointMinSqlConditionPassedPercentOnTable_3));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnTable_3 = monthlyCheckpointMinSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnTable_3, "monthly_checkpoint_min_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnTable_1() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnTable_1(TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_1, monthlyCheckpointMaxSqlConditionFailedCountOnTable_1));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_1 = monthlyCheckpointMaxSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnTable_1, "monthly_checkpoint_max_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnTable_2() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnTable_2(TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_2, monthlyCheckpointMaxSqlConditionFailedCountOnTable_2));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_2 = monthlyCheckpointMaxSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnTable_2, "monthly_checkpoint_max_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnTable_3() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnTable_3(TableMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_3, monthlyCheckpointMaxSqlConditionFailedCountOnTable_3));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnTable_3 = monthlyCheckpointMaxSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnTable_3, "monthly_checkpoint_max_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMinValueCheckSpec getMonthlyCheckpointMinSqlAggregatedExpressionValueOnTable() {
        return monthlyCheckpointMinSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMonthlyCheckpointMinSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMinValueCheckSpec monthlyCheckpointMinSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlAggregatedExpressionValueOnTable, monthlyCheckpointMinSqlAggregatedExpressionValueOnTable));
        this.monthlyCheckpointMinSqlAggregatedExpressionValueOnTable = monthlyCheckpointMinSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlAggregatedExpressionValueOnTable, "monthly_checkpoint_min_sql_aggregated_expression_value_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMaxValueCheckSpec getMonthlyCheckpointMaxSqlAggregatedExpressionValueOnTable() {
        return monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMonthlyCheckpointMaxSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMaxValueCheckSpec monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable, monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable));
        this.monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable = monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlAggregatedExpressionValueOnTable, "monthly_checkpoint_max_sql_aggregated_expression_value_on_table");
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