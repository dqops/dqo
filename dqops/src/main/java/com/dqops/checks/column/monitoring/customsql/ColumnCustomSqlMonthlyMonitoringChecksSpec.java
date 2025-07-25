/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.customsql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.customsql.*;
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
public class ColumnCustomSqlMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_failed_on_column", o -> o.monthlySqlConditionFailedOnColumn);
            put("monthly_sql_condition_passed_percent_on_column", o -> o.monthlySqlConditionPassedPercentOnColumn);
            put("monthly_sql_aggregate_expression_on_column", o -> o.monthlySqlAggregateExpressionOnColumn);
            put("monthly_sql_invalid_value_count_on_column", o -> o.monthlySqlInvalidValueCountOnColumn);
            put("monthly_import_custom_result_on_column", o -> o.monthlyImportCustomResultOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between the current column and another column: `{alias}.{column} > {alias}.col_tax`. " +
            "Stores the most recent captured count of failed rows for each month when the data quality check was evaluated.")
    private ColumnSqlConditionFailedCheckSpec monthlySqlConditionFailedOnColumn;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: `{alias}.{column} > {alias}.col_tax`.  Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves invalid values found in a column and returns the number of them," +
            " and raises an issue if too many failures were detected. " +
            "This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). " +
            "For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: `SELECT {column} FROM {table} WHERE {column} < 18`.")
    private ColumnSqlInvalidValueCountCheckSpec monthlySqlInvalidValueCountOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private ColumnSqlImportCustomResultCheckSpec monthlyImportCustomResultOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getMonthlySqlConditionFailedOnColumn() {
        return monthlySqlConditionFailedOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionFailedOnColumn Check specification.
     */
    public void setMonthlySqlConditionFailedOnColumn(ColumnSqlConditionFailedCheckSpec monthlySqlConditionFailedOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionFailedOnColumn, monthlySqlConditionFailedOnColumn));
        this.monthlySqlConditionFailedOnColumn = monthlySqlConditionFailedOnColumn;
        propagateHierarchyIdToField(monthlySqlConditionFailedOnColumn, "monthly_sql_condition_failed_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getMonthlySqlConditionPassedPercentOnColumn() {
        return monthlySqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionPassedPercentOnColumn Check specification.
     */
    public void setMonthlySqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionPassedPercentOnColumn, monthlySqlConditionPassedPercentOnColumn));
        this.monthlySqlConditionPassedPercentOnColumn = monthlySqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(monthlySqlConditionPassedPercentOnColumn, "monthly_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getMonthlySqlAggregateExpressionOnColumn() {
        return monthlySqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExpressionOnColumn Check specification.
     */
    public void setMonthlySqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExpressionOnColumn, monthlySqlAggregateExpressionOnColumn));
        this.monthlySqlAggregateExpressionOnColumn = monthlySqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(monthlySqlAggregateExpressionOnColumn, "monthly_sql_aggregate_expression_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlInvalidValueCountCheckSpec getMonthlySqlInvalidValueCountOnColumn() {
        return monthlySqlInvalidValueCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlInvalidValueCountOnColumn Check specification.
     */
    public void setMonthlySqlInvalidValueCountOnColumn(ColumnSqlInvalidValueCountCheckSpec monthlySqlInvalidValueCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlInvalidValueCountOnColumn, monthlySqlInvalidValueCountOnColumn));
        this.monthlySqlInvalidValueCountOnColumn = monthlySqlInvalidValueCountOnColumn;
        propagateHierarchyIdToField(monthlySqlInvalidValueCountOnColumn, "monthly_sql_invalid_value_count_on_column");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public ColumnSqlImportCustomResultCheckSpec getMonthlyImportCustomResultOnColumn() {
        return monthlyImportCustomResultOnColumn;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param monthlyImportCustomResultOnColumn Import a result from a custom table.
     */
    public void setMonthlyImportCustomResultOnColumn(ColumnSqlImportCustomResultCheckSpec monthlyImportCustomResultOnColumn) {
        this.setDirtyIf(!Objects.equals(this.monthlyImportCustomResultOnColumn, monthlyImportCustomResultOnColumn));
        this.monthlyImportCustomResultOnColumn = monthlyImportCustomResultOnColumn;
        propagateHierarchyIdToField(monthlyImportCustomResultOnColumn, "monthly_import_custom_result_on_column");
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
        return CheckType.monitoring;
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