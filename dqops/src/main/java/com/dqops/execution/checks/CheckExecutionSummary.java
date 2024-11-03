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
package com.dqops.execution.checks;

import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import tech.tablesaw.api.*;

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
    private final IntColumn executionErrorsCountColumn;
    private final Table summaryTable;
    private CheckExecutionErrorSummary checkExecutionErrorSummary;

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
     * Returns the column that has a count of checks that failed to execute due to an error (network, credentials, metadata, sensor bug, rule bug).
     * @return Column with the number of execution errors count.
     */
    public IntColumn getExecutionErrorsCountColumn() {
        return executionErrorsCountColumn;
    }

    /**
     * Returns a check execution error summary, digging into the details of errors that <code>executionErrorsCountColumn</code> comprises.
     * @return Check execution error summary.
     */
    public CheckExecutionErrorSummary getCheckExecutionErrorSummary() {
        return checkExecutionErrorSummary;
    }

    /**
     * Adds a table check summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param executionStatistics Check execution statistics.
     */
    public void reportTableStats(ConnectionWrapper connection, TableSpec tableSpec, TableChecksExecutionStatistics executionStatistics) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getPhysicalTableName().toString());
		this.checksExecutedColumn.set(row.getRowNumber(), executionStatistics.getExecutedChecksCount());
		this.sensorResultsColumn.set(row.getRowNumber(), executionStatistics.getSensorReadoutsCount());
		this.validResultsColumn.set(row.getRowNumber(), executionStatistics.getPassedRulesCount());
		this.warningsCountColumn.set(row.getRowNumber(), executionStatistics.getWarningIssuesCount());
		this.errorsCountColumn.set(row.getRowNumber(), executionStatistics.getErrorIssuesCount());
		this.fatalErrorsCountColumn.set(row.getRowNumber(), executionStatistics.getFatalIssuesCount());
        this.executionErrorsCountColumn.set(row.getRowNumber(), executionStatistics.getSensorExecutionErrorsCount() +
                executionStatistics.getRuleExecutionErrorsCount());
    }

    /**
     * Sets <code>checkExecutionErrorSummary</code> if it's null. Else noop.
     * @param checkExecutionErrorSummary New check execution error summary.
     */
    public void updateCheckExecutionErrorSummary(CheckExecutionErrorSummary checkExecutionErrorSummary) {
        if (this.checkExecutionErrorSummary == null) {
            this.checkExecutionErrorSummary = checkExecutionErrorSummary;
        }
    }

    /**
     * Counts the number of checks that were executed with rules evaluated.
     * @return Total number of checks that were executed.
     */
    public int getTotalChecksExecutedCount() {
        return (int)this.checksExecutedColumn.sum();
    }

    /**
     * Counts the number of valid results.
     * @return Number of valid results.
     */
    public int getValidResultsCount() {
        return (int)this.validResultsColumn.sum();
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
     * Returns the total number of check results that did not fail to run (due to execution errors).
     * Counts check results at all severity levels.
     * @return Total count of results.
     */
    public int getTotalCheckResultsCount() {
        return this.getValidResultsCount() + getWarningSeverityIssuesCount() +
                this.getErrorSeverityIssuesCount() + this.getFatalSeverityIssuesCount();
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
        this.updateCheckExecutionErrorSummary(otherCheckExecutionSummary.checkExecutionErrorSummary);
    }
}
