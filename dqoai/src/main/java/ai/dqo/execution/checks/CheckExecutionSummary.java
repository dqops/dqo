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
import tech.tablesaw.api.*;

/**
 * Tabular object returned from {@link CheckExecutionService} with a summary of sensors that were executed.
 */
public class CheckExecutionSummary {
    private final TextColumn connectionColumn;
    private final TextColumn tableColumn;
    private final IntColumn checksExecutedColumn;
    private final IntColumn validResultsColumn;
    private final IntColumn warningsCountColumn;
    private final IntColumn errorsCountColumn;
    private final IntColumn fatalErrorsCountColumn;
    private final IntColumn sensorResultsColumn;
    private final IntColumn executionErrorsCountColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public CheckExecutionSummary() {
		this.summaryTable = Table.create("Check execution summary");
		connectionColumn = TextColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);
		tableColumn = TextColumn.create("Table");
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
        executionErrorsCountColumn = IntColumn.create("Execution errors");
        this.summaryTable.addColumns(executionErrorsCountColumn);
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
    public TextColumn getConnectionColumn() {
        return connectionColumn;
    }

    /**
     * Full table name column.
     * @return Table name column.
     */
    public TextColumn getTableColumn() {
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
     * Returns the column that has a count of checks that failed to execute due to an error (network, credentials, metadata, sensor bug, rule bug).
     * @return Column with the number of execution errors count.
     */
    public IntColumn getExecutionErrorsCountColumn() {
        return executionErrorsCountColumn;
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
     * @param executionErrorsCount Count of check execution errors (checks that failed to execute correctly).
     */
    public void reportTableStats(ConnectionWrapper connection, TableSpec tableSpec, int checksExecuted, int sensorResults, int validResults,
								 int warningsCount, int errorsCount, int fatalErrorsCount, int executionErrorsCount) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getPhysicalTableName().toString());
		this.checksExecutedColumn.set(row.getRowNumber(), checksExecuted);
		this.sensorResultsColumn.set(row.getRowNumber(), sensorResults);
		this.validResultsColumn.set(row.getRowNumber(), validResults);
		this.warningsCountColumn.set(row.getRowNumber(), warningsCount);
		this.errorsCountColumn.set(row.getRowNumber(), errorsCount);
		this.fatalErrorsCountColumn.set(row.getRowNumber(), fatalErrorsCount);
        this.executionErrorsCountColumn.set(row.getRowNumber(), executionErrorsCount);
    }

    /**
     * Counts the number of checks that were executed with rules evaluated.
     * @return Total number of checks that were executed.
     */
    public int getTotalChecksExecutedCount() {
        return (int)this.checksExecutedColumn.sum();
    }

    /**
     * Counts the number of warnings that were raised.
     * @return Number of warnings.
     */
    public int getWarningSeverityIssuesCount() {
        return (int)this.warningsCountColumn.sum();
    }

    /**
     * Counts the number of errors (normal severity) that were raised.
     * @return Number of error severity alerts (default alerts).
     */
    public int getErrorSeverityIssuesCount() {
        return (int)this.errorsCountColumn.sum();
    }

    /**
     * Counts the number of fatal (high) severity errors that were raised.
     * @return Number of fatal severity errors.
     */
    public int getFatalSeverityIssuesCount() {
        return (int)this.fatalErrorsCountColumn.sum();
    }

    /**
     * Returns the total count of checks that failed to execute.
     * @return Total count of execution errors (checks failed to execute).
     */
    public int getTotalExecutionErrorsCount() {
        return (int)this.executionErrorsCountColumn.sum();
    }

    /**
     * Appends results from another check execution result.
     * Usable when multiple parallel jobs were executed to run checks in parallel.
     * @param otherCheckExecutionSummary Other checks execution summary.
     */
    public void append(CheckExecutionSummary otherCheckExecutionSummary) {
        this.summaryTable.append(otherCheckExecutionSummary.getSummaryTable());
    }
}
