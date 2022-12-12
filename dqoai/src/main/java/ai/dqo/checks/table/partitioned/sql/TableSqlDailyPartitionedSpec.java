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
public class TableSqlDailyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_min_sql_condition_passed_percent_on_table_1", o -> o.dailyPartitionMinSqlConditionPassedPercentOnTable_1);
            put("daily_partition_min_sql_condition_passed_percent_on_table_2", o -> o.dailyPartitionMinSqlConditionPassedPercentOnTable_2);
            put("daily_partition_min_sql_condition_passed_percent_on_table_3", o -> o.dailyPartitionMinSqlConditionPassedPercentOnTable_3);

            put("daily_partition_max_sql_condition_failed_count_on_table_1", o -> o.dailyPartitionMaxSqlConditionFailedCountOnTable_1);
            put("daily_partition_max_sql_condition_failed_count_on_table_2", o -> o.dailyPartitionMaxSqlConditionFailedCountOnTable_2);
            put("daily_partition_max_sql_condition_failed_count_on_table_3", o -> o.dailyPartitionMaxSqlConditionFailedCountOnTable_3);

            put("daily_partition_min_sql_aggregated_expression_value_on_table", o -> o.dailyPartitionMinSqlAggregatedExpressionValueOnTable);
            put("daily_partition_max_sql_aggregated_expression_value_on_table", o -> o.dailyPartitionMaxSqlAggregatedExpressionValueOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionMinValueCheckSpec dailyPartitionMinSqlAggregatedExpressionValueOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionMaxValueCheckSpec dailyPartitionMaxSqlAggregatedExpressionValueOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return dailyPartitionMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnTable_1() {
        return dailyPartitionMinSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnTable_1(TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnTable_1, dailyPartitionMinSqlConditionPassedPercentOnTable_1));
        this.dailyPartitionMinSqlConditionPassedPercentOnTable_1 = dailyPartitionMinSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnTable_1, "daily_partition_min_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnTable_2() {
        return dailyPartitionMinSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnTable_2(TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnTable_2, dailyPartitionMinSqlConditionPassedPercentOnTable_2));
        this.dailyPartitionMinSqlConditionPassedPercentOnTable_2 = dailyPartitionMinSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnTable_1, "daily_partition_min_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnTable_3() {
        return dailyPartitionMinSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnTable_3(TableMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnTable_3, dailyPartitionMinSqlConditionPassedPercentOnTable_3));
        this.dailyPartitionMinSqlConditionPassedPercentOnTable_3 = dailyPartitionMinSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnTable_3, "daily_partition_min_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnTable_1() {
        return dailyPartitionMaxSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnTable_1(TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnTable_1, dailyPartitionMaxSqlConditionFailedCountOnTable_1));
        this.dailyPartitionMaxSqlConditionFailedCountOnTable_1 = dailyPartitionMaxSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnTable_1, "daily_partition_max_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnTable_2() {
        return dailyPartitionMaxSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnTable_2(TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnTable_2, dailyPartitionMaxSqlConditionFailedCountOnTable_2));
        this.dailyPartitionMaxSqlConditionFailedCountOnTable_2 = dailyPartitionMaxSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnTable_2, "daily_partition_max_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnTable_3() {
        return dailyPartitionMaxSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnTable_3(TableMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnTable_3, dailyPartitionMaxSqlConditionFailedCountOnTable_3));
        this.dailyPartitionMaxSqlConditionFailedCountOnTable_3 = dailyPartitionMaxSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnTable_3, "daily_partition_max_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMinValueCheckSpec getDailyPartitionMinSqlAggregatedExpressionValueOnTable() {
        return dailyPartitionMinSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setDailyPartitionMinSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMinValueCheckSpec dailyPartitionMinSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlAggregatedExpressionValueOnTable, dailyPartitionMinSqlAggregatedExpressionValueOnTable));
        this.dailyPartitionMinSqlAggregatedExpressionValueOnTable = dailyPartitionMinSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(dailyPartitionMinSqlAggregatedExpressionValueOnTable, "daily_partition_min_sql_aggregated_expression_value_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMaxValueCheckSpec getDailyPartitionMaxSqlAggregatedExpressionValueOnTable() {
        return dailyPartitionMaxSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setDailyPartitionMaxSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMaxValueCheckSpec dailyPartitionMaxSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlAggregatedExpressionValueOnTable, dailyPartitionMaxSqlAggregatedExpressionValueOnTable));
        this.dailyPartitionMaxSqlAggregatedExpressionValueOnTable = dailyPartitionMaxSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(dailyPartitionMaxSqlAggregatedExpressionValueOnTable, "daily_partition_max_sql_aggregated_expression_value_on_table");
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