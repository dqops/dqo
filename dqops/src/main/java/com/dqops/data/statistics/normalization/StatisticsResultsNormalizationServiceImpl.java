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
package com.dqops.data.statistics.normalization;

import com.dqops.core.configuration.DqoStatisticsCollectorConfigurationProperties;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorResultNormalizeException;
import com.dqops.data.statistics.factory.*;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.utils.tables.TableColumnUtility;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.AbstractColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.strings.AbstractStringColumn;
import tech.tablesaw.selection.Selection;

import java.time.LocalDateTime;

/**
 * Normalization service that adapts the results received from a statistics collector into a normalized "statistics" results table.
 */
@Component
public class StatisticsResultsNormalizationServiceImpl implements StatisticsResultsNormalizationService {
    private CommonTableNormalizationService commonNormalizationService;
    private DqoStatisticsCollectorConfigurationProperties statisticsCollectorConfigurationProperties;

    /**
     * Creates a statistics results normalization service given the dependencies.
     * @param commonNormalizationService Common normalization service with utility methods.
     * @param statisticsCollectorConfigurationProperties Statistics collector configuration parameters.
     */
    @Autowired
    public StatisticsResultsNormalizationServiceImpl(CommonTableNormalizationService commonNormalizationService,
                                                     DqoStatisticsCollectorConfigurationProperties statisticsCollectorConfigurationProperties) {
        this.commonNormalizationService = commonNormalizationService;
        this.statisticsCollectorConfigurationProperties = statisticsCollectorConfigurationProperties;
    }

    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the statistics results storage. Returns a new table with fixed column types.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param collectedAt Timestamp of the start of the statistics collector session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the statistics results table. Contains also a normalized results table.
     */
    @Override
    public StatisticsResultsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                              LocalDateTime collectedAt,
                                                              SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        int resultRowCount = resultsTable != null ? resultsTable.rowCount() : 1;  // one row to return when there is an error
        Table normalizedResults = Table.create("statistics_results_normalized");

        DateTimeColumn collectedAtColumn = DateTimeColumn.create(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME, resultRowCount);
        collectedAtColumn.setMissingTo(collectedAt);
        normalizedResults.addColumns(collectedAtColumn);

        TextColumn statusColumn = TextColumn.create(StatisticsColumnNames.STATUS_COLUMN_NAME, resultRowCount);
        statusColumn.setMissingTo(sensorExecutionResult.isSuccess() ? StatisticsCollectorResultStatus.success.name() : StatisticsCollectorResultStatus.error.name());
        normalizedResults.addColumns(statusColumn);

        AbstractColumn<?,?> normalizedResultColumn = normalizeCollectorResult(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME); // we are using the column name from SensorReadoutsColumnNames because we are reusing sensors and the sensor returns its result as an "actual_value" column

        TextColumn resultTypeColumn = TextColumn.create(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(resultTypeColumn); // we will set the data type when we detect it...

        if (normalizedResultColumn == null) {
            resultTypeColumn.setMissingTo(StatisticsResultDataType.NULL.getName());
        }

        if (normalizedResultColumn instanceof TextColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.STRING.getName());
        }
        else {
            if (normalizedResultColumn instanceof StringColumn) {
                TextColumn normalizedTextColumn = TableColumnUtility.convertToTextColumn(normalizedResultColumn);
                normalizedTextColumn.setName(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME);
                normalizedResults.addColumns(normalizedTextColumn);
                resultTypeColumn.setMissingTo(StatisticsResultDataType.STRING.getName());
            }
            else {
                TextColumn resultStringColumn = TextColumn.create(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultStringColumn);
            }
        }

        if (normalizedResultColumn instanceof LongColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.INTEGER.getName());
        }
        else {
            if (normalizedResultColumn instanceof IntColumn || normalizedResultColumn instanceof ShortColumn) {
                LongColumn normalizedLongColumn = TableColumnUtility.convertToLongColumn(normalizedResultColumn);
                normalizedLongColumn.setName(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME);
                normalizedResults.addColumns(normalizedLongColumn);
                resultTypeColumn.setMissingTo(StatisticsResultDataType.INTEGER.getName());
            }
            else {
                LongColumn resultIntegerColumn = LongColumn.create(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultIntegerColumn);
            }
        }

        if (normalizedResultColumn instanceof DoubleColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.FLOAT.getName());
        }
        else {
            if (normalizedResultColumn instanceof FloatColumn) {
                normalizedResultColumn.setName(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME);
                DoubleColumn normalizedDoubleColumn = TableColumnUtility.convertToDoubleColumn(normalizedResultColumn);
                normalizedResults.addColumns(normalizedDoubleColumn);
                resultTypeColumn.setMissingTo(StatisticsResultDataType.FLOAT.getName());
            }
            else {
                DoubleColumn resultFloatColumn = DoubleColumn.create(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultFloatColumn);
            }
        }

        if (normalizedResultColumn instanceof BooleanColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.BOOLEAN.getName());
        }
        else {
            BooleanColumn resultBooleanColumn = BooleanColumn.create(StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultBooleanColumn);
        }

        if (normalizedResultColumn instanceof DateColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_DATE_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.DATE.getName());
        }
        else {
            DateColumn resultDateColumn = DateColumn.create(StatisticsColumnNames.RESULT_DATE_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateColumn);
        }

        if (normalizedResultColumn instanceof DateTimeColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.DATETIME.getName());
        }
        else {
            DateTimeColumn resultDateTimeColumn = DateTimeColumn.create(StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateTimeColumn);
        }

        if (normalizedResultColumn instanceof InstantColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.INSTANT.getName());
        }
        else {
            InstantColumn resultInstantColumn = InstantColumn.create(StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultInstantColumn);
        }

        if (normalizedResultColumn instanceof TimeColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.TIME.getName());
        }
        else {
            TimeColumn resultTimeColumn = TimeColumn.create(StatisticsColumnNames.RESULT_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultTimeColumn);
        }

        IntColumn sampleIndexColumn = this.normalizeSampleIndex(resultsTable, StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME);
        if (sampleIndexColumn == null) {
            sampleIndexColumn = IntColumn.create(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME, resultRowCount);
        }
        normalizedResults.addColumns(sampleIndexColumn);

        LongColumn sampleCountColumn = this.normalizeSampleCount(resultsTable, StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME);
        if (sampleCountColumn == null) {
            sampleCountColumn = LongColumn.create(StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME, resultRowCount);
        }
        normalizedResults.addColumns(sampleCountColumn);

        TextColumn scopeColumn = TextColumn.create(StatisticsColumnNames.SCOPE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            scopeColumn.setMissingTo(sensorRunParameters.getDataGroupings() != null ? StatisticsDataScope.data_group.name() : StatisticsDataScope.table.name());
        }
        normalizedResults.addColumns(scopeColumn);

        // now detect data stream level columns...
        TextColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataGroupingDimensionColumns(
                resultsTable, sensorRunParameters.getDataGroupings(), resultRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(TextColumn.create(dataStreamLevelColumnName, resultRowCount));
            }
        }

        LongColumn dataStreamHashColumn = this.commonNormalizationService.createDataGroupingHashColumn(
                dataStreamLevelColumns, sensorRunParameters.getDataGroupings(), resultRowCount);
        normalizedResults.addColumns(dataStreamHashColumn);

        TextColumn dataStreamNameColumn = this.commonNormalizationService.createDataGroupingNameColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataStreamNameColumn);

        TextColumn dataStreamMappingNameColumn = TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getDataGroupings() != null) {
            dataStreamMappingNameColumn.setMissingTo(sensorRunParameters.getDataGroupings().getDataGroupingConfigurationName());
        }
        normalizedResults.addColumns(dataStreamMappingNameColumn);

        LongColumn connectionHashColumn = LongColumn.create(StatisticsColumnNames.CONNECTION_HASH_COLUMN_NAME, resultRowCount);
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        normalizedResults.addColumns(connectionHashColumn);

        TextColumn connectionNameColumn = TextColumn.create(StatisticsColumnNames.CONNECTION_NAME_COLUMN_NAME, resultRowCount);
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        normalizedResults.addColumns(connectionNameColumn);

        TextColumn providerColumn = TextColumn.create(StatisticsColumnNames.PROVIDER_COLUMN_NAME, resultRowCount);
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        normalizedResults.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(StatisticsColumnNames.TABLE_HASH_COLUMN_NAME, resultRowCount);
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        normalizedResults.addColumns(tableHashColumn);

        TextColumn schemaNameColumn = TextColumn.create(StatisticsColumnNames.SCHEMA_NAME_COLUMN_NAME, resultRowCount);
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getSchemaName());
        normalizedResults.addColumns(schemaNameColumn);

        TextColumn tableNameColumn = TextColumn.create(StatisticsColumnNames.TABLE_NAME_COLUMN_NAME, resultRowCount);
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getTableName());
        normalizedResults.addColumns(tableNameColumn);

        TextColumn tableStageColumn = TextColumn.create(StatisticsColumnNames.TABLE_STAGE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getTable().getStage() != null) {
            tableStageColumn.setMissingTo(sensorRunParameters.getTable().getStage());
        }
        normalizedResults.addColumns(tableStageColumn);

        LongColumn columnHashColumn = LongColumn.create(StatisticsColumnNames.COLUMN_HASH_COLUMN_NAME, resultRowCount);
        Long columnHash = null;
        if (sensorRunParameters.getColumn() != null) {
            columnHash = sensorRunParameters.getColumn().getHierarchyId().hashCode64();
            columnHashColumn.setMissingTo(columnHash);
        }
        normalizedResults.addColumns(columnHashColumn);

        TextColumn columnNameColumn = TextColumn.create(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        normalizedResults.addColumns(columnNameColumn);

        LongColumn collectorHashColumn = LongColumn.create(StatisticsColumnNames.COLLECTOR_HASH_COLUMN_NAME, resultRowCount);
        long collectorHash = sensorRunParameters.getProfiler().getHierarchyId().hashCode64();
        collectorHashColumn.setMissingTo(collectorHash);
        normalizedResults.addColumns(collectorHashColumn);

        TextColumn collectorNameColumn = TextColumn.create(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME, resultRowCount);
        String collectorName = sensorRunParameters.getProfiler().getProfilerName();
        collectorNameColumn.setMissingTo(collectorName);
        normalizedResults.addColumns(collectorNameColumn);

        TextColumn collectorTargetColumn = TextColumn.create(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME, resultRowCount);
        String collectorTargetType = sensorRunParameters.getColumn() != null ? StatisticsCollectorTarget.column.name() : StatisticsCollectorTarget.table.name();
        collectorTargetColumn.setMissingTo(collectorTargetType);
        normalizedResults.addColumns(collectorTargetColumn);

        TextColumn collectorCategoryColumn = TextColumn.create(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME, resultRowCount);
        String categoryName = sensorRunParameters.getProfiler().getCategoryName();
        collectorCategoryColumn.setMissingTo(categoryName);
        normalizedResults.addColumns(collectorCategoryColumn);

        TextColumn sensorNameColumn = TextColumn.create(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME, resultRowCount);
        String sensorDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        normalizedResults.addColumns(sensorNameColumn);

        TextColumn timeSeriesUuidColumn = this.commonNormalizationService.createTimeSeriesUuidColumn(dataStreamHashColumn, collectorHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME, resultRowCount);
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        normalizedResults.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(StatisticsColumnNames.DURATION_MS_COLUMN_NAME, resultRowCount);
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        normalizedResults.addColumns(durationMsColumn);

        TextColumn errorMessageColumn = TextColumn.create(StatisticsColumnNames.ERROR_MESSAGE_COLUMN_NAME, resultRowCount);
        if (!sensorExecutionResult.isSuccess() && sensorExecutionResult.getException() != null) {
            errorMessageColumn.setMissingTo(sensorExecutionResult.getException().getMessage());
        }
        normalizedResults.addColumns(errorMessageColumn);

        InstantColumn createdAtColumn = InstantColumn.create(StatisticsColumnNames.CREATED_AT_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(createdAtColumn);
        InstantColumn updatedAtColumn = InstantColumn.create(StatisticsColumnNames.UPDATED_AT_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(updatedAtColumn);
        TextColumn createdByColumn = TextColumn.create(StatisticsColumnNames.CREATED_BY_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(createdByColumn);
        TextColumn updatedByColumn = TextColumn.create(StatisticsColumnNames.UPDATED_BY_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(updatedByColumn);

        TextColumn idColumn = this.commonNormalizationService.createRowIdColumn(dataStreamHashColumn, executedAtColumn, sampleIndexColumn,
                collectorHash, tableHash, columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.insertColumn(0, idColumn);

        Selection statisticResultWithNulls = normalizedResultColumn != null ? normalizedResultColumn.isMissing() : Selection.with();
        if (!statisticResultWithNulls.isEmpty()) {
            normalizedResults = normalizedResults.dropWhere(statisticResultWithNulls);
        }

        StatisticsResultsNormalizedResult datasetMetadata = new StatisticsResultsNormalizedResult(normalizedResults);
        return datasetMetadata;
    }

    /**
     * Detects the type of the result column and returns a normalized column. Small data types (such as short, int) are converted to bigger data types to reduce the number of columns.
     * @param resultsTable Result table to extract the value.
     * @param columnName Column name to find in the result.
     * @return Normalized column (copied).
     */
    public AbstractColumn normalizeCollectorResult(Table resultsTable, String columnName) {
        if (resultsTable == null) {
            // the collector returned with an error, we don't have a result column
            return null;
        }

        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);
        if (currentColumn == null) {
            throw new SensorResultNormalizeException(resultsTable,
                    "Missing '" + columnName + "' column, the sensor query must return this column");
        }

        if (currentColumn instanceof DoubleColumn) {
            return ((DoubleColumn) currentColumn).copy();
        }

        if (currentColumn instanceof IntColumn) {
            return ((IntColumn) currentColumn).asLongColumn();
        }

        if (currentColumn instanceof LongColumn) {
            return ((LongColumn)currentColumn).copy();
        }

        if (currentColumn instanceof FloatColumn) {
            return ((FloatColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof ShortColumn) {
            return ((ShortColumn)currentColumn).asLongColumn();
        }

        if (currentColumn instanceof BooleanColumn) {
            return ((BooleanColumn)currentColumn).copy();
        }

        if (currentColumn instanceof DateColumn) {
            return ((DateColumn)currentColumn).copy();
        }

        if (currentColumn instanceof DateTimeColumn) {
            return ((DateTimeColumn)currentColumn).copy();
        }

        if (currentColumn instanceof InstantColumn) {
            return ((InstantColumn)currentColumn).copy();
        }

        if (currentColumn instanceof TimeColumn) {
            return ((TimeColumn)currentColumn).copy();
        }

        if (currentColumn instanceof AbstractStringColumn) {  // covers StringColumn and TextColumn
            AbstractStringColumn<?> stringColumn = (AbstractStringColumn<?>) currentColumn;
            TextColumn truncatedTextColumn = TextColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (!Strings.isNullOrEmpty(stringValue)) {
                    if (stringValue.length() > this.statisticsCollectorConfigurationProperties.getTruncatedStringsLength()) {
                        stringValue = stringValue.substring(0, this.statisticsCollectorConfigurationProperties.getTruncatedStringsLength());
                    }

                    truncatedTextColumn.set(i, stringValue);
                }
            }

            return truncatedTextColumn;
        }


        throw new SensorResultNormalizeException(resultsTable, "Cannot detect an " + columnName + " column data type");
    }

    /**
     * Finds and normalizes the sample_index column.
     * @param resultsTable Resul table.
     * @param columnName Column name to find.
     * @return A normalized sample index column or null, when the column was not found.
     */
    public IntColumn normalizeSampleIndex(Table resultsTable, String columnName) {
        if (resultsTable == null) {
            // the collector returned with an error, we don't have a result column
            return null;
        }

        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);
        if (currentColumn == null) {
            return null;
        }

        IntColumn resultColumn = TableColumnUtility.convertToIntColumn(currentColumn);
        if (resultColumn == currentColumn) {
            resultColumn = resultColumn.copy();
        }
        return resultColumn;
    }

    /**
     * Finds and normalizes the sample_count column.
     * @param resultsTable Resul table.
     * @param columnName Column name to find.
     * @return A normalized sample count column or null, when the column was not found.
     */
    public LongColumn normalizeSampleCount(Table resultsTable, String columnName) {
        if (resultsTable == null) {
            // the collector returned with an error, we don't have a result column
            return null;
        }

        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);
        if (currentColumn == null) {
            return null;
        }

        LongColumn resultColumn = TableColumnUtility.convertToLongColumn(currentColumn);
        if (resultColumn == currentColumn) {
            resultColumn = resultColumn.copy();
        }
        return resultColumn;
    }
}
