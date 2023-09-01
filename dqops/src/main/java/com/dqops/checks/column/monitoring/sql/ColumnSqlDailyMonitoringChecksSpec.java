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
package com.dqops.checks.column.monitoring.sql;

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
public class ColumnSqlDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_sql_condition_passed_percent_on_column", o -> o.dailySqlConditionPassedPercentOnColumn);
            put("daily_sql_condition_failed_count_on_column", o -> o.dailySqlConditionFailedCountOnColumn);

            put("daily_sql_aggregate_expr_column", o -> o.dailySqlAggregateExprColumn);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSqlConditionPassedPercentCheckSpec dailySqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSqlConditionFailedCountCheckSpec dailySqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSqlAggregateExprCheckSpec dailySqlAggregateExprColumn;

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
    public ColumnSqlConditionFailedCountCheckSpec getDailySqlConditionFailedCountOnColumn() {
        return dailySqlConditionFailedCountOnColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlConditionFailedCountOnColumn Check specification.
     */
    public void setDailySqlConditionFailedCountOnColumn(ColumnSqlConditionFailedCountCheckSpec dailySqlConditionFailedCountOnColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlConditionFailedCountOnColumn, dailySqlConditionFailedCountOnColumn));
        this.dailySqlConditionFailedCountOnColumn = dailySqlConditionFailedCountOnColumn;
        propagateHierarchyIdToField(dailySqlConditionFailedCountOnColumn, "daily_sql_condition_failed_count_on_column");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregateExprCheckSpec getDailySqlAggregateExprColumn() {
        return dailySqlAggregateExprColumn;
    }

    /**
     * Sets a new check specification.
     * @param dailySqlAggregateExprColumn Check specification.
     */
    public void setDailySqlAggregateExprColumn(ColumnSqlAggregateExprCheckSpec dailySqlAggregateExprColumn) {
        this.setDirtyIf(!Objects.equals(this.dailySqlAggregateExprColumn, dailySqlAggregateExprColumn));
        this.dailySqlAggregateExprColumn = dailySqlAggregateExprColumn;
        propagateHierarchyIdToField(dailySqlAggregateExprColumn, "daily_sql_aggregate_expr_column");
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