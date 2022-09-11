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
//    private final LongColumn tableHashIdColumn;
    private final IntColumn checksExecutedColumn;
    private final IntColumn validResultsColumn;
    private final IntColumn lowSeverityAlertsColumn;
    private final IntColumn mediumSeverityAlertsColumn;
    private final IntColumn highSeverityAlertsColumn;
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
//        tableHashIdColumn = LongColumn.create("Table hash id");
//        this.summaryTable.addColumns(tableHashIdColumn);
		checksExecutedColumn = IntColumn.create("Checks");
		this.summaryTable.addColumns(checksExecutedColumn);
		sensorResultsColumn = IntColumn.create("Sensor results");
		this.summaryTable.addColumns(sensorResultsColumn);
		validResultsColumn = IntColumn.create("Valid results");
		this.summaryTable.addColumns(validResultsColumn);
		lowSeverityAlertsColumn = IntColumn.create("Alerts (low)");
		this.summaryTable.addColumns(lowSeverityAlertsColumn);
		mediumSeverityAlertsColumn = IntColumn.create("Alerts (medium)");
		this.summaryTable.addColumns(mediumSeverityAlertsColumn);
		highSeverityAlertsColumn = IntColumn.create("Alerts (high)");
		this.summaryTable.addColumns(highSeverityAlertsColumn);
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

//    /**
//     * Table hash id column.
//     * @return Column.
//     */
//    public LongColumn getTableHashIdColumn() {
//        return tableHashIdColumn;
//    }

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
     * Count of low severity alerts column.
     * @return Column.
     */
    public IntColumn getLowSeverityAlertsColumn() {
        return lowSeverityAlertsColumn;
    }

    /**
     * Count of medium severity alerts column.
     * @return Column.
     */
    public IntColumn getMediumSeverityAlertsColumn() {
        return mediumSeverityAlertsColumn;
    }

    /**
     * Count of high severity alerts column.
     * @return Column.
     */
    public IntColumn getHighSeverityAlertsColumn() {
        return highSeverityAlertsColumn;
    }

    /**
     * Adds a table check summary row.
     * @param connection Connection wrapper.
     * @param tableSpec Table specification.
     * @param checksExecuted Number of checks that were executed.
     * @param sensorResults Number of sensor results returned from sensors.
     * @param validResults Number of valid results that passed the rules.
     * @param lowSeverityAlerts Count of low severity alerts.
     * @param mediumSeverityAlerts Count of medium severity alerts.
     * @param highSeverityAlerts Count of high severity alerts.
     */
    public void reportTableStats(ConnectionWrapper connection, TableSpec tableSpec, int checksExecuted, int sensorResults, int validResults,
								 int lowSeverityAlerts, int mediumSeverityAlerts, int highSeverityAlerts) {
        Row row = this.summaryTable.appendRow();
		this.connectionColumn.set(row.getRowNumber(), connection.getName());
		this.tableColumn.set(row.getRowNumber(), tableSpec.getTarget().toPhysicalTableName().toString());
//        this.tableHashIdColumn.set(row.getRowNumber(), tableSpec.getHierarchyId().hashCode64());
		this.checksExecutedColumn.set(row.getRowNumber(), checksExecuted);
		this.sensorResultsColumn.set(row.getRowNumber(), sensorResults);
		this.validResultsColumn.set(row.getRowNumber(), validResults);
		this.lowSeverityAlertsColumn.set(row.getRowNumber(), lowSeverityAlerts);
		this.mediumSeverityAlertsColumn.set(row.getRowNumber(), mediumSeverityAlerts);
		this.highSeverityAlertsColumn.set(row.getRowNumber(), highSeverityAlerts);
    }

    /**
     * Counts the number of checks that were executed.
     * @return Total number of checks that were executed.
     */
    public int getTotalChecksExecutedCount() {
        return this.checksExecutedColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of low severity alerts that were raised.
     * @return Number of low severity alerts.
     */
    public int getLowSeverityAlertsCount() {
        return this.lowSeverityAlertsColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of medium severity alerts that were raised.
     * @return Number of medium severity alerts.
     */
    public int getMediumSeverityAlertsCount() {
        return this.mediumSeverityAlertsColumn.isGreaterThan(0).size();
    }

    /**
     * Counts the number of high severity alerts that were raised.
     * @return Number of high severity alerts.
     */
    public int getHighSeverityAlertsCount() {
        return this.highSeverityAlertsColumn.isGreaterThan(0).size();
    }
}
