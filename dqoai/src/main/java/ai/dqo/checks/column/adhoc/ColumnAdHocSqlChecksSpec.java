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
package ai.dqo.checks.column.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlAggregatedExpressionValueMaxCheckSpec;
import ai.dqo.checks.column.checkspecs.sql.ColumnSqlAggregatedExpressionValueMinCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnAdHocSqlChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocSqlChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("sql_condition_passed_percent_on_column", o -> o.sqlConditionPassedPercentOnColumn);

            put("sql_condition_failed_count_on_column", o -> o.sqlConditionFailedCountOnColumn);

            put("sql_aggregated_expression_value_on_column_min", o -> o.sqlAggregatedExpressionValueOnColumnMin);
            put("sql_aggregated_expression_value_on_column_max", o -> o.sqlAggregatedExpressionValueOnColumnMax);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private ColumnSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnColumn;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private ColumnSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnColumn;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private ColumnSqlAggregatedExpressionValueMinCheckSpec sqlAggregatedExpressionValueOnColumnMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private ColumnSqlAggregatedExpressionValueMaxCheckSpec sqlAggregatedExpressionValueOnColumnMax;

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
    public ColumnSqlAggregatedExpressionValueMinCheckSpec getSqlAggregatedExpressionValueOnColumnMin() {
        return sqlAggregatedExpressionValueOnColumnMin;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregatedExpressionValueOnColumnMin Check specification.
     */
    public void setSqlAggregatedExpressionValueOnColumnMin(ColumnSqlAggregatedExpressionValueMinCheckSpec sqlAggregatedExpressionValueOnColumnMin) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregatedExpressionValueOnColumnMin, sqlAggregatedExpressionValueOnColumnMin));
        this.sqlAggregatedExpressionValueOnColumnMin = sqlAggregatedExpressionValueOnColumnMin;
        propagateHierarchyIdToField(sqlAggregatedExpressionValueOnColumnMin, "sql_aggregated_expression_value_on_column_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public ColumnSqlAggregatedExpressionValueMaxCheckSpec getSqlAggregatedExpressionValueOnColumnMax() {
        return sqlAggregatedExpressionValueOnColumnMax;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregatedExpressionValueOnColumnMax Check specification.
     */
    public void setSqlAggregatedExpressionValueOnColumnMax(ColumnSqlAggregatedExpressionValueMaxCheckSpec sqlAggregatedExpressionValueOnColumnMax) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregatedExpressionValueOnColumnMax, sqlAggregatedExpressionValueOnColumnMax));
        this.sqlAggregatedExpressionValueOnColumnMax = sqlAggregatedExpressionValueOnColumnMax;
        propagateHierarchyIdToField(sqlAggregatedExpressionValueOnColumnMax, "sql_aggregated_expression_value_on_column_max");
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