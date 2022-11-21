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
package ai.dqo.execution.checks;

import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 * Tabular object returned from {@link CheckExecutionService} with a summary of sensors that were executed.
 */
public class CheckExecutionSummary {
    private final StringColumn connectionColumn;
    private final StringColumn tableColumn;
    private final IntColumn checksExecutedColumn;
    private final IntColumn validResultsColumn;
    private final IntColumn warningsCountColumn;
    private final IntColumn errorsCountColumn;
    private final IntColumn fatalErrorsCountColumn;
    private final IntColumn sensorResultsColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public CheckExecutionSummary() {
		this.summaryTable = Table.create("Check execution summary");
		connectionColumn = StringColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);
		tableColumn = StringColumn.create("Table");
		this.summaryTable.addColumns(tableColumn);
		checksExecutedColumn = IntColumn.create("Checks");
		this.summaryTable.addColumns(checksExecutedColumn);
		sensorResultsColumn = IntColumn.create("Sensor results");
		this.summaryTable.addColumns(sensorResultsColumn);
		validResultsColumn = IntColumn.create("Valid results");
		this.summaryTable.addColumns(validResultsColumn);
		warningsCountColumn = IntColumn.create("Warnings");
		this.summaryTable.addColumns(warningsCountColumn);
		errorsCountColumn = IntColumn.create("Errors");
		this.summaryTable.addColumns(errorsCountColumn);
		fatalErrorsCountColumn = IntColumn.create("Fatal errors");
		this.summaryTable.addColumns(fatalErrorsCountColumn);
    }

    /**
     * Summary table.
     * @return Check execution summary.
     */
    public Table getSummaryTable() {
        return summaryTable;
    }

    /**
     * Connection name column.
     * @return Column.
     */
    public StringColumn getConnectionColumn() {
        return connectionColumn;
    }

    /**
     * Full table name column.
     * @return Table name column.
     */
    public StringColumn getTableColumn() {
        return tableColumn;
    }

    /**
     * Column with the count of executed checks.
     * @return Count of executed checks.
     */
    public IntColumn getChecksExecutedColumn() {
        return checksExecutedColumn;
    }

    /**
     * Column with a count of sensor results that were returned for evaluation.
     * @return Sensor results count.
     */
    public IntColumn getSensorResultsColumn() {
        return sensorResultsColumn;
    }

    /**
     * Column with the count of rules that were evaluated and passed (no alerts).
     * @return Valid (passed results).
     */
    public IntColumn getValidResultsColumn() {
        return validResultsColumn;
    }

    /**
     * Count of warning severity alerts column.
     * @return Column.
     */
    public IntColumn getWarningsCountColumn() {
        return warningsCountColumn;
    }

    /**
     * Count of errors (alert) - medium severity alerts column.
     * @return Column.
     */
    public IntColumn getErrorsCountColumn() {
        return errorsCountColumn;
    }

    /**
     * Count of fatal (high) severity errors column.
     * @return Column.
     */
    public IntColumn getFatalErrorsCountColumn() {
        return fatalErrorsCountColumn;
    }

    /**
     * Adds a table check summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param checksExecuted Number of checks that were executed.
     * @param sensorResults Number of sensor results returned from sensors.
     * @param validResults Number of valid results that passed the rules.
     * @param warningsCount Count of warning severity alerts.
     * @param errorsCount Count of errors (normal, medium) severity alerts.
     * @param fatalErrorsCount Count of fatal (high) severity alerts.
     */
    public void reportTableStats(ConnectionWrapper connection, TableSpec tableSpec, int checksExecuted, int sensorResults, int validResults,
								 int warningsCount, int errorsCount, int fatalErrorsCount) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getTarget().toPhysicalTableName().toString());
		this.checksExecutedColumn.set(row.getRowNumber(), checksExecuted);
		this.sensorResultsColumn.set(row.getRowNumber(), sensorResults);
		this.validResultsColumn.set(row.getRowNumber(), validResults);
		this.warningsCountColumn.set(row.getRowNumber(), warningsCount);
		this.errorsCountColumn.set(row.getRowNumber(), errorsCount);
		this.fatalErrorsCountColumn.set(row.getRowNumber(), fatalErrorsCount);
    }

    /**
     * Counts the number of checks that were executed.
     * @return Total number of checks that were executed.
     */
    public int getTotalChecksExecutedCount() {
        return this.checksExecutedColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of warnings that were raised.
     * @return Number of warnings.
     */
    public int getWarningSeverityIssuesCount() {
        return this.warningsCountColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of errors (normal severity) that were raised.
     * @return Number of error severity alerts (default alerts).
     */
    public int getErrorSeverityIssuesCount() {
        return this.errorsCountColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of fatal (high) severity errors that were raised.
     * @return Number of fatal severity errors.
     */
    public int getFatalSeverityIssuesCount() {
        return this.fatalErrorsCountColumn.isGreaterThan(0).size();
    }
}
