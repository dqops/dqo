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
import ai.dqo.checks.table.checkspecs.sql.TableMaxSqlConditionFailedCountCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableMinSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionMaxValueCheckSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlAggregatedExpressionMinValueCheckSpec;
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
            put("min_sql_condition_passed_percent_on_table_1", o -> o.minSqlConditionPassedPercentOnTable_1);
            put("min_sql_condition_passed_percent_on_table_2", o -> o.minSqlConditionPassedPercentOnTable_2);
            put("min_sql_condition_passed_percent_on_table_3", o -> o.minSqlConditionPassedPercentOnTable_3);

            put("max_sql_condition_failed_count_on_table_1", o -> o.maxSqlConditionFailedCountOnTable_1);
            put("max_sql_condition_failed_count_on_table_2", o -> o.maxSqlConditionFailedCountOnTable_2);
            put("max_sql_condition_failed_count_on_table_3", o -> o.maxSqlConditionFailedCountOnTable_3);

            put("min_sql_aggregated_expression_value_on_table", o -> o.minSqlAggregatedExpressionValueOnTable);
            put("max_sql_aggregated_expression_value_on_table", o -> o.maxSqlAggregatedExpressionValueOnTable);
        }
    };

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_1;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_2;

    @JsonPropertyDescription("Verifies that a minimum percentage of rows passed a custom SQL condition (expression).")
    private TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_3;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_1;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_2;

    @JsonPropertyDescription("Verifies that a maximum number of rows failed a custom SQL condition (expression).")
    private TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_3;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.")
    private TableSqlAggregatedExpressionMinValueCheckSpec minSqlAggregatedExpressionValueOnTable;

    @JsonPropertyDescription("Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given maximum accepted value.")
    private TableSqlAggregatedExpressionMaxValueCheckSpec maxSqlAggregatedExpressionValueOnTable;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_1() {
        return minSqlConditionPassedPercentOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnTable_1 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnTable_1(TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnTable_1, minSqlConditionPassedPercentOnTable_1));
        this.minSqlConditionPassedPercentOnTable_1 = minSqlConditionPassedPercentOnTable_1;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnTable_1, "min_sql_condition_passed_percent_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_2() {
        return minSqlConditionPassedPercentOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnTable_2 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnTable_2(TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnTable_2, minSqlConditionPassedPercentOnTable_2));
        this.minSqlConditionPassedPercentOnTable_2 = minSqlConditionPassedPercentOnTable_2;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnTable_2, "min_sql_condition_passed_percent_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMinSqlConditionPassedPercentCheckSpec getMinSqlConditionPassedPercentOnTable_3() {
        return minSqlConditionPassedPercentOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param minSqlConditionPassedPercentOnTable_3 Check specification.
     */
    public void setMinSqlConditionPassedPercentOnTable_3(TableMinSqlConditionPassedPercentCheckSpec minSqlConditionPassedPercentOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.minSqlConditionPassedPercentOnTable_3, minSqlConditionPassedPercentOnTable_3));
        this.minSqlConditionPassedPercentOnTable_3 = minSqlConditionPassedPercentOnTable_3;
        propagateHierarchyIdToField(minSqlConditionPassedPercentOnTable_3, "min_sql_condition_passed_percent_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnTable_1() {
        return maxSqlConditionFailedCountOnTable_1;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnTable_1 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnTable_1(TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_1) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnTable_1, maxSqlConditionFailedCountOnTable_1));
        this.maxSqlConditionFailedCountOnTable_1 = maxSqlConditionFailedCountOnTable_1;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnTable_1, "max_sql_condition_failed_count_on_table_1");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnTable_2() {
        return maxSqlConditionFailedCountOnTable_2;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnTable_2 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnTable_2(TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_2) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnTable_2, maxSqlConditionFailedCountOnTable_2));
        this.maxSqlConditionFailedCountOnTable_2 = maxSqlConditionFailedCountOnTable_2;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnTable_2, "max_sql_condition_failed_count_on_table_2");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableMaxSqlConditionFailedCountCheckSpec getMaxSqlConditionFailedCountOnTable_3() {
        return maxSqlConditionFailedCountOnTable_3;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlConditionFailedCountOnTable_3 Check specification.
     */
    public void setMaxSqlConditionFailedCountOnTable_3(TableMaxSqlConditionFailedCountCheckSpec maxSqlConditionFailedCountOnTable_3) {
        this.setDirtyIf(!Objects.equals(this.maxSqlConditionFailedCountOnTable_3, maxSqlConditionFailedCountOnTable_3));
        this.maxSqlConditionFailedCountOnTable_3 = maxSqlConditionFailedCountOnTable_3;
        propagateHierarchyIdToField(maxSqlConditionFailedCountOnTable_3, "max_sql_condition_failed_count_on_table_3");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMinValueCheckSpec getMinSqlAggregatedExpressionValueOnTable() {
        return minSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param minSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMinSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMinValueCheckSpec minSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.minSqlAggregatedExpressionValueOnTable, minSqlAggregatedExpressionValueOnTable));
        this.minSqlAggregatedExpressionValueOnTable = minSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(minSqlAggregatedExpressionValueOnTable, "min_sql_aggregated_expression_value_on_table");
    }

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableSqlAggregatedExpressionMaxValueCheckSpec getMaxSqlAggregatedExpressionValueOnTable() {
        return maxSqlAggregatedExpressionValueOnTable;
    }

    /**
     * Sets a new check specification.
     * @param maxSqlAggregatedExpressionValueOnTable Check specification.
     */
    public void setMaxSqlAggregatedExpressionValueOnTable(TableSqlAggregatedExpressionMaxValueCheckSpec maxSqlAggregatedExpressionValueOnTable) {
        this.setDirtyIf(!Objects.equals(this.maxSqlAggregatedExpressionValueOnTable, maxSqlAggregatedExpressionValueOnTable));
        this.maxSqlAggregatedExpressionValueOnTable = maxSqlAggregatedExpressionValueOnTable;
        propagateHierarchyIdToField(maxSqlAggregatedExpressionValueOnTable, "max_sql_aggregated_expression_value_on_table");
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