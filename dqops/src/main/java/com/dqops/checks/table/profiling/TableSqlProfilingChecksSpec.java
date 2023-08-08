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
import com.dqops.checks.table.checkspecs.sql.TableSqlAggregateExprCheckSpec;
import com.dqops.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import com.dqops.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class TableSqlProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_sql_condition_passed_percent_on_table", o -> o.profileSqlConditionPassedPercentOnTable);
            put("profile_sql_condition_failed_count_on_table", o -> o.profileSqlConditionFailedCountOnTable);

            put("profile_sql_aggregate_expr_table", o -> o.profileSqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec profileSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec profileSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.")
    private TableSqlAggregateExprCheckSpec profileSqlAggregateExprTable;

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
    public TableSqlConditionFailedCountCheckSpec getProfileSqlConditionFailedCountOnTable() {
        return profileSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlConditionFailedCountOnTable Check specification.
     */
    public void setProfileSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec profileSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.profileSqlConditionFailedCountOnTable, profileSqlConditionFailedCountOnTable));
        this.profileSqlConditionFailedCountOnTable = profileSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(profileSqlConditionFailedCountOnTable, "profile_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getProfileSqlAggregateExprTable() {
        return profileSqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param profileSqlAggregateExprTable Check specification.
     */
    public void setProfileSqlAggregateExprTable(TableSqlAggregateExprCheckSpec profileSqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.profileSqlAggregateExprTable, profileSqlAggregateExprTable));
        this.profileSqlAggregateExprTable = profileSqlAggregateExprTable;
        propagateHierarchyIdToField(profileSqlAggregateExprTable, "profile_sql_aggregate_expr_table");
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