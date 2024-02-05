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
public class ColumnCustomSqlMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_sql_condition_failed_on_column", o -> o.monthlyPartitionSqlConditionFailedOnColumn);
            put("monthly_partition_sql_condition_passed_percent_on_column", o -> o.monthlyPartitionSqlConditionPassedPercentOnColumn);
            put("monthly_partition_sql_aggregate_expression_on_column", o -> o.monthlyPartitionSqlAggregateExpressionOnColumn);
            put("monthly_partition_import_custom_result_on_column", o -> o.monthlyPartitionImportCustomResultOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between the current column and another column: `{alias}.{column} > {alias}.col_tax`. " +
            "Stores a separate data quality check result for each monthly partition.")
    private ColumnSqlConditionFailedCheckSpec monthlyPartitionSqlConditionFailedOnColumn;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: `{alias}.{column} > {alias}.col_tax`. " +
            "Stores a separate data quality check result for each monthly partition.")
    private ColumnSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnSqlAggregateExpressionCheckSpec monthlyPartitionSqlAggregateExpressionOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private ColumnSqlImportCustomResultCheckSpec monthlyPartitionImportCustomResultOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getMonthlyPartitionSqlConditionFailedOnColumn() {
        return monthlyPartitionSqlConditionFailedOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedOnColumn Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedOnColumn(ColumnSqlConditionFailedCheckSpec monthlyPartitionSqlConditionFailedOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedOnColumn, monthlyPartitionSqlConditionFailedOnColumn));
        this.monthlyPartitionSqlConditionFailedOnColumn = monthlyPartitionSqlConditionFailedOnColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedOnColumn, "monthly_partition_sql_condition_failed_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnColumn() {
        return monthlyPartitionSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnColumn, monthlyPartitionSqlConditionPassedPercentOnColumn));
        this.monthlyPartitionSqlConditionPassedPercentOnColumn = monthlyPartitionSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnColumn, "monthly_partition_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getMonthlyPartitionSqlAggregateExpressionOnColumn() {
        return monthlyPartitionSqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregateExpressionOnColumn Check specification.
     */
    public void setMonthlyPartitionSqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec monthlyPartitionSqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregateExpressionOnColumn, monthlyPartitionSqlAggregateExpressionOnColumn));
        this.monthlyPartitionSqlAggregateExpressionOnColumn = monthlyPartitionSqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregateExpressionOnColumn, "monthly_partition_sql_aggregate_expression_on_column");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public ColumnSqlImportCustomResultCheckSpec getMonthlyPartitionImportCustomResultOnColumn() {
        return monthlyPartitionImportCustomResultOnColumn;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param monthlyPartitionImportCustomResultOnColumn Import a result from a custom table.
     */
    public void setMonthlyPartitionImportCustomResultOnColumn(ColumnSqlImportCustomResultCheckSpec monthlyPartitionImportCustomResultOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionImportCustomResultOnColumn, monthlyPartitionImportCustomResultOnColumn));
        this.monthlyPartitionImportCustomResultOnColumn = monthlyPartitionImportCustomResultOnColumn;
        propagateHierarchyIdToField(monthlyPartitionImportCustomResultOnColumn, "monthly_partition_import_custom_result_on_column");
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
        return CheckTimeScale.monthly;
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