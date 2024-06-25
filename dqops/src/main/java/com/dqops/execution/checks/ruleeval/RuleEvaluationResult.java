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
package com.dqops.execution.checks.ruleeval;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.util.Objects;

/**
 * Object that contains a dataset (table) with the result of evaluating a rule.
 */
public class RuleEvaluationResult {
    private final Table ruleResultsTable;
    private final DoubleColumn actualValueColumn;
    private final DoubleColumn expectedValueColumn;
    private final IntColumn severityColumn;
    private final LongColumn incidentHashColumn;
    private final TextColumn referenceConnectionColumn;
    private final TextColumn referenceSchemaColumn;
    private final TextColumn referenceTableColumn;
    private final TextColumn referenceColumnColumn;
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
    public RuleEvaluationResult(Table ruleResultsTable) {
        this(ruleResultsTable, true);
    }

    /**
     * Creates a rule evaluation result table.
     * @param ruleResultsTable Rule result table, will be modified and additional columns will be added.
     * @param addColumWhenMissing Add columns if they are missing.
     */
    public RuleEvaluationResult(Table ruleResultsTable, boolean addColumWhenMissing) {
        this.ruleResultsTable = ruleResultsTable;
        this.actualValueColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, addColumWhenMissing);
        this.expectedValueColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME, addColumWhenMissing);
		this.severityColumn = TableColumnUtility.getOrAddIntColumn(ruleResultsTable, CheckResultsColumnNames.SEVERITY_COLUMN_NAME, addColumWhenMissing);
        this.incidentHashColumn = TableColumnUtility.getOrAddLongColumn(ruleResultsTable, CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME, addColumWhenMissing);
        this.referenceConnectionColumn = TableColumnUtility.getOrAddTextColumn(ruleResultsTable, CheckResultsColumnNames.REFERENCE_CONNECTION_COLUMN_NAME, addColumWhenMissing);
        this.referenceSchemaColumn = TableColumnUtility.getOrAddTextColumn(ruleResultsTable, CheckResultsColumnNames.REFERENCE_SCHEMA_COLUMN_NAME, addColumWhenMissing);
        this.referenceTableColumn = TableColumnUtility.getOrAddTextColumn(ruleResultsTable, CheckResultsColumnNames.REFERENCE_TABLE_COLUMN_NAME, addColumWhenMissing);
        this.referenceColumnColumn = TableColumnUtility.getOrAddTextColumn(ruleResultsTable, CheckResultsColumnNames.REFERENCE_COLUMN_COLUMN_NAME, addColumWhenMissing);
        this.includeInKpiColumn = TableColumnUtility.getOrAddBooleanColumn(ruleResultsTable, CheckResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME, addColumWhenMissing);
        this.includeInSlaColumn = TableColumnUtility.getOrAddBooleanColumn(ruleResultsTable, CheckResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME, addColumWhenMissing);
		this.fatalLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
		this.fatalUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
		this.errorLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
		this.errorUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
		this.warningLowerBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME, addColumWhenMissing);
		this.warningUpperBoundColumn = TableColumnUtility.getOrAddDoubleColumn(ruleResultsTable, CheckResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME, addColumWhenMissing);
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
     * Returns the table_hash long column that stores a matching incident hash for failed data quality checks.
     * @return table_hash column.
     */
    public LongColumn getIncidentHashColumn() {
        return incidentHashColumn;
    }

    /**
     * Returns the column that stores the name of a reference connection name for accuracy checks.
     * @return Reference connection name column.
     */
    public TextColumn getReferenceConnectionColumn() {
        return referenceConnectionColumn;
    }

    /**
     * Returns the column that stores the name of a reference schema name for accuracy checks.
     * @return Reference schema name column.
     */
    public TextColumn getReferenceSchemaColumn() {
        return referenceSchemaColumn;
    }

    /**
     * Returns the column that stores the name of a reference table name for accuracy checks.
     * @return Reference table name column.
     */
    public TextColumn getReferenceTableColumn() {
        return referenceTableColumn;
    }

    /**
     * Returns the column that stores the name of a reference column name for accuracy checks.
     * @return Reference column name column.
     */
    public TextColumn getReferenceColumnColumn() {
        return referenceColumnColumn;
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
     * Counts results with an issue severity level that is at least <code>minimumSeverityLevel</code>. Use 1 to count checks that failed with a warning, error or fatal severity levels.
     * @param minimumSeverityLevel Minimum severity level to include in the count.
     * @return Count of issues at the given or higher severity levels.
     */
    public int countIssueSeverityResults(int minimumSeverityLevel) {
        return this.getSeverityColumn().isLessThanOrEqualTo(minimumSeverityLevel).size();
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
            String columnName = sourceColumn.name();
            if (!Objects.equals(columnName, targetColumn.name())) {
                targetColumn = (Column<Object>) TableColumnUtility.findColumn(this.ruleResultsTable, columnName);
                if (targetColumn == null) {
                    throw new DqoRuntimeException("Cannot find the target column named " + columnName + " when copying columns");
                }
            }
            targetColumn.set(targetRowIndex, sourceColumn, sourceRowIndex);
        }
    }
}
