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
            put("monthly_checkpoint_sql_condition_passed_percent_on_table", o -> o.monthlyCheckpointSqlConditionPassedPercentOnTable);

            put("monthly_checkpoint_sql_condition_failed_count_on_table", o -> o.monthlyCheckpointSqlConditionFailedCountOnTable);

            put("monthly_checkpoint_sql_aggregated_expression_value_on_table_min", o -> o.monthlyCheckpointSqlAggregatedExpressionValueOnTableMin);
            put("monthly_checkpoint_max_sql_aggregated_expression_value_on_table", o -> o.monthlyCheckpointSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec monthlyCheckpointSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyCheckpointSqlConditionPassedPercentOnTable() {
        return monthlyCheckpointSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionPassedPercentOnTable Check specification.
     */
    public void setMonthlyCheckpointSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec monthlyCheckpointSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionPassedPercentOnTable, monthlyCheckpointSqlConditionPassedPercentOnTable));
        this.monthlyCheckpointSqlConditionPassedPercentOnTable = monthlyCheckpointSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionPassedPercentOnTable, "monthly_checkpoint_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyCheckpointSqlConditionFailedCountOnTable() {
        return monthlyCheckpointSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointSqlConditionFailedCountOnTable Check specification.
     */
    public void setMonthlyCheckpointSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec monthlyCheckpointSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSqlConditionFailedCountOnTable, monthlyCheckpointSqlConditionFailedCountOnTable));
        this.monthlyCheckpointSqlConditionFailedCountOnTable = monthlyCheckpointSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(monthlyCheckpointSqlConditionFailedCountOnTable, "monthly_checkpoint_sql_condition_failed_count_on_table");
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