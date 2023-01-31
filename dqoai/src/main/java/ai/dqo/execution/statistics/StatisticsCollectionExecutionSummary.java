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
package ai.dqo.execution.statistics;

import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 * Tabular object returned from {@link StatisticsCollectorsExecutionService} with a summary of statistics collector that were executed.
 */
public class StatisticsCollectionExecutionSummary {
    private final StringColumn connectionColumn;
    private final StringColumn tableColumn;
    private final IntColumn collectorsExecutedColumn;
    private final IntColumn collectorsColumnsColumn;
    private final IntColumn collectorsColumnsSuccessfullyColumn;
    private final IntColumn collectorsFailedColumn;
    private final IntColumn collectorsResultsColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public StatisticsCollectionExecutionSummary() {
		this.summaryTable = Table.create("Statistics collection execution summary");

        this.connectionColumn = StringColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);

        this.tableColumn = StringColumn.create("Table");
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
     * @param collectorsExecuted Number of statistics collectors that were executed.
     * @param profiledColumns Number of profiled columns (successfully or with a failure).
     * @param profiledColumnsSuccessfully Number of columns that were profiled successfully - at least one collector finished correctly.
     * @param collectorsFailed Count of collectors that failed to retrieve valid information.
     * @param collectorsResults Count of collectors results, may be higher than the number of collectors executed.
     */
    public void reportTableStats(ConnectionWrapper connection,
                                 TableSpec tableSpec,
                                 int collectorsExecuted,
                                 int profiledColumns,
                                 int profiledColumnsSuccessfully,
								 int collectorsFailed,
                                 int collectorsResults) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getTarget().toPhysicalTableName().toString());
		this.collectorsExecutedColumn.set(row.getRowNumber(), collectorsExecuted);
		this.collectorsColumnsColumn.set(row.getRowNumber(), profiledColumns);
		this.collectorsColumnsSuccessfullyColumn.set(row.getRowNumber(), profiledColumnsSuccessfully);
		this.collectorsFailedColumn.set(row.getRowNumber(), collectorsFailed);
        this.collectorsResultsColumn.set(row.getRowNumber(), collectorsResults);
    }
}
