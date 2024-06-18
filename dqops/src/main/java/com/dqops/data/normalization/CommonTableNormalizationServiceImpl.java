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
package com.dqops.data.normalization;

import com.dqops.metadata.groupings.DataGroupingDimensionSource;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.utils.tables.TableColumnUtility;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Table normalization service that provides basic features used for normalization of sensor readout results or profiler results.
 */
@Component
public class CommonTableNormalizationServiceImpl implements CommonTableNormalizationService {
    /**
     * Finds all data grouping dimension level columns and returns them as an array of columns. The array length is 9 elements (the number of data grouping dimension levels supported).
     * Data grouping dimension level columns are returned at their respective index, shifted one index down (because the array indexes start at 0).
     * The grouping_level_1 column (if present) is returned at result[0] index.
     * All columns are also converted to a string column.
     * @param resultsTable Sensor results table to analyze.
     * @param dataGroupingConfigurationSpec Data grouping configuration (optional) used to retrieve the tags if the data stream columns were not returned (due to an error in sensor execution).
     * @param rowCount Number of rows in the result table or 1 when the table is null.
     * @return Array of data grouping dimension level columns that were found.
     */
    @Override
    public TextColumn[] extractAndNormalizeDataGroupingDimensionColumns(Table resultsTable,
                                                                        DataGroupingConfigurationSpec dataGroupingConfigurationSpec,
                                                                        int rowCount) {
        TextColumn[] dataGroupingLevelColumns = new TextColumn[9]; // we support 9 data stream levels, we store them at their respective indexes shifted 1 value down (0-based array)

        for (int levelIndex = 1; levelIndex <= 9; levelIndex++) {
            String dataGroupingLevelColumnName = CommonColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + levelIndex;
            Column<?> existingDataGroupingLevelColumn = resultsTable != null ? TableColumnUtility.findColumn(resultsTable, dataGroupingLevelColumnName) : null;
            if (existingDataGroupingLevelColumn == null) {
                if (dataGroupingConfigurationSpec != null && dataGroupingConfigurationSpec.getLevel(levelIndex) != null) {
                    DataGroupingDimensionSpec dataGroupingDimensionSpec = dataGroupingConfigurationSpec.getLevel(levelIndex);
                    if (dataGroupingDimensionSpec.getSource() == DataGroupingDimensionSource.tag && !Strings.isNullOrEmpty(dataGroupingDimensionSpec.getTag())) {
                        TextColumn tagColumn = TextColumn.create(dataGroupingLevelColumnName, rowCount);
                        tagColumn.setMissingTo(dataGroupingDimensionSpec.getTag());
                        dataGroupingLevelColumns[levelIndex - 1] = tagColumn;
                    }
                }
                continue;
            }

            if (existingDataGroupingLevelColumn instanceof TextColumn) {
                TextColumn stringExistingGroupingLevelCol = (TextColumn)existingDataGroupingLevelColumn;
                dataGroupingLevelColumns[levelIndex - 1] = stringExistingGroupingLevelCol.copy();
                continue;
            }

            @SuppressWarnings("SpellCheckingInspection")
            TextColumn stringifiedColumn = TableColumnUtility.convertToTextColumn(existingDataGroupingLevelColumn);
            dataGroupingLevelColumns[levelIndex - 1] = stringifiedColumn;
        }

        return dataGroupingLevelColumns;
    }

    /**
     * Calculates a data_grouping_hash hash from all the data grouping dimension level columns. Returns 0 when there are no grouping dimension levels.
     * @param dataGroupingLevelColumns Array of data grouping dimension level columns.
     * @param dataGroupingConfigurationSpec Data grouping configuration used to decide which data grouping dimension levels are configured and will be included in the data group hash.
     * @param rowIndex Row index to calculate.
     * @return Data grouping hash.
     */
    @Override
    public long calculateDataGroupingHashForRow(TextColumn[] dataGroupingLevelColumns,
                                                DataGroupingConfigurationSpec dataGroupingConfigurationSpec,
                                                int rowIndex) {
        if (dataGroupingConfigurationSpec == null || dataGroupingConfigurationSpec.countConfiguredLevels() == 0) {
            return 0L;
        }

        List<HashCode> hashCodes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            DataGroupingDimensionSpec dimensionLevelSpec = dataGroupingConfigurationSpec.getLevel(i + 1);
            if (dimensionLevelSpec == null) {
                continue;
            }

            if (dimensionLevelSpec.getSource() == DataGroupingDimensionSource.tag) {
                String tag = dimensionLevelSpec.getTag();
                if (tag != null) {
                    HashCode stringHashCode = Hashing.farmHashFingerprint64().hashString(tag, StandardCharsets.UTF_8);
                    hashCodes.add(HashCode.fromLong(stringHashCode.asLong() + i));
                } else if (i != 0) {
                    hashCodes.add(HashCode.fromLong(i));
                }

                continue;
            }

            TextColumn dataGroupingLevelColumn = dataGroupingLevelColumns[i];
            if (dataGroupingLevelColumn == null) {
                continue;
            }

            String columnValue = dataGroupingLevelColumn.get(rowIndex);
            if (!Strings.isNullOrEmpty(columnValue)) {
                HashCode stringHashCode = Hashing.farmHashFingerprint64().hashString(columnValue, StandardCharsets.UTF_8);
                hashCodes.add(HashCode.fromLong(stringHashCode.asLong() + i));
            } else if (i != 0) {
                hashCodes.add(HashCode.fromLong(i));
            }
        }

        if (hashCodes.size() == 0) {
            return 0L;
        }

        return Math.abs(Hashing.combineOrdered(hashCodes).asLong()); // we return only positive hashes which limits the hash space to 2^63, but positive hashes are easier to use (more user friendly)
    }

    /**
     * Creates and calculates a data_group_hash column from all grouping_level_X columns (grouping_level_1, grouping_level_2, ..., grouping_level_9).
     * @param dataGroupingLevelColumns Array of data stream level columns.
     * @param dataGroupingConfigurationSpec Data grouping configuration used to decide which data grouping dimension levels are configured and will be included in the data group hash.
     * @param rowCount Count of rows to process.
     * @return Data stream hash column.
     */
    @Override
    public LongColumn createDataGroupingHashColumn(TextColumn[] dataGroupingLevelColumns,
                                                   DataGroupingConfigurationSpec dataGroupingConfigurationSpec,
                                                   int rowCount) {
        LongColumn dataGroupingHashColumn = LongColumn.create(CommonColumnNames.DATA_GROUP_HASH_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            long dimensionIdHash = calculateDataGroupingHashForRow(dataGroupingLevelColumns, dataGroupingConfigurationSpec, i);
            dataGroupingHashColumn.set(i, dimensionIdHash);
        }

        return dataGroupingHashColumn;
    }

    /**
     * Calculates a data_stream_name name from all the data stream level columns. Returns 0 when there are no stream levels.
     * @param dataGroupingLevelColumns Array of data stream level columns.
     * @param rowIndex Row index to calculate.
     * @return Data stream name.
     */
    @Override
    public String calculateDataGroupingNameForRow(TextColumn[] dataGroupingLevelColumns, int rowIndex) {
        int notNullColumnCount = 0;
        int lastNotNullColumn = -1;
        for (int i = 0; i < dataGroupingLevelColumns.length ; i++) {
            TextColumn dataStreamLevelColumn = dataGroupingLevelColumns[i];
            if (dataStreamLevelColumn != null) {
                notNullColumnCount++;
                lastNotNullColumn = i;
            }
        }

        if (notNullColumnCount == 0) {
            // when no data stream columns are used, we return data_stream_name as "all data"
            return NO_GROUPING_DATA_GROUP_NAME;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= lastNotNullColumn; i++){
            if (i > 0) {
                sb.append(" / ");
            }

            TextColumn levelColumn = dataGroupingLevelColumns[i];
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
     * Creates and calculates a data_grouping_name column from all grouping_level_X columns (grouping_level_1, grouping_level_2, ..., grouping_level_9).
     * The data grouping name is in the form [grouping_level_1] / [grouping_level_2] / [grouping_level_3] / ...
     * @param dataGroupingLevelColumns Array of data grouping level columns.
     * @param rowCount Count of rows to process.
     * @return Data grouping name column.
     */
    @Override
    public TextColumn createDataGroupingNameColumn(TextColumn[] dataGroupingLevelColumns, int rowCount) {
        TextColumn dataGroupingNameColumn = TextColumn.create(CommonColumnNames.DATA_GROUP_NAME_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            String dataGroupingName = calculateDataGroupingNameForRow(dataGroupingLevelColumns, i);
            dataGroupingNameColumn.set(i, dataGroupingName);
        }

        return dataGroupingNameColumn;
    }

    /**
     * Creates and populates a time_series_uuid column that is a hash of the check hash (or profiler hash) and the data_grouping_hash and uniquely identifies a time series.
     * @param sortedDataGroupingHashColumn Column with data grouping hashes for each row.
     * @param checkOrProfilerHash Check hash (or a profiler hash) that should be hashed into the time_series_uuid.
     * @param tableHash Table hash.
     * @param columnHash Column hash (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return Time series uuid column, filled with values.
     */
    @Override
    public TextColumn createTimeSeriesUuidColumn(LongColumn sortedDataGroupingHashColumn,
                                                 long checkOrProfilerHash,
                                                 long tableHash,
                                                 long columnHash,
                                                 int rowCount) {
        TextColumn timeSeriesUuidColumn = TextColumn.create(CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataGroupingHash = sortedDataGroupingHashColumn.get(i);
            UUID uuid = new UUID(checkOrProfilerHash, dataGroupingHash ^ tableHash ^ columnHash);
            String timeSeriesUuidString = uuid.toString();
            timeSeriesUuidColumn.set(i, timeSeriesUuidString);
        }

        return timeSeriesUuidColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes.
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param sortedTimePeriodColumn Time period column.
     * @param checkHash Check hash value.
     * @param tableHash Table hash value.
     * @param columnHash Column hash value (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return ID column, filled with values.
     */
    @Override
    public TextColumn createStatisticsRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                                  DateTimeColumn sortedTimePeriodColumn,
                                                  long checkHash,
                                                  long tableHash,
                                                  long columnHash,
                                                  int rowCount) {
        TextColumn idColumn = TextColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataGroupingHash = sortedDataGroupingHashColumn.get(i);
            long timePeriodLong = sortedTimePeriodColumn.getLongInternal(i);
            long timePeriodHashed = Hashing.farmHashFingerprint64().hashLong(timePeriodLong).asLong();
            UUID uuid = new UUID(checkHash ^ timePeriodHashed, dataGroupingHash ^ tableHash ^ columnHash ^ ~timePeriodHashed);
            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes for the error table.
     *
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param errorMessageColumn           Error message column.
     * @param checkHash                    Check hash value.
     * @param tableHash                    Table hash value.
     * @param columnHash                   Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                     Row count.
     * @return ID column, filled with values.
     */
    @Override
    public TextColumn createErrorRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                             TextColumn errorMessageColumn,
                                             long checkHash,
                                             long tableHash,
                                             long columnHash,
                                             int rowCount) {
        TextColumn idColumn = TextColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataGroupingHash = sortedDataGroupingHashColumn.get(i);
            String errorMessage = errorMessageColumn.getString(i);
            long errorMessageHash = errorMessage != null ? Hashing.farmHashFingerprint64().hashString(errorMessage, StandardCharsets.UTF_8).asLong() : 0L;
            UUID uuid = new UUID(checkHash ^ errorMessageHash, dataGroupingHash ^ tableHash ^ columnHash ^ ~errorMessageHash);
            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes. Also when a hash was already seen, assigns a new index to it
     * and updates the <code>sampleIndexColumn</code> with the next available index.
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param sortedTimePeriodColumn Time period column.
     * @param sampleIndexColumn Optional sample index column.
     * @param collectorHash Collector hash value.
     * @param tableHash Table hash value.
     * @param columnHash Column hash value (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return ID column, filled with values.
     */
    @Override
    public TextColumn createStatisticsRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                                  InstantColumn sortedTimePeriodColumn,
                                                  IntColumn sampleIndexColumn,
                                                  long collectorHash,
                                                  long tableHash,
                                                  long columnHash,
                                                  int rowCount) {
        TextColumn idColumn = TextColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);
        Map<UUID, Integer> idsGenerated = new LinkedHashMap<>();

        for (int i = 0; i < rowCount ; i++) {
            Long dataGroupingHash = sortedDataGroupingHashColumn.get(i);
            long timePeriodLong = sortedTimePeriodColumn.getLongInternal(i);
            long timePeriodHashed = Hashing.farmHashFingerprint64().hashLong(timePeriodLong).asLong();
            long sampleIndexHashed = sampleIndexColumn == null || sampleIndexColumn.isMissing(i) ? 0L :
                    Hashing.farmHashFingerprint64().hashInt(sampleIndexColumn.get(i) + 1).asLong();
            UUID uuid = new UUID(collectorHash ^ timePeriodHashed ^ sampleIndexHashed,
                    dataGroupingHash ^ tableHash ^ columnHash ^ ~timePeriodHashed ^ ~sampleIndexHashed);
            Integer lastSampleIndexForUuid = idsGenerated.get(uuid);
            if (lastSampleIndexForUuid == null) {
                idsGenerated.put(uuid, 0);
            } else {
                lastSampleIndexForUuid = lastSampleIndexForUuid + 1;
                idsGenerated.put(uuid, lastSampleIndexForUuid);
                sampleIndexColumn.set(i, lastSampleIndexForUuid);
                long newSampleIndexHashed = Hashing.farmHashFingerprint64().hashInt(lastSampleIndexForUuid + 1).asLong();
                uuid = new UUID(collectorHash ^ timePeriodHashed ^ newSampleIndexHashed,
                        dataGroupingHash ^ tableHash ^ columnHash ^ ~timePeriodHashed ^ ~newSampleIndexHashed);
            }

            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes. Also when a hash was already seen, assigns a new index to it
     * and updates the <code>sampleIndexColumn</code> with the next available index.
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param collectedAtColumn Time period column.
     * @param sampleIndexColumn Optional sample index column.
     * @param collectorHash Collector hash value.
     * @param tableHash Table hash value.
     * @param columnHash Column hash value (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return ID column, filled with values.
     */
    @Override
    public TextColumn createErrorSampleRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                                   DateTimeColumn collectedAtColumn,
                                                   IntColumn sampleIndexColumn,
                                                   long collectorHash,
                                                   long tableHash,
                                                   long columnHash,
                                                   int rowCount) {
        TextColumn idColumn = TextColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);
        Map<UUID, Integer> idsGenerated = new LinkedHashMap<>();

        for (int i = 0; i < rowCount ; i++) {
            Long dataGroupingHash = sortedDataGroupingHashColumn.get(i);
            long timePeriodLong = collectedAtColumn.getLongInternal(i);
            long timePeriodHashed = Hashing.farmHashFingerprint64().hashLong(timePeriodLong).asLong();
            long sampleIndexHashed = sampleIndexColumn == null || sampleIndexColumn.isMissing(i) ? 0L :
                    Hashing.farmHashFingerprint64().hashInt(sampleIndexColumn.get(i) + 1).asLong();
            UUID uuid = new UUID(collectorHash ^ timePeriodHashed ^ sampleIndexHashed,
                    dataGroupingHash ^ tableHash ^ columnHash ^ ~timePeriodHashed ^ ~sampleIndexHashed);
            Integer lastSampleIndexForUuid = idsGenerated.get(uuid);
            if (lastSampleIndexForUuid == null) {
                idsGenerated.put(uuid, 0);
            } else {
                lastSampleIndexForUuid = lastSampleIndexForUuid + 1;
                idsGenerated.put(uuid, lastSampleIndexForUuid);
                sampleIndexColumn.set(i, lastSampleIndexForUuid);
                long newSampleIndexHashed = Hashing.farmHashFingerprint64().hashInt(lastSampleIndexForUuid + 1).asLong();
                uuid = new UUID(collectorHash ^ timePeriodHashed ^ newSampleIndexHashed,
                        dataGroupingHash ^ tableHash ^ columnHash ^ ~timePeriodHashed ^ ~newSampleIndexHashed);
            }

            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }
}
