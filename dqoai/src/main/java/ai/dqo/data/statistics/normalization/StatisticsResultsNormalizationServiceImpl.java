package ai.dqo.data.statistics.normalization;

import ai.dqo.core.configuration.DqoStatisticsCollectorConfigurationProperties;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorResultNormalizeException;
import ai.dqo.data.statistics.factory.*;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.utils.tables.TableColumnUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.AbstractColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.strings.AbstractStringColumn;

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

        StringColumn statusColumn = StringColumn.create(StatisticsColumnNames.STATUS_COLUMN_NAME, resultRowCount);
        statusColumn.setMissingTo(sensorExecutionResult.isSuccess() ? StatisticsCollectorResultStatus.success.name() : StatisticsCollectorResultStatus.error.name());
        normalizedResults.addColumns(statusColumn);

        AbstractColumn<?,?> normalizedResultColumn = normalizeCollectorResult(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME); // we are using the column name from SensorReadoutsColumnNames because we are reusing sensors and the sensor returns its result as an "actual_value" column

        StringColumn resultTypeColumn = StringColumn.create(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME, resultRowCount);
        normalizedResults.addColumns(resultTypeColumn); // we will set the data type when we detect it...

        if (normalizedResultColumn == null) {
            resultTypeColumn.setMissingTo(StatisticsResultDataType.NULL.getName());
        }

        if (normalizedResultColumn instanceof StringColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.STRING.getName());
        }
        else {
            StringColumn resultStringColumn = StringColumn.create(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultStringColumn);
        }

        if (normalizedResultColumn instanceof LongColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.INTEGER.getName());
        }
        else {
            LongColumn resultIntegerColumn = LongColumn.create(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultIntegerColumn);
        }

        if (normalizedResultColumn instanceof DoubleColumn) {
            normalizedResultColumn.setName(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME);
            normalizedResults.addColumns(normalizedResultColumn);
            resultTypeColumn.setMissingTo(StatisticsResultDataType.FLOAT.getName());
        }
        else {
            DoubleColumn resultFloatColumn = DoubleColumn.create(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME, resultRowCount);
            normalizedResults.addColumns(resultFloatColumn);
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

        StringColumn scopeColumn = StringColumn.create(StatisticsColumnNames.SCOPE_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            scopeColumn.setMissingTo(sensorRunParameters.getDataStreams() != null ? StatisticsDataScope.data_stream.name() : StatisticsDataScope.table.name());
        }
        normalizedResults.addColumns(scopeColumn);

        // now detect data stream level columns...
        StringColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataStreamLevelColumns(
                resultsTable, sensorRunParameters.getDataStreams(), resultRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(StringColumn.create(dataStreamLevelColumnName, resultRowCount));
            }
        }

        LongColumn dataStreamHashColumn = this.commonNormalizationService.createDataStreamHashColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataStreamHashColumn);

        StringColumn dataStreamNameColumn = this.commonNormalizationService.createDataStreamNameColumn(dataStreamLevelColumns, resultRowCount);
        normalizedResults.addColumns(dataStreamNameColumn);

        LongColumn connectionHashColumn = LongColumn.create(StatisticsColumnNames.CONNECTION_HASH_COLUMN_NAME, resultRowCount);
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        normalizedResults.addColumns(connectionHashColumn);

        StringColumn connectionNameColumn = StringColumn.create(StatisticsColumnNames.CONNECTION_NAME_COLUMN_NAME, resultRowCount);
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        normalizedResults.addColumns(connectionNameColumn);

        StringColumn providerColumn = StringColumn.create(StatisticsColumnNames.PROVIDER_COLUMN_NAME, resultRowCount);
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        normalizedResults.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(StatisticsColumnNames.TABLE_HASH_COLUMN_NAME, resultRowCount);
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        normalizedResults.addColumns(tableHashColumn);

        StringColumn schemaNameColumn = StringColumn.create(StatisticsColumnNames.SCHEMA_NAME_COLUMN_NAME, resultRowCount);
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getSchemaName());
        normalizedResults.addColumns(schemaNameColumn);

        StringColumn tableNameColumn = StringColumn.create(StatisticsColumnNames.TABLE_NAME_COLUMN_NAME, resultRowCount);
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getTableName());
        normalizedResults.addColumns(tableNameColumn);

        StringColumn tableStageColumn = StringColumn.create(StatisticsColumnNames.TABLE_STAGE_COLUMN_NAME, resultRowCount);
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

        StringColumn columnNameColumn = StringColumn.create(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME, resultRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        normalizedResults.addColumns(columnNameColumn);

        LongColumn collectorHashColumn = LongColumn.create(StatisticsColumnNames.COLLECTOR_HASH_COLUMN_NAME, resultRowCount);
        long collectorHash = sensorRunParameters.getProfiler().getHierarchyId().hashCode64();
        collectorHashColumn.setMissingTo(collectorHash);
        normalizedResults.addColumns(collectorHashColumn);

        StringColumn collectorNameColumn = StringColumn.create(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME, resultRowCount);
        String collectorName = sensorRunParameters.getProfiler().getProfilerName();
        collectorNameColumn.setMissingTo(collectorName);
        normalizedResults.addColumns(collectorNameColumn);

        StringColumn collectorTargetColumn = StringColumn.create(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME, resultRowCount);
        String collectorTargetType = sensorRunParameters.getColumn() != null ? StatisticsCollectorTarget.column.toString() : StatisticsCollectorTarget.table.toString();
        collectorTargetColumn.setMissingTo(collectorTargetType);
        normalizedResults.addColumns(collectorTargetColumn);

        StringColumn collectorCategoryColumn = StringColumn.create(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME, resultRowCount);
        String categoryName = sensorRunParameters.getProfiler().getCategoryName();
        collectorCategoryColumn.setMissingTo(categoryName);
        normalizedResults.addColumns(collectorCategoryColumn);

        StringColumn sensorNameColumn = StringColumn.create(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME, resultRowCount);
        String sensorDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        normalizedResults.addColumns(sensorNameColumn);

        StringColumn timeSeriesUuidColumn = this.commonNormalizationService.createTimeSeriesUuidColumn(dataStreamHashColumn, collectorHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME, resultRowCount);
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        normalizedResults.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(StatisticsColumnNames.DURATION_MS_COLUMN_NAME, resultRowCount);
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        normalizedResults.addColumns(durationMsColumn);

        StringColumn errorMessageColumn = StringColumn.create(StatisticsColumnNames.ERROR_MESSAGE_COLUMN_NAME, resultRowCount);
        if (!sensorExecutionResult.isSuccess() && sensorExecutionResult.getException() != null) {
            errorMessageColumn.setMissingTo(sensorExecutionResult.getException().getMessage());
        }
        normalizedResults.addColumns(errorMessageColumn);

        StringColumn idColumn = this.commonNormalizationService.createRowIdColumn(dataStreamHashColumn, executedAtColumn, collectorHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultRowCount);
        normalizedResults.insertColumn(0, idColumn);

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
            StringColumn truncatedStringsColumn = StringColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (stringValue != null) {
                    if (stringValue.length() > this.statisticsCollectorConfigurationProperties.getTruncatedStringsLength()) {
                        stringValue = stringValue.substring(0, this.statisticsCollectorConfigurationProperties.getTruncatedStringsLength());
                    }

                    truncatedStringsColumn.set(i, stringValue);
                }
            }

            return truncatedStringsColumn;
        }


        throw new SensorResultNormalizeException(resultsTable, "Cannot detect an " + columnName + " column data type");
    }
}
