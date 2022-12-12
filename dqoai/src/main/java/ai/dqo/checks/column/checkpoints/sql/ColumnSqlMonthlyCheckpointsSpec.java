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
package ai.dqo.checks.column.checkpoints.sql;

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
public class ColumnSqlMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_column_1", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1);
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_column_2", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2);
            put("monthly_checkpoint_min_sql_condition_passed_percent_on_column_3", o -> o.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3);

            put("monthly_checkpoint_max_sql_condition_failed_count_on_column_1", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1);
            put("monthly_checkpoint_max_sql_condition_failed_count_on_column_2", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2);
            put("monthly_checkpoint_max_sql_condition_failed_count_on_column_3", o -> o.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3);

            put("monthly_checkpoint_min_sql_aggregated_expression_value_on_column", o -> o.monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn);
            put("monthly_checkpoint_max_sql_aggregated_expression_value_on_column", o -> o.monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionMinValueCheckSpec monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionMaxValueCheckSpec monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_1() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_1(ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1, monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1 = monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1, "monthly_checkpoint_min_sql_condition_passed_percent_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_2() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_2(ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2, monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2 = monthlyCheckpointMinSqlConditionPassedPercentOnColumn_2;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnColumn_1, "monthly_checkpoint_min_sql_condition_passed_percent_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_3() {
        return monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3 Check specification.
     */
    public void setMonthlyCheckpointMinSqlConditionPassedPercentOnColumn_3(ColumnMinSqlConditionPassedPercentCheckSpec monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3, monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3));
        this.monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3 = monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlConditionPassedPercentOnColumn_3, "monthly_checkpoint_min_sql_condition_passed_percent_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_1() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_1(ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1, monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1 = monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnColumn_1, "monthly_checkpoint_max_sql_condition_failed_count_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_2() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_2(ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2, monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2 = monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnColumn_2, "monthly_checkpoint_max_sql_condition_failed_count_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_3() {
        return monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3 Check specification.
     */
    public void setMonthlyCheckpointMaxSqlConditionFailedCountOnColumn_3(ColumnMaxSqlConditionFailedCountCheckSpec monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3, monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3));
        this.monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3 = monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlConditionFailedCountOnColumn_3, "monthly_checkpoint_max_sql_condition_failed_count_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMinValueCheckSpec getMonthlyCheckpointMinSqlAggregatedExpressionValueOnColumn() {
        return monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMonthlyCheckpointMinSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMinValueCheckSpec monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn, monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn));
        this.monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn = monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(monthlyCheckpointMinSqlAggregatedExpressionValueOnColumn, "monthly_checkpoint_min_sql_aggregated_expression_value_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMaxValueCheckSpec getMonthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn() {
        return monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMonthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMaxValueCheckSpec monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn, monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn));
        this.monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn = monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(monthlyCheckpointMaxSqlAggregatedExpressionValueOnColumn, "monthly_checkpoint_max_sql_aggregated_expression_value_on_column");
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