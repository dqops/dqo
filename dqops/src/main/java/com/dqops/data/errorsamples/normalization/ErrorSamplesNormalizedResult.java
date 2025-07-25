/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.normalization;

import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * Wrapper over a {@link Table} data frame that contains the error samples normalized results. Provides access to typed columns.
 */
public class ErrorSamplesNormalizedResult {
    private final Table table;
    private final StringColumn idColumn;
    private final DateTimeColumn collectedAtColumn;
    private final StringColumn scopeColumn;
    private final StringColumn dataGroupingLevel1Column;
    private final StringColumn dataGroupingLevel2Column;
    private final StringColumn dataGroupingLevel3Column;
    private final StringColumn dataGroupingLevel4Column;
    private final StringColumn dataGroupingLevel5Column;
    private final StringColumn dataGroupingLevel6Column;
    private final StringColumn dataGroupingLevel7Column;
    private final StringColumn dataGroupingLevel8Column;
    private final StringColumn dataGroupingLevel9Column;
    private final LongColumn dataGroupHashColumn;
    private final StringColumn dataGroupNameColumn;
    private final StringColumn dataGroupingConfigurationColumn;
    private final LongColumn connectionHashColumn;
    private final StringColumn connectionNameColumn;
    private final StringColumn providerColumn;
    private final LongColumn tableHashColumn;
    private final StringColumn schemaNameColumn;
    private final StringColumn tableNameColumn;
    private final StringColumn tableStageColumn;
    private final IntColumn tablePriorityColumn;
    private final LongColumn columnHashColumn;
    private final StringColumn columnNameColumn;
    private final LongColumn checkHashColumn;
    private final StringColumn checkNameColumn;
    private final StringColumn checkDisplayNameColumn;
    private final StringColumn checkTypeColumn;
    private final StringColumn timeGradientColumn;
    private final StringColumn checkCategoryColumn;
    private final StringColumn tableComparisonNameColumn;
    private final StringColumn qualityDimensionColumn;
    private final StringColumn sensorNameColumn;
    private final StringColumn timeSeriesIdColumn;
    private final StringColumn resultTypeColumn;
    private final StringColumn resultStringColumn;
    private final LongColumn resultIntegerColumn;
    private final DoubleColumn resultFloatColumn;
    private final BooleanColumn resultBooleanColumn;
    private final DateColumn resultDateColumn;
    private final DateTimeColumn resultDateTimeColumn;
    private final InstantColumn resultInstantColumn;
    private final TimeColumn resultTimeColumn;
    private final IntColumn sampleIndex;
    private final StringColumn sampleFilterColumn;
    private final StringColumn sampleRowId1Column;
    private final StringColumn sampleRowId2Column;
    private final StringColumn sampleRowId3Column;
    private final StringColumn sampleRowId4Column;
    private final StringColumn sampleRowId5Column;
    private final InstantColumn executedAtColumn;
    private final IntColumn durationMsColumn;
    private final InstantColumn createdAtColumn;
    private final InstantColumn updatedAtColumn;
    private final StringColumn createdByColumn;
    private final StringColumn updatedByColumn;

    /**
     * Creates an error samples result dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     * @param table Sorted table with error samples results - may be modified by adding missing columns.
     */
    public ErrorSamplesNormalizedResult(Table table) {
        this(table, true);
    }

    /**
     * Creates an error samples result dataset, extracting key columns.
     * @param table Sorted table with error samples results - may be modified by adding missing columns.
     * @param addColumWhenMissing Adds columns if they are missing.
     */
    public ErrorSamplesNormalizedResult(Table table, boolean addColumWhenMissing) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ID_COLUMN_NAME, addColumWhenMissing);
        this.collectedAtColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME, addColumWhenMissing);
        this.resultTypeColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.RESULT_TYPE_COLUMN_NAME, addColumWhenMissing);
        this.resultStringColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME, addColumWhenMissing);
        this.resultIntegerColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME, addColumWhenMissing);
        this.resultFloatColumn = TableColumnUtility.getOrAddDoubleColumn(table, ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME, addColumWhenMissing);
        this.resultBooleanColumn = TableColumnUtility.getOrAddBooleanColumn(table, ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME, addColumWhenMissing);
        this.resultDateColumn = TableColumnUtility.getOrAddDateColumn(table, ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME, addColumWhenMissing);
        this.resultDateTimeColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME, addColumWhenMissing);
        this.resultInstantColumn = TableColumnUtility.getOrAddInstantColumn(table, ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME, addColumWhenMissing);
        this.resultTimeColumn = TableColumnUtility.getOrAddTimeColumn(table, ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME, addColumWhenMissing);
        this.sampleIndex = TableColumnUtility.getOrAddIntColumn(table, ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME, addColumWhenMissing);
        this.scopeColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.SCOPE_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupingLevel1Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1", addColumWhenMissing);
        this.dataGroupingLevel2Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2", addColumWhenMissing);
        this.dataGroupingLevel3Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3", addColumWhenMissing);
        this.dataGroupingLevel4Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4", addColumWhenMissing);
        this.dataGroupingLevel5Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5", addColumWhenMissing);
        this.dataGroupingLevel6Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6", addColumWhenMissing);
        this.dataGroupingLevel7Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7", addColumWhenMissing);
        this.dataGroupingLevel8Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8", addColumWhenMissing);
        this.dataGroupingLevel9Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9", addColumWhenMissing);
        this.dataGroupHashColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.DATA_GROUP_HASH_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME, addColumWhenMissing);
        this.dataGroupingConfigurationColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, addColumWhenMissing);
        this.connectionHashColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.CONNECTION_HASH_COLUMN_NAME, addColumWhenMissing);
        this.connectionNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CONNECTION_NAME_COLUMN_NAME, addColumWhenMissing);
        this.providerColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.PROVIDER_COLUMN_NAME, addColumWhenMissing);
        this.tableHashColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.TABLE_HASH_COLUMN_NAME, addColumWhenMissing);
        this.schemaNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.SCHEMA_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tableNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.TABLE_NAME_COLUMN_NAME, addColumWhenMissing);
        this.tableStageColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.TABLE_STAGE_COLUMN_NAME, addColumWhenMissing);
        this.tablePriorityColumn = TableColumnUtility.getOrAddIntColumn(table, ErrorSamplesColumnNames.TABLE_PRIORITY_COLUMN_NAME, addColumWhenMissing);
        this.columnHashColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.COLUMN_HASH_COLUMN_NAME, addColumWhenMissing);
        this.columnNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME, addColumWhenMissing);
        this.checkHashColumn = TableColumnUtility.getOrAddLongColumn(table, ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME, addColumWhenMissing);
        this.checkNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME, addColumWhenMissing);
        this.checkDisplayNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME, addColumWhenMissing);
        this.checkTypeColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME, addColumWhenMissing);
        this.timeGradientColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME, addColumWhenMissing);
        this.checkCategoryColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME, addColumWhenMissing);
        this.tableComparisonNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, addColumWhenMissing);
        this.qualityDimensionColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME, addColumWhenMissing);
        this.sensorNameColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME, addColumWhenMissing);
        this.sampleFilterColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.SAMPLE_FILTER_COLUMN_NAME, addColumWhenMissing);
        this.timeSeriesIdColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.TIME_SERIES_ID_COLUMN_NAME, addColumWhenMissing);
        this.sampleRowId1Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "1", addColumWhenMissing);
        this.sampleRowId2Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "2", addColumWhenMissing);
        this.sampleRowId3Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "3", addColumWhenMissing);
        this.sampleRowId4Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "4", addColumWhenMissing);
        this.sampleRowId5Column = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "5", addColumWhenMissing);
        this.executedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME, addColumWhenMissing);
        this.durationMsColumn = TableColumnUtility.getOrAddIntColumn(table, ErrorSamplesColumnNames.DURATION_MS_COLUMN_NAME, addColumWhenMissing);
        this.createdAtColumn = TableColumnUtility.getOrAddInstantColumn(table, ErrorSamplesColumnNames.CREATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.updatedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, ErrorSamplesColumnNames.UPDATED_AT_COLUMN_NAME, addColumWhenMissing);
        this.createdByColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.CREATED_BY_COLUMN_NAME, addColumWhenMissing);
        this.updatedByColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorSamplesColumnNames.UPDATED_BY_COLUMN_NAME, addColumWhenMissing);
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
     * Returns the sample index column that stores an index of a value from a column value sampling collector.
     * @return Sample index column.
     */
    public IntColumn getSampleIndex() {
        return sampleIndex;
    }

    /**
     * Returns the column that contains a statistics scope. Values are from the {@link StatisticsDataScope} ("table" or "data_streams").
     * @return Statistics scope column (contains "table" or "data_streams").
     */
    public StringColumn getScopeColumn() {
        return scopeColumn;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 1.
     * @return Data stream value for the grouping_level_1.
     */
    public StringColumn getDataGroupingLevel1Column() {
        return dataGroupingLevel1Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 2.
     * @return Data stream value for the grouping_level_2.
     */
    public StringColumn getDataGroupingLevel2Column() {
        return dataGroupingLevel2Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 3.
     * @return Data stream value for the grouping_level_3.
     */
    public StringColumn getDataGroupingLevel3Column() {
        return dataGroupingLevel3Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 4.
     * @return Data stream value for the grouping_level_4.
     */
    public StringColumn getDataGroupingLevel4Column() {
        return dataGroupingLevel4Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 5.
     * @return Data stream value for the grouping_level_5.
     */
    public StringColumn getDataGroupingLevel5Column() {
        return dataGroupingLevel5Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 6.
     * @return Data stream value for the grouping_level_6.
     */
    public StringColumn getDataGroupingLevel6Column() {
        return dataGroupingLevel6Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 7.
     * @return Data stream value for the grouping_level_7.
     */
    public StringColumn getDataGroupingLevel7Column() {
        return dataGroupingLevel7Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 8.
     * @return Data stream value for the grouping_level_8.
     */
    public StringColumn getDataGroupingLevel8Column() {
        return dataGroupingLevel8Column;
    }

    /**
     * Returns the column that contains the value of the data stream at the level 9.
     * @return Data stream value for the grouping_level_9.
     */
    public StringColumn getDataGroupingLevel9Column() {
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
    public StringColumn getDataGroupNameColumn() {
        return dataGroupNameColumn;
    }

    /**
     * Returns the data stream mapping name column. It contains the name of the selected data stream mapping that was used.
     * @return Data stream mapping name column.
     */
    public StringColumn getDataGroupingConfigurationColumn() {
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
     * Returns a tablesaw column with the table's priority.
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
    public StringColumn getColumnNameColumn() {
        return columnNameColumn;
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
    public StringColumn getCheckNameColumn() {
        return checkNameColumn;
    }

    /**
     * Returns a tablesaw column with the check display name.
     * @return Check display name column.
     */
    public StringColumn getCheckDisplayNameColumn() {
        return checkDisplayNameColumn;
    }

    /**
     * Returns a column that stores the check type (profiling, monitoring, partitioned).
     * @return Check type column.
     */
    public StringColumn getCheckTypeColumn() {
        return checkTypeColumn;
    }

    /**
     * Time gradient column that returns the time gradient that was used by the sensor. It is a lower case name of
     * a time gradient from {@link TimePeriodGradient} enumeration.
     * @return Time gradient name, e.q. "day", "week", "month".
     */
    public StringColumn getTimeGradientColumn() {
        return timeGradientColumn;
    }

    /**
     * Returns the column that stores the check category (the node in YAML that is a parent of the group of checks).
     * @return Quality check category name column.
     */
    public StringColumn getCheckCategoryColumn() {
        return checkCategoryColumn;
    }

    /**
     * Returns the column that contains the table comparison name for data comparison checks.
     * @return Table comparison name column.
     */
    public StringColumn getTableComparisonNameColumn() {
        return tableComparisonNameColumn;
    }

    /**
     * Returns a tablesaw column with the parent quality dimension of a check.
     * @return Quality dimension of a check.
     */
    public StringColumn getQualityDimensionColumn() {
        return qualityDimensionColumn;
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
     * Returns a tablesaw's column that stores the table filter that was used to capture the sample.
     * @return Table filter column.
     */
    public StringColumn getSampleFilterColumn() {
        return sampleFilterColumn;
    }

    /**
     * Returns a tablesaw column that stores an ID retrieved from the analyzed table for the 1st ID column.
     * @return ID 1 column.
     */
    public StringColumn getSampleRowId1Column() {
        return sampleRowId1Column;
    }

    /**
     * Returns a tablesaw column that stores an ID retrieved from the analyzed table for the 2nd ID column.
     * @return ID 2 column.
     */
    public StringColumn getSampleRowId2Column() {
        return sampleRowId2Column;
    }

    /**
     * Returns a tablesaw column that stores an ID retrieved from the analyzed table for the 3rd ID column.
     * @return ID 3 column.
     */
    public StringColumn getSampleRowId3Column() {
        return sampleRowId3Column;
    }

    /**
     * Returns a tablesaw column that stores an ID retrieved from the analyzed table for the 4th ID column.
     * @return ID 4 column.
     */
    public StringColumn getSampleRowId4Column() {
        return sampleRowId4Column;
    }

    /**
     * Returns a tablesaw column that stores an ID retrieved from the analyzed table for the 5th ID column.
     * @return ID 5 column.
     */
    public StringColumn getSampleRowId5Column() {
        return sampleRowId5Column;
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
    public StringColumn getCreatedByColumn() {
        return createdByColumn;
    }

    /**
     * Returns the updated by column.
     * @return Updated by column.
     */
    public StringColumn getUpdatedByColumn() {
        return updatedByColumn;
    }
}
