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

import com.cronutils.utils.StringUtils;
import com.dqops.core.configuration.DqoErrorSamplingConfigurationProperties;
import com.dqops.data.errorsamples.factory.ErrorSampleResultDataType;
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorResultNormalizeException;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.tables.TableColumnUtility;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.AbstractColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.strings.AbstractStringColumn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Normalization service that adapts the results received from a error sampling queries into a normalized "error samples" results table.
 */
@Component
public class ErrorSamplesNormalizationServiceImpl implements ErrorSamplesNormalizationService {
    private CommonTableNormalizationService commonNormalizationService;
    private DqoErrorSamplingConfigurationProperties errorSamplingConfigurationProperties;

    /**
     * Creates a statistics results normalization service given the dependencies.
     * @param commonNormalizationService Common normalization service with utility methods.
     * @param errorSamplingConfigurationProperties Error samples collector configuration parameters.
     */
    @Autowired
    public ErrorSamplesNormalizationServiceImpl(CommonTableNormalizationService commonNormalizationService,
                                                DqoErrorSamplingConfigurationProperties errorSamplingConfigurationProperties) {
        this.commonNormalizationService = commonNormalizationService;
        this.errorSamplingConfigurationProperties = errorSamplingConfigurationProperties;
    }

    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the error samples results storage. Returns a new table with fixed column types.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param collectedAt Timestamp of the start of the error sampler session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the error samples table. Contains also a normalized results table.
     */
    @Override
    public ErrorSamplesNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                         LocalDateTime collectedAt,
                                                         SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        int resultRowCount = resultsTable != null ? resultsTable.rowCount() : 0;
        Table normalizedResults = Table.create("error_samples_normalized");

        DateTimeColumn collectedAtColumn = DateTimeColumn.create(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME, resultRowCount);
        collectedAtColumn.setMissingTo(collectedAt);
        normalizedResults.addColumns(collectedAtColumn);

        AbstractColumn<?,?> normalizedResultColumn = normalizeErrorSamplerResult(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME); // we are using the column name from SensorReadoutsColumnNames because we are reusing sensors and the sensor returns its result as an "actual_value" column

        StringColumn resultTypeColumn = StringColumn.create(ErrorSamplesColumnNames.RESULT_TYPE_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(resultTypeColumn); // we will set the data type when we detect it...

        if (normalizedResultColumn == null) {
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.NULL.getName());
        }

        if (normalizedResultColumn instanceof StringColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.STRING.getName());
        }
        else {
            if (normalizedResultColumn instanceof TextColumn) {
                StringColumn normalizedStringColumn = TableColumnUtility.convertToStringColumn(normalizedResultColumn);
                normalizedStringColumn.setName(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME);
                normalizedResults.addColumns(normalizedStringColumn);
                resultTypeColumn.setMissingTo(ErrorSampleResultDataType.STRING.getName());
            }
            else {
                StringColumn resultStringColumn = StringColumn.create(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultStringColumn);
            }
        }

        if (normalizedResultColumn instanceof LongColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.INTEGER.getName());
        }
        else {
            if (normalizedResultColumn instanceof IntColumn || normalizedResultColumn instanceof ShortColumn) {
                LongColumn normalizedLongColumn = TableColumnUtility.convertToLongColumn(normalizedResultColumn);
                normalizedLongColumn.setName(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME);
                normalizedResults.addColumns(normalizedLongColumn);
                resultTypeColumn.setMissingTo(ErrorSampleResultDataType.INTEGER.getName());
            }
            else {
                LongColumn resultIntegerColumn = LongColumn.create(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultIntegerColumn);
            }
        }

        if (normalizedResultColumn instanceof DoubleColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.FLOAT.getName());
        }
        else {
            if (normalizedResultColumn instanceof FloatColumn) {
                normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME);
                DoubleColumn normalizedDoubleColumn = TableColumnUtility.convertToDoubleColumn(normalizedResultColumn);
                normalizedResults.addColumns(normalizedDoubleColumn);
                resultTypeColumn.setMissingTo(ErrorSampleResultDataType.FLOAT.getName());
            }
            else {
                DoubleColumn resultFloatColumn = DoubleColumn.create(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME, resultRowCount);
                normalizedResults.addColumns(resultFloatColumn);
            }
        }

        if (normalizedResultColumn instanceof BooleanColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.BOOLEAN.getName());
        }
        else {
            BooleanColumn resultBooleanColumn = BooleanColumn.create(ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultBooleanColumn);
        }

        if (normalizedResultColumn instanceof DateColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.DATE.getName());
        }
        else {
            DateColumn resultDateColumn = DateColumn.create(ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateColumn);
        }

        if (normalizedResultColumn instanceof DateTimeColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.DATETIME.getName());
        }
        else {
            DateTimeColumn resultDateTimeColumn = DateTimeColumn.create(ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateTimeColumn);
        }

        if (normalizedResultColumn instanceof InstantColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.INSTANT.getName());
        }
        else {
            InstantColumn resultInstantColumn = InstantColumn.create(ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultInstantColumn);
        }

        if (normalizedResultColumn instanceof TimeColumn) {
            normalizedResultColumn.setName(ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ErrorSampleResultDataType.TIME.getName());
        }
        else {
            TimeColumn resultTimeColumn = TimeColumn.create(ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultTimeColumn);
        }

        IntColumn sampleIndexColumn = this.normalizeSampleIndex(resultsTable, ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME);
        if (sampleIndexColumn == null) {
            sampleIndexColumn = IntColumn.create(ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME, resultRowCount);
        }
        normalizedResults.addColumns(sampleIndexColumn);

        StringColumn scopeColumn = StringColumn.create(ErrorSamplesColumnNames.SCOPE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            scopeColumn.setMissingTo(sensorRunParameters.getDataGroupings() != null ? ErrorSamplesDataScope.data_group.name() : ErrorSamplesDataScope.table.name());
        }
        normalizedResults.addColumns(scopeColumn);

        // now detect data stream level columns...
        StringColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataGroupingDimensionColumns(
                resultsTable, sensorRunParameters.getDataGroupings(), resultRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(StringColumn.create(dataStreamLevelColumnName, resultRowCount));
            }
        }

        LongColumn dataGroupingHashColumn = this.commonNormalizationService.createDataGroupingHashColumn(
                dataStreamLevelColumns, sensorRunParameters.getDataGroupings(), resultRowCount);
        normalizedResults.addColumns(dataGroupingHashColumn);

        StringColumn dataGroupingNameColumn = this.commonNormalizationService.createDataGroupingNameColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataGroupingNameColumn);

        StringColumn dataStreamMappingNameColumn = StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getDataGroupings() != null) {
            dataStreamMappingNameColumn.setMissingTo(sensorRunParameters.getDataGroupings().getDataGroupingConfigurationName());
        }
        normalizedResults.addColumns(dataStreamMappingNameColumn);

        LongColumn connectionHashColumn = LongColumn.create(ErrorSamplesColumnNames.CONNECTION_HASH_COLUMN_NAME, resultRowCount);
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        normalizedResults.addColumns(connectionHashColumn);

        StringColumn connectionNameColumn = StringColumn.create(ErrorSamplesColumnNames.CONNECTION_NAME_COLUMN_NAME, resultRowCount);
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        normalizedResults.addColumns(connectionNameColumn);

        StringColumn providerColumn = StringColumn.create(ErrorSamplesColumnNames.PROVIDER_COLUMN_NAME, resultRowCount);
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        normalizedResults.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(ErrorSamplesColumnNames.TABLE_HASH_COLUMN_NAME, resultRowCount);
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        normalizedResults.addColumns(tableHashColumn);

        StringColumn schemaNameColumn = StringColumn.create(ErrorSamplesColumnNames.SCHEMA_NAME_COLUMN_NAME, resultRowCount);
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getSchemaName());
        normalizedResults.addColumns(schemaNameColumn);

        StringColumn tableNameColumn = StringColumn.create(ErrorSamplesColumnNames.TABLE_NAME_COLUMN_NAME, resultRowCount);
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getTableName());
        normalizedResults.addColumns(tableNameColumn);

        StringColumn tableStageColumn = StringColumn.create(ErrorSamplesColumnNames.TABLE_STAGE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getTable().getStage() != null) {
            tableStageColumn.setMissingTo(sensorRunParameters.getTable().getStage());
        }
        normalizedResults.addColumns(tableStageColumn);

        IntColumn tablePriorityColumn = IntColumn.create(ErrorSamplesColumnNames.TABLE_PRIORITY_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getTable().getPriority() != null) {
            tablePriorityColumn.setMissingTo(sensorRunParameters.getTable().getPriority());
        }
        normalizedResults.addColumns(tablePriorityColumn);

        LongColumn columnHashColumn = LongColumn.create(ErrorSamplesColumnNames.COLUMN_HASH_COLUMN_NAME, resultRowCount);
        Long columnHash = null;
        if (sensorRunParameters.getColumn() != null) {
            columnHash = sensorRunParameters.getColumn().getHierarchyId().hashCode64();
            columnHashColumn.setMissingTo(columnHash);
        }
        normalizedResults.addColumns(columnHashColumn);

        StringColumn columnNameColumn = StringColumn.create(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        normalizedResults.addColumns(columnNameColumn);

        LongColumn checkHashColumn = LongColumn.create(ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME, resultRowCount);
        long checkHash = sensorRunParameters.getCheck().getHierarchyId().hashCode64();
        checkHashColumn.setMissingTo(checkHash);
        normalizedResults.addColumns(checkHashColumn);

        StringColumn checkNameColumn = StringColumn.create(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME, resultRowCount);
        String checkName = sensorRunParameters.getCheck().getCheckName();
        checkNameColumn.setMissingTo(checkName);
        normalizedResults.addColumns(checkNameColumn);

        StringColumn checkDisplayNameColumn = StringColumn.create(ErrorSamplesColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME, resultRowCount);
        String checkDisplayName = sensorRunParameters.getCheck().getDisplayName();
        checkDisplayNameColumn.setMissingTo(checkDisplayName != null ? checkDisplayName : checkName); // we store the check name if there is no display name (a fallback value)
        normalizedResults.addColumns(checkDisplayNameColumn);

        StringColumn checkTypeColumn = StringColumn.create(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getCheckType() != null) {
            String checkType = sensorRunParameters.getCheckType().getDisplayName();
            checkTypeColumn.setMissingTo(checkType);
        }
        normalizedResults.addColumns(checkTypeColumn);

        TimePeriodGradient timePeriodGradient = sensorRunParameters.getTimePeriodGradient();
        StringColumn timeGradientColumn = StringColumn.create(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME, resultRowCount);
        if (timePeriodGradient != null) {
            timeGradientColumn.setMissingTo(timePeriodGradient.name());
        }
        normalizedResults.addColumns(timeGradientColumn);

        StringColumn checkCategoryColumn = StringColumn.create(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME, resultRowCount);
        String categoryName = sensorRunParameters.getCheck().getCategoryName();
        checkCategoryColumn.setMissingTo(categoryName);
        normalizedResults.addColumns(checkCategoryColumn);

        StringColumn tableComparisonNameColumn = StringColumn.create(ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getTableComparisonConfiguration() != null) {
            String tableComparisonName = sensorRunParameters.getTableComparisonConfiguration().getComparisonName();
            tableComparisonNameColumn.setMissingTo(tableComparisonName);
        }
        normalizedResults.addColumns(tableComparisonNameColumn);

        StringColumn qualityDimensionColumn = StringColumn.create(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME, resultRowCount);
        String effectiveDataQualityDimension = sensorRunParameters.getCheck().getEffectiveDataQualityDimension();
        qualityDimensionColumn.setMissingTo(effectiveDataQualityDimension);
        normalizedResults.addColumns(qualityDimensionColumn);

        StringColumn sensorNameColumn = StringColumn.create(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME, resultRowCount);
        String sensorDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        normalizedResults.addColumns(sensorNameColumn);

        StringColumn timeSeriesUuidColumn = this.commonNormalizationService.createTimeSeriesUuidColumn(dataGroupingHashColumn, checkHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME, resultRowCount);
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        normalizedResults.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(ErrorSamplesColumnNames.DURATION_MS_COLUMN_NAME, resultRowCount);
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        normalizedResults.addColumns(durationMsColumn);

        StringColumn sampleFilterColumn = StringColumn.create(ErrorSamplesColumnNames.SAMPLE_FILTER_COLUMN_NAME, resultRowCount);
        List<String> allFilters = new ArrayList<>();
        if (!Strings.isNullOrEmpty(sensorRunParameters.getTable().getFilter())) {
            allFilters.add(sensorRunParameters.getTable().getFilter());
        }
        if (sensorRunParameters.getAdditionalFilters() != null) {
            allFilters.addAll(sensorRunParameters.getAdditionalFilters());
        }
        if (!allFilters.isEmpty()) {
            String aggregatedFilters = StringUtils.join(allFilters.toArray(), " AND ");
            sampleFilterColumn.setMissingTo(aggregatedFilters);
        }
        normalizedResults.addColumns(sampleFilterColumn);

        StringColumn id1Column = normalizeSourceIdColumn(resultsTable, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "1");
        normalizedResults.addColumns(id1Column);
        StringColumn id2Column = normalizeSourceIdColumn(resultsTable, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "2");
        normalizedResults.addColumns(id2Column);
        StringColumn id3Column = normalizeSourceIdColumn(resultsTable, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "3");
        normalizedResults.addColumns(id3Column);
        StringColumn id4Column = normalizeSourceIdColumn(resultsTable, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "4");
        normalizedResults.addColumns(id4Column);
        StringColumn id5Column = normalizeSourceIdColumn(resultsTable, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "5");
        normalizedResults.addColumns(id5Column);

        InstantColumn createdAtColumn = InstantColumn.create(ErrorSamplesColumnNames.CREATED_AT_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(createdAtColumn);
        InstantColumn updatedAtColumn = InstantColumn.create(ErrorSamplesColumnNames.UPDATED_AT_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(updatedAtColumn);
        StringColumn createdByColumn = StringColumn.create(ErrorSamplesColumnNames.CREATED_BY_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(createdByColumn);
        StringColumn updatedByColumn = StringColumn.create(ErrorSamplesColumnNames.UPDATED_BY_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(updatedByColumn);

        StringColumn idColumn = this.commonNormalizationService.createErrorSampleRowIdColumn(dataGroupingHashColumn, collectedAtColumn, sampleIndexColumn,
                checkHash, tableHash, columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.insertColumn(0, idColumn);

        if (!sensorExecutionResult.isSuccess()) {
            normalizedResults.clear(); // remove any fake columns, in case that the check failed to execute
        }

        ErrorSamplesNormalizedResult datasetMetadata = new ErrorSamplesNormalizedResult(normalizedResults);
        return datasetMetadata;
    }

    /**
     * Detects the type of the result column and returns a normalized column. Small data types (such as short, int) are converted to bigger data types to reduce the number of columns.
     * @param resultsTable Result table to extract the value.
     * @param columnName Column name to find in the result.
     * @return Normalized column (copied).
     */
    public AbstractColumn normalizeErrorSamplerResult(Table resultsTable, String columnName) {
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

        if (currentColumn instanceof StringColumn) {
            return ((StringColumn)currentColumn).copy();
        }

        if (currentColumn instanceof TextColumn) {  // covers TextColumn and StringColumn
            TextColumn textColumn = (TextColumn) currentColumn;
            StringColumn truncatedStringColumn = StringColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < textColumn.size(); i++) {
                String stringValue = textColumn.get(i);
                if (!Strings.isNullOrEmpty(stringValue)) {
                    if (stringValue.length() > this.errorSamplingConfigurationProperties.getTruncatedStringsLength()) {
                        stringValue = stringValue.substring(0, this.errorSamplingConfigurationProperties.getTruncatedStringsLength());
                    }

                    truncatedStringColumn.set(i, stringValue);
                }
            }

            return truncatedStringColumn;
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
            IntColumn indexesColumn = IntColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < resultsTable.rowCount(); i++) {
                indexesColumn.set(i, i + 1); // sample indexes are 1-based
            }
            return indexesColumn;
        }

        IntColumn resultColumn = TableColumnUtility.convertToIntColumn(currentColumn);
        if (resultColumn == currentColumn) {
            resultColumn = resultColumn.copy();
        }
        return resultColumn;
    }

    /**
     * Finds and normalizes an ID column.
     * @param resultsTable Result table.
     * @param columnName Column name to find.
     * @return A normalized id column or an empty column when the column was not found.
     */
    public StringColumn normalizeSourceIdColumn(Table resultsTable, String columnName) {
        if (resultsTable == null) {
            // the collector returned with an error, we don't have a result column
            return null;
        }

        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);
        if (currentColumn == null) {
            return StringColumn.create(columnName, resultsTable.rowCount());
        }

        StringColumn resultColumn = TableColumnUtility.convertToStringColumn(currentColumn);
        if (resultColumn == currentColumn) {
            resultColumn = resultColumn.copy();
        }
        return resultColumn;
    }
}
