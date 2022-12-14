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
package ai.dqo.checks.column.adhoc;

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
public class ColumnAdHocSqlChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocSqlChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("min_sql_condition_passed_percent_on_column_1", o -> o.minSqlConditionPassedPercentOnColumn_1);
            put("min_sql_condition_passed_percent_on_column_2", o -> o.minSqlConditionPassedPercentOnColumn_2);
            put("min_sql_condition_passed_percent_on_column_3", o -> o.minSqlConditionPassedPercentOnColumn_3);

            put("max_sql_condition_failed_count_on_column_1", o -> o.maxSqlConditionFailedCountOnColumn_1);
            put("max_sql_condition_failed_count_on_column_2", o -> o.maxSqlConditionFailedCountOnColumn_2);
            put("max_sql_condition_failed_count_on_column_3", o -> o.maxSqlConditionFailedCountOnColumn_3);

            put("min_sql_aggregated_expression_value_on_column", o -> o.minSqlAggregatedExpressionValueOnColumn);
            put("max_sql_aggregated_expression_value_on_column", o -> o.maxSqlAggregatedExpressionValueOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionMinValueCheckSpec minSqlAggregatedExpressionValueOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionMaxValueCheckSpec maxSqlAggregatedExpressionValueOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnColumn_1() {
        return minSqlConditionPassedPercentOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnColumn_1 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnColumn_1(ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnColumn_1, minSqlConditionPassedPercentOnColumn_1));
        this.minSqlConditionPassedPercentOnColumn_1 = minSqlConditionPassedPercentOnColumn_1;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnColumn_1, "min_sql_condition_passed_percent_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnColumn_2() {
        return minSqlConditionPassedPercentOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnColumn_2 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnColumn_2(ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnColumn_2, minSqlConditionPassedPercentOnColumn_2));
        this.minSqlConditionPassedPercentOnColumn_2 = minSqlConditionPassedPercentOnColumn_2;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnColumn_1, "min_sql_condition_passed_percent_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnColumn_3() {
        return minSqlConditionPassedPercentOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnColumn_3 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnColumn_3(ColumnMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnColumn_3, minSqlConditionPassedPercentOnColumn_3));
        this.minSqlConditionPassedPercentOnColumn_3 = minSqlConditionPassedPercentOnColumn_3;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnColumn_3, "min_sql_condition_passed_percent_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnColumn_1() {
        return maxSqlConditionFailedCountOnColumn_1;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnColumn_1 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnColumn_1(ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_1) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnColumn_1, maxSqlConditionFailedCountOnColumn_1));
        this.maxSqlConditionFailedCountOnColumn_1 = maxSqlConditionFailedCountOnColumn_1;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnColumn_1, "max_sql_condition_failed_count_on_column_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnColumn_2() {
        return maxSqlConditionFailedCountOnColumn_2;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnColumn_2 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnColumn_2(ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_2) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnColumn_2, maxSqlConditionFailedCountOnColumn_2));
        this.maxSqlConditionFailedCountOnColumn_2 = maxSqlConditionFailedCountOnColumn_2;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnColumn_2, "max_sql_condition_failed_count_on_column_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnColumn_3() {
        return maxSqlConditionFailedCountOnColumn_3;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnColumn_3 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnColumn_3(ColumnMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnColumn_3) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnColumn_3, maxSqlConditionFailedCountOnColumn_3));
        this.maxSqlConditionFailedCountOnColumn_3 = maxSqlConditionFailedCountOnColumn_3;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnColumn_3, "max_sql_condition_failed_count_on_column_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMinValueCheckSpec getMinSqlAggregatedExpressionValueOnColumn() {
        return minSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param minSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMinSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMinValueCheckSpec minSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.minSqlAggregatedExpressionValueOnColumn, minSqlAggregatedExpressionValueOnColumn));
        this.minSqlAggregatedExpressionValueOnColumn = minSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(minSqlAggregatedExpressionValueOnColumn, "min_sql_aggregated_expression_value_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionMaxValueCheckSpec getMaxSqlAggregatedExpressionValueOnColumn() {
        return maxSqlAggregatedExpressionValueOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlAggregatedExpressionValueOnColumn Check specification.
     */
    public void setMaxSqlAggregatedExpressionValueOnColumn(ColumnSqlAggregatedExpressionMaxValueCheckSpec maxSqlAggregatedExpressionValueOnColumn) {
        this.setDirtyIf(!Objects.equals(this.maxSqlAggregatedExpressionValueOnColumn, maxSqlAggregatedExpressionValueOnColumn));
        this.maxSqlAggregatedExpressionValueOnColumn = maxSqlAggregatedExpressionValueOnColumn;
        propagateHierarchyIdToField(maxSqlAggregatedExpressionValueOnColumn, "max_sql_aggregated_expression_value_on_column");
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