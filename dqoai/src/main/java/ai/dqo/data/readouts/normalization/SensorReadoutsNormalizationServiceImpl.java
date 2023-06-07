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

import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimePeriodGradient;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableColumnUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.time.*;
import java.util.Arrays;

/**
 * Service that parses datasets with results returned by a sensor query.
 * Detects column types (data stream level columns), describes the metadata of the result. Also fixes missing information, adds a data_stream_hash column with a hash of all data stream levels.
 */
@Service
public class SensorReadoutsNormalizationServiceImpl implements SensorReadoutsNormalizationService {
    private CommonTableNormalizationService commonNormalizationService;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Creates a sensor readout normalization service given the dependencies.
     * @param commonNormalizationService Common normalization service with utility methods.
     */
    @Autowired
    public SensorReadoutsNormalizationServiceImpl(CommonTableNormalizationService commonNormalizationService,
                                                  DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.commonNormalizationService = commonNormalizationService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for using in a sensor. Returns a new table with fixed column types.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param timePeriodGradient Time series gradient.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    public SensorReadoutsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                           TimePeriodGradient timePeriodGradient,
                                                           SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        int resultsRowCount = resultsTable.rowCount();
        ZoneId defaultTimeZone = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        Table normalizedResults = Table.create("sensor_results_normalized");
        Column<?> actualValueColumn = TableColumnUtility.findColumn(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        if (actualValueColumn == null && sensorExecutionResult.isSuccess()) {
            throw new SensorResultNormalizeException(resultsTable,
                    "Missing '" + SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME + "' column, the sensor query must return this column");
        }
        DoubleColumn normalizedActualValueColumn = actualValueColumn != null ?
                makeNormalizedDoubleColumn(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME) :
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, resultsRowCount);
        normalizedResults.addColumns(normalizedActualValueColumn);

        if (resultsTable.containsColumn(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME)) {
            DoubleColumn normalizedExpectedValueColumn = makeNormalizedDoubleColumn(resultsTable, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
            normalizedResults.addColumns(normalizedExpectedValueColumn);
        }
        else {
            normalizedResults.addColumns(DoubleColumn.create(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME, resultsRowCount));
        }

        DateTimeColumn timePeriodColumn = makeNormalizedTimePeriodColumn(resultsTable, defaultTimeZone, timePeriodGradient);
        normalizedResults.addColumns(timePeriodColumn);

        InstantColumn normalizedTimePeriodUtcColumn = makeNormalizedTimePeriodUtcColumn(resultsTable, timePeriodColumn,
                defaultTimeZone);
        normalizedResults.addColumns(normalizedTimePeriodUtcColumn);

        // now detect data stream level columns...
        TextColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataStreamLevelColumns(
                resultsTable, sensorRunParameters.getDataStreams(), resultsRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = SensorReadoutsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(TextColumn.create(dataStreamLevelColumnName, resultsRowCount));
            }
        }

        LongColumn dataStreamHashColumn = this.commonNormalizationService.createDataStreamHashColumn(dataStreamLevelColumns, resultsRowCount);
        normalizedResults.addColumns(dataStreamHashColumn);

        TextColumn dataStreamNameColumn = this.commonNormalizationService.createDataStreamNameColumn(dataStreamLevelColumns, resultsRowCount);
        normalizedResults.addColumns(dataStreamNameColumn);

        TextColumn dataStreamMappingNameColumn = TextColumn.create(SensorReadoutsColumnNames.DATA_STREAM_MAPPING_NAME_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getDataStreams() != null) {
            dataStreamMappingNameColumn.setMissingTo(sensorRunParameters.getDataStreams().getDataStreamMappingName());
        }
        normalizedResults.addColumns(dataStreamMappingNameColumn);

        // sort the columns to make any continuous time series value extraction faster
        String[] sortableColumnNames = Arrays.stream(dataStreamLevelColumns)
                .filter(d -> d != null)
                .map(col -> col.name())
                .toArray(size -> new String[size + 1]);
        sortableColumnNames[sortableColumnNames.length - 1] = timePeriodColumn.name();
        Table sortedNormalizedTable = normalizedResults.sortAscendingOn(sortableColumnNames);

        // TODO: add a data stream name to the sorted normalized table (generate it)

        TextColumn timeGradientColumn = TextColumn.create(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME, resultsRowCount);
        if (timePeriodGradient != null) {
            timeGradientColumn.setMissingTo(timePeriodGradient.name());
        }
        sortedNormalizedTable.insertColumn(4, timeGradientColumn);

        LongColumn connectionHashColumn = LongColumn.create(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME, resultsRowCount);
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        sortedNormalizedTable.addColumns(connectionHashColumn);

        TextColumn connectionNameColumn = TextColumn.create(SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME, resultsRowCount);
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        sortedNormalizedTable.addColumns(connectionNameColumn);

        TextColumn providerColumn = TextColumn.create(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME, resultsRowCount);
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        sortedNormalizedTable.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME, resultsRowCount);
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        sortedNormalizedTable.addColumns(tableHashColumn);

        TextColumn schemaNameColumn = TextColumn.create(SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME, resultsRowCount);
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getSchemaName());
        sortedNormalizedTable.addColumns(schemaNameColumn);

        TextColumn tableNameColumn = TextColumn.create(SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME, resultsRowCount);
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getTableName());
        sortedNormalizedTable.addColumns(tableNameColumn);

        TextColumn tableNamePatternColumn = TextColumn.create(SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME, resultsRowCount);
        tableNamePatternColumn.setMissingTo(sensorRunParameters.getTable().getPhysicalTableName().getTableName());
        sortedNormalizedTable.addColumns(tableNamePatternColumn);

        TextColumn tableStageColumn = TextColumn.create(SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getTable().getStage() != null) {
            tableStageColumn.setMissingTo(sensorRunParameters.getTable().getStage());
        }
        sortedNormalizedTable.addColumns(tableStageColumn);

        IntColumn tablePriorityColumn = IntColumn.create(SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getTable().getPriority() != null) {
            tablePriorityColumn.setMissingTo(sensorRunParameters.getTable().getPriority());
        }
        sortedNormalizedTable.addColumns(tablePriorityColumn);

        LongColumn columnHashColumn = LongColumn.create(SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME, resultsRowCount);
        Long columnHash = null;
        if (sensorRunParameters.getColumn() != null) {
            columnHash = sensorRunParameters.getColumn().getHierarchyId().hashCode64();
            columnHashColumn.setMissingTo(columnHash);
        }
        sortedNormalizedTable.addColumns(columnHashColumn);

        TextColumn columnNameColumn = TextColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        sortedNormalizedTable.addColumns(columnNameColumn);

        TextColumn columnNamePatternColumn = TextColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getColumn() != null) {
            columnNamePatternColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        sortedNormalizedTable.addColumns(columnNamePatternColumn);

        LongColumn checkHashColumn = LongColumn.create(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME, resultsRowCount);
        long checkHash = sensorRunParameters.getCheck().getHierarchyId().hashCode64();
        checkHashColumn.setMissingTo(checkHash);
        sortedNormalizedTable.addColumns(checkHashColumn);

        TextColumn checkNameColumn = TextColumn.create(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME, resultsRowCount);
        String checkName = sensorRunParameters.getCheck().getCheckName();
        checkNameColumn.setMissingTo(checkName);
        sortedNormalizedTable.addColumns(checkNameColumn);

        TextColumn checkDisplayNameColumn = TextColumn.create(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME, resultsRowCount);
        String checkDisplayName = sensorRunParameters.getCheck().getDisplayName();
        checkDisplayNameColumn.setMissingTo(checkDisplayName != null ? checkDisplayName : checkName); // we store the check name if there is no display name (a fallback value)
        sortedNormalizedTable.addColumns(checkDisplayNameColumn);

        TextColumn checkTypeColumn = TextColumn.create(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME, resultsRowCount);
        String checkType = sensorRunParameters.getCheckType().getDisplayName();
        checkTypeColumn.setMissingTo(checkType);
        sortedNormalizedTable.addColumns(checkTypeColumn);

        TextColumn checkCategoryColumn = TextColumn.create(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME, resultsRowCount);
        String categoryName = sensorRunParameters.getCheck().getCategoryName();
        checkCategoryColumn.setMissingTo(categoryName);
        sortedNormalizedTable.addColumns(checkCategoryColumn);

        TextColumn qualityDimensionColumn = TextColumn.create(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, resultsRowCount);
        String effectiveDataQualityDimension = sensorRunParameters.getCheck().getEffectiveDataQualityDimension();
        qualityDimensionColumn.setMissingTo(effectiveDataQualityDimension);
        sortedNormalizedTable.addColumns(qualityDimensionColumn);

        TextColumn sensorNameColumn = TextColumn.create(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME, resultsRowCount);
        String sensorDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        sortedNormalizedTable.addColumns(sensorNameColumn);

        LongColumn sortedDataStreamHashColumn = (LongColumn) sortedNormalizedTable.column(SensorReadoutsColumnNames.DATA_STREAM_HASH_COLUMN_NAME);
        TextColumn timeSeriesUuidColumn = this.commonNormalizationService.createTimeSeriesUuidColumn(sortedDataStreamHashColumn, checkHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultsRowCount);
        sortedNormalizedTable.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, resultsRowCount);
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        sortedNormalizedTable.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME, resultsRowCount);
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        sortedNormalizedTable.addColumns(durationMsColumn);

        InstantColumn createdAtColumn = InstantColumn.create(SensorReadoutsColumnNames.CREATED_AT_COLUMN_NAME, resultsRowCount);
        sortedNormalizedTable.addColumns(createdAtColumn);
        InstantColumn updatedAtColumn = InstantColumn.create(SensorReadoutsColumnNames.UPDATED_AT_COLUMN_NAME, resultsRowCount);
        sortedNormalizedTable.addColumns(updatedAtColumn);
        TextColumn createdByColumn = TextColumn.create(SensorReadoutsColumnNames.CREATED_BY_COLUMN_NAME, resultsRowCount);
        sortedNormalizedTable.addColumns(createdByColumn);
        TextColumn updatedByColumn = TextColumn.create(SensorReadoutsColumnNames.UPDATED_BY_COLUMN_NAME, resultsRowCount);
        sortedNormalizedTable.addColumns(updatedByColumn);

        DateTimeColumn sortedTimePeriodColumn = (DateTimeColumn) sortedNormalizedTable.column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        TextColumn idColumn = this.commonNormalizationService.createRowIdColumnAndUpdateIndexes(sortedDataStreamHashColumn, sortedTimePeriodColumn, checkHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultsRowCount);
        sortedNormalizedTable.insertColumn(0, idColumn);

        SensorReadoutsNormalizedResult datasetMetadata = new SensorReadoutsNormalizedResult(sortedNormalizedTable);
        return datasetMetadata;
    }

    /**
     * Extracts a given column name from the resultsTable and returns a cloned DoubleColumn. Converts wrong data types.
     * @param resultsTable Result table to extract.
     * @param columnName Column name.
     * @return Requested value column, cloned and converted to a double column.
     */
    public DoubleColumn makeNormalizedDoubleColumn(Table resultsTable, String columnName) {
        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);

        if (currentColumn instanceof DoubleColumn) {
            return ((DoubleColumn)currentColumn).copy();
        }

        if (currentColumn instanceof IntColumn) {
            return ((IntColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof LongColumn) {
            return ((LongColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof FloatColumn) {
            return ((FloatColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof ShortColumn) {
            return ((ShortColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof BooleanColumn) {
            return ((BooleanColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof DateColumn) {
            return ((DateColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof DateTimeColumn) {
            return ((DateTimeColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof InstantColumn) {
            return ((InstantColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof TimeColumn) {
            return ((TimeColumn)currentColumn).asDoubleColumn();
        }

        if (currentColumn instanceof StringColumn) {
            StringColumn stringColumn = (StringColumn) currentColumn;
            DoubleColumn parsedValues = DoubleColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (stringValue != null) {
                    try {
                        double parsedValue = Double.parseDouble(stringValue);
                        parsedValues.set(i, parsedValue);
                    }
                    catch (NumberFormatException nfe) {
                        // ignore errors
                    }
                }
            }

            return parsedValues;
        }

        if (currentColumn instanceof TextColumn) {
            TextColumn stringColumn = (TextColumn) currentColumn;
            DoubleColumn parsedValues = DoubleColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (stringValue != null) {
                    try {
                        double parsedValue = Double.parseDouble(stringValue);
                        parsedValues.set(i, parsedValue);
                    }
                    catch (NumberFormatException nfe) {
                        // ignore errors
                    }
                }
            }

            return parsedValues;
        }

        throw new SensorResultNormalizeException(resultsTable, "Cannot detect an " + columnName + " column data type, it should be a double column");
    }

    /**
     * Extracts a time_period column from the results. Creates a new time_period with the time of now if the time_period column was missing.
     * The values in the time period column are converted to a date time column (without a time zone). Instant columns (based on an UTC timezone) are converted to the timezone of question.
     * Any values that are not aligned at the beginning of the time series gradient (week, day, month, etc.) are truncated to the beginning of the period.
     * @param resultsTable Result table to extract the time_period column.
     * @param defaultTimeZone Default DQO time zone, used to create a time period value if the time period was not received from a sensor result.
     * @param timePeriodGradient Time series gradient (for truncation).
     * @return A copy of a time_period column that is truncated, normalized to a datetime without the time zone.
     */
    public DateTimeColumn makeNormalizedTimePeriodColumn(Table resultsTable, ZoneId defaultTimeZone, TimePeriodGradient timePeriodGradient) {
        DateTimeColumn newTimestampColumn = DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, resultsTable.rowCount());
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime truncatedNow = timePeriodGradient != null ?
                LocalDateTimeTruncateUtility.truncateTimePeriod(timeNow, timePeriodGradient) : timeNow;

        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        if (currentColumn == null) {
            // missing time_period column, we will create a fake one
            newTimestampColumn.fillWith(() -> truncatedNow);
            return newTimestampColumn;
        }

        if (currentColumn instanceof DateColumn) {
            DateColumn dateColumn = (DateColumn)currentColumn;

            for (int i = 0; i < dateColumn.size(); i++) {
                LocalDate localDate = dateColumn.get(i);
                if (localDate == null) {
                    // we need a timestamp, we will use a truncated now instead
                    newTimestampColumn.set(i, truncatedNow);
                }
                else {
                    LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
                    LocalDateTime truncatedDate =  timePeriodGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTime, timePeriodGradient) : localDateTime;
                    newTimestampColumn.set(i, truncatedDate);
                }
            }
            return newTimestampColumn;
        }

        if (currentColumn instanceof DateTimeColumn) {
            DateTimeColumn dateColumn = (DateTimeColumn)currentColumn;

            for (int i = 0; i < dateColumn.size(); i++) {
                LocalDateTime localDateTime = dateColumn.get(i);
                if (localDateTime == null) {
                    // we need a timestamp, we will use a truncated now instead
                    newTimestampColumn.set(i, truncatedNow);
                }
                else {
                    LocalDateTime truncatedDate = timePeriodGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTime, timePeriodGradient) : localDateTime;
                    newTimestampColumn.set(i, truncatedDate);
                }
            }
            return newTimestampColumn;
        }

        if (currentColumn instanceof InstantColumn) {
            InstantColumn dateColumn = (InstantColumn)currentColumn;

            for (int i = 0; i < dateColumn.size(); i++) {
                Instant instant = dateColumn.get(i);
                if (instant == null) {
                    // we need a timestamp, we will use a truncated now instead
                    newTimestampColumn.set(i, truncatedNow);
                }
                else {
                    LocalDateTime localDateTimeFromInstant = LocalDateTime.ofInstant(instant, defaultTimeZone);
                    LocalDateTime truncatedInstant = timePeriodGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTimeFromInstant, timePeriodGradient) : localDateTimeFromInstant;
                    newTimestampColumn.set(i, truncatedInstant);
                }
            }
            return newTimestampColumn;
        }

        // NOTE: we could parse the string, but it is better if the user casts timestamp columns that are strings to a datetime column in the sensor SQL query

        throw new SensorResultNormalizeException(resultsTable, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME + " column must be a date, datetime or an instant (utc datetime).");
    }

    /**
     * Makes a normalized time_period_utc column. Converts non Instant (UTC date) to UTC date. Tries to convert the time period column.
     * @param resultsTable Result table to find the column.
     * @param timePeriodColumn Time period column used to convert the dates.
     * @param defaultTimeZone Default DQO time zone.
     * @return Instant column with the time_period_utc time periods.
     */
    public InstantColumn makeNormalizedTimePeriodUtcColumn(Table resultsTable, DateTimeColumn timePeriodColumn, ZoneId defaultTimeZone) {
        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
        if (currentColumn == null || !(currentColumn instanceof InstantColumn)) {
            InstantColumn newTimePeriodUtcColumn = InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, resultsTable.rowCount());

            // missing time_period_utc column or invalid type, we will convert it from the time_period, using the default DQO server time zone
            for (int i = 0; i < timePeriodColumn.size(); i++) {
                LocalDateTime timePeriodLocalValue = timePeriodColumn.get(i);
                Instant timePeriodUtc = timePeriodLocalValue.toInstant(defaultTimeZone.getRules().getOffset(timePeriodLocalValue));
                newTimePeriodUtcColumn.set(i, timePeriodUtc);
            }

            return newTimePeriodUtcColumn;
        }

        InstantColumn timePeriodUtcColumn = (InstantColumn)currentColumn;
        for (int i = 0; i < timePeriodUtcColumn.size(); i++) {
            Instant instant = timePeriodUtcColumn.get(i);
            if (instant == null) {
                // we need to fix it
                LocalDateTime timePeriodLocalValue = timePeriodColumn.get(i);
                Instant timePeriodUtc = timePeriodLocalValue.toInstant(defaultTimeZone.getRules().getOffset(timePeriodLocalValue));
                timePeriodUtcColumn.set(i, timePeriodUtc);
            }
        }

        return timePeriodUtcColumn;
    }
}
