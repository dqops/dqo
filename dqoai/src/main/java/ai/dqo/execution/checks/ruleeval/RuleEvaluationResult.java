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
package ai.dqo.execution.checks.ruleeval;

import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.util.Objects;

/**
 * Object that contains a dataset (table) with the result of evaluating a rule.
 */
public class RuleEvaluationResult {
    /**
     * Rule severity (0, 1, 2, 3) for none, low, medium and high alerts.
     */
    public static final String SEVERITY_COLUMN_NAME = "severity";

    /**
     * Column name that stores the rule, identified by a hash.
     */
    public static final String RULE_HASH_COLUMN_NAME = "rule_hash";

    /**
     * Rule name.
     */
    public static final String RULE_NAME_COLUMN_NAME = "rule_name";

    /**
     * Column name for the lower bound, returned by the high severity rule.
     */
    public static final String HIGH_LOWER_BOUND_COLUMN_NAME = "high_lower_bound";

    /**
     * Column name for the upper bound, returned by the high severity rule.
     */
    public static final String HIGH_UPPER_BOUND_COLUMN_NAME = "high_upper_bound";

    /**
     * Column name for the lower bound, returned by the medium severity rule.
     */
    public static final String MEDIUM_LOWER_BOUND_COLUMN_NAME = "medium_lower_bound";

    /**
     * Column name for the upper bound, returned by the medium severity rule.
     */
    public static final String MEDIUM_UPPER_BOUND_COLUMN_NAME = "medium_upper_bound";

    /**
     * Column name for the lower bound, returned by the low severity rule.
     */
    public static final String LOW_LOWER_BOUND_COLUMN_NAME = "low_lower_bound";

    /**
     * Column name for the upper bound, returned by the low severity rule.
     */
    public static final String LOW_UPPER_BOUND_COLUMN_NAME = "low_upper_bound";

    private final Table ruleResultsTable;
    private final DoubleColumn actualValueColumn;
    private final DoubleColumn expectedValueColumn;
    private final IntColumn severityColumn;
    private final LongColumn ruleHashColumn;
    private final StringColumn ruleNameColumn;
    private final DoubleColumn highLowerBoundColumn;
    private final DoubleColumn highUpperBoundColumn;
    private final DoubleColumn mediumLowerBoundColumn;
    private final DoubleColumn mediumUpperBoundColumn;
    private final DoubleColumn lowLowerBoundColumn;
    private final DoubleColumn lowUpperBoundColumn;

    /**
     * Creates a rule evaluation result table.
     * @param ruleResultsTable Rule result table, will be modified and additional columns will be added.
     */
    private RuleEvaluationResult(Table ruleResultsTable) {
        this.ruleResultsTable = ruleResultsTable;
        this.actualValueColumn = (DoubleColumn) ruleResultsTable.column(SensorNormalizedResult.ACTUAL_VALUE_COLUMN_NAME);
        this.expectedValueColumn = getOrAddDoubleColumn(ruleResultsTable, SensorNormalizedResult.EXPECTED_VALUE_COLUMN_NAME);
		this.severityColumn = getOrAddIntColumn(ruleResultsTable, SEVERITY_COLUMN_NAME);
		this.ruleHashColumn = getOrAddLongColumn(ruleResultsTable, RULE_HASH_COLUMN_NAME);
        this.ruleNameColumn = getOrAddStringColumn(ruleResultsTable, RULE_NAME_COLUMN_NAME);
		this.highLowerBoundColumn = getOrAddDoubleColumn(ruleResultsTable, HIGH_LOWER_BOUND_COLUMN_NAME);
		this.highUpperBoundColumn = getOrAddDoubleColumn(ruleResultsTable, HIGH_UPPER_BOUND_COLUMN_NAME);
		this.mediumLowerBoundColumn = getOrAddDoubleColumn(ruleResultsTable, MEDIUM_LOWER_BOUND_COLUMN_NAME);
		this.mediumUpperBoundColumn = getOrAddDoubleColumn(ruleResultsTable, MEDIUM_UPPER_BOUND_COLUMN_NAME);
		this.lowLowerBoundColumn = getOrAddDoubleColumn(ruleResultsTable, LOW_LOWER_BOUND_COLUMN_NAME);
		this.lowUpperBoundColumn = getOrAddDoubleColumn(ruleResultsTable, LOW_UPPER_BOUND_COLUMN_NAME);
    }

    /**
     * Create an empty rule evaluation result table, adding all additional required columns (severity, expected_value, high_lower_bound, etc.)
     * @param normalizedSensorResults Normalized results from a sensor execution.
     * @return Empty table to be populated with the rule evaluation results.
     */
    public static RuleEvaluationResult makeEmptyFromSensorResults(SensorNormalizedResult normalizedSensorResults) {
        Table emptyTable = normalizedSensorResults.getTable().emptyCopy();
        emptyTable.setName("sensor_rule_evaluation_results");

        return new RuleEvaluationResult(emptyTable);
    }

    /**
     * Result table with the rule evaluation results, with additional severity values after evaluating the alerts.
     * @return Rule evaluation results table.
     */
    public Table getRuleResultsTable() {
        return ruleResultsTable;
    }

    /**
     * Returns a column name that stores the rule name.
     * @return Rule name column.
     */
    public StringColumn getRuleNameColumn() {
        return ruleNameColumn;
    }

    /**
     * Returns the severity column with the output severity level of an alert.
     * @return Severity column.
     */
    public IntColumn getSeverityColumn() {
        return severityColumn;
    }

    /**
     * Actual value column.
     * @return Actual value column.
     */
    public DoubleColumn getActualValueColumn() {
        return actualValueColumn;
    }

    /**
     * Expected value column.
     * @return Expected value column.
     */
    public DoubleColumn getExpectedValueColumn() {
        return expectedValueColumn;
    }

    /**
     * Rule hash column.
     * @return Rule hash column.
     */
    public LongColumn getRuleHashColumn() {
        return ruleHashColumn;
    }

    /**
     * Lower bound column for the high severity rule.
     * @return High severity rule lower bound column.
     */
    public DoubleColumn getHighLowerBoundColumn() {
        return highLowerBoundColumn;
    }

    /**
     * Upper bound column for the high severity rule.
     * @return High severity rule upper bound column.
     */
    public DoubleColumn getHighUpperBoundColumn() {
        return highUpperBoundColumn;
    }

    /**
     * Lower bound column for the medium severity rule.
     * @return Medium severity rule lower bound column.
     */
    public DoubleColumn getMediumLowerBoundColumn() {
        return mediumLowerBoundColumn;
    }

    /**
     * Upper bound column for the medium severity rule.
     * @return Medium severity rule upper bound column.
     */
    public DoubleColumn getMediumUpperBoundColumn() {
        return mediumUpperBoundColumn;
    }

    /**
     * Lower bound column for the low severity rule.
     * @return Low severity rule lower bound column.
     */
    public DoubleColumn getLowLowerBoundColumn() {
        return lowLowerBoundColumn;
    }

    /**
     * Upper bound column for the low severity rule.
     * @return Low severity rule upper bound column.
     */
    public DoubleColumn getLowUpperBoundColumn() {
        return lowUpperBoundColumn;
    }

    /**
     * Retrieves or adds a new integer column. Used to access/create the severity column.
     * @param targetTable Target table to get/add the column.
     * @param columnName Column name.
     * @return Integer column that was found or added.
     */
    public IntColumn getOrAddIntColumn(Table targetTable, String columnName) {
        if (targetTable.containsColumn(columnName)) {
            return (IntColumn) targetTable.column(columnName);
        }

        IntColumn newColumn = IntColumn.create(columnName);
        targetTable.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds a new long column. Used to access/create the severity column.
     * @param targetTable Target table to get/add the column.
     * @param columnName Column name.
     * @return Long column that was found or added.
     */
    public LongColumn getOrAddLongColumn(Table targetTable, String columnName) {
        if (targetTable.containsColumn(columnName)) {
            return (LongColumn) targetTable.column(columnName);
        }

        LongColumn newColumn = LongColumn.create(columnName);
        targetTable.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds a new double column. Used to access/create the expected value column, lower and upper bound columns.
     * @param targetTable Target table to get/add the column.
     * @param columnName Column name.
     * @return Double column that was found or added.
     */
    public DoubleColumn getOrAddDoubleColumn(Table targetTable, String columnName) {
        if (targetTable.containsColumn(columnName)) {
            return (DoubleColumn) targetTable.column(columnName);
        }

        DoubleColumn newColumn = DoubleColumn.create(columnName);
        targetTable.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds a new String column. Used to access/create additional columns, like the rule name.
     * @param targetTable Target table to get/add the column.
     * @param columnName Column name.
     * @return String column that was found or added.
     */
    public StringColumn getOrAddStringColumn(Table targetTable, String columnName) {
        if (targetTable.containsColumn(columnName)) {
            return (StringColumn) targetTable.column(columnName);
        }

        StringColumn newColumn = StringColumn.create(columnName);
        targetTable.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Appends a row to the rule results table.
     * @return Appends a new empty row.
     */
    public Row appendRow() {
        Row newRow = this.ruleResultsTable.appendRow();
        return newRow;
    }

    /**
     * Copy a row at index <code>sourceRowIndex</code> from <code>sourceTable</code> source table to the row result table (handled by this instance)
     * at the <code>targetRowIndex</code> target row index.
     * @param targetRowIndex Target row index.
     * @param sourceTable Source table that has the same columns. The target table must have the same columns, but may have additional columns.
     * @param sourceRowIndex Source row index.
     */
    public void copyRowFrom(int targetRowIndex, Table sourceTable, int sourceRowIndex) {
        for (int colIndex = 0; colIndex < sourceTable.columnCount() ; colIndex++) {
            Column<Object> sourceColumn = (Column<Object>) sourceTable.column(colIndex);
            Column<Object> targetColumn = (Column<Object>) this.ruleResultsTable.column(colIndex);
            assert Objects.equals(sourceColumn.name(), targetColumn.name());
            targetColumn.set(targetRowIndex, sourceColumn, sourceRowIndex);
        }
    }
}
