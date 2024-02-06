/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.checks.column.partitioned.customsql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlAggregateExpressionCheckSpec;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlConditionFailedCheckSpec;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlConditionPassedPercentCheckSpec;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlImportCustomResultCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ColumnCustomSqlDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sql_condition_failed_on_column", o -> o.dailyPartitionSqlConditionFailedOnColumn);
            put("daily_partition_sql_condition_passed_percent_on_column", o -> o.dailyPartitionSqlConditionPassedPercentOnColumn);
            put("daily_partition_sql_aggregate_expression_on_column", o -> o.dailyPartitionSqlAggregateExpressionOnColumn);
            put("daily_partition_import_custom_result_on_column", o -> o.dailyPartitionImportCustomResultOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between the current column and another column: `{alias}.{column} > {alias}.col_tax`. " +
            "Stores a separate data quality check result for each daily partition.")
    private ColumnSqlConditionFailedCheckSpec dailyPartitionSqlConditionFailedOnColumn;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: `{alias}.{column} > {alias}.col_tax`. "+
            "Stores a separate data quality check result for each daily partition.")
    private ColumnSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. " +
            "Stores a separate data quality check result for each daily partition.")
    private ColumnSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private ColumnSqlImportCustomResultCheckSpec dailyPartitionImportCustomResultOnColumn;


    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getDailyPartitionSqlConditionFailedOnColumn() {
        return dailyPartitionSqlConditionFailedOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedOnColumn Check specification.
     */
    public void setDailyPartitionSqlConditionFailedOnColumn(ColumnSqlConditionFailedCheckSpec dailyPartitionSqlConditionFailedOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedOnColumn, dailyPartitionSqlConditionFailedOnColumn));
        this.dailyPartitionSqlConditionFailedOnColumn = dailyPartitionSqlConditionFailedOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedOnColumn, "daily_partition_sql_condition_failed_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnColumn() {
        return dailyPartitionSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnColumn, dailyPartitionSqlConditionPassedPercentOnColumn));
        this.dailyPartitionSqlConditionPassedPercentOnColumn = dailyPartitionSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnColumn, "daily_partition_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getDailyPartitionSqlAggregateExpressionOnColumn() {
        return dailyPartitionSqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregateExpressionOnColumn Check specification.
     */
    public void setDailyPartitionSqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec dailyPartitionSqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregateExpressionOnColumn, dailyPartitionSqlAggregateExpressionOnColumn));
        this.dailyPartitionSqlAggregateExpressionOnColumn = dailyPartitionSqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(dailyPartitionSqlAggregateExpressionOnColumn, "daily_partition_sql_aggregate_expression_on_column");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public ColumnSqlImportCustomResultCheckSpec getDailyPartitionImportCustomResultOnColumn() {
        return dailyPartitionImportCustomResultOnColumn;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param dailyPartitionImportCustomResultOnColumn Import a result from a custom table.
     */
    public void setDailyPartitionImportCustomResultOnColumn(ColumnSqlImportCustomResultCheckSpec dailyPartitionImportCustomResultOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionImportCustomResultOnColumn, dailyPartitionImportCustomResultOnColumn));
        this.dailyPartitionImportCustomResultOnColumn = dailyPartitionImportCustomResultOnColumn;
        propagateHierarchyIdToField(dailyPartitionImportCustomResultOnColumn, "daily_partition_import_custom_result_on_column");
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

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}