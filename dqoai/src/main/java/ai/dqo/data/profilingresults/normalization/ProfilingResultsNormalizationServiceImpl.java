package ai.dqo.data.profilingresults.normalization;

import ai.dqo.core.configuration.DqoProfilerConfigurationProperties;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.profilingresults.factory.*;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorResultNormalizeException;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.AbstractColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.strings.AbstractStringColumn;

import java.time.LocalDateTime;

/**
 * Normalization service that adapts the results received from a profiler into a normalized "profiler results" table.
 */
@Component
public class ProfilingResultsNormalizationServiceImpl implements ProfilingResultsNormalizationService {
    private CommonTableNormalizationService commonNormalizationService;
    private DqoProfilerConfigurationProperties profilerConfigurationProperties;

    /**
     * Creates a profiler results normalization service given the dependencies.
     * @param commonNormalizationService Common normalization service with utility methods.
     * @param profilerConfigurationProperties Profiler configuration parameters.
     */
    @Autowired
    public ProfilingResultsNormalizationServiceImpl(CommonTableNormalizationService commonNormalizationService,
                                                    DqoProfilerConfigurationProperties profilerConfigurationProperties) {
        this.commonNormalizationService = commonNormalizationService;
        this.profilerConfigurationProperties = profilerConfigurationProperties;
    }

    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the profiler results storage. Returns a new table with fixed column types.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param profiledAt Timestamp of the start of the profiler session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the profiler results table. Contains also a normalized results table.
     */
    @Override
    public ProfilingResultsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                             LocalDateTime profiledAt,
                                                             SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        int resultRowCount = resultsTable != null ? resultsTable.rowCount() : 1;  // one row to return when there is an error
        Table normalizedResults = Table.create("profiling_results_normalized");

        DateTimeColumn profiledAtColumn = DateTimeColumn.create(ProfilingResultsColumnNames.PROFILED_AT_COLUMN_NAME, resultRowCount);
        profiledAtColumn.setMissingTo(profiledAt);
        normalizedResults.addColumns(profiledAtColumn);

        StringColumn statusColumn = StringColumn.create(ProfilingResultsColumnNames.STATUS_COLUMN_NAME, resultRowCount);
        statusColumn.setMissingTo(sensorExecutionResult.isSuccess() ? ProfilerResultStatus.success.name() : ProfilerResultStatus.error.name());
        normalizedResults.addColumns(statusColumn);

        AbstractColumn<?,?> normalizedResultColumn = normalizeProfilerResult(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME); // we are using the column name from SensorReadoutsColumnNames because we are reusing sensors and the sensor returns its result as an "actual_value" column

        StringColumn resultTypeColumn = StringColumn.create(ProfilingResultsColumnNames.RESULT_TYPE_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(resultTypeColumn); // we will set the data type when we detect it...

        if (normalizedResultColumn == null) {
            resultTypeColumn.setMissingTo(ProfilingResultDataType.NULL.getName());
        }

        if (normalizedResultColumn instanceof StringColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_STRING_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.STRING.getName());
        }
        else {
            StringColumn resultStringColumn = StringColumn.create(ProfilingResultsColumnNames.RESULT_STRING_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultStringColumn);
        }

        if (normalizedResultColumn instanceof LongColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_INTEGER_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.INTEGER.getName());
        }
        else {
            LongColumn resultIntegerColumn = LongColumn.create(ProfilingResultsColumnNames.RESULT_INTEGER_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultIntegerColumn);
        }

        if (normalizedResultColumn instanceof DoubleColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_FLOAT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.FLOAT.getName());
        }
        else {
            DoubleColumn resultFloatColumn = DoubleColumn.create(ProfilingResultsColumnNames.RESULT_FLOAT_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultFloatColumn);
        }

        if (normalizedResultColumn instanceof BooleanColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_BOOLEAN_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.BOOLEAN.getName());
        }
        else {
            BooleanColumn resultBooleanColumn = BooleanColumn.create(ProfilingResultsColumnNames.RESULT_BOOLEAN_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultBooleanColumn);
        }

        if (normalizedResultColumn instanceof DateColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_DATE_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.DATE.getName());
        }
        else {
            DateColumn resultDateColumn = DateColumn.create(ProfilingResultsColumnNames.RESULT_DATE_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateColumn);
        }

        if (normalizedResultColumn instanceof DateTimeColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_DATE_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.DATETIME.getName());
        }
        else {
            DateTimeColumn resultDateTimeColumn = DateTimeColumn.create(ProfilingResultsColumnNames.RESULT_DATE_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultDateTimeColumn);
        }

        if (normalizedResultColumn instanceof InstantColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_INSTANT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.INSTANT.getName());
        }
        else {
            InstantColumn resultInstantColumn = InstantColumn.create(ProfilingResultsColumnNames.RESULT_INSTANT_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultInstantColumn);
        }

        if (normalizedResultColumn instanceof TimeColumn) {
            normalizedResultColumn.setName(ProfilingResultsColumnNames.RESULT_TIME_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(ProfilingResultDataType.TIME.getName());
        }
        else {
            TimeColumn resultTimeColumn = TimeColumn.create(ProfilingResultsColumnNames.RESULT_TIME_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultTimeColumn);
        }

        StringColumn scopeColumn = StringColumn.create(ProfilingResultsColumnNames.SCOPE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            scopeColumn.setMissingTo(sensorRunParameters.getDataStreams() != null ? ProfilerDataScope.data_stream.name() : ProfilerDataScope.table.name());
        }
        normalizedResults.addColumns(scopeColumn);

        // now detect data stream level columns...
        StringColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataStreamLevelColumns(
                resultsTable, sensorRunParameters.getDataStreams(), resultRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(StringColumn.create(dataStreamLevelColumnName, resultRowCount));
            }
        }

        LongColumn dataStreamHashColumn = this.commonNormalizationService.createDataStreamHashColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataStreamHashColumn);

        StringColumn dataStreamNameColumn = this.commonNormalizationService.createDataStreamNameColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataStreamNameColumn);

        LongColumn connectionHashColumn = LongColumn.create(ProfilingResultsColumnNames.CONNECTION_HASH_COLUMN_NAME, resultRowCount);
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        normalizedResults.addColumns(connectionHashColumn);

        StringColumn connectionNameColumn = StringColumn.create(ProfilingResultsColumnNames.CONNECTION_NAME_COLUMN_NAME, resultRowCount);
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        normalizedResults.addColumns(connectionNameColumn);

        StringColumn providerColumn = StringColumn.create(ProfilingResultsColumnNames.PROVIDER_COLUMN_NAME, resultRowCount);
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        normalizedResults.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(ProfilingResultsColumnNames.TABLE_HASH_COLUMN_NAME, resultRowCount);
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        normalizedResults.addColumns(tableHashColumn);

        StringColumn schemaNameColumn = StringColumn.create(ProfilingResultsColumnNames.SCHEMA_NAME_COLUMN_NAME, resultRowCount);
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getTarget().getSchemaName());
        normalizedResults.addColumns(schemaNameColumn);

        StringColumn tableNameColumn = StringColumn.create(ProfilingResultsColumnNames.TABLE_NAME_COLUMN_NAME, resultRowCount);
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getTarget().getTableName());
        normalizedResults.addColumns(tableNameColumn);

        StringColumn tableStageColumn = StringColumn.create(ProfilingResultsColumnNames.TABLE_STAGE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getTable().getStage() != null) {
            tableStageColumn.setMissingTo(sensorRunParameters.getTable().getStage());
        }
        normalizedResults.addColumns(tableStageColumn);

        LongColumn columnHashColumn = LongColumn.create(ProfilingResultsColumnNames.COLUMN_HASH_COLUMN_NAME, resultRowCount);
        Long columnHash = null;
        if (sensorRunParameters.getColumn() != null) {
            columnHash = sensorRunParameters.getColumn().getHierarchyId().hashCode64();
            columnHashColumn.setMissingTo(columnHash);
        }
        normalizedResults.addColumns(columnHashColumn);

        StringColumn columnNameColumn = StringColumn.create(ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        normalizedResults.addColumns(columnNameColumn);

        LongColumn profilerHashColumn = LongColumn.create(ProfilingResultsColumnNames.PROFILER_HASH_COLUMN_NAME, resultRowCount);
        long profilerHash = sensorRunParameters.getProfiler().getHierarchyId().hashCode64();
        profilerHashColumn.setMissingTo(profilerHash);
        normalizedResults.addColumns(profilerHashColumn);

        StringColumn profilerNameColumn = StringColumn.create(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME, resultRowCount);
        String profilerName = sensorRunParameters.getProfiler().getProfilerName();
        profilerNameColumn.setMissingTo(profilerName);
        normalizedResults.addColumns(profilerNameColumn);

        StringColumn profilerTargetColumn = StringColumn.create(ProfilingResultsColumnNames.PROFILER_TYPE_COLUMN_NAME, resultRowCount);
        String profilerTargetType = sensorRunParameters.getColumn() != null ? ProfilerTargetType.column.toString() : ProfilerTargetType.table.toString();
        profilerTargetColumn.setMissingTo(profilerTargetType);
        normalizedResults.addColumns(profilerTargetColumn);

        StringColumn profilerCategoryColumn = StringColumn.create(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME, resultRowCount);
        String categoryName = sensorRunParameters.getProfiler().getCategoryName();
        profilerCategoryColumn.setMissingTo(categoryName);
        normalizedResults.addColumns(profilerCategoryColumn);

        StringColumn sensorNameColumn = StringColumn.create(ProfilingResultsColumnNames.SENSOR_NAME_COLUMN_NAME, resultRowCount);
        String sensorDefinitionName = sensorRunParameters.getSensorParameters().getSensorDefinitionName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        normalizedResults.addColumns(sensorNameColumn);

        StringColumn timeSeriesUuidColumn = this.commonNormalizationService.createTimeSeriesUuidColumn(dataStreamHashColumn, profilerHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(ProfilingResultsColumnNames.EXECUTED_AT_COLUMN_NAME, resultRowCount);
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        normalizedResults.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(ProfilingResultsColumnNames.DURATION_MS_COLUMN_NAME, resultRowCount);
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        normalizedResults.addColumns(durationMsColumn);

        StringColumn errorMessageColumn = StringColumn.create(ProfilingResultsColumnNames.ERROR_MESSAGE_COLUMN_NAME, resultRowCount);
        if (!sensorExecutionResult.isSuccess() && sensorExecutionResult.getException() != null) {
            errorMessageColumn.setMissingTo(sensorExecutionResult.getException().getMessage());
        }
        normalizedResults.addColumns(errorMessageColumn);

        StringColumn idColumn = this.commonNormalizationService.createRowIdColumn(dataStreamHashColumn, executedAtColumn, profilerHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.insertColumn(0, idColumn);

        ProfilingResultsNormalizedResult datasetMetadata = new ProfilingResultsNormalizedResult(normalizedResults);
        return datasetMetadata;
    }

    /**
     * Detects the type of the result column and returns a normalized column. Small data types (such as short, int) are converted to bigger data types to reduce the number of columns.
     * @param resultsTable Result table to extract the value.
     * @param columnName Column name to find in the result.
     * @return Normalized column (copied).
     */
    public AbstractColumn normalizeProfilerResult(Table resultsTable, String columnName) {
        if (resultsTable == null) {
            // the profiler returned with an error, we don't have a result column
            return null;
        }

        Column<?> currentColumn = this.commonNormalizationService.findColumn(resultsTable, columnName);
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
            StringColumn truncatedStringsColumn = StringColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (stringValue != null) {
                    if (stringValue.length() > this.profilerConfigurationProperties.getTruncatedStringsLength()) {
                        stringValue = stringValue.substring(0, this.profilerConfigurationProperties.getTruncatedStringsLength());
                    }

                    truncatedStringsColumn.set(i, stringValue);
                }
            }

            return truncatedStringsColumn;
        }


        throw new SensorResultNormalizeException(resultsTable, "Cannot detect an " + columnName + " column data type");
    }
}
