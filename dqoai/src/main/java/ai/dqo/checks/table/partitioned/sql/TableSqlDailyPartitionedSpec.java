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
public class TableSqlDailyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sql_condition_passed_percent_on_table_1", o -> o.dailyPartitionSqlConditionPassedPercentOnTable_1);
            put("daily_partition_sql_condition_passed_percent_on_table_2", o -> o.dailyPartitionSqlConditionPassedPercentOnTable_2);
            put("daily_partition_sql_condition_passed_percent_on_table_3", o -> o.dailyPartitionSqlConditionPassedPercentOnTable_3);

            put("daily_partition_sql_condition_failed_count_on_table_1", o -> o.dailyPartitionSqlConditionFailedCountOnTable_1);
            put("daily_partition_sql_condition_failed_count_on_table_2", o -> o.dailyPartitionSqlConditionFailedCountOnTable_2);
            put("daily_partition_sql_condition_failed_count_on_table_3", o -> o.dailyPartitionSqlConditionFailedCountOnTable_3);

            put("daily_partition_sql_aggregated_expression_value_on_table_min", o -> o.dailyPartitionSqlAggregatedExpressionValueOnTableMin);
            put("daily_partition_sql_aggregated_expression_value_on_table_max", o -> o.dailyPartitionSqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec dailyPartitionSqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec dailyPartitionSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return dailyPartitionSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnTable_1() {
        return dailyPartitionSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnTable_1(TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnTable_1, dailyPartitionSqlConditionPassedPercentOnTable_1));
        this.dailyPartitionSqlConditionPassedPercentOnTable_1 = dailyPartitionSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnTable_1, "daily_partition_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnTable_2() {
        return dailyPartitionSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnTable_2(TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnTable_2, dailyPartitionSqlConditionPassedPercentOnTable_2));
        this.dailyPartitionSqlConditionPassedPercentOnTable_2 = dailyPartitionSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnTable_2, "daily_partition_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnTable_3() {
        return dailyPartitionSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnTable_3(TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnTable_3, dailyPartitionSqlConditionPassedPercentOnTable_3));
        this.dailyPartitionSqlConditionPassedPercentOnTable_3 = dailyPartitionSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnTable_3, "daily_partition_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnTable_1() {
        return dailyPartitionSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnTable_1(TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnTable_1, dailyPartitionSqlConditionFailedCountOnTable_1));
        this.dailyPartitionSqlConditionFailedCountOnTable_1 = dailyPartitionSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnTable_1, "daily_partition_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnTable_2() {
        return dailyPartitionSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnTable_2(TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnTable_2, dailyPartitionSqlConditionFailedCountOnTable_2));
        this.dailyPartitionSqlConditionFailedCountOnTable_2 = dailyPartitionSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnTable_2, "daily_partition_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnTable_3() {
        return dailyPartitionSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnTable_3(TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnTable_3, dailyPartitionSqlConditionFailedCountOnTable_3));
        this.dailyPartitionSqlConditionFailedCountOnTable_3 = dailyPartitionSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnTable_3, "daily_partition_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMinCheckSpec getDailyPartitionSqlAggregatedExpressionValueOnTableMin() {
        return dailyPartitionSqlAggregatedExpressionValueOnTableMin;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregatedExpressionValueOnTableMin Check specification.
     */
    public void setDailyPartitionSqlAggregatedExpressionValueOnTableMin(TableSqlAggregatedExpressionValueMinCheckSpec dailyPartitionSqlAggregatedExpressionValueOnTableMin) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregatedExpressionValueOnTableMin, dailyPartitionSqlAggregatedExpressionValueOnTableMin));
        this.dailyPartitionSqlAggregatedExpressionValueOnTableMin = dailyPartitionSqlAggregatedExpressionValueOnTableMin;
        propagateHierarchyIdToField(dailyPartitionSqlAggregatedExpressionValueOnTableMin, "daily_partition_sql_aggregated_expression_value_on_table_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMaxCheckSpec getDailyPartitionSqlAggregatedExpressionValueOnTableMax() {
        return dailyPartitionSqlAggregatedExpressionValueOnTableMax;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregatedExpressionValueOnTableMax Check specification.
     */
    public void setDailyPartitionSqlAggregatedExpressionValueOnTableMax(TableSqlAggregatedExpressionValueMaxCheckSpec dailyPartitionSqlAggregatedExpressionValueOnTableMax) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregatedExpressionValueOnTableMax, dailyPartitionSqlAggregatedExpressionValueOnTableMax));
        this.dailyPartitionSqlAggregatedExpressionValueOnTableMax = dailyPartitionSqlAggregatedExpressionValueOnTableMax;
        propagateHierarchyIdToField(dailyPartitionSqlAggregatedExpressionValueOnTableMax, "daily_partition_sql_aggregated_expression_value_on_table_max");
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