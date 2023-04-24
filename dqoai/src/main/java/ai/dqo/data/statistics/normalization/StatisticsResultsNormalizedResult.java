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
package ai.dqo.data.statistics.normalization;

import ai.dqo.data.statistics.factory.StatisticsCollectorResultStatus;
import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * Wrapper over a {@link tech.tablesaw.api.Table} data frame that contains the statistics collection results. Provides access to typed columns.
 */
public class StatisticsResultsNormalizedResult {
    private final Table table;
    private final StringColumn idColumn;
    private final StringColumn statusColumn;
    private final DateTimeColumn collectedAtColumn;
    private final StringColumn resultTypeColumn;
    private final StringColumn resultStringColumn;
    private final LongColumn resultIntegerColumn;
    private final DoubleColumn resultFloatColumn;
    private final BooleanColumn resultBooleanColumn;
    private final DateColumn resultDateColumn;
    private final DateTimeColumn resultDateTimeColumn;
    private final InstantColumn resultInstantColumn;
    private final TimeColumn resultTimeColumn;
    private final StringColumn scopeColumn;
    private final LongColumn dataStreamHashColumn;
    private final StringColumn dataStreamNameColumn;
    private final LongColumn connectionHashColumn;
    private final StringColumn connectionNameColumn;
    private final StringColumn providerColumn;
    private final LongColumn tableHashColumn;
    private final StringColumn schemaNameColumn;
    private final StringColumn tableNameColumn;
    private final StringColumn tableStageColumn;
    private final LongColumn columnHashColumn;
    private final StringColumn columnNameColumn;
    private final LongColumn collectorHashColumn;
    private final StringColumn collectorNameColumn;
    private final StringColumn collectorTargetColumn;
    private final StringColumn collectorCategoryColumn;
    private final StringColumn sensorNameColumn;
    private final StringColumn timeSeriesIdColumn;
    private final InstantColumn executedAtColumn;
    private final IntColumn durationMsColumn;
    private final StringColumn errorMessageColumn;

    /**
     * Creates a statistics result dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     * @param table Sorted table with statistics results - may be modified by adding missing columns.
     */
    public StatisticsResultsNormalizedResult(Table table) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.ID_COLUMN_NAME);
        this.collectedAtColumn = TableColumnUtility.getOrAddDateTimeColumn(table, StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME);
        this.statusColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.STATUS_COLUMN_NAME);
        this.resultTypeColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME);
        this.resultStringColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.RESULT_STRING_COLUMN_NAME);
        this.resultIntegerColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME);
        this.resultFloatColumn = TableColumnUtility.getOrAddDoubleColumn(table, StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME);
        this.resultBooleanColumn = TableColumnUtility.getOrAddBooleanColumn(table, StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME);
        this.resultDateColumn = TableColumnUtility.getOrAddDateColumn(table, StatisticsColumnNames.RESULT_DATE_COLUMN_NAME);
        this.resultDateTimeColumn = TableColumnUtility.getOrAddDateTimeColumn(table, StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME);
        this.resultInstantColumn = TableColumnUtility.getOrAddInstantColumn(table, StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME);
        this.resultTimeColumn = TableColumnUtility.getOrAddTimeColumn(table, StatisticsColumnNames.RESULT_TIME_COLUMN_NAME);
        this.scopeColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.SCOPE_COLUMN_NAME);
        this.dataStreamHashColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.DATA_STREAM_HASH_COLUMN_NAME);
        this.dataStreamNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        this.connectionHashColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.CONNECTION_HASH_COLUMN_NAME);
        this.connectionNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.CONNECTION_NAME_COLUMN_NAME);
        this.providerColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.PROVIDER_COLUMN_NAME);
        this.tableHashColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.TABLE_HASH_COLUMN_NAME);
        this.schemaNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.SCHEMA_NAME_COLUMN_NAME);
        this.tableNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.TABLE_NAME_COLUMN_NAME);
        this.tableStageColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.TABLE_STAGE_COLUMN_NAME);
        this.columnHashColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.COLUMN_HASH_COLUMN_NAME);
        this.columnNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME);
        this.collectorHashColumn = TableColumnUtility.getOrAddLongColumn(table, StatisticsColumnNames.COLLECTOR_HASH_COLUMN_NAME);
        this.collectorNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME);
        this.collectorTargetColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME);
        this.collectorCategoryColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME);
        this.sensorNameColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME);
        this.timeSeriesIdColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        this.executedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME);
        this.durationMsColumn = TableColumnUtility.getOrAddIntColumn(table, StatisticsColumnNames.DURATION_MS_COLUMN_NAME);
        this.errorMessageColumn = TableColumnUtility.getOrAddStringColumn(table, StatisticsColumnNames.ERROR_MESSAGE_COLUMN_NAME);
    }


    /**
     * Dataset table that was returned from the sensor query.
     * @return Sensor results as a table with a time dimension column and grouping dimension columns.
     */
    public Table getTable() {
        return table;
    }

    /**
     * id column that is a primary for both the sensor_readouts and rule_results tables.
     * @return Check result id (primary key) column.
     */
    public StringColumn getIdColumn() {
        return idColumn;
    }

    /**
     * Returns the instant column that contains a timestamp (utc) when the statistics collection session was started. All results from all providers
     * executed during that session will share the same timestamp.
     * @return Collection started at instant column.
     */
    public DateTimeColumn getCollectedAtColumn() {
        return collectedAtColumn;
    }

    /**
     * Returns the column name that holds the status code. Status codes are defined in {@link StatisticsCollectorResultStatus}.
     * @return Status column.
     */
    public StringColumn getStatusColumn() {
        return statusColumn;
    }

    /**
     * Returns the column that identifies the result data type retrieved from the collector.
     * @return result data type.
     */
    public StringColumn getResultTypeColumn() {
        return resultTypeColumn;
    }

    /**
     * Returns the column that contains the collector results that are strings.
     * @return Column with the string results.
     */
    public StringColumn getResultStringColumn() {
        return resultStringColumn;
    }

    /**
     * Returns the column that contains the statistics result that are integers, stored as a long integer.
     * @return Column with the integer results.
     */
    public LongColumn getResultIntegerColumn() {
        return resultIntegerColumn;
    }

    /**
     * Returns the column that contains the statistics results that are floats (single, double) or a fixed precision decimal or numeric, stored as a double floating value.
     * @return Column with the float results.
     */
    public DoubleColumn getResultFloatColumn() {
        return resultFloatColumn;
    }

    /**
     * Returns the column that contains the statistics results that are boolean.
     * @return Column with the boolean results.
     */
    public BooleanColumn getResultBooleanColumn() {
        return resultBooleanColumn;
    }

    /**
     * Returns the column that contains the statistics results that are dates.
     * @return Column with the date results.
     */
    public DateColumn getResultDateColumn() {
        return resultDateColumn;
    }

    /**
     * Returns the column that contains the statistics results that are date time.
     * @return Column with the date time results.
     */
    public DateTimeColumn getResultDateTimeColumn() {
        return resultDateTimeColumn;
    }

    /**
     * Returns the column that contains the statistics results that are instants.
     * @return Column with the instant results.
     */
    public InstantColumn getResultInstantColumn() {
        return resultInstantColumn;
    }

    /**
     * Returns the column that contains the statistics results that are time.
     * @return Column with the time results.
     */
    public TimeColumn getResultTimeColumn() {
        return resultTimeColumn;
    }

    /**
     * Returns the column that contains a statistics scope. Values are from the {@link StatisticsDataScope} ("table" or "data_streams").
     * @return Statistics scope column (contains "table" or "data_streams").
     */
    public StringColumn getScopeColumn() {
        return scopeColumn;
    }

    /**
     * Data stream hash column.
     * @return Data stream hash column.
     */
    public LongColumn getDataStreamHashColumn() {
        return dataStreamHashColumn;
    }

    /**
     * Data stream name column. The data stream name is concatenated from data stream levels and is a user friendly value.
     * @return Data stream name column.
     */
    public StringColumn getDataStreamNameColumn() {
        return dataStreamNameColumn;
    }

    /**
     * Connection hash column. The column contains a 64-bit hash of the connection's hierarchy id.
     * @return Connection hash column.
     */
    public LongColumn getConnectionHashColumn() {
        return connectionHashColumn;
    }

    /**
     * Returns a connection name column. The column contains the source connection name.
     * @return Connection name column.
     */
    public StringColumn getConnectionNameColumn() {
        return connectionNameColumn;
    }

    /**
     * Returns a provider name column. The column contains the provider name.
     * @return Provider name column.
     */
    public StringColumn getProviderColumn() {
        return providerColumn;
    }

    /**
     * Table hash column. The column contains a 64-bit hash of the table's hierarchy id.
     * @return Table hash column.
     */
    public LongColumn getTableHashColumn() {
        return tableHashColumn;
    }

    /**
     * Returns a tablesaw column with the schema name of a table.
     * @return Schema name column.
     */
    public StringColumn getSchemaNameColumn() {
        return schemaNameColumn;
    }

    /**
     * Returns a tablesaw column with the table name.
     * @return Column with the table name.
     */
    public StringColumn getTableNameColumn() {
        return tableNameColumn;
    }

    /**
     * Returns a tablesaw column with the table's stage.
     * @return Table's stage column.
     */
    public StringColumn getTableStageColumn() {
        return tableStageColumn;
    }

    /**
     * Column hash column. The column contains a 64-bit hash of the column's hierarchy id.
     * @return Column hash column.
     */
    public LongColumn getColumnHashColumn() {
        return columnHashColumn;
    }

    /**
     * Returns a tablesaw column that stores the column name. The column with "column names" may contain nulls when checks are defined on a whole table level.
     * @return Column name tablesaw column.
     */
    public StringColumn getColumnNameColumn() {
        return columnNameColumn;
    }

    /**
     * Collector hash column. The column contains a 64-bit hash of the collector's hierarchy id.
     * @return Collector hash column.
     */
    public LongColumn getCollectorHashColumn() {
        return collectorHashColumn;
    }

    /**
     * Returns a tablesaw column with the collector name.
     * @return Collector name column.
     */
    public StringColumn getCollectorNameColumn() {
        return collectorNameColumn;
    }

    /**
     * Returns a column that stores the collector target (table, column).
     * @return Collector target column.
     */
    public StringColumn getCollectorTargetColumn() {
        return collectorTargetColumn;
    }

    /**
     * Returns the column that stores the collector category (the node in YAML that is a parent of the group of collectors).
     * @return Statistics collector category name column.
     * @return Statistics collector category name column.
     */
    public StringColumn getCollectorCategoryColumn() {
        return collectorCategoryColumn;
    }

    /**
     * Returns a column with the sensor name.
     * @return Sensor name column.
     */
    public StringColumn getSensorNameColumn() {
        return sensorNameColumn;
    }

    /**
     * Returns a time series id column.
     * @return Column that stores a time series id.
     */
    public StringColumn getTimeSeriesIdColumn() {
        return timeSeriesIdColumn;
    }

    /**
     * Absolute timestamp when the sensor execution started.
     * @return Sensor started timestamp.
     */
    public InstantColumn getExecutedAtColumn() {
        return executedAtColumn;
    }

    /**
     * Sensor execution duration in milliseconds.
     * @return Sensor duration in millis.
     */
    public IntColumn getDurationMsColumn() {
        return durationMsColumn;
    }

    /**
     * Returns the column that stores an error message when the collector failed to execute.
     * @return Error message column.
     */
    public StringColumn getErrorMessageColumn() {
        return errorMessageColumn;
    }
}
