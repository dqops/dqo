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
package ai.dqo.execution.profiler;

import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 * Tabular object returned from {@link ProfilerExecutionService} with a summary of profilers that were executed.
 */
public class ProfilerExecutionSummary {
    private final StringColumn connectionColumn;
    private final StringColumn tableColumn;
    private final IntColumn profilersExecutedColumn;
    private final IntColumn profiledColumnsColumn;
    private final IntColumn profiledColumnsSuccessfullyColumn;
    private final IntColumn profilersFailedColumn;
    private final IntColumn profilerResultsColumn;
    private final Table summaryTable;

    /**
     * Default constructor that creates the output table.
     */
    public ProfilerExecutionSummary() {
		this.summaryTable = Table.create("Profiler execution summary");

        this.connectionColumn = StringColumn.create("Connection");
		this.summaryTable.addColumns(connectionColumn);

        this.tableColumn = StringColumn.create("Table");
		this.summaryTable.addColumns(tableColumn);

		this.profilersExecutedColumn = IntColumn.create("Profilers executed");
		this.summaryTable.addColumns(profilersExecutedColumn);

        this.profiledColumnsColumn = IntColumn.create("Columns profiled");
        this.summaryTable.addColumns(profiledColumnsColumn);

        this.profiledColumnsSuccessfullyColumn = IntColumn.create("Columns profiled successfully");
        this.summaryTable.addColumns(profiledColumnsSuccessfullyColumn);

        this.profilersFailedColumn = IntColumn.create("Profilers failed count");
        this.summaryTable.addColumns(profilersFailedColumn);

        this.profilerResultsColumn = IntColumn.create("Profiler results count");
        this.summaryTable.addColumns(profilerResultsColumn);
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
     * Returns the column that contains the total number of table and column level profilers that were evaluated for the given column.
     * @return Profilers count.
     */
    public IntColumn getProfilersExecutedColumn() {
        return profilersExecutedColumn;
    }

    /**
     * Returns the column that returns the number of columns that were profiled.
     * @return Column with the number of profiled columns.
     */
    public IntColumn getProfiledColumnsColumn() {
        return profiledColumnsColumn;
    }

    /**
     * Returns a dataset column that contains the number of columns on a table that had at least one profiler successfully executed, so the column exists.
     * @return Number of columns that were analysed with at least one profiler.
     */
    public IntColumn getProfiledColumnsSuccessfullyColumn() {
        return profiledColumnsSuccessfullyColumn;
    }

    /**
     * Returns a column that returns the number of failed profilers that were not able to capture results.
     * @return Number of failed issues.
     */
    public IntColumn getProfilersFailedColumn() {
        return profilersFailedColumn;
    }

    /**
     * Returns a column that contains the count of results retrieved from profilers. The total count could be higher when profilers are using data streams
     * and a separate profile is captured for each data stream.
     * @return Profiler results count column.
     */
    public IntColumn getProfilerResultsColumn() {
        return profilerResultsColumn;
    }

    /**
     * Adds a table profiler summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param profilersExecuted Number of profilers that were executed.
     * @param profiledColumns Number of profiled columns (successfully or with a failure).
     * @param profiledColumnsSuccessfully Number of columns that were profiled successfully - at least one profiler finished correctly.
     * @param profilersFailed Count of profilers that failed to retrieve valid information.
     * @param profilerResults Count of profiler results, may be higher than the number of profilers executed.
     */
    public void reportTableStats(ConnectionWrapper connection,
                                 TableSpec tableSpec,
                                 int profilersExecuted,
                                 int profiledColumns,
                                 int profiledColumnsSuccessfully,
								 int profilersFailed,
                                 int profilerResults) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getTarget().toPhysicalTableName().toString());
		this.profilersExecutedColumn.set(row.getRowNumber(), profilersExecuted);
		this.profiledColumnsColumn.set(row.getRowNumber(), profiledColumns);
		this.profiledColumnsSuccessfullyColumn.set(row.getRowNumber(), profiledColumnsSuccessfully);
		this.profilersFailedColumn.set(row.getRowNumber(), profilersFailed);
        this.profilerResultsColumn.set(row.getRowNumber(), profilerResults);
    }
}
