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
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregateExprCheckSpec;
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
public class TableSqlDailyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlDailyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sql_condition_passed_percent_on_table", o -> o.dailyPartitionSqlConditionPassedPercentOnTable);
            put("daily_partition_sql_condition_failed_count_on_table", o -> o.dailyPartitionSqlConditionFailedCountOnTable);

            put("daily_partition_sql_aggregate_expr_table", o -> o.dailyPartitionSqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.")
    private TableSqlAggregateExprCheckSpec dailyPartitionSqlAggregateExprTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregateExprCheckSpec dailyPartitionSqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable() {
        return dailyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getDailyPartitionSqlConditionPassedPercentOnTable() {
        return dailyPartitionSqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionPassedPercentOnTable Check specification.
     */
    public void setDailyPartitionSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec dailyPartitionSqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionPassedPercentOnTable, dailyPartitionSqlConditionPassedPercentOnTable));
        this.dailyPartitionSqlConditionPassedPercentOnTable = dailyPartitionSqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(dailyPartitionSqlConditionPassedPercentOnTable, "daily_partition_sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getDailyPartitionSqlConditionFailedCountOnTable() {
        return dailyPartitionSqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlConditionFailedCountOnTable Check specification.
     */
    public void setDailyPartitionSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec dailyPartitionSqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlConditionFailedCountOnTable, dailyPartitionSqlConditionFailedCountOnTable));
        this.dailyPartitionSqlConditionFailedCountOnTable = dailyPartitionSqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(dailyPartitionSqlConditionFailedCountOnTable, "daily_partition_sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getDailyPartitionSqlAggregateExprTable() {
        return dailyPartitionSqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param dailyPartitionSqlAggregateExprTable Check specification.
     */
    public void setDailyPartitionSqlAggregateExprTable(TableSqlAggregateExprCheckSpec dailyPartitionSqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSqlAggregateExprTable, dailyPartitionSqlAggregateExprTable));
        this.dailyPartitionSqlAggregateExprTable = dailyPartitionSqlAggregateExprTable;
        propagateHierarchyIdToField(dailyPartitionSqlAggregateExprTable, "sql_aggregate_expr_table");
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