/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.monitoring.customsql;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.customsql.*;
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
 * Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableCustomSqlMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCustomSqlMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_failed_on_table", o -> o.monthlySqlConditionFailedOnTable);
            put("monthly_sql_condition_passed_percent_on_table", o -> o.monthlySqlConditionPassedPercentOnTable);
            put("monthly_sql_aggregate_expression_on_table", o -> o.monthlySqlAggregateExpressionOnTable);
            put("monthly_sql_invalid_record_count_on_table", o -> o.monthlySqlInvalidRecordCountOnTable);
            put("monthly_import_custom_result_on_table", o -> o.monthlyImportCustomResultOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between columns: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores the most recent count of failed rows for each month when the data quality check was evaluated.")
    private TableSqlConditionFailedCheckSpec monthlySqlConditionFailedOnTable;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: `{alias}.col_price > {alias}.col_tax`. " +
            "Stores the most recent value for each month when the data quality check was evaluated.")
    private TableSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private TableSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnTable;

    @JsonPropertyDescription("Runs a custom query that retrieves invalid records found in a table and returns the number of them," +
            " and raises an issue if too many failures were detected. " +
            "This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). " +
            "For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: `SELECT age FROM {table} WHERE age < 18`.")
    private TableSqlInvalidRecordCountCheckSpec monthlySqlInvalidRecordCountOnTable;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private TableSqlImportCustomResultCheckSpec monthlyImportCustomResultOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCheckSpec getMonthlySqlConditionFailedOnTable() {
        return monthlySqlConditionFailedOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionFailedOnTable Check specification.
     */
    public void setMonthlySqlConditionFailedOnTable(TableSqlConditionFailedCheckSpec monthlySqlConditionFailedOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionFailedOnTable, monthlySqlConditionFailedOnTable));
        this.monthlySqlConditionFailedOnTable = monthlySqlConditionFailedOnTable;
        propagateHierarchyIdToField(monthlySqlConditionFailedOnTable, "monthly_sql_condition_failed_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlySqlConditionPassedPercentOnTable() {
        return monthlySqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionPassedPercentOnTable Check specification.
     */
    public void setMonthlySqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionPassedPercentOnTable, monthlySqlConditionPassedPercentOnTable));
        this.monthlySqlConditionPassedPercentOnTable = monthlySqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(monthlySqlConditionPassedPercentOnTable, "monthly_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getMonthlySqlAggregateExpressionOnTable() {
        return monthlySqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExpressionOnTable Check specification.
     */
    public void setMonthlySqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec monthlySqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExpressionOnTable, monthlySqlAggregateExpressionOnTable));
        this.monthlySqlAggregateExpressionOnTable = monthlySqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(monthlySqlAggregateExpressionOnTable, "monthly_sql_aggregate_expression_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlInvalidRecordCountCheckSpec getMonthlySqlInvalidRecordCountOnTable() {
        return monthlySqlInvalidRecordCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlInvalidRecordCountOnTable Check specification.
     */
    public void setMonthlySqlInvalidRecordCountOnTable(TableSqlInvalidRecordCountCheckSpec monthlySqlInvalidRecordCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlInvalidRecordCountOnTable, monthlySqlInvalidRecordCountOnTable));
        this.monthlySqlInvalidRecordCountOnTable = monthlySqlInvalidRecordCountOnTable;
        propagateHierarchyIdToField(monthlySqlInvalidRecordCountOnTable, "monthly_sql_invalid_record_count_on_table");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public TableSqlImportCustomResultCheckSpec getMonthlyImportCustomResultOnTable() {
        return monthlyImportCustomResultOnTable;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param monthlyImportCustomResultOnTable Import a result from a custom table.
     */
    public void setMonthlyImportCustomResultOnTable(TableSqlImportCustomResultCheckSpec monthlyImportCustomResultOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyImportCustomResultOnTable, monthlyImportCustomResultOnTable));
        this.monthlyImportCustomResultOnTable = monthlyImportCustomResultOnTable;
        propagateHierarchyIdToField(monthlyImportCustomResultOnTable, "monthly_import_custom_result_on_table");
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
        return CheckTarget.table;
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