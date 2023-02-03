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
public class ColumnSqlDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_sql_condition_passed_percent_on_column", o -> o.dailyCheckpointSqlConditionPassedPercentOnColumn);
            put("daily_checkpoint_sql_condition_failed_count_on_column", o -> o.dailyCheckpointSqlConditionFailedCountOnColumn);

            put("daily_checkpoint_sql_aggregate_expr_column", o -> o.dailyCheckpointSqlAggregateExprColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.")
    private ColumnSqlAggregateExprCheckSpec dailyCheckpointSqlAggregateExprColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getDailyCheckpointSqlConditionPassedPercentOnColumn() {
        return dailyCheckpointSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setDailyCheckpointSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionPassedPercentOnColumn, dailyCheckpointSqlConditionPassedPercentOnColumn));
        this.dailyCheckpointSqlConditionPassedPercentOnColumn = dailyCheckpointSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionPassedPercentOnColumn, "daily_checkpoint_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCountCheckSpec getDailyCheckpointSqlConditionFailedCountOnColumn() {
        return dailyCheckpointSqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionFailedCountOnColumn Check specification.
     */
    public void setDailyCheckpointSqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionFailedCountOnColumn, dailyCheckpointSqlConditionFailedCountOnColumn));
        this.dailyCheckpointSqlConditionFailedCountOnColumn = dailyCheckpointSqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionFailedCountOnColumn, "daily_checkpoint_sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExprCheckSpec getDailyCheckpointSqlAggregateExprColumn() {
        return dailyCheckpointSqlAggregateExprColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlAggregateExprColumn Check specification.
     */
    public void setDailyCheckpointSqlAggregateExprColumn(ColumnSqlAggregateExprCheckSpec dailyCheckpointSqlAggregateExprColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlAggregateExprColumn, dailyCheckpointSqlAggregateExprColumn));
        this.dailyCheckpointSqlAggregateExprColumn = dailyCheckpointSqlAggregateExprColumn;
        propagateHierarchyIdToField(dailyCheckpointSqlAggregateExprColumn, "daily_checkpoint_sql_aggregated_expression_value_on_column_min");
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