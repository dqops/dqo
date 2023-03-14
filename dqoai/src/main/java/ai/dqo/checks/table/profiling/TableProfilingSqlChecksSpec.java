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
package ai.dqo.checks.table.profiling;

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
public class TableProfilingSqlChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableProfilingSqlChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("sql_condition_passed_percent_on_table", o -> o.sqlConditionPassedPercentOnTable);
            put("sql_condition_failed_count_on_table", o -> o.sqlConditionFailedCountOnTable);

            put("sql_aggregate_expr_table", o -> o.sqlAggregateExprTable);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.")
    private TableSqlAggregateExprCheckSpec sqlAggregateExprTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getSqlConditionPassedPercentOnTable() {
        return sqlConditionPassedPercentOnTable;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionPassedPercentOnTable Check specification.
     */
    public void setSqlConditionPassedPercentOnTable(TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionPassedPercentOnTable, sqlConditionPassedPercentOnTable));
        this.sqlConditionPassedPercentOnTable = sqlConditionPassedPercentOnTable;
        propagateHierarchyIdToField(sqlConditionPassedPercentOnTable, "sql_condition_passed_percent_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getSqlConditionFailedCountOnTable() {
        return sqlConditionFailedCountOnTable;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionFailedCountOnTable Check specification.
     */
    public void setSqlConditionFailedCountOnTable(TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionFailedCountOnTable, sqlConditionFailedCountOnTable));
        this.sqlConditionFailedCountOnTable = sqlConditionFailedCountOnTable;
        propagateHierarchyIdToField(sqlConditionFailedCountOnTable, "sql_condition_failed_count_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregateExprCheckSpec getSqlAggregateExprTable() {
        return sqlAggregateExprTable;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregateExprTable Check specification.
     */
    public void setSqlAggregateExprTable(TableSqlAggregateExprCheckSpec sqlAggregateExprTable) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregateExprTable, sqlAggregateExprTable));
        this.sqlAggregateExprTable = sqlAggregateExprTable;
        propagateHierarchyIdToField(sqlAggregateExprTable, "sql_aggregate_expr_table");
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