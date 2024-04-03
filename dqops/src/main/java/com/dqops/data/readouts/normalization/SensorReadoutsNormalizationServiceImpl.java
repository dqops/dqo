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
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.tables.TableColumnUtility;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Doubles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Service that parses datasets with results returned by a sensor query.
 * Detects column types (data group level columns), describes the metadata of the result. Also fixes missing information, adds a data_group_hash column with a hash of all data grouping levels.
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
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    public SensorReadoutsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                           SensorExecutionRunParameters sensorRunParameters) {
        Table resultsTable = sensorExecutionResult.getResultTable();
        int resultsRowCount = resultsTable.rowCount();
        TimePeriodGradient timePeriodGradient = sensorRunParameters.getTimePeriodGradient();

        ZoneId defaultTimeZone = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        Table normalizedResults = Table.create("sensor_results_normalized");

        Column<?> actualValueColumn = TableColumnUtility.findColumn(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        if (actualValueColumn != null) {
            DoubleColumn normalizedActualValueColumn = actualValueColumn != null ?
                    makeNormalizedDoubleColumn(resultsTable, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME) :
                    DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, resultsRowCount);
            normalizedResults.addColumns(normalizedActualValueColumn);
        } else {
            normalizedResults.addColumns(DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, resultsRowCount));
        }

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
        TextColumn[] dataStreamLevelColumns = this.commonNormalizationService.extractAndNormalizeDataGroupingDimensionColumns(
                resultsTable, sensorRunParameters.getDataGroupings(), resultsRowCount);

        for (int streamLevelId = 0; streamLevelId < dataStreamLevelColumns.length; streamLevelId++) {
            if (dataStreamLevelColumns[streamLevelId] != null) {
                normalizedResults.addColumns(dataStreamLevelColumns[streamLevelId]);
            } else {
                String dataStreamLevelColumnName = SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + (streamLevelId + 1);
                normalizedResults.addColumns(TextColumn.create(dataStreamLevelColumnName, resultsRowCount));
            }
        }

        LongColumn dataStreamHashColumn = this.commonNormalizationService.createDataGroupingHashColumn(
                dataStreamLevelColumns, sensorRunParameters.getDataGroupings(), resultsRowCount);
        normalizedResults.addColumns(dataStreamHashColumn);

        TextColumn dataStreamNameColumn = this.commonNormalizationService.createDataGroupingNameColumn(dataStreamLevelColumns, resultsRowCount);
        normalizedResults.addColumns(dataStreamNameColumn);

        TextColumn dataStreamMappingNameColumn = TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getDataGroupings() != null) {
            dataStreamMappingNameColumn.setMissingTo(sensorRunParameters.getDataGroupings().getDataGroupingConfigurationName());
        } else {
            dataStreamMappingNameColumn.setMissingTo(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);
        }
        normalizedResults.addColumns(dataStreamMappingNameColumn);

        if (resultsTable.containsColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME)) {
            // optional column to import external check results, bypassing rule evaluation
            IntColumn normalizedSeverityValueColumn = makeNormalizedIntColumn(resultsTable, CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
            normalizedResults.addColumns(normalizedSeverityValueColumn);
        } else {
            normalizedResults.addColumns(IntColumn.create(CheckResultsColumnNames.SEVERITY_COLUMN_NAME, resultsRowCount));
        }

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
        if (sensorRunParameters.getCheckType() != null) {
            String checkType = sensorRunParameters.getCheckType().getDisplayName();
            checkTypeColumn.setMissingTo(checkType);
        }
        sortedNormalizedTable.addColumns(checkTypeColumn);

        TextColumn checkCategoryColumn = TextColumn.create(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME, resultsRowCount);
        String categoryName = sensorRunParameters.getCheck().getCategoryName();
        checkCategoryColumn.setMissingTo(categoryName);
        sortedNormalizedTable.addColumns(checkCategoryColumn);

        TextColumn tableComparisonNameColumn = TextColumn.create(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getTableComparisonConfiguration() != null) {
            String tableComparisonName = sensorRunParameters.getTableComparisonConfiguration().getComparisonName();
            tableComparisonNameColumn.setMissingTo(tableComparisonName);
        }
        sortedNormalizedTable.addColumns(tableComparisonNameColumn);

        TextColumn qualityDimensionColumn = TextColumn.create(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, resultsRowCount);
        String effectiveDataQualityDimension = sensorRunParameters.getCheck().getEffectiveDataQualityDimension();
        qualityDimensionColumn.setMissingTo(effectiveDataQualityDimension);
        sortedNormalizedTable.addColumns(qualityDimensionColumn);

        TextColumn sensorNameColumn = TextColumn.create(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME, resultsRowCount);
        if (sensorRunParameters.getEffectiveSensorRuleNames() != null) {
            String sensorDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
            sensorNameColumn.setMissingTo(sensorDefinitionName);
        }
        sortedNormalizedTable.addColumns(sensorNameColumn);

        LongColumn sortedDataStreamHashColumn = (LongColumn) sortedNormalizedTable.column(SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME);
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
        TextColumn idColumn = this.commonNormalizationService.createRowIdColumn(sortedDataStreamHashColumn, sortedTimePeriodColumn, checkHash, tableHash,
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
                if (!Strings.isNullOrEmpty(stringValue)) {
                    Double parsedDoubleValue = Doubles.tryParse(stringValue);
                    if (parsedDoubleValue != null) {
                        parsedValues.set(i, parsedDoubleValue);
                    } else {
                        HashCode hashCode = Hashing.farmHashFingerprint64().hashString(stringValue, StandardCharsets.UTF_8);
                        long hashFitInDoubleExponent = Math.abs(hashCode.asLong()) & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations
                        parsedValues.set(i, (double)hashFitInDoubleExponent);
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
                Double parsedDoubleValue = Doubles.tryParse(stringValue);
                if (parsedDoubleValue != null) {
                    parsedValues.set(i, parsedDoubleValue);
                } else {
                    HashCode hashCode = Hashing.farmHashFingerprint64().hashString(stringValue, StandardCharsets.UTF_8);
                    long hashFitInDoubleExponent = Math.abs(hashCode.asLong()) & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations
                    parsedValues.set(i, (double)hashFitInDoubleExponent);
                }
            }

            return parsedValues;
        }

        throw new SensorResultNormalizeException(resultsTable, "Cannot detect an " + columnName + " column data type, it should be a double column");
    }

    /**
     * Extracts a given column name from the resultsTable and returns a cloned IntColumn. Converts wrong data types.
     * @param resultsTable Result table to extract.
     * @param columnName Column name.
     * @return Requested value column, cloned and converted to an inteer column.
     */
    public IntColumn makeNormalizedIntColumn(Table resultsTable, String columnName) {
        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, columnName);

        if (currentColumn instanceof IntColumn) {
            return ((IntColumn)currentColumn).copy();
        }

        if (currentColumn instanceof DoubleColumn) {
            return ((DoubleColumn)currentColumn).asIntColumn();
        }

        if (currentColumn instanceof LongColumn) {
            return ((LongColumn)currentColumn).asIntColumn();
        }

        if (currentColumn instanceof FloatColumn) {
            return ((FloatColumn)currentColumn).asIntColumn();
        }

        if (currentColumn instanceof ShortColumn) {
            return ((ShortColumn)currentColumn).asIntColumn();
        }

        if (currentColumn instanceof BooleanColumn) {
            return ((BooleanColumn)currentColumn).asDoubleColumn().asIntColumn();
        }

        if (currentColumn instanceof DateColumn) {
            return ((DateColumn)currentColumn).asDoubleColumn().asIntColumn();
        }

        if (currentColumn instanceof DateTimeColumn) {
            return ((DateTimeColumn)currentColumn).asDoubleColumn().asIntColumn();
        }

        if (currentColumn instanceof InstantColumn) {
            return ((InstantColumn)currentColumn).asDoubleColumn().asIntColumn();
        }

        if (currentColumn instanceof TimeColumn) {
            return ((TimeColumn)currentColumn).asDoubleColumn().asIntColumn();
        }

        if (currentColumn instanceof StringColumn) {
            StringColumn stringColumn = (StringColumn) currentColumn;
            IntColumn parsedValues = IntColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                if (!Strings.isNullOrEmpty(stringValue)) {
                    Double parsedDoubleValue = Doubles.tryParse(stringValue);
                    if (parsedDoubleValue != null) {
                        parsedValues.set(i, parsedDoubleValue.intValue());
                    } else {
                        HashCode hashCode = Hashing.farmHashFingerprint64().hashString(stringValue, StandardCharsets.UTF_8);
                        long hashFitInDoubleExponent = Math.abs(hashCode.asLong()) & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations
                        parsedValues.set(i, (int)hashFitInDoubleExponent);
                    }
                }
            }

            return parsedValues;
        }

        if (currentColumn instanceof TextColumn) {
            TextColumn stringColumn = (TextColumn) currentColumn;
            IntColumn parsedValues = IntColumn.create(columnName, resultsTable.rowCount());
            for (int i = 0; i < stringColumn.size(); i++) {
                String stringValue = stringColumn.get(i);
                Double parsedDoubleValue = Doubles.tryParse(stringValue);
                if (parsedDoubleValue != null) {
                    parsedValues.set(i, parsedDoubleValue.intValue());
                } else {
                    HashCode hashCode = Hashing.farmHashFingerprint64().hashString(stringValue, StandardCharsets.UTF_8);
                    long hashFitInDoubleExponent = Math.abs(hashCode.asLong()) & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations
                    parsedValues.set(i, (int)hashFitInDoubleExponent);
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
     * @param defaultTimeZone Default DQOps time zone, used to create a time period value if the time period was not received from a sensor result.
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

        if (currentColumn instanceof StringColumn) {
            StringColumn stringColumn = (StringColumn)currentColumn;

            for (int i = 0; i < stringColumn.size(); i++) {
                LocalDateTime parsedDateTime = parseStringToDateTime(stringColumn.get(i));

                if (parsedDateTime == null) {
                    // we need a timestamp, we will use a truncated now instead
                    newTimestampColumn.set(i, truncatedNow);
                }
                else {
                    LocalDateTime truncatedInstant = timePeriodGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(parsedDateTime, timePeriodGradient) : parsedDateTime;
                    newTimestampColumn.set(i, truncatedInstant);
                }
            }
            return newTimestampColumn;
        }

        if (currentColumn instanceof TextColumn) {
            TextColumn stringColumn = (TextColumn)currentColumn;

            for (int i = 0; i < stringColumn.size(); i++) {
                LocalDateTime parsedDateTime = parseStringToDateTime(stringColumn.get(i));

                if (parsedDateTime == null) {
                    // we need a timestamp, we will use a truncated now instead
                    newTimestampColumn.set(i, truncatedNow);
                }
                else {
                    LocalDateTime truncatedInstant = timePeriodGradient != null ?
                            LocalDateTimeTruncateUtility.truncateTimePeriod(parsedDateTime, timePeriodGradient) : parsedDateTime;
                    newTimestampColumn.set(i, truncatedInstant);
                }
            }
            return newTimestampColumn;
        }

        // NOTE: we could parse the string, but it is better if the user casts timestamp columns that are strings to a datetime column in the sensor SQL query

        throw new SensorResultNormalizeException(resultsTable, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME + " column must be a date, datetime or an instant (utc datetime).");
    }

    /**
     * Parses an ISO datetime or date to a local date.
     * @param text Text with an ISO date time or date.
     * @return Null or a correct date.
     */
    protected LocalDateTime parseStringToDateTime(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return null;
        }

        try {
            if (text.indexOf(' ') > 0) {
                text = text.replace(' ', 'T');
            }

            LocalDateTime dateTime = LocalDateTime.parse(text);
            return dateTime;
        }
        catch (DateTimeParseException pex) {
            try {
                LocalDate date = LocalDate.parse(text);
                return date.atStartOfDay();
            }
            catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Makes a normalized time_period_utc column. Converts non Instant (UTC date) to UTC date. Tries to convert the time period column.
     * @param resultsTable Result table to find the column.
     * @param timePeriodColumn Time period column used to convert the dates.
     * @param defaultTimeZone Default DQOps time zone.
     * @return Instant column with the time_period_utc time periods.
     */
    public InstantColumn makeNormalizedTimePeriodUtcColumn(Table resultsTable, DateTimeColumn timePeriodColumn, ZoneId defaultTimeZone) {
        Column<?> currentColumn = TableColumnUtility.findColumn(resultsTable, SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
        if (currentColumn == null || !(currentColumn instanceof InstantColumn)) {
            InstantColumn newTimePeriodUtcColumn = InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, resultsTable.rowCount());

            // missing time_period_utc column or invalid type, we will convert it from the time_period, using the default DQOps server time zone
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
