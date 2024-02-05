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
package com.dqops.data.readouts.normalization;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

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
    private final TextColumn dataGroupingLevel1Column;
    private final TextColumn dataGroupingLevel2Column;
    private final TextColumn dataGroupingLevel3Column;
    private final TextColumn dataGroupingLevel4Column;
    private final TextColumn dataGroupingLevel5Column;
    private final TextColumn dataGroupingLevel6Column;
    private final TextColumn dataGroupingLevel7Column;
    private final TextColumn dataGroupingLevel8Column;
    private final TextColumn dataGroupingLevel9Column;
    private final LongColumn dataGroupHashColumn;
    private final TextColumn dataGroupNameColumn;
    private final TextColumn dataGroupingConfigurationColumn;
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
    private final TextColumn tableComparisonNameColumn;
    private final TextColumn qualityDimensionColumn;
    private final TextColumn sensorNameColumn;
    private final TextColumn timeSeriesIdColumn;
    private final InstantColumn executedAtColumn;
    private final IntColumn durationMsColumn;
    private final InstantColumn createdAtColumn;
    private final InstantColumn updatedAtColumn;
    private final TextColumn createdByColumn;
    private final TextColumn updatedByColumn;

    private final IntColumn severityColumn;

    /**
     * Creates a sensor result dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     * @param table Sorted table with sensor readouts - may be modified by adding missing columns.
     */
    public SensorReadoutsNormalizedResult(Table table) {
        this(table, true);
    }

    /**
     * Creates a sensor result dataset, extracting key columns.
     * @param table Sorted table with sensor readouts - may be modified by adding missing columns.
     * @param addColumWhenMissing Adds the columns if they are missing.
     */
    public SensorReadoutsNormalizedResult(Table table, boolean addColumWhenMissing) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.ID_COLUMN_NAME, addColumWhenMissing);
        this.actualValueColumn = TableColumnUtility.getOrAddDoubleColumn (table, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, addColumWhenMissing);
        this.expectedValueColumn = TableColumnUtility.getOrAddDoubleColumn(table, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME, addColumWhenMissing);
        this.timePeriodColumn = TableColumnUtility.getOrAddDateTimeColumn(table, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, addColumWhenMissing);
        this.timePeriodUtcColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, addColumWhenMissing);
        this.timeGradientColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupingLevel1Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1", addColumWhenMissing);
        this.dataGroupingLevel2Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2", addColumWhenMissing);
        this.dataGroupingLevel3Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3", addColumWhenMissing);
        this.dataGroupingLevel4Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4", addColumWhenMissing);
        this.dataGroupingLevel5Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5", addColumWhenMissing);
        this.dataGroupingLevel6Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6", addColumWhenMissing);
        this.dataGroupingLevel7Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7", addColumWhenMissing);
        this.dataGroupingLevel8Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8", addColumWhenMissing);
        this.dataGroupingLevel9Column = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9", addColumWhenMissing);
        this.dataGroupHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupingConfigurationColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, addColumWhenMissing);
        this.connectionHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME, addColumWhenMissing);
        this.connectionNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME, addColumWhenMissing);
        this.providerColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME, addColumWhenMissing);
        this.tableHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME, addColumWhenMissing);
        this.schemaNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tableNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tableNamePatternColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME, addColumWhenMissing);
        this.tableStageColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME, addColumWhenMissing);
        this.tablePriorityColumn = TableColumnUtility.getOrAddIntColumn(table, SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME, addColumWhenMissing);
        this.columnHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME, addColumWhenMissing);
        this.columnNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME, addColumWhenMissing);
        this.columnNamePatternColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME, addColumWhenMissing);
        this.checkHashColumn = TableColumnUtility.getOrAddLongColumn(table, SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME, addColumWhenMissing);
        this.checkNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME, addColumWhenMissing);
        this.checkDisplayNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME, addColumWhenMissing);
        this.checkTypeColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME, addColumWhenMissing);
        this.checkCategoryColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME, addColumWhenMissing);
        this.tableComparisonNameColumn = TableColumnUtility.getOrAddTextColumn(table, CheckResultsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, addColumWhenMissing);
        this.qualityDimensionColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, addColumWhenMissing);
        this.sensorNameColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME, addColumWhenMissing);
        this.timeSeriesIdColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME, addColumWhenMissing);
        this.executedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, addColumWhenMissing);
        this.durationMsColumn = TableColumnUtility.getOrAddIntColumn(table, SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME, addColumWhenMissing);
        this.createdAtColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.CREATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.updatedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, SensorReadoutsColumnNames.UPDATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.createdByColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.CREATED_BY_COLUMN_NAME, addColumWhenMissing);
        this.updatedByColumn = TableColumnUtility.getOrAddTextColumn(table, SensorReadoutsColumnNames.UPDATED_BY_COLUMN_NAME, addColumWhenMissing);

        // this is a special condition, we are storing a severity column only when receiving results from custom data quality checks using custom queries
        // that return a 'severity' column, to bypass calling the rules
        this.severityColumn = TableColumnUtility.getOrAddIntColumn(table, CheckResultsColumnNames.SEVERITY_COLUMN_NAME, false);
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
     * Returns the column that contains the value of the data stream at the level 1.
     * @return Data stream value for the grouping_level_1.
     */
    public TextColumn getDataGroupingLevel1Column() {
        return dataGroupingLevel1Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 2.
     * @return Data stream value for the grouping_level_2.
     */
    public TextColumn getDataGroupingLevel2Column() {
        return dataGroupingLevel2Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 3.
     * @return Data stream value for the grouping_level_3.
     */
    public TextColumn getDataGroupingLevel3Column() {
        return dataGroupingLevel3Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 4.
     * @return Data stream value for the grouping_level_4.
     */
    public TextColumn getDataGroupingLevel4Column() {
        return dataGroupingLevel4Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 5.
     * @return Data stream value for the grouping_level_5.
     */
    public TextColumn getDataGroupingLevel5Column() {
        return dataGroupingLevel5Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 6.
     * @return Data stream value for the grouping_level_6.
     */
    public TextColumn getDataGroupingLevel6Column() {
        return dataGroupingLevel6Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 7.
     * @return Data stream value for the grouping_level_7.
     */
    public TextColumn getDataGroupingLevel7Column() {
        return dataGroupingLevel7Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 8.
     * @return Data stream value for the grouping_level_8.
     */
    public TextColumn getDataGroupingLevel8Column() {
        return dataGroupingLevel8Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 9.
     * @return Data stream value for the grouping_level_9.
     */
    public TextColumn getDataGroupingLevel9Column() {
        return dataGroupingLevel9Column;
    }

    /**
     * Data stream hash column.
     * @return Data stream hash column.
     */
    public LongColumn getDataGroupHashColumn() {
        return dataGroupHashColumn;
    }

    /**
     * Data stream name column. The data stream name is concatenated from data stream levels and is a user friendly value.
     * @return Data stream name column.
     */
    public TextColumn getDataGroupNameColumn() {
        return dataGroupNameColumn;
    }

    /**
     * Returns the data stream mapping name column. It contains the name of the selected data stream mapping that was used.
     * @return Data stream mapping name column.
     */
    public TextColumn getDataGroupingConfigurationColumn() {
        return dataGroupingConfigurationColumn;
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
     * Returns a tablesaw column that stores the column name. The column with "column names" may contain nulls when checks are defined for the entire table level.
     * @return Column name tablesaw column.
     */
    public TextColumn getColumnNameColumn() {
        return columnNameColumn;
    }

    /**
     * Returns a tablesaw column that stores the column name pattern. The column with "column names" may contain nulls when checks are defined for the entire table level.
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
     * Returns a column that stores the check type (profiling, monitoring, partitioned).
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
     * Returns the column that contains the table comparison name for data comparison checks.
     * @return Table comparison name column.
     */
    public TextColumn getTableComparisonNameColumn() {
        return tableComparisonNameColumn;
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

    /**
     * Returns the created at column.
     * @return Created at column.
     */
    public InstantColumn getCreatedAtColumn() {
        return createdAtColumn;
    }

    /**
     * Returns the updated at column.
     * @return Updated at column.
     */
    public InstantColumn getUpdatedAtColumn() {
        return updatedAtColumn;
    }

    /**
     * Returns the created by column.
     * @return Created by column.
     */
    public TextColumn getCreatedByColumn() {
        return createdByColumn;
    }

    /**
     * Returns the updated by column.
     * @return Updated by column.
     */
    public TextColumn getUpdatedByColumn() {
        return updatedByColumn;
    }

    /**
     * Returns an optional column with the severity status imported from custom checks that want to bypass calling rules.
     * @return Severity rule.
     */
    public IntColumn getSeverityColumn() {
        return severityColumn;
    }
}
