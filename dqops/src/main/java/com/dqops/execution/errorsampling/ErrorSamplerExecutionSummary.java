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
    private final IntColumn collectorsExecutedColumn;
    private final IntColumn collectorsColumnsColumn;
    private final IntColumn collectorsColumnsSuccessfullyColumn;
    private final IntColumn collectorsFailedColumn;
    private final IntColumn collectorsResultsColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public ErrorSamplerExecutionSummary() {
		this.summaryTable = Table.create("Statistics collection execution summary");

        this.connectionColumn = TextColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);

        this.tableColumn = TextColumn.create("Table");
		this.summaryTable.addColumns(tableColumn);

		this.collectorsExecutedColumn = IntColumn.create("Collectors executed");
		this.summaryTable.addColumns(collectorsExecutedColumn);

        this.collectorsColumnsColumn = IntColumn.create("Columns profiled");
        this.summaryTable.addColumns(collectorsColumnsColumn);

        this.collectorsColumnsSuccessfullyColumn = IntColumn.create("Columns profiled successfully");
        this.summaryTable.addColumns(collectorsColumnsSuccessfullyColumn);

        this.collectorsFailedColumn = IntColumn.create("Collectors failed count");
        this.summaryTable.addColumns(collectorsFailedColumn);

        this.collectorsResultsColumn = IntColumn.create("Collectors results count");
        this.summaryTable.addColumns(collectorsResultsColumn);
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
     * Returns the column that contains the total number of table and column level statistics collectors that were evaluated for the given column.
     * @return Statistics collectors count.
     */
    public IntColumn getCollectorsExecutedColumn() {
        return collectorsExecutedColumn;
    }

    /**
     * Returns the column that returns the number of columns that were profiled.
     * @return Column with the number of profiled columns.
     */
    public IntColumn getCollectorsColumnsColumn() {
        return collectorsColumnsColumn;
    }

    /**
     * Returns a dataset column that contains the number of columns on a table that had at least one collector successfully executed, so the column exists.
     * @return Number of columns that were analysed with at least one statistics collector.
     */
    public IntColumn getCollectorsColumnsSuccessfullyColumn() {
        return collectorsColumnsSuccessfullyColumn;
    }

    /**
     * Returns a column that returns the number of failed statistics collectors that were not able to capture results.
     * @return Number of failed statistics collectors.
     */
    public IntColumn getCollectorsFailedColumn() {
        return collectorsFailedColumn;
    }

    /**
     * Returns a column that contains the count of results retrieved from statistics collectors. The total count could be higher when statistics collectors are using data streams
     * and separate statistics are captured for each data stream.
     * @return Statistics results count column.
     */
    public IntColumn getCollectorsResultsColumn() {
        return collectorsResultsColumn;
    }

    /**
     * Adds a table statistics collection summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param executionStatistics Collector execution statistics with the counts of collectors executed, statistics results collected count, etc..
     */
    public void reportTableStats(ConnectionWrapper connection,
                                 TableSpec tableSpec,
                                 ErrorSamplerExecutionStatistics executionStatistics) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getPhysicalTableName().toString());
		this.collectorsExecutedColumn.set(row.getRowNumber(), executionStatistics.getCollectorsExecutedCount());
		this.collectorsColumnsColumn.set(row.getRowNumber(), executionStatistics.getProfiledColumnsCount());
		this.collectorsColumnsSuccessfullyColumn.set(row.getRowNumber(), executionStatistics.getProfiledColumnSuccessfullyCount());
		this.collectorsFailedColumn.set(row.getRowNumber(), executionStatistics.getCollectorsFailedCount());
        this.collectorsResultsColumn.set(row.getRowNumber(), executionStatistics.getCollectorsResultsCount());
        if (this.firstException == null && executionStatistics.getFirstException() != null) {
            this.firstException = executionStatistics.getFirstException();
        }
    }

    /**
     * Appends results from another summary. Used to append results from child jobs.
     * @param otherExecutionSummary Other summary, from a child job (a table level job that collected statistics from a single table).
     */
    public void append(ErrorSamplerExecutionSummary otherExecutionSummary) {
        this.summaryTable.append(otherExecutionSummary.getSummaryTable());
        if (this.firstException == null && otherExecutionSummary.getFirstException() != null) {
            this.firstException = otherExecutionSummary.getFirstException();
        }
    }

    /**
     * Returns the number of executed collectors.
     * @return Number of executed collectors.
     */
    public int getTotalCollectorsExecuted() {
        return (int)this.collectorsExecutedColumn.sum();
    }

    /**
     * Returns the count of columns for which DQOps executed a collector and tried to read the statistics.
     * @return The count of columns for which DQOps executed a collector and tried to read the statistics.
     */
    public int getColumnsAnalyzedCount() {
        return (int)this.collectorsColumnsColumn.sum();
    }

    /**
     * Returns the number of columns for which DQOps managed to obtain statistics.
     * @return Number of columns that were analyzed successfully.
     */
    public int getColumnsSuccessfullyAnalyzed() {
        return (int)this.collectorsColumnsSuccessfullyColumn.sum();
    }

    /**
     * Returns the count of statistics collectors that failed to execute.
     * @return The count of statistics collectors that failed to execute.
     */
    public int getTotalCollectorsFailed() {
        return (int)this.collectorsFailedColumn.sum();
    }

    /**
     * Returns the total number of results that were collected.
     * @return The total number of results that were collected.
     */
    public int getTotalCollectedResults() {
        return (int)this.collectorsResultsColumn.sum();
    }

    /**
     * Returns the first exception that was thrown when running the statistics collection. It is the exception from the first statistics collector that failed.
     * @return The exception from the first failed statistics collector or null when no collectors failed.
     */
    public Throwable getFirstException() {
        return firstException;
    }

    /**
     * Checks if the whole statistics collection job failed, resulting only in failures.
     * @return True when the statistics collection failed for all collectors, false when some collectors managed to collect statistics.
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
