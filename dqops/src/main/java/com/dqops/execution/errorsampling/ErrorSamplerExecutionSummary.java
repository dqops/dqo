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
package com.dqops.execution.errorsampling;

import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;

/**
 * Tabular object returned from {@link TableErrorSamplerExecutionServiceImpl} with a summary of error samples that were collected.
 */
public class ErrorSamplerExecutionSummary {
    private Throwable firstException;
    private final TextColumn connectionColumn;
    private final TextColumn tableColumn;
    private final IntColumn errorSamplersExecutedColumn;
    private final IntColumn errorSampledColumnsColumn;
    private final IntColumn successfullyErrorSampledColumnsColumn;
    private final IntColumn errorSamplersFailedColumn;
    private final IntColumn collectedErrorSamplesCountColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public ErrorSamplerExecutionSummary() {
		this.summaryTable = Table.create("Error samples collection execution summary");

        this.connectionColumn = TextColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);

        this.tableColumn = TextColumn.create("Table");
		this.summaryTable.addColumns(tableColumn);

		this.errorSamplersExecutedColumn = IntColumn.create("Error samplers executed");
		this.summaryTable.addColumns(errorSamplersExecutedColumn);

        this.errorSampledColumnsColumn = IntColumn.create("Columns analyzed for error samples");
        this.summaryTable.addColumns(errorSampledColumnsColumn);

        this.successfullyErrorSampledColumnsColumn = IntColumn.create("Columns error sampled successfully");
        this.summaryTable.addColumns(successfullyErrorSampledColumnsColumn);

        this.errorSamplersFailedColumn = IntColumn.create("Error samplers failed count");
        this.summaryTable.addColumns(errorSamplersFailedColumn);

        this.collectedErrorSamplesCountColumn = IntColumn.create("Collected error samples count");
        this.summaryTable.addColumns(collectedErrorSamplesCountColumn);
    }

    /**
     * Summary table.
     * @return Error sampler execution summary as a table.
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
     * Returns the column that contains the total number of table and column level error samplers that were evaluated for the given column.
     * @return Error sampler count.
     */
    public IntColumn getErrorSamplersExecutedColumn() {
        return errorSamplersExecutedColumn;
    }

    /**
     * Returns the column that returns the number of columns that were analyzed for error samples.
     * @return Column with the number of error sampled columns.
     */
    public IntColumn getErrorSampledColumnsColumn() {
        return errorSampledColumnsColumn;
    }

    /**
     * Returns a dataset column that contains the number of columns on a table that had at least one error sampler successfully executed, so the column exists.
     * @return Number of columns that were analysed with at least one error sampler.
     */
    public IntColumn getSuccessfullyErrorSampledColumnsColumn() {
        return successfullyErrorSampledColumnsColumn;
    }

    /**
     * Returns a column that returns the number of failed error samplers that were not able to capture results.
     * @return Number of failed error samplers.
     */
    public IntColumn getErrorSamplersFailedColumn() {
        return errorSamplersFailedColumn;
    }

    /**
     * Returns a column that contains the count of results retrieved error samples by error samplers. The total count could be higher when error samplers are using data streams
     * and separate error samples are captured for each data stream.
     * @return Collected error samples count column.
     */
    public IntColumn getCollectedErrorSamplesCountColumn() {
        return collectedErrorSamplesCountColumn;
    }

    /**
     * Adds a table error sampling statistics summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param executionStatistics Error sampler execution statistics with the counts of error samplers executed, error samples results collected count, etc..
     */
    public void reportTableStats(ConnectionWrapper connection,
                                 TableSpec tableSpec,
                                 ErrorSamplerExecutionStatistics executionStatistics) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getPhysicalTableName().toString());
		this.errorSamplersExecutedColumn.set(row.getRowNumber(), executionStatistics.getErrorSamplersExecutedCount());
		this.errorSampledColumnsColumn.set(row.getRowNumber(), executionStatistics.getAnalyzedColumnsCount());
		this.successfullyErrorSampledColumnsColumn.set(row.getRowNumber(), executionStatistics.getAnalyzedColumnSuccessfullyCount());
		this.errorSamplersFailedColumn.set(row.getRowNumber(), executionStatistics.getErrorSamplersFailedCount());
        this.collectedErrorSamplesCountColumn.set(row.getRowNumber(), executionStatistics.getCollectedErrorSamplesCount());
        if (this.firstException == null && executionStatistics.getFirstException() != null) {
            this.firstException = executionStatistics.getFirstException();
        }
    }

    /**
     * Appends results from another summary. Used to append results from child jobs.
     * @param otherExecutionSummary Other summary, from a child job (a table level job that collected error samples from a single table).
     */
    public void append(ErrorSamplerExecutionSummary otherExecutionSummary) {
        this.summaryTable.append(otherExecutionSummary.getSummaryTable());
        if (this.firstException == null && otherExecutionSummary.getFirstException() != null) {
            this.firstException = otherExecutionSummary.getFirstException();
        }
    }

    /**
     * Returns the number of executed error samplers.
     * @return Number of executed error samplers.
     */
    public int getTotalCollectorsExecuted() {
        return (int)this.errorSamplersExecutedColumn.sum();
    }

    /**
     * Returns the count of columns for which DQOps executed an error sampler and tried to read the error samples.
     * @return The count of columns for which DQOps executed an error sampler and tried to read the error samples.
     */
    public int getColumnsAnalyzedCount() {
        return (int)this.errorSampledColumnsColumn.sum();
    }

    /**
     * Returns the number of columns for which DQOps managed to obtain error samples.
     * @return Number of columns that were analyzed successfully.
     */
    public int getColumnsSuccessfullyAnalyzed() {
        return (int)this.successfullyErrorSampledColumnsColumn.sum();
    }

    /**
     * Returns the count of error samplers that failed to execute.
     * @return The count of error samplers that failed to execute.
     */
    public int getTotalCollectorsFailed() {
        return (int)this.errorSamplersFailedColumn.sum();
    }

    /**
     * Returns the total number of error samples (values) that were collected.
     * @return The total number of error samples (values) that were collected.
     */
    public int getTotalCollectedResults() {
        return (int)this.collectedErrorSamplesCountColumn.sum();
    }

    /**
     * Returns the first exception that was thrown when running the error samples collection. It is the exception from the first error sampler that failed.
     * @return The exception from the first failed error sampler collector or null when no collectors failed.
     */
    public Throwable getFirstException() {
        return firstException;
    }

    /**
     * Checks if the whole error sampling collection job failed, resulting only in failures.
     * @return True when the error sampling collection failed for all collectors, false when some error samplers managed to collect error samples.
     */
    public boolean getAllChecksFailed() {
        if (this.getTotalCollectorsExecuted() > 0 &&  // there are some matches to run collects
            this.getTotalCollectedResults() == 0 &&   // but no results were collected
            this.getTotalCollectorsFailed() > 0) {    // and only failures were recorded, probably no access to the data source
            return true;
        }

        return false;
    }

    /**
     * Extracts the table name from the first reported row.
     * @return The table name for the first row or null.
     */
    public String getFirstTableName() {
        if (this.summaryTable.rowCount() == 0) {
            return null;
        }

        String tableName = this.getTableColumn().getString(0);
        return tableName;
    }

    /**
     * Extracts the connection name from the first reported row.
     * @return The table connection for the first row or null.
     */
    public String getFirstConnectionName() {
        if (this.summaryTable.rowCount() == 0) {
            return null;
        }

        String connectionName = this.getConnectionColumn().getString(0);
        return connectionName;
    }
}
