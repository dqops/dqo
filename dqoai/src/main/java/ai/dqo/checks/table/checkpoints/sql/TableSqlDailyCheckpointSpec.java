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
public class TableSqlDailyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_sql_condition_passed_percent_on_table", o -> o.dailyCheckpointSqlConditionPassedPercentOnTable);

            put("daily_checkpoint_sql_condition_failed_count_on_table", o -> o.dailyCheckpointSqlConditionFailedCountOnTable);

            put("daily_checkpoint_sql_aggregated_expression_value_on_table_min", o -> o.dailyCheckpointSqlAggregatedExpressionValueOnTableMin);
            put("daily_checkpoint_sql_aggregated_expression_value_on_table_max", o -> o.dailyCheckpointSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return dailyCheckpointSqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyCheckpointSqlConditionPassedPercentOnTable() {
        return dailyCheckpointSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionPassedPercentOnTable Check specification.
     */
    public void setDailyCheckpointSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionPassedPercentOnTable, dailyCheckpointSqlConditionPassedPercentOnTable));
        this.dailyCheckpointSqlConditionPassedPercentOnTable = dailyCheckpointSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionPassedPercentOnTable, "daily_checkpoint_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyCheckpointSqlConditionFailedCountOnTable() {
        return dailyCheckpointSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionFailedCountOnTable Check specification.
     */
    public void setDailyCheckpointSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionFailedCountOnTable, dailyCheckpointSqlConditionFailedCountOnTable));
        this.dailyCheckpointSqlConditionFailedCountOnTable = dailyCheckpointSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionFailedCountOnTable, "daily_checkpoint_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMinCheckSpec getDailyCheckpointSqlAggregatedExpressionValueOnTableMin() {
        return dailyCheckpointSqlAggregatedExpressionValueOnTableMin;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlAggregatedExpressionValueOnTableMin Check specification.
     */
    public void setDailyCheckpointSqlAggregatedExpressionValueOnTableMin(TableSqlAggregatedExpressionValueMinCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMin) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlAggregatedExpressionValueOnTableMin, dailyCheckpointSqlAggregatedExpressionValueOnTableMin));
        this.dailyCheckpointSqlAggregatedExpressionValueOnTableMin = dailyCheckpointSqlAggregatedExpressionValueOnTableMin;
        propagateHierarchyIdToField(dailyCheckpointSqlAggregatedExpressionValueOnTableMin, "daily_checkpoint_sql_aggregated_expression_value_on_table_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMaxCheckSpec getDailyCheckpointSqlAggregatedExpressionValueOnTableMax() {
        return dailyCheckpointSqlAggregatedExpressionValueOnTableMax;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlAggregatedExpressionValueOnTableMax Check specification.
     */
    public void setDailyCheckpointSqlAggregatedExpressionValueOnTableMax(TableSqlAggregatedExpressionValueMaxCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMax) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlAggregatedExpressionValueOnTableMax, dailyCheckpointSqlAggregatedExpressionValueOnTableMax));
        this.dailyCheckpointSqlAggregatedExpressionValueOnTableMax = dailyCheckpointSqlAggregatedExpressionValueOnTableMax;
        propagateHierarchyIdToField(dailyCheckpointSqlAggregatedExpressionValueOnTableMax, "daily_checkpoint_sql_aggregated_expression_value_on_table_max");
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