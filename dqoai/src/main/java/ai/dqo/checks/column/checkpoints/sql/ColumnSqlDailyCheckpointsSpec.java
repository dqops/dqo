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
public class ColumnSqlDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_min_sql_condition_passed_percent_on_column_1", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnColumn_1);
            put("daily_checkpoint_min_sql_condition_passed_percent_on_column_2", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnColumn_2);
            put("daily_checkpoint_min_sql_condition_passed_percent_on_column_3", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnColumn_3);

            put("daily_checkpoint_max_sql_condition_failed_count_on_column_1", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnColumn_1);
            put("daily_checkpoint_max_sql_condition_failed_count_on_column_2", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnColumn_2);
            put("daily_checkpoint_max_sql_condition_failed_count_on_column_3", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnColumn_3);

            put("daily_checkpoint_min_sql_aggregated_expression_value_on_column", o -> o.dailyCheckpointMinSqlAggregatedExpressionValueOnColumn);
            put("daily_checkpoint_max_sql_aggregated_expression_value_on_column", o -> o.dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionMinValueCheckSpec dailyCheckpointMinSqlAggregatedExpressionValueOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionMaxValueCheckSpec dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnColumn_1() {
        return dailyCheckpointMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnColumn_1() {
        return dailyCheckpointMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnColumn_1 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnColumn_1(ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_1, dailyCheckpointMinSqlConditionPassedPercentOnColumn_1));
        this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_1 = dailyCheckpointMinSqlConditionPassedPercentOnColumn_1;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnColumn_1, "daily_checkpoint_min_sql_condition_passed_percent_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnColumn_2() {
        return dailyCheckpointMinSqlConditionPassedPercentOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnColumn_2 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnColumn_2(ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_2, dailyCheckpointMinSqlConditionPassedPercentOnColumn_2));
        this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_2 = dailyCheckpointMinSqlConditionPassedPercentOnColumn_2;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnColumn_1, "daily_checkpoint_min_sql_condition_passed_percent_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnColumn_3() {
        return dailyCheckpointMinSqlConditionPassedPercentOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnColumn_3 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnColumn_3(ColumnMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_3, dailyCheckpointMinSqlConditionPassedPercentOnColumn_3));
        this.dailyCheckpointMinSqlConditionPassedPercentOnColumn_3 = dailyCheckpointMinSqlConditionPassedPercentOnColumn_3;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnColumn_3, "daily_checkpoint_min_sql_condition_passed_percent_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnColumn_1() {
        return dailyCheckpointMaxSqlConditionFailedCountOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnColumn_1 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnColumn_1(ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_1, dailyCheckpointMaxSqlConditionFailedCountOnColumn_1));
        this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_1 = dailyCheckpointMaxSqlConditionFailedCountOnColumn_1;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnColumn_1, "daily_checkpoint_max_sql_condition_failed_count_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnColumn_2() {
        return dailyCheckpointMaxSqlConditionFailedCountOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnColumn_2 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnColumn_2(ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_2, dailyCheckpointMaxSqlConditionFailedCountOnColumn_2));
        this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_2 = dailyCheckpointMaxSqlConditionFailedCountOnColumn_2;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnColumn_2, "daily_checkpoint_max_sql_condition_failed_count_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnColumn_3() {
        return dailyCheckpointMaxSqlConditionFailedCountOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnColumn_3 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnColumn_3(ColumnMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_3, dailyCheckpointMaxSqlConditionFailedCountOnColumn_3));
        this.dailyCheckpointMaxSqlConditionFailedCountOnColumn_3 = dailyCheckpointMaxSqlConditionFailedCountOnColumn_3;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnColumn_3, "daily_checkpoint_max_sql_condition_failed_count_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMinValueCheckSpec getDailyCheckpointMinSqlAggregatedExpressionValueOnColumn() {
        return dailyCheckpointMinSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setDailyCheckpointMinSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMinValueCheckSpec dailyCheckpointMinSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlAggregatedExpressionValueOnColumn, dailyCheckpointMinSqlAggregatedExpressionValueOnColumn));
        this.dailyCheckpointMinSqlAggregatedExpressionValueOnColumn = dailyCheckpointMinSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(dailyCheckpointMinSqlAggregatedExpressionValueOnColumn, "daily_checkpoint_min_sql_aggregated_expression_value_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMaxValueCheckSpec getDailyCheckpointMaxSqlAggregatedExpressionValueOnColumn() {
        return dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setDailyCheckpointMaxSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMaxValueCheckSpec dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn, dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn));
        this.dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn = dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlAggregatedExpressionValueOnColumn, "daily_checkpoint_max_sql_aggregated_expression_value_on_column");
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