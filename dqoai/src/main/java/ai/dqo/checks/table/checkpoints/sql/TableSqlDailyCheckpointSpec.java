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
public class TableSqlDailyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_min_sql_condition_passed_percent_on_table_1", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnTable_1);
            put("daily_checkpoint_min_sql_condition_passed_percent_on_table_2", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnTable_2);
            put("daily_checkpoint_min_sql_condition_passed_percent_on_table_3", o -> o.dailyCheckpointMinSqlConditionPassedPercentOnTable_3);

            put("daily_checkpoint_max_sql_condition_failed_count_on_table_1", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnTable_1);
            put("daily_checkpoint_max_sql_condition_failed_count_on_table_2", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnTable_2);
            put("daily_checkpoint_max_sql_condition_failed_count_on_table_3", o -> o.dailyCheckpointMaxSqlConditionFailedCountOnTable_3);

            put("daily_checkpoint_min_sql_aggregated_expression_value_on_table", o -> o.dailyCheckpointMinSqlAggregatedExpressionValueOnTable);
            put("daily_checkpoint_max_sql_aggregated_expression_value_on_table", o -> o.dailyCheckpointMaxSqlAggregatedExpressionValueOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionMinValueCheckSpec dailyCheckpointMinSqlAggregatedExpressionValueOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionMaxValueCheckSpec dailyCheckpointMaxSqlAggregatedExpressionValueOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return dailyCheckpointMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnTable_1() {
        return dailyCheckpointMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnTable_1(TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnTable_1, dailyCheckpointMinSqlConditionPassedPercentOnTable_1));
        this.dailyCheckpointMinSqlConditionPassedPercentOnTable_1 = dailyCheckpointMinSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnTable_1, "daily_checkpoint_min_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnTable_2() {
        return dailyCheckpointMinSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnTable_2(TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnTable_2, dailyCheckpointMinSqlConditionPassedPercentOnTable_2));
        this.dailyCheckpointMinSqlConditionPassedPercentOnTable_2 = dailyCheckpointMinSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnTable_1, "daily_checkpoint_min_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyCheckpointMinSqlConditionPassedPercentOnTable_3() {
        return dailyCheckpointMinSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setDailyCheckpointMinSqlConditionPassedPercentOnTable_3(TableMinSqlConditionPassedPercentCheckSpec dailyCheckpointMinSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlConditionPassedPercentOnTable_3, dailyCheckpointMinSqlConditionPassedPercentOnTable_3));
        this.dailyCheckpointMinSqlConditionPassedPercentOnTable_3 = dailyCheckpointMinSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(dailyCheckpointMinSqlConditionPassedPercentOnTable_3, "daily_checkpoint_min_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnTable_1() {
        return dailyCheckpointMaxSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnTable_1(TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnTable_1, dailyCheckpointMaxSqlConditionFailedCountOnTable_1));
        this.dailyCheckpointMaxSqlConditionFailedCountOnTable_1 = dailyCheckpointMaxSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnTable_1, "daily_checkpoint_max_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnTable_2() {
        return dailyCheckpointMaxSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnTable_2(TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnTable_2, dailyCheckpointMaxSqlConditionFailedCountOnTable_2));
        this.dailyCheckpointMaxSqlConditionFailedCountOnTable_2 = dailyCheckpointMaxSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnTable_2, "daily_checkpoint_max_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyCheckpointMaxSqlConditionFailedCountOnTable_3() {
        return dailyCheckpointMaxSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setDailyCheckpointMaxSqlConditionFailedCountOnTable_3(TableMaxSqlConditionFailedCountCheckSpec dailyCheckpointMaxSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlConditionFailedCountOnTable_3, dailyCheckpointMaxSqlConditionFailedCountOnTable_3));
        this.dailyCheckpointMaxSqlConditionFailedCountOnTable_3 = dailyCheckpointMaxSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlConditionFailedCountOnTable_3, "daily_checkpoint_max_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMinValueCheckSpec getDailyCheckpointMinSqlAggregatedExpressionValueOnTable() {
        return dailyCheckpointMinSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMinSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setDailyCheckpointMinSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMinValueCheckSpec dailyCheckpointMinSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinSqlAggregatedExpressionValueOnTable, dailyCheckpointMinSqlAggregatedExpressionValueOnTable));
        this.dailyCheckpointMinSqlAggregatedExpressionValueOnTable = dailyCheckpointMinSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(dailyCheckpointMinSqlAggregatedExpressionValueOnTable, "daily_checkpoint_min_sql_aggregated_expression_value_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMaxValueCheckSpec getDailyCheckpointMaxSqlAggregatedExpressionValueOnTable() {
        return dailyCheckpointMaxSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyCheckpointMaxSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setDailyCheckpointMaxSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMaxValueCheckSpec dailyCheckpointMaxSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxSqlAggregatedExpressionValueOnTable, dailyCheckpointMaxSqlAggregatedExpressionValueOnTable));
        this.dailyCheckpointMaxSqlAggregatedExpressionValueOnTable = dailyCheckpointMaxSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(dailyCheckpointMaxSqlAggregatedExpressionValueOnTable, "daily_checkpoint_max_sql_aggregated_expression_value_on_table");
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