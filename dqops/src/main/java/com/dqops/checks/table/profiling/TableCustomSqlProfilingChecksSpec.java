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
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.customsql.TableSqlAggregateExpressionCheckSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionFailedCheckSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionPassedPercentCheckSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlImportCustomResultCheckSpec;
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
public class TableCustomSqlProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableCustomSqlProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_sql_condition_failed_on_table", o -> o.profileSqlConditionFailedOnTable);
            put("profile_sql_condition_passed_percent_on_table", o -> o.profileSqlConditionPassedPercentOnTable);
            put("profile_sql_aggregate_expression_on_table", o -> o.profileSqlAggregateExpressionOnTable);
            put("profile_import_custom_result_on_table", o -> o.profileImportCustomResultOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: `{alias}.col_price > {alias}.col_tax`.")
    private TableSqlConditionFailedCheckSpec profileSqlConditionFailedOnTable;

    @JsonPropertyDescription("Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. " +
            "This check is used also to compare values between columns: `{alias}.col_price > {alias}.col_tax`.")
    private TableSqlConditionPassedPercentCheckSpec profileSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.")
    private TableSqlAggregateExpressionCheckSpec profileSqlAggregateExpressionOnTable;

    @JsonPropertyDescription("Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.")
    private TableSqlImportCustomResultCheckSpec profileImportCustomResultOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCheckSpec getProfileSqlConditionFailedOnTable() {
        return profileSqlConditionFailedOnTable;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlConditionFailedOnTable Check specification.
     */
    public void setProfileSqlConditionFailedOnTable(TableSqlConditionFailedCheckSpec profileSqlConditionFailedOnTable) {
        this.setDirtyIf(!Objects.equals(this.profileSqlConditionFailedOnTable, profileSqlConditionFailedOnTable));
        this.profileSqlConditionFailedOnTable = profileSqlConditionFailedOnTable;
        propagateHierarchyIdToField(profileSqlConditionFailedOnTable, "profile_sql_condition_failed_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getProfileSqlConditionPassedPercentOnTable() {
        return profileSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlConditionPassedPercentOnTable Check specification.
     */
    public void setProfileSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec profileSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.profileSqlConditionPassedPercentOnTable, profileSqlConditionPassedPercentOnTable));
        this.profileSqlConditionPassedPercentOnTable = profileSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(profileSqlConditionPassedPercentOnTable, "profile_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExpressionCheckSpec getProfileSqlAggregateExpressionOnTable() {
        return profileSqlAggregateExpressionOnTable;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlAggregateExpressionOnTable Check specification.
     */
    public void setProfileSqlAggregateExpressionOnTable(TableSqlAggregateExpressionCheckSpec profileSqlAggregateExpressionOnTable) {
        this.setDirtyIf(!Objects.equals(this.profileSqlAggregateExpressionOnTable, profileSqlAggregateExpressionOnTable));
        this.profileSqlAggregateExpressionOnTable = profileSqlAggregateExpressionOnTable;
        propagateHierarchyIdToField(profileSqlAggregateExpressionOnTable, "profile_sql_aggregate_expression_on_table");
    }

    /**
     * Returns a custom check that imports data quality results from custom log tables.
     * @return Import custom result check.
     */
    public TableSqlImportCustomResultCheckSpec getProfileImportCustomResultOnTable() {
        return profileImportCustomResultOnTable;
    }

    /**
     * Sets a custom check that pulls results from a custom table.
     * @param profileImportCustomResultOnTable Import a result from a custom table.
     */
    public void setProfileImportCustomResultOnTable(TableSqlImportCustomResultCheckSpec profileImportCustomResultOnTable) {
        this.setDirtyIf(!Objects.equals(this.profileImportCustomResultOnTable, profileImportCustomResultOnTable));
        this.profileImportCustomResultOnTable = profileImportCustomResultOnTable;
        propagateHierarchyIdToField(profileImportCustomResultOnTable, "profile_import_custom_result_on_table");
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
}