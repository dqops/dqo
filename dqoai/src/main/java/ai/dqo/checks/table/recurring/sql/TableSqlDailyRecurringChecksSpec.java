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
package ai.dqo.checks.table.recurring.sql;

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
public class TableSqlDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_sql_condition_passed_percent_on_table", o -> o.dailySqlConditionPassedPercentOnTable);
            put("daily_sql_condition_failed_count_on_table", o -> o.dailySqlConditionFailedCountOnTable);

            put("daily_sql_aggregate_expr_table", o -> o.dailySqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.")
    private TableSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.")
    private TableSqlConditionFailedCountCheckSpec dailySqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private TableSqlAggregateExprCheckSpec dailySqlAggregateExprTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return dailySqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailySqlConditionPassedPercentOnTable() {
        return dailySqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionPassedPercentOnTable Check specification.
     */
    public void setDailySqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionPassedPercentOnTable, dailySqlConditionPassedPercentOnTable));
        this.dailySqlConditionPassedPercentOnTable = dailySqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(dailySqlConditionPassedPercentOnTable, "daily_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailySqlConditionFailedCountOnTable() {
        return dailySqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionFailedCountOnTable Check specification.
     */
    public void setDailySqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec dailySqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionFailedCountOnTable, dailySqlConditionFailedCountOnTable));
        this.dailySqlConditionFailedCountOnTable = dailySqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(dailySqlConditionFailedCountOnTable, "daily_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getDailySqlAggregateExprTable() {
        return dailySqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlAggregateExprTable Check specification.
     */
    public void setDailySqlAggregateExprTable(TableSqlAggregateExprCheckSpec dailySqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.dailySqlAggregateExprTable, dailySqlAggregateExprTable));
        this.dailySqlAggregateExprTable = dailySqlAggregateExprTable;
        propagateHierarchyIdToField(dailySqlAggregateExprTable, "daily_sql_aggregate_expr_table");
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