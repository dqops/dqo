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
package ai.dqo.checks.table.partitioned.sql;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregateExprCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class TableSqlMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_sql_condition_passed_percent_on_table", o -> o.monthlyPartitionSqlConditionPassedPercentOnTable);
            put("monthly_partition_sql_condition_failed_count_on_table", o -> o.monthlyPartitionSqlConditionFailedCountOnTable);

            put("monthly_partition_sql_aggregate_expr_table", o -> o.monthlyPartitionSqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.")
    private TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.")
    private TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private TableSqlAggregateExprCheckSpec monthlyPartitionSqlAggregateExprTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return monthlyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMonthlyPartitionSqlConditionPassedPercentOnTable() {
        return monthlyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionPassedPercentOnTable Check specification.
     */
    public void setMonthlyPartitionSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec monthlyPartitionSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionPassedPercentOnTable, monthlyPartitionSqlConditionPassedPercentOnTable));
        this.monthlyPartitionSqlConditionPassedPercentOnTable = monthlyPartitionSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionPassedPercentOnTable, "monthly_partition_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getMonthlyPartitionSqlConditionFailedCountOnTable() {
        return monthlyPartitionSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlConditionFailedCountOnTable Check specification.
     */
    public void setMonthlyPartitionSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec monthlyPartitionSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlConditionFailedCountOnTable, monthlyPartitionSqlConditionFailedCountOnTable));
        this.monthlyPartitionSqlConditionFailedCountOnTable = monthlyPartitionSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(monthlyPartitionSqlConditionFailedCountOnTable, "monthly_partition_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getMonthlyPartitionSqlAggregateExprTable() {
        return monthlyPartitionSqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param monthlyPartitionSqlAggregateExprTable Check specification.
     */
    public void setMonthlyPartitionSqlAggregateExprTable(TableSqlAggregateExprCheckSpec monthlyPartitionSqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSqlAggregateExprTable, monthlyPartitionSqlAggregateExprTable));
        this.monthlyPartitionSqlAggregateExprTable = monthlyPartitionSqlAggregateExprTable;
        propagateHierarchyIdToField(monthlyPartitionSqlAggregateExprTable, "monthly_partition_sql_aggregate_expr_table");
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