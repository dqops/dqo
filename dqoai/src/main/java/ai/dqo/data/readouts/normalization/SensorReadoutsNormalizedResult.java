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
package ai.dqo.data.readouts.normalization;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.metadata.groupings.TimePeriodGradient;
import ai.dqo.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * Describes the dataset (dataframe) returned from the sensor. Identifies the time series column, data stream columns, etc.
 * The columns are normalized.
 */
public class SensorReadoutsNormalizedResult {
    private final Table table;
    private final TextColumn idColumn;
    private final DoubleColumn actualValueColumn;
    private final DoubleColumn expectedValueColumn;
    private final DateTimeColumn timePeriodColumn;
    private final InstantColumn timePeriodUtcColumn;
    private final TextColumn timeGradientColumn;
    private final LongColumn dataStreamHashColumn;
    private final TextColumn dataStreamNameColumn;
    private final LongColumn connectionHashColumn;
    private final TextColumn connectionNameColumn;
    private final TextColumn providerColumn;
    private final LongColumn tableHashColumn;
    private final TextColumn schemaNameColumn;
    private final TextColumn tableNameColumn;
    private final TextColumn tableNamePatternColumn;
    private final TextColumn tableStageColumn;
    private final IntColumn tablePriorityColumn;
    private final LongColumn columnHashColumn;
    private final TextColumn columnNameColumn;
    private final TextColumn columnNamePatternColumn;
    private final LongColumn checkHashColumn;
    private final TextColumn checkNameColumn;
    private final TextColumn checkDisplayNameColumn;
    private final TextColumn checkTypeColumn;
    private final TextColumn checkCategoryColumn;
    private final TextColumn qualityDimensionColumn;
    private final TextColumn sensorNameColumn;
    private final TextColumn timeSeriesIdColumn;
    private final InstantColumn executedAtColumn;
    private final IntColumn durationMsColumn;

    /**
     * Creates a sensor result dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     * @param table Sorted table with sensor readouts - may be modified by adding missing columns.
     */
    public SensorReadoutsNormalizedResult(Table table) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.ID_COLUMN_NAME);
        this.actualValueColumn = TableColumnUtility.getOrAddDoubleColumn (table, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        this.expectedValueColumn = TableColumnUtility.getOrAddDoubleColumn(table, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
        this.timePeriodColumn = TableColumnUtility.getOrAddDateTimeColumn(table, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        this.timePeriodUtcColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
        this.timeGradientColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
        this.dataStreamHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.DATA_STREAM_HASH_COLUMN_NAME);
        this.dataStreamNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        this.connectionHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME);
        this.connectionNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME);
        this.providerColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
        this.tableHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME);
        this.schemaNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME);
        this.tableNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME);
        this.tableNamePatternColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME);
        this.tableStageColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME);
        this.tablePriorityColumn = TableColumnUtility.getOrAddIntColumn(table, SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME);
        this.columnHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME);
        this.columnNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        this.columnNamePatternColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME);
        this.checkHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        this.checkNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        this.checkDisplayNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
        this.checkTypeColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);
        this.checkCategoryColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        this.qualityDimensionColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        this.sensorNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME);
        this.timeSeriesIdColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        this.executedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
        this.durationMsColumn = TableColumnUtility.getOrAddIntColumn(table, SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME);
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
    public TextColumn getIdColumn() {
        return idColumn;
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
     * @return Optional expected value column. The column may contain nulls.
     */
    public DoubleColumn getExpectedValueColumn() {
        return expectedValueColumn;
    }

    /**
     * Time period column.
     * @return Time period column.
     */
    public DateTimeColumn getTimePeriodColumn() {
        return timePeriodColumn;
    }

    /**
     * Return the time period UTC column that stores a UTC timestamp.
     * @return Time period UTC column.
     */
    public InstantColumn getTimePeriodUtcColumn() {
        return timePeriodUtcColumn;
    }

    /**
     * Time gradient column that returns the time gradient that was used by the sensor. It is a lower case name of
     * a time gradient from {@link TimePeriodGradient} enumeration.
     * @return Time gradient name, e.q. "day", "week", "month".
     */
    public TextColumn getTimeGradientColumn() {
        return timeGradientColumn;
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
    public TextColumn getDataStreamNameColumn() {
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
    public TextColumn getConnectionNameColumn() {
        return connectionNameColumn;
    }

    /**
     * Returns a provider name column. The column contains the provider name.
     * @return Provider name column.
     */
    public TextColumn getProviderColumn() {
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
    public TextColumn getSchemaNameColumn() {
        return schemaNameColumn;
    }

    /**
     * Returns a tablesaw column with the table name.
     * @return Column with the table name.
     */
    public TextColumn getTableNameColumn() {
        return tableNameColumn;
    }

    /**
     * Returns a tablesaw column with the table name pattern.
     * @return Column with the table name pattern.
     */
    public TextColumn getTableNamePatternColumn() {
        return tableNamePatternColumn;
    }

    /**
     * Returns a tablesaw column with the table's stage.
     * @return Table's stage column.
     */
    public TextColumn getTableStageColumn() {
        return tableStageColumn;
    }

    /**
     * Returns the tablesaw column with the table priority.
     * @return Table's priority column.
     */
    public IntColumn getTablePriorityColumn() {
        return tablePriorityColumn;
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
    public TextColumn getColumnNameColumn() {
        return columnNameColumn;
    }

    /**
     * Returns a tablesaw column that stores the column name pattern. The column with "column names" may contain nulls when checks are defined on a whole table level.
     * @return Column name pattern tablesaw column.
     */
    public TextColumn getColumnNamePatternColumn() {
        return columnNamePatternColumn;
    }

    /**
     * Check hash column. The column contains a 64-bit hash of the check's hierarchy id.
     * @return Check hash column.
     */
    public LongColumn getCheckHashColumn() {
        return checkHashColumn;
    }

    /**
     * Returns a tablesaw column with the check name.
     * @return Check name column.
     */
    public TextColumn getCheckNameColumn() {
        return checkNameColumn;
    }

    /**
     * Returns a tablesaw column with the check display name.
     * @return Check display name column.
     */
    public TextColumn getCheckDisplayNameColumn() {
        return checkDisplayNameColumn;
    }

    /**
     * Returns a column that stores the check type (adhoc, checkpoint, partitioned).
     * @return Check type column.
     */
    public TextColumn getCheckTypeColumn() {
        return checkTypeColumn;
    }

    /**
     * Returns the column that stores the check category (the node in YAML that is a parent of the group of checks).
     * @return Quality check category name column.
     */
    public TextColumn getCheckCategoryColumn() {
        return checkCategoryColumn;
    }

    /**
     * Returns a tablesaw column with the parent quality dimension of a check.
     * @return Quality dimension of a check.
     */
    public TextColumn getQualityDimensionColumn() {
        return qualityDimensionColumn;
    }

    /**
     * Returns a column with the sensor name.
     * @return Sensor name column.
     */
    public TextColumn getSensorNameColumn() {
        return sensorNameColumn;
    }

    /**
     * Returns a time series id column.
     * @return Column that stores a time series id.
     */
    public TextColumn getTimeSeriesIdColumn() {
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
}
