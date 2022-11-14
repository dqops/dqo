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
package ai.dqo.data.readings.normalization;

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service that parses datasets with results returned by a sensor query.
 * Detects column types (data stream level columns), describes the metadata of the result. Also fixes missing information, adds a data_stream_hash column with a hash of all data stream levels.
 */
@Service
public class SensorResultNormalizeServiceImpl implements SensorResultNormalizeService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for using in a sensor. Returns a new table with fixed column types.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param timeSeriesGradient Time series gradient.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    public SensorNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
												   TimeSeriesGradient timeSeriesGradient,
												   SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        ZoneId connectionTimeZone = sensorRunParameters.getConnectionTimeZoneId();
        Table normalizedResults = Table.create("sensor_results_normalized");
        DoubleColumn normalizedActualValueColumn = makeNormalizedDoubleColumn(resultsTable, SensorNormalizedResult.ACTUAL_VALUE_COLUMN_NAME);
        normalizedActualValueColumn.setMissingTo(0.0);
        normalizedResults.addColumns(normalizedActualValueColumn);

        if (resultsTable.containsColumn(SensorNormalizedResult.EXPECTED_VALUE_COLUMN_NAME)) {
            DoubleColumn normalizedExpectedValueColumn = makeNormalizedDoubleColumn(resultsTable, SensorNormalizedResult.EXPECTED_VALUE_COLUMN_NAME);
            normalizedResults.addColumns(normalizedExpectedValueColumn);
        }
        else {
            normalizedResults.addColumns(DoubleColumn.create(SensorNormalizedResult.EXPECTED_VALUE_COLUMN_NAME, resultsTable.rowCount()));
        }

        DateTimeColumn timePeriodColumn = makeNormalizedTimePeriodColumn(resultsTable, connectionTimeZone, timeSeriesGradient);
        normalizedResults.addColumns(timePeriodColumn);

        // now detect data stream level columns...
        StringColumn[] dataStreamLevelColumns = extractAndNormalizeDataStreamLevelColumns(resultsTable);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(StringColumn.create(dataStreamLevelColumnName, resultsTable.rowCount()));
            }
        }

        LongColumn dataStreamHashColumn = createDataStreamHashColumn(dataStreamLevelColumns, resultsTable.rowCount());
        normalizedResults.addColumns(dataStreamHashColumn);

        StringColumn dataStreamNameColumn = createDataStreamNameColumn(dataStreamLevelColumns, resultsTable.rowCount());
        normalizedResults.addColumns(dataStreamNameColumn);

        // sort the columns to make any continuous time series value extraction faster
        String[] sortableColumnNames = Arrays.stream(dataStreamLevelColumns)
                .filter(d -> d != null)
                .map(col -> col.name())
                .toArray(size -> new String[size + 1]);
        sortableColumnNames[sortableColumnNames.length - 1] = timePeriodColumn.name();
        Table sortedNormalizedTable = normalizedResults.sortAscendingOn(sortableColumnNames);

        // TODO: add a data stream name to the sorted normalized table (generate it)

        StringColumn timeGradientColumn = StringColumn.create(SensorNormalizedResult.TIME_GRADIENT_COLUMN_NAME, resultsTable.rowCount());
        if (timeSeriesGradient != null) {
            timeGradientColumn.setMissingTo(timeSeriesGradient.name().toLowerCase(Locale.ENGLISH));
        }
        sortedNormalizedTable.insertColumn(3, timeGradientColumn);

        LongColumn connectionHashColumn = LongColumn.create(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME, resultsTable.rowCount());
        connectionHashColumn.setMissingTo(sensorRunParameters.getConnection().getHierarchyId().hashCode64());
        sortedNormalizedTable.addColumns(connectionHashColumn);

        StringColumn connectionNameColumn = StringColumn.create(SensorNormalizedResult.CONNECTION_NAME_COLUMN_NAME, resultsTable.rowCount());
        connectionNameColumn.setMissingTo(sensorRunParameters.getConnection().getConnectionName());
        sortedNormalizedTable.addColumns(connectionNameColumn);

        StringColumn providerColumn = StringColumn.create(SensorNormalizedResult.PROVIDER_COLUMN_NAME, resultsTable.rowCount());
        providerColumn.setMissingTo(sensorRunParameters.getConnection().getProviderType().name());
        sortedNormalizedTable.addColumns(providerColumn);

        LongColumn tableHashColumn = LongColumn.create(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME, resultsTable.rowCount());
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        tableHashColumn.setMissingTo(tableHash);
        sortedNormalizedTable.addColumns(tableHashColumn);

        StringColumn schemaNameColumn = StringColumn.create(SensorNormalizedResult.SCHEMA_NAME_COLUMN_NAME, resultsTable.rowCount());
        schemaNameColumn.setMissingTo(sensorRunParameters.getTable().getTarget().getSchemaName());
        sortedNormalizedTable.addColumns(schemaNameColumn);

        StringColumn tableNameColumn = StringColumn.create(SensorNormalizedResult.TABLE_NAME_COLUMN_NAME, resultsTable.rowCount());
        tableNameColumn.setMissingTo(sensorRunParameters.getTable().getTarget().getTableName());
        sortedNormalizedTable.addColumns(tableNameColumn);

        StringColumn tableStageColumn = StringColumn.create(SensorNormalizedResult.TABLE_STAGE_COLUMN_NAME, resultsTable.rowCount());
        if (sensorRunParameters.getTable().getStage() != null) {
            tableStageColumn.setMissingTo(sensorRunParameters.getTable().getStage());
        }
        sortedNormalizedTable.addColumns(tableStageColumn);

        LongColumn columnHashColumn = LongColumn.create(SensorNormalizedResult.COLUMN_HASH_COLUMN_NAME, resultsTable.rowCount());
        Long columnHash = null;
        if (sensorRunParameters.getColumn() != null) {
            columnHash = sensorRunParameters.getColumn().getHierarchyId().hashCode64();
            columnHashColumn.setMissingTo(columnHash);
        }
        sortedNormalizedTable.addColumns(columnHashColumn);

        StringColumn columnNameColumn = StringColumn.create(SensorNormalizedResult.COLUMN_NAME_COLUMN_NAME, resultsTable.rowCount());
        if (sensorRunParameters.getColumn() != null) {
            columnNameColumn.setMissingTo(sensorRunParameters.getColumn().getColumnName());
        }
        sortedNormalizedTable.addColumns(columnNameColumn);

        LongColumn checkHashColumn = LongColumn.create(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME, resultsTable.rowCount());
        long checkHash = sensorRunParameters.getCheck().getHierarchyId().hashCode64();
        checkHashColumn.setMissingTo(checkHash);
        sortedNormalizedTable.addColumns(checkHashColumn);

        StringColumn checkNameColumn = StringColumn.create(SensorNormalizedResult.CHECK_NAME_COLUMN_NAME, resultsTable.rowCount());
        String checkName = sensorRunParameters.getCheck().getCheckName();
        checkNameColumn.setMissingTo(checkName);
        sortedNormalizedTable.addColumns(checkNameColumn);

        StringColumn checkCategoryColumn = StringColumn.create(SensorNormalizedResult.CHECK_CATEGORY_COLUMN_NAME, resultsTable.rowCount());
        String categoryName = sensorRunParameters.getCheck().getCategoryName();
        checkCategoryColumn.setMissingTo(categoryName);
        sortedNormalizedTable.addColumns(checkCategoryColumn);

        StringColumn qualityDimensionColumn = StringColumn.create(SensorNormalizedResult.QUALITY_DIMENSION_COLUMN_NAME, resultsTable.rowCount());
        String effectiveDataQualityDimension = sensorRunParameters.getCheck().getEffectiveDataQualityDimension();
        qualityDimensionColumn.setMissingTo(effectiveDataQualityDimension);
        sortedNormalizedTable.addColumns(qualityDimensionColumn);

        StringColumn sensorNameColumn = StringColumn.create(SensorNormalizedResult.SENSOR_NAME_COLUMN_NAME, resultsTable.rowCount());
        String sensorDefinitionName = sensorRunParameters.getSensorParameters().getSensorDefinitionName();
        sensorNameColumn.setMissingTo(sensorDefinitionName);
        sortedNormalizedTable.addColumns(sensorNameColumn);

        LongColumn sortedDataStreamHashColumn = (LongColumn) sortedNormalizedTable.column(SensorNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME);
        StringColumn timeSeriesUuidColumn = createTimeSeriesUuidColumn(sortedDataStreamHashColumn, checkHash, tableHash,
                columnHash != null ? columnHash.longValue() : 0L, resultsTable.rowCount());
        sortedNormalizedTable.addColumns(timeSeriesUuidColumn);

        InstantColumn executedAtColumn = InstantColumn.create(SensorNormalizedResult.EXECUTED_AT_COLUMN_NAME, resultsTable.rowCount());
        executedAtColumn.setMissingTo(sensorRunParameters.getStartedAt());
        sortedNormalizedTable.addColumns(executedAtColumn);

        IntColumn durationMsColumn = IntColumn.create(SensorNormalizedResult.DURATION_MS_COLUMN_NAME, resultsTable.rowCount());
        durationMsColumn.setMissingTo(sensorExecutionResult.getSensorDurationMs());
        sortedNormalizedTable.addColumns(durationMsColumn);

        SensorNormalizedResult datasetMetadata = new SensorNormalizedResult(sortedNormalizedTable);
        return datasetMetadata;
    }

    /**
     * Finds a named column in the table. Performs a case-insensitive search, so the columns may be named in upper or lower case.
     * @param resultsTable Table to analyze.
     * @param columnName Expected column name.
     * @return Column that was found or null.
     */
    public Column<?> findColumn(Table resultsTable, String columnName) {
        if (resultsTable.containsColumn(columnName)) {
            return resultsTable.column(columnName);
        }

        for (String existingColumnName: resultsTable.columnNames()) {
            if (StringUtils.equalsIgnoreCase(columnName, existingColumnName)) {
                return resultsTable.column(existingColumnName);
            }
        }

        return null;
    }

    /**
     * Extracts a given column name from the resultsTable and returns a cloned DoubleColumn. Converts wrong data types.
     * @param resultsTable Result table to extract.
     * @param columnName Column name.
     * @return Requested value column, cloned and converted to a double column.
     */
    public DoubleColumn makeNormalizedDoubleColumn(Table resultsTable, String columnName) {
        Column<?> currentColumn = findColumn(resultsTable, columnName);
        if (currentColumn == null) {
            throw new SensorResultNormalizeException(resultsTable,
                    "Missing '" + columnName + "' column, the sensor query must return this column");
        }

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
     * @param connectionTimeZone Expected time zone of the database.
     * @param timeSeriesGradient Time series gradient (for truncation).
     * @return A copy of a time_period column that is truncated, normalized to a datetime without the time zone.
     */
    public DateTimeColumn makeNormalizedTimePeriodColumn(Table resultsTable, ZoneId connectionTimeZone, TimeSeriesGradient timeSeriesGradient) {
        DateTimeColumn newTimestampColumn = DateTimeColumn.create(SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME, resultsTable.rowCount());
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime truncatedNow = timeSeriesGradient != null ?
                LocalDateTimeTruncateUtility.truncateTimePeriod(timeNow, timeSeriesGradient) : timeNow;

        Column<?> currentColumn = findColumn(resultsTable, SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME);
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
                    LocalDateTime truncatedDate =  timeSeriesGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTime, timeSeriesGradient) : localDateTime;
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
                    LocalDateTime truncatedDate = timeSeriesGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTime, timeSeriesGradient) : localDateTime;
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
                    LocalDateTime localDateTimeFromInstant = LocalDateTime.ofInstant(instant, connectionTimeZone);
                    LocalDateTime truncatedInstant = timeSeriesGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(localDateTimeFromInstant, timeSeriesGradient) : localDateTimeFromInstant;
                    newTimestampColumn.set(i, truncatedInstant);
                }
            }
            return newTimestampColumn;
        }

        // NOTE: we could parse the string, but it is better if the user casts timestamp columns that are strings to a datetime column in the sensor SQL query

        throw new SensorResultNormalizeException(resultsTable, SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME + " column must be a date, datetime or an instant (utc datetime).");
    }

    /**
     * Finds all data stream level columns and returns them as an array of columns. The array length is 9 elements (the number of data stream levels supported).
     * Data stream level columns are returned at their respective index, shifted one index down (because the array indexes start at 0).
     * The stream_level_1 column (if present) is returned at result[0] index.
     * All columns are also converted to a string column.
     * @param resultsTable Sensor results table to analyze.
     * @return Array of data stream level columns that were found.
     */
    public StringColumn[] extractAndNormalizeDataStreamLevelColumns(Table resultsTable) {
        StringColumn[] dataStreamLevelColumns = new StringColumn[9]; // we support 9 data stream levels, we store them at their respective indexes shifted 1 value down (0-based array)

        for (int levelIndex = 1; levelIndex <= 9; levelIndex++) {
            String dataStreamLevelColumnName = SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + levelIndex;
            Column<?> existingDataStreamLevelColumn = findColumn(resultsTable, dataStreamLevelColumnName);
            if (existingDataStreamLevelColumn == null) {
                continue; // no data stream level
            }

            if (existingDataStreamLevelColumn instanceof StringColumn) {
                StringColumn stringExistingStreamLevelCol = (StringColumn)existingDataStreamLevelColumn;
                dataStreamLevelColumns[levelIndex - 1] = stringExistingStreamLevelCol.copy();
                continue;
            }

            StringColumn stringifiedColumn = existingDataStreamLevelColumn.asStringColumn();
            dataStreamLevelColumns[levelIndex - 1] = stringifiedColumn;
        }

        return dataStreamLevelColumns;
    }

    /**
     * Calculates a data_stream_hash hash from all the data stream level columns. Returns 0 when there are no stream levels.
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowIndex Row index to calculate.
     * @return Data stream hash.
     */
    public long calculateDataStreamHashForRow(StringColumn[] dataStreamLevelColumns, int rowIndex) {
        int notNullColumnCount = 0;
        for (StringColumn dataStreamLevelColumn : dataStreamLevelColumns) {
            if (dataStreamLevelColumn != null) {
                notNullColumnCount++;
            }
        }

        if (notNullColumnCount == 0) {
            // when no data stream levels columns are used, we return data_stream_hash as 0
            return 0L;
        }

        List<HashCode> dataStreamColumnHashes = Arrays.stream(dataStreamLevelColumns)
                .map(column -> {
                    if (column == null) {
                        return HashCode.fromLong(0L);
                    }
                    String columnValue = column.get(rowIndex);
                    if (columnValue == null) {
                        return HashCode.fromLong(0L);
                    }
                    return  Hashing.farmHashFingerprint64().hashString(columnValue, StandardCharsets.UTF_8);
                })
                .collect(Collectors.toList());
        return Math.abs(Hashing.combineOrdered(dataStreamColumnHashes).asLong()); // we return only positive hashes which limits the hash space to 2^63, but positive hashes are easier for users
    }

    /**
     * Creates and calculates a data_stream_hash column from all stream_level_X columns (stream_level_1, stream_level_2, ..., stream_level_9).
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowCount Count of rows to process.
     * @return Data stream hash column.
     */
    public LongColumn createDataStreamHashColumn(StringColumn[] dataStreamLevelColumns, int rowCount) {
        LongColumn dataStreamHashColumn = LongColumn.create(SensorNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            long dimensionIdHash = calculateDataStreamHashForRow(dataStreamLevelColumns, i);
            dataStreamHashColumn.set(i, dimensionIdHash);
        }

        return dataStreamHashColumn;
    }

    /**
     * Calculates a data_stream_name name from all the data stream level columns. Returns 0 when there are no stream levels.
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowIndex Row index to calculate.
     * @return Data stream name.
     */
    public String calculateDataStreamNameForRow(StringColumn[] dataStreamLevelColumns, int rowIndex) {
        int notNullColumnCount = 0;
        int lastNotNullColumn = -1;
        for (int i = 0; i < dataStreamLevelColumns.length ; i++) {
            StringColumn dataStreamLevelColumn = dataStreamLevelColumns[i];
            if (dataStreamLevelColumn != null) {
                notNullColumnCount++;
                lastNotNullColumn = i;
            }
        }

        if (notNullColumnCount == 0) {
            // when no data stream columns are used, we return data_stream_name as "default"
            return "default";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= lastNotNullColumn; i++){
            if (i > 0) {
                sb.append(" / ");
            }

            StringColumn levelColumn = dataStreamLevelColumns[i];
            if (levelColumn == null) {
                continue;
            }

            String columnValue = levelColumn.get(rowIndex);
            if (!Strings.isNullOrEmpty(columnValue)) {
                sb.append(columnValue);
            }
        }

        return sb.toString();
    }

    /**
     * Creates and calculates a data_stream_name column from all stream_level_X columns (stream_level_1, stream_level_2, ..., stream_level_9).
     * The data stream name is in the form [stream_level_1] / [stream_level_2] / [stream_level_3] / ...
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowCount Count of rows to process.
     * @return Data stream name column.
     */
    public StringColumn createDataStreamNameColumn(StringColumn[] dataStreamLevelColumns, int rowCount) {
        StringColumn dataStreamNameColumn = StringColumn.create(SensorNormalizedResult.DATA_STREAM_NAME_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            String dataStreamName = calculateDataStreamNameForRow(dataStreamLevelColumns, i);
            dataStreamNameColumn.set(i, dataStreamName);
        }

        return dataStreamNameColumn;
    }

    /**
     * Creates and populates a time_series_uuid column that is a hash of the check hash and the data_stream_hash and uniquely identifies a time series.
     * @param sortedDataStreamHashColumn Column with data stream hashes for each row.
     * @param checkHash Check hash that should be hashed into the time_series_uuid.
     * @param tableHash Table hash.
     * @param columnHash Column hash (or 0 when the check is not on a column level).
     * @param rowCount Row count.
     * @return Time series uuid column, filled with values.
     */
    public StringColumn createTimeSeriesUuidColumn(LongColumn sortedDataStreamHashColumn,
                                                   long checkHash,
                                                   long tableHash,
                                                   long columnHash,
                                                   int rowCount) {
        StringColumn timeSeriesUuidColumn = StringColumn.create(SensorNormalizedResult.TIME_SERIES_UUID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataStreamHash = sortedDataStreamHashColumn.get(i);
            UUID uuid = new UUID(checkHash, dataStreamHash ^ tableHash ^ columnHash);
            String timeSeriesUuidString = uuid.toString();
            timeSeriesUuidColumn.set(i, timeSeriesUuidString);
        }

        return timeSeriesUuidColumn;
    }
}
