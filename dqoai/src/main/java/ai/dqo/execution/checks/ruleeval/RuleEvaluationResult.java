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

import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.utils.tables.TableColumnUtility;
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
     * Column name for a boolean column that identifies data quality rule results that should be counted in the data quality KPI.
     */
    public static final String INCLUDE_IN_KPI_COLUMN_NAME = "include_in_kpi";

    /**
     * Column name for a boolean column that identifies data quality rule results that should be counted in the data quality SLA.
     */
    public static final String INCLUDE_IN_SLA_COLUMN_NAME = "include_in_sla";

    /**
     * Column name for the warning lower bound, returned by the fatal severity rule.
     */
    public static final String FATAL_LOWER_BOUND_COLUMN_NAME = "fatal_lower_bound";

    /**
     * Column name for the fatal upper bound, returned by the fatal severity rule.
     */
    public static final String FATAL_UPPER_BOUND_COLUMN_NAME = "fatal_upper_bound";

    /**
     * Column name for the error lower bound, returned by the error (medium) severity rule.
     */
    public static final String ERROR_LOWER_BOUND_COLUMN_NAME = "error_lower_bound";

    /**
     * Column name for the error upper bound, returned by the error severity rule.
     */
    public static final String ERROR_UPPER_BOUND_COLUMN_NAME = "error_upper_bound";

    /**
     * Column name for the warning lower bound, returned by the warning severity rule.
     */
    public static final String WARNING_LOWER_BOUND_COLUMN_NAME = "warning_lower_bound";

    /**
     * Column name for the warning upper bound, returned by the warning severity rule.
     */
    public static final String WARNING_UPPER_BOUND_COLUMN_NAME = "warning_upper_bound";

    private final Table ruleResultsTable;
    private final DoubleColumn actualValueColumn;
    private final DoubleColumn expectedValueColumn;
    private final IntColumn severityColumn;
    private final BooleanColumn includeInKpiColumn;
    private final BooleanColumn includeInSlaColumn;

    private final DoubleColumn fatalLowerBoundColumn;
    private final DoubleColumn fatalUpperBoundColumn;
    private final DoubleColumn errorLowerBoundColumn;
    private final DoubleColumn errorUpperBoundColumn;
    private final DoubleColumn warningLowerBoundColumn;
    private final DoubleColumn warningUpperBoundColumn;

    /**
     * Creates a rule evaluation result table.
     * @param ruleResultsTable Rule result table, will be modified and additional columns will be added.
     */
    private RuleEvaluationResult(Table ruleResultsTable) {
        this.ruleResultsTable = ruleResultsTable;
        this.actualValueColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME);
        this.expectedValueColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, SensorReadoutsNormalizedResult.EXPECTED_VALUE_COLUMN_NAME);
		this.severityColumn = TableColumnUtility.getOrAddIntColumn(ruleResultsTable, SEVERITY_COLUMN_NAME);
        this.includeInKpiColumn = TableColumnUtility.getOrAddBooleanColumn(ruleResultsTable, INCLUDE_IN_KPI_COLUMN_NAME);
        this.includeInSlaColumn = TableColumnUtility.getOrAddBooleanColumn(ruleResultsTable, INCLUDE_IN_SLA_COLUMN_NAME);
		this.fatalLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, FATAL_LOWER_BOUND_COLUMN_NAME);
		this.fatalUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, FATAL_UPPER_BOUND_COLUMN_NAME);
		this.errorLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, ERROR_LOWER_BOUND_COLUMN_NAME);
		this.errorUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, ERROR_UPPER_BOUND_COLUMN_NAME);
		this.warningLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, WARNING_LOWER_BOUND_COLUMN_NAME);
		this.warningUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, WARNING_UPPER_BOUND_COLUMN_NAME);
    }

    /**
     * Create an empty rule evaluation result table, adding all additional required columns (severity, expected_value, fatal_lower_bound, etc.)
     * @param normalizedSensorResults Normalized results from a sensor execution.
     * @return Empty table to be populated with the rule evaluation results.
     */
    public static RuleEvaluationResult makeEmptyFromSensorResults(SensorReadoutsNormalizedResult normalizedSensorResults) {
        Table emptyTable = normalizedSensorResults.getTable().emptyCopy();
        emptyTable.setName("rule_evaluation_results");

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
     * Returns the severity column with the output severity level of an alert (data quality rule result).
     * @return Severity column.
     */
    public IntColumn getSeverityColumn() {
        return severityColumn;
    }

    /**
     * Returns the include_in_kpi boolean column that should have a 'true' value for rule results that should be included in the KPI calculation.
     * @return Include in KPI column.
     */
    public BooleanColumn getIncludeInKpiColumn() {
        return includeInKpiColumn;
    }

    /**
     * Returns the include_in_sla boolean column that should have a 'true' value for rule results that should be included in the data quality SLA (the data contract).
     * @return Include in the data quality SLA (the data contract) column.
     */
    public BooleanColumn getIncludeInSlaColumn() {
        return includeInSlaColumn;
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
     * Lower bound column for the fatal severity rule.
     * @return Fatal severity rule lower bound column.
     */
    public DoubleColumn getFatalLowerBoundColumn() {
        return fatalLowerBoundColumn;
    }

    /**
     * Upper bound column for the fatal severity rule.
     * @return Fatal severity rule upper bound column.
     */
    public DoubleColumn getFatalUpperBoundColumn() {
        return fatalUpperBoundColumn;
    }

    /**
     * Lower bound column for the error severity rule.
     * @return Error severity rule lower bound column.
     */
    public DoubleColumn getErrorLowerBoundColumn() {
        return errorLowerBoundColumn;
    }

    /**
     * Upper bound column for the error severity rule.
     * @return Error severity rule upper bound column.
     */
    public DoubleColumn getErrorUpperBoundColumn() {
        return errorUpperBoundColumn;
    }

    /**
     * Lower bound column for the warning severity rule.
     * @return Warning severity rule lower bound column.
     */
    public DoubleColumn getWarningLowerBoundColumn() {
        return warningLowerBoundColumn;
    }

    /**
     * Upper bound column for the warning severity rule.
     * @return Warning severity rule upper bound column.
     */
    public DoubleColumn getWarningUpperBoundColumn() {
        return warningUpperBoundColumn;
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
