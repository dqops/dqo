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
package com.dqops.checks.column.profiling;

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
public class ColumnCustomSqlProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnCustomSqlProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_sql_condition_failed_on_column", o -> o.profileSqlConditionFailedOnColumn);
            put("profile_sql_condition_passed_percent_on_column", o -> o.profileSqlConditionPassedPercentOnColumn);
            put("profile_sql_aggregate_expression_on_column", o -> o.profileSqlAggregateExpressionOnColumn);
            put("profile_sql_invalid_value_count_on_column", o -> o.profileSqlInvalidValueCountOnColumn);
            put("profile_import_custom_result_on_column", o -> o.profileImportCustomResultOnColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between the current column and another column: `{alias}.{column} > col_tax`.")
    private ColumnSqlConditionFailedCheckSpec profileSqlConditionFailedOnColumn;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: `{alias}.{column} > {alias}.col_tax`.")
    private ColumnSqlConditionPassedPercentCheckSpec profileSqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.")
    private ColumnSqlAggregateExpressionCheckSpec profileSqlAggregateExpressionOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves invalid values found in a column and returns the number of them," +
            " and raises an issue if too many failures were detected. " +
            "This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). " +
            "For example, when this check is applied on a column, the condition can verify that the column has lower value than 18 using an SQL expression: `{alias}.{column} < 18`.")
    private ColumnSqlConditionFailedCheckSpec profileSqlInvalidValueCountOnColumn;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private ColumnSqlImportCustomResultCheckSpec profileImportCustomResultOnColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getProfileSqlConditionFailedOnColumn() {
        return profileSqlConditionFailedOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlConditionFailedOnColumn Check specification.
     */
    public void setProfileSqlConditionFailedOnColumn(ColumnSqlConditionFailedCheckSpec profileSqlConditionFailedOnColumn) {
        this.setDirtyIf(!Objects.equals(this.profileSqlConditionFailedOnColumn, profileSqlConditionFailedOnColumn));
        this.profileSqlConditionFailedOnColumn = profileSqlConditionFailedOnColumn;
        propagateHierarchyIdToField(profileSqlConditionFailedOnColumn, "profile_sql_condition_failed_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getProfileSqlConditionPassedPercentOnColumn() {
        return profileSqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlConditionPassedPercentOnColumn Check specification.
     */
    public void setProfileSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec profileSqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.profileSqlConditionPassedPercentOnColumn, profileSqlConditionPassedPercentOnColumn));
        this.profileSqlConditionPassedPercentOnColumn = profileSqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(profileSqlConditionPassedPercentOnColumn, "profile_sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExpressionCheckSpec getProfileSqlAggregateExpressionOnColumn() {
        return profileSqlAggregateExpressionOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlAggregateExpressionOnColumn Check specification.
     */
    public void setProfileSqlAggregateExpressionOnColumn(ColumnSqlAggregateExpressionCheckSpec profileSqlAggregateExpressionOnColumn) {
        this.setDirtyIf(!Objects.equals(this.profileSqlAggregateExpressionOnColumn, profileSqlAggregateExpressionOnColumn));
        this.profileSqlAggregateExpressionOnColumn = profileSqlAggregateExpressionOnColumn;
        propagateHierarchyIdToField(profileSqlAggregateExpressionOnColumn, "profile_sql_aggregate_expression_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCheckSpec getProfileSqlInvalidValueCountOnColumn() {
        return profileSqlInvalidValueCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlInvalidValueCountOnColumn Check specification.
     */
    public void setProfileSqlInvalidValueCountOnColumn(ColumnSqlConditionFailedCheckSpec profileSqlInvalidValueCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.profileSqlInvalidValueCountOnColumn, profileSqlInvalidValueCountOnColumn));
        this.profileSqlInvalidValueCountOnColumn = profileSqlInvalidValueCountOnColumn;
        propagateHierarchyIdToField(profileSqlInvalidValueCountOnColumn, "profile_sql_invalid_value_count_on_column");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public ColumnSqlImportCustomResultCheckSpec getProfileImportCustomResultOnColumn() {
        return profileImportCustomResultOnColumn;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param profileImportCustomResultOnColumn Import a result from a custom table.
     */
    public void setProfileImportCustomResultOnColumn(ColumnSqlImportCustomResultCheckSpec profileImportCustomResultOnColumn) {
        this.setDirtyIf(!Objects.equals(this.profileImportCustomResultOnColumn, profileImportCustomResultOnColumn));
        this.profileImportCustomResultOnColumn = profileImportCustomResultOnColumn;
        propagateHierarchyIdToField(profileImportCustomResultOnColumn, "profile_import_custom_result_on_column");
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
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