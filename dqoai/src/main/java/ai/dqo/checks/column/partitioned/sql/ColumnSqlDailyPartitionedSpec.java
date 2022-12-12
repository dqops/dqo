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
package ai.dqo.checks.column.partitioned.sql;

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
public class ColumnSqlDailyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlDailyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_min_sql_condition_passed_percent_on_column_1", o -> o.dailyPartitionMinSqlConditionPassedPercentOnColumn_1);
            put("daily_partition_min_sql_condition_passed_percent_on_column_2", o -> o.dailyPartitionMinSqlConditionPassedPercentOnColumn_2);
            put("daily_partition_min_sql_condition_passed_percent_on_column_3", o -> o.dailyPartitionMinSqlConditionPassedPercentOnColumn_3);

            put("daily_partition_max_sql_condition_failed_count_on_column_1", o -> o.dailyPartitionMaxSqlConditionFailedCountOnColumn_1);
            put("daily_partition_max_sql_condition_failed_count_on_column_2", o -> o.dailyPartitionMaxSqlConditionFailedCountOnColumn_2);
            put("daily_partition_max_sql_condition_failed_count_on_column_3", o -> o.dailyPartitionMaxSqlConditionFailedCountOnColumn_3);

            put("daily_partition_min_sql_aggregated_expression_value_on_column", o -> o.dailyPartitionMinSqlAggregatedExpressionValueOnColumn);
            put("daily_partition_max_sql_aggregated_expression_value_on_column", o -> o.dailyPartitionMaxSqlAggregatedExpressionValueOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionMinValueCheckSpec dailyPartitionMinSqlAggregatedExpressionValueOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionMaxValueCheckSpec dailyPartitionMaxSqlAggregatedExpressionValueOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnColumn_1() {
        return dailyPartitionMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnColumn_1() {
        return dailyPartitionMinSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnColumn_1 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnColumn_1(ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnColumn_1, dailyPartitionMinSqlConditionPassedPercentOnColumn_1));
        this.dailyPartitionMinSqlConditionPassedPercentOnColumn_1 = dailyPartitionMinSqlConditionPassedPercentOnColumn_1;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnColumn_1, "daily_partition_min_sql_condition_passed_percent_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnColumn_2() {
        return dailyPartitionMinSqlConditionPassedPercentOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnColumn_2 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnColumn_2(ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnColumn_2, dailyPartitionMinSqlConditionPassedPercentOnColumn_2));
        this.dailyPartitionMinSqlConditionPassedPercentOnColumn_2 = dailyPartitionMinSqlConditionPassedPercentOnColumn_2;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnColumn_1, "daily_partition_min_sql_condition_passed_percent_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getDailyPartitionMinSqlConditionPassedPercentOnColumn_3() {
        return dailyPartitionMinSqlConditionPassedPercentOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlConditionPassedPercentOnColumn_3 Check specification.
     */
    public void setDailyPartitionMinSqlConditionPassedPercentOnColumn_3(ColumnMinSqlConditionPassedPercentCheckSpec dailyPartitionMinSqlConditionPassedPercentOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlConditionPassedPercentOnColumn_3, dailyPartitionMinSqlConditionPassedPercentOnColumn_3));
        this.dailyPartitionMinSqlConditionPassedPercentOnColumn_3 = dailyPartitionMinSqlConditionPassedPercentOnColumn_3;
        propagateHierarchyIdToField(dailyPartitionMinSqlConditionPassedPercentOnColumn_3, "daily_partition_min_sql_condition_passed_percent_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnColumn_1() {
        return dailyPartitionMaxSqlConditionFailedCountOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnColumn_1 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnColumn_1(ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnColumn_1, dailyPartitionMaxSqlConditionFailedCountOnColumn_1));
        this.dailyPartitionMaxSqlConditionFailedCountOnColumn_1 = dailyPartitionMaxSqlConditionFailedCountOnColumn_1;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnColumn_1, "daily_partition_max_sql_condition_failed_count_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnColumn_2() {
        return dailyPartitionMaxSqlConditionFailedCountOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnColumn_2 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnColumn_2(ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnColumn_2, dailyPartitionMaxSqlConditionFailedCountOnColumn_2));
        this.dailyPartitionMaxSqlConditionFailedCountOnColumn_2 = dailyPartitionMaxSqlConditionFailedCountOnColumn_2;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnColumn_2, "daily_partition_max_sql_condition_failed_count_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getDailyPartitionMaxSqlConditionFailedCountOnColumn_3() {
        return dailyPartitionMaxSqlConditionFailedCountOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlConditionFailedCountOnColumn_3 Check specification.
     */
    public void setDailyPartitionMaxSqlConditionFailedCountOnColumn_3(ColumnMaxSqlConditionFailedCountCheckSpec dailyPartitionMaxSqlConditionFailedCountOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlConditionFailedCountOnColumn_3, dailyPartitionMaxSqlConditionFailedCountOnColumn_3));
        this.dailyPartitionMaxSqlConditionFailedCountOnColumn_3 = dailyPartitionMaxSqlConditionFailedCountOnColumn_3;
        propagateHierarchyIdToField(dailyPartitionMaxSqlConditionFailedCountOnColumn_3, "daily_partition_max_sql_condition_failed_count_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMinValueCheckSpec getDailyPartitionMinSqlAggregatedExpressionValueOnColumn() {
        return dailyPartitionMinSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMinSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setDailyPartitionMinSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMinValueCheckSpec dailyPartitionMinSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinSqlAggregatedExpressionValueOnColumn, dailyPartitionMinSqlAggregatedExpressionValueOnColumn));
        this.dailyPartitionMinSqlAggregatedExpressionValueOnColumn = dailyPartitionMinSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(dailyPartitionMinSqlAggregatedExpressionValueOnColumn, "daily_partition_min_sql_aggregated_expression_value_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMaxValueCheckSpec getDailyPartitionMaxSqlAggregatedExpressionValueOnColumn() {
        return dailyPartitionMaxSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionMaxSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setDailyPartitionMaxSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMaxValueCheckSpec dailyPartitionMaxSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxSqlAggregatedExpressionValueOnColumn, dailyPartitionMaxSqlAggregatedExpressionValueOnColumn));
        this.dailyPartitionMaxSqlAggregatedExpressionValueOnColumn = dailyPartitionMaxSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(dailyPartitionMaxSqlAggregatedExpressionValueOnColumn, "daily_partition_max_sql_aggregated_expression_value_on_column");
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