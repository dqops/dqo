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
package ai.dqo.checks.table.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionValueMaxCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionValueMinCheckSpec;
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
public class TableAdHocSqlChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAdHocSqlChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("sql_condition_passed_percent_on_table_1", o -> o.sqlConditionPassedPercentOnTable_1);
            put("sql_condition_passed_percent_on_table_2", o -> o.sqlConditionPassedPercentOnTable_2);
            put("sql_condition_passed_percent_on_table_3", o -> o.sqlConditionPassedPercentOnTable_3);

            put("sql_condition_failed_count_on_table_1", o -> o.sqlConditionFailedCountOnTable_1);
            put("sql_condition_failed_count_on_table_2", o -> o.sqlConditionFailedCountOnTable_2);
            put("sql_condition_failed_count_on_table_3", o -> o.sqlConditionFailedCountOnTable_3);

            put("sql_aggregated_expression_value_on_table_min", o -> o.sqlAggregatedExpressionValueOnTableMin);
            put("sql_aggregated_expression_value_on_table_max", o -> o.sqlAggregatedExpressionValueOnTableMax);
        }
    };

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a set percentage of rows passed a custom SQL condition (expression).")
    private TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a set number of rows failed a custom SQL condition (expression).")
    private TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionValueMinCheckSpec sqlAggregatedExpressionValueOnTableMin;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionValueMaxCheckSpec sqlAggregatedExpressionValueOnTableMax;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getSqlConditionPassedPercentOnTable_1() {
        return sqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setSqlConditionPassedPercentOnTable_1(TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionPassedPercentOnTable_1, sqlConditionPassedPercentOnTable_1));
        this.sqlConditionPassedPercentOnTable_1 = sqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(sqlConditionPassedPercentOnTable_1, "sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getSqlConditionPassedPercentOnTable_2() {
        return sqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setSqlConditionPassedPercentOnTable_2(TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionPassedPercentOnTable_2, sqlConditionPassedPercentOnTable_2));
        this.sqlConditionPassedPercentOnTable_2 = sqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(sqlConditionPassedPercentOnTable_2, "sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionPassedPercentCheckSpec getSqlConditionPassedPercentOnTable_3() {
        return sqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setSqlConditionPassedPercentOnTable_3(TableSqlConditionPassedPercentCheckSpec sqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionPassedPercentOnTable_3, sqlConditionPassedPercentOnTable_3));
        this.sqlConditionPassedPercentOnTable_3 = sqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(sqlConditionPassedPercentOnTable_3, "sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getSqlConditionFailedCountOnTable_1() {
        return sqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setSqlConditionFailedCountOnTable_1(TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionFailedCountOnTable_1, sqlConditionFailedCountOnTable_1));
        this.sqlConditionFailedCountOnTable_1 = sqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(sqlConditionFailedCountOnTable_1, "sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getSqlConditionFailedCountOnTable_2() {
        return sqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setSqlConditionFailedCountOnTable_2(TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionFailedCountOnTable_2, sqlConditionFailedCountOnTable_2));
        this.sqlConditionFailedCountOnTable_2 = sqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(sqlConditionFailedCountOnTable_2, "sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlConditionFailedCountCheckSpec getSqlConditionFailedCountOnTable_3() {
        return sqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param sqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setSqlConditionFailedCountOnTable_3(TableSqlConditionFailedCountCheckSpec sqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.sqlConditionFailedCountOnTable_3, sqlConditionFailedCountOnTable_3));
        this.sqlConditionFailedCountOnTable_3 = sqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(sqlConditionFailedCountOnTable_3, "sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMinCheckSpec getSqlAggregatedExpressionValueOnTableMin() {
        return sqlAggregatedExpressionValueOnTableMin;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregatedExpressionValueOnTableMin Check specification.
     */
    public void setSqlAggregatedExpressionValueOnTableMin(TableSqlAggregatedExpressionValueMinCheckSpec sqlAggregatedExpressionValueOnTableMin) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregatedExpressionValueOnTableMin, sqlAggregatedExpressionValueOnTableMin));
        this.sqlAggregatedExpressionValueOnTableMin = sqlAggregatedExpressionValueOnTableMin;
        propagateHierarchyIdToField(sqlAggregatedExpressionValueOnTableMin, "sql_aggregated_expression_value_on_table_min");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionValueMaxCheckSpec getSqlAggregatedExpressionValueOnTableMax() {
        return sqlAggregatedExpressionValueOnTableMax;
    }

    /**
     * Sets a new check specification.
     * @param sqlAggregatedExpressionValueOnTableMax Check specification.
     */
    public void setSqlAggregatedExpressionValueOnTableMax(TableSqlAggregatedExpressionValueMaxCheckSpec sqlAggregatedExpressionValueOnTableMax) {
        this.setDirtyIf(!Objects.equals(this.sqlAggregatedExpressionValueOnTableMax, sqlAggregatedExpressionValueOnTableMax));
        this.sqlAggregatedExpressionValueOnTableMax = sqlAggregatedExpressionValueOnTableMax;
        propagateHierarchyIdToField(sqlAggregatedExpressionValueOnTableMax, "sql_aggregated_expression_value_on_table_max");
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