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
import com.dqops.checks.column.checkspecs.sql.ColumnSqlAggregateExprCheckSpec;
import com.dqops.checks.column.checkspecs.sql.ColumnSqlConditionFailedCountCheckSpec;
import com.dqops.checks.column.checkspecs.sql.ColumnSqlConditionPassedPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnSqlProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("sql_condition_passed_percent_on_column", o -> o.sqlConditionPassedPercentOnColumn);
            put("sql_condition_failed_count_on_column", o -> o.sqlConditionFailedCountOnColumn);

            put("sql_aggregate_expr_column", o -> o.sqlAggregateExprColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.")
    private ColumnSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.")
    private ColumnSqlAggregateExprCheckSpec sqlAggregateExprColumn;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionPassedPercentCheckSpec getSqlConditionPassedPercentOnColumn() {
        return sqlConditionPassedPercentOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionPassedPercentOnColumn Check specification.
     */
    public void setSqlConditionPassedPercentOnColumn(ColumnSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnColumn) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionPassedPercentOnColumn, sqlConditionPassedPercentOnColumn));
        this.sqlConditionPassedPercentOnColumn = sqlConditionPassedPercentOnColumn;
        propagateHierarchyIdToField(sqlConditionPassedPercentOnColumn, "sql_condition_passed_percent_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlConditionFailedCountCheckSpec getSqlConditionFailedCountOnColumn() {
        return sqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionFailedCountOnColumn Check specification.
     */
    public void setSqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionFailedCountOnColumn, sqlConditionFailedCountOnColumn));
        this.sqlConditionFailedCountOnColumn = sqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(sqlConditionFailedCountOnColumn, "sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExprCheckSpec getSqlAggregateExprColumn() {
        return sqlAggregateExprColumn;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregateExprColumn Check specification.
     */
    public void setSqlAggregateExprColumn(ColumnSqlAggregateExprCheckSpec sqlAggregateExprColumn) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregateExprColumn, sqlAggregateExprColumn));
        this.sqlAggregateExprColumn = sqlAggregateExprColumn;
        propagateHierarchyIdToField(sqlAggregateExprColumn, "sql_aggregate_expr_column");
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