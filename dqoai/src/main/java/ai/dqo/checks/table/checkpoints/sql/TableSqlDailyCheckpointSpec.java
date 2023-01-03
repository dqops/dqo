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
            put("daily_checkpoint_sql_condition_passed_percent_on_table_1", o -> o.dailyCheckpointSqlConditionPassedPercentOnTable_1);
            put("daily_checkpoint_sql_condition_passed_percent_on_table_2", o -> o.dailyCheckpointSqlConditionPassedPercentOnTable_2);
            put("daily_checkpoint_sql_condition_passed_percent_on_table_3", o -> o.dailyCheckpointSqlConditionPassedPercentOnTable_3);

            put("daily_checkpoint_sql_condition_failed_count_on_table_1", o -> o.dailyCheckpointSqlConditionFailedCountOnTable_1);
            put("daily_checkpoint_sql_condition_failed_count_on_table_2", o -> o.dailyCheckpointSqlConditionFailedCountOnTable_2);
            put("daily_checkpoint_sql_condition_failed_count_on_table_3", o -> o.dailyCheckpointSqlConditionFailedCountOnTable_3);

            put("daily_checkpoint_sql_aggregated_expression_value_on_table_min", o -> o.dailyCheckpointSqlAggregatedExpressionValueOnTableMin);
            put("daily_checkpoint_sql_aggregated_expression_value_on_table_max", o -> o.dailyCheckpointSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec dailyCheckpointSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return dailyCheckpointSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyCheckpointSqlConditionPassedPercentOnTable_1() {
        return dailyCheckpointSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setDailyCheckpointSqlConditionPassedPercentOnTable_1(TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionPassedPercentOnTable_1, dailyCheckpointSqlConditionPassedPercentOnTable_1));
        this.dailyCheckpointSqlConditionPassedPercentOnTable_1 = dailyCheckpointSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionPassedPercentOnTable_1, "daily_checkpoint_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyCheckpointSqlConditionPassedPercentOnTable_2() {
        return dailyCheckpointSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setDailyCheckpointSqlConditionPassedPercentOnTable_2(TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionPassedPercentOnTable_2, dailyCheckpointSqlConditionPassedPercentOnTable_2));
        this.dailyCheckpointSqlConditionPassedPercentOnTable_2 = dailyCheckpointSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionPassedPercentOnTable_2, "daily_checkpoint_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyCheckpointSqlConditionPassedPercentOnTable_3() {
        return dailyCheckpointSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setDailyCheckpointSqlConditionPassedPercentOnTable_3(TableSqlConditionPassedPercentCheckSpec dailyCheckpointSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionPassedPercentOnTable_3, dailyCheckpointSqlConditionPassedPercentOnTable_3));
        this.dailyCheckpointSqlConditionPassedPercentOnTable_3 = dailyCheckpointSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionPassedPercentOnTable_3, "daily_checkpoint_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyCheckpointSqlConditionFailedCountOnTable_1() {
        return dailyCheckpointSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setDailyCheckpointSqlConditionFailedCountOnTable_1(TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionFailedCountOnTable_1, dailyCheckpointSqlConditionFailedCountOnTable_1));
        this.dailyCheckpointSqlConditionFailedCountOnTable_1 = dailyCheckpointSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionFailedCountOnTable_1, "daily_checkpoint_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyCheckpointSqlConditionFailedCountOnTable_2() {
        return dailyCheckpointSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setDailyCheckpointSqlConditionFailedCountOnTable_2(TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionFailedCountOnTable_2, dailyCheckpointSqlConditionFailedCountOnTable_2));
        this.dailyCheckpointSqlConditionFailedCountOnTable_2 = dailyCheckpointSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionFailedCountOnTable_2, "daily_checkpoint_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyCheckpointSqlConditionFailedCountOnTable_3() {
        return dailyCheckpointSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setDailyCheckpointSqlConditionFailedCountOnTable_3(TableSqlConditionFailedCountCheckSpec dailyCheckpointSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSqlConditionFailedCountOnTable_3, dailyCheckpointSqlConditionFailedCountOnTable_3));
        this.dailyCheckpointSqlConditionFailedCountOnTable_3 = dailyCheckpointSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(dailyCheckpointSqlConditionFailedCountOnTable_3, "daily_checkpoint_sql_condition_failed_count_on_table_3");
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