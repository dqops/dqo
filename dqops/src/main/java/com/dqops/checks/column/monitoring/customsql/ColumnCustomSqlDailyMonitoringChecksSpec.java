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
public class ColumnCustomSqlDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_sql_condition_failed_on_column", o -> o.dailySqlConditionFailedOnColumn);
            put("daily_sql_condition_passed_percent_on_column", o -> o.dailySqlConditionPassedPercentOnColumn);
            put("daily_sql_aggregate_expression_on_column", o -> o.dailySqlAggregateExpressionOnColumn);
            put("daily_sql_invalid_value_count_on_column", o -> o.dailySqlInvalidValueCountOnColumn);
            put("daily_import_custom_result_on_column", o -> o.dailyImportCustomResultOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between the current column and another column: `{alias}.{column} > col_tax`. " +
            "Stores the most recent captured count of failed rows for each day when the data quality check was evaluated.")
    private ColumnSqlConditionFailedCheckSpec dailySqlConditionFailedOnColumn;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: `{alias}.{column} > {alias}.col_tax`. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSqlAggregateExpressionCheckSpec dailySqlAggregateExpressionOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves invalid values found in a column and returns the number of them," +
            " and raises an issue if too many failures were detected. " +
            "This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). " +
            "For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: `SELECT {column} FROM {table} WHERE {column} < 18`.")
    private ColumnSqlInvalidValueCountCheckSpec dailySqlInvalidValueCountOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private ColumnSqlImportCustomResultCheckSpec dailyImportCustomResultOnColumn;


    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getDailySqlConditionFailedOnColumn() {
        return dailySqlConditionFailedOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionFailedOnColumn Check specification.
     */
    public void setDailySqlConditionFailedOnColumn(ColumnSqlConditionFailedCheckSpec dailySqlConditionFailedOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionFailedOnColumn, dailySqlConditionFailedOnColumn));
        this.dailySqlConditionFailedOnColumn = dailySqlConditionFailedOnColumn;
        propagateHierarchyIdToField(dailySqlConditionFailedOnColumn, "daily_sql_condition_failed_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getDailySqlConditionPassedPercentOnColumn() {
        return dailySqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionPassedPercentOnColumn Check specification.
     */
    public void setDailySqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionPassedPercentOnColumn, dailySqlConditionPassedPercentOnColumn));
        this.dailySqlConditionPassedPercentOnColumn = dailySqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(dailySqlConditionPassedPercentOnColumn, "daily_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getDailySqlAggregateExpressionOnColumn() {
        return dailySqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlAggregateExpressionOnColumn Check specification.
     */
    public void setDailySqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec dailySqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlAggregateExpressionOnColumn, dailySqlAggregateExpressionOnColumn));
        this.dailySqlAggregateExpressionOnColumn = dailySqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(dailySqlAggregateExpressionOnColumn, "daily_sql_aggregate_expression_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlInvalidValueCountCheckSpec getDailySqlInvalidValueCountOnColumn() {
        return dailySqlInvalidValueCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlInvalidValueCountOnColumn Check specification.
     */
    public void setDailySqlInvalidValueCountOnColumn(ColumnSqlInvalidValueCountCheckSpec dailySqlInvalidValueCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlInvalidValueCountOnColumn, dailySqlInvalidValueCountOnColumn));
        this.dailySqlInvalidValueCountOnColumn = dailySqlInvalidValueCountOnColumn;
        propagateHierarchyIdToField(dailySqlInvalidValueCountOnColumn, "daily_sql_invalid_value_count_on_column");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public ColumnSqlImportCustomResultCheckSpec getDailyImportCustomResultOnColumn() {
        return dailyImportCustomResultOnColumn;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param dailyImportCustomResultOnColumn Import a result from a custom table.
     */
    public void setDailyImportCustomResultOnColumn(ColumnSqlImportCustomResultCheckSpec dailyImportCustomResultOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailyImportCustomResultOnColumn, dailyImportCustomResultOnColumn));
        this.dailyImportCustomResultOnColumn = dailyImportCustomResultOnColumn;
        propagateHierarchyIdToField(dailyImportCustomResultOnColumn, "daily_import_custom_result_on_column");
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