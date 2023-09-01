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
package com.dqops.checks.table.monitoring.sql;

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
public class TableSqlMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sql_condition_passed_percent_on_table", o -> o.monthlySqlConditionPassedPercentOnTable);
            put("monthly_sql_condition_failed_count_on_table", o -> o.monthlySqlConditionFailedCountOnTable);

            put("monthly_sql_aggregate_expr_table", o -> o.monthlySqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlConditionPassedPercentCheckSpec monthlySqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableSqlAggregateExprCheckSpec monthlySqlAggregateExprTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return monthlySqlConditionPassedPercentOnTable;
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
    public TableSqlConditionFailedCountCheckSpec getMonthlySqlConditionFailedCountOnTable() {
        return monthlySqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlConditionFailedCountOnTable Check specification.
     */
    public void setMonthlySqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec monthlySqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlConditionFailedCountOnTable, monthlySqlConditionFailedCountOnTable));
        this.monthlySqlConditionFailedCountOnTable = monthlySqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(monthlySqlConditionFailedCountOnTable, "monthly_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getMonthlySqlAggregateExprTable() {
        return monthlySqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlySqlAggregateExprTable Check specification.
     */
    public void setMonthlySqlAggregateExprTable(TableSqlAggregateExprCheckSpec monthlySqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.monthlySqlAggregateExprTable, monthlySqlAggregateExprTable));
        this.monthlySqlAggregateExprTable = monthlySqlAggregateExprTable;
        propagateHierarchyIdToField(monthlySqlAggregateExprTable, "monthly_sql_aggregate_expr_table");
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