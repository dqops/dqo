package ai.dqo.data.normalization;

import ai.dqo.metadata.groupings.DataStreamLevelSource;
import ai.dqo.metadata.groupings.DataStreamLevelSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Table normalization service that provides basic features used for normalization of sensor readout results or profiler results.
 */
@Component
public class CommonTableNormalizationServiceImpl implements CommonTableNormalizationService {
    /**
     * Finds a named column in the table. Performs a case-insensitive search, so the columns may be named in upper or lower case.
     * @param resultsTable Table to analyze.
     * @param columnName Expected column name.
     * @return Column that was found or null.
     */
    @Override
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
     * Finds all data stream level columns and returns them as an array of columns. The array length is 9 elements (the number of data stream levels supported).
     * Data stream level columns are returned at their respective index, shifted one index down (because the array indexes start at 0).
     * The stream_level_1 column (if present) is returned at result[0] index.
     * All columns are also converted to a string column.
     * @param resultsTable Sensor results table to analyze.
     * @param dataStreamMappingSpec Data stream mapping configuration (optional) used to retrieve the tags if the data stream columns were not returned (due to an error in sensor execution).
     * @param rowCount Number of rows in the result table or 1 when the table is null.
     * @return Array of data stream level columns that were found.
     */
    @Override
    public StringColumn[] extractAndNormalizeDataStreamLevelColumns(Table resultsTable, DataStreamMappingSpec dataStreamMappingSpec, int rowCount) {
        StringColumn[] dataStreamLevelColumns = new StringColumn[9]; // we support 9 data stream levels, we store them at their respective indexes shifted 1 value down (0-based array)

        for (int levelIndex = 1; levelIndex <= 9; levelIndex++) {
            String dataStreamLevelColumnName = CommonColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + levelIndex;
            Column<?> existingDataStreamLevelColumn = resultsTable != null ? findColumn(resultsTable, dataStreamLevelColumnName) : null;
            if (existingDataStreamLevelColumn == null) {
                continue; // no data stream level
            }
            else {
                if (dataStreamMappingSpec != null && dataStreamMappingSpec.getLevel(levelIndex) != null) {
                    DataStreamLevelSpec dataStreamLevelSpec = dataStreamMappingSpec.getLevel(levelIndex);
                    if (dataStreamLevelSpec.getSource() == DataStreamLevelSource.tag && !Strings.isNullOrEmpty(dataStreamLevelSpec.getTag())) {
                        StringColumn tagColumn = StringColumn.create(dataStreamLevelColumnName, rowCount);
                        tagColumn.setMissingTo(dataStreamLevelSpec.getTag());
                        dataStreamLevelColumns[levelIndex - 1] = tagColumn;
                    }
                }
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
    @Override
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
    @Override
    public LongColumn createDataStreamHashColumn(StringColumn[] dataStreamLevelColumns, int rowCount) {
        LongColumn dataStreamHashColumn = LongColumn.create(CommonColumnNames.DATA_STREAM_HASH_COLUMN_NAME, rowCount);

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
    @Override
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
            // when no data stream columns are used, we return data_stream_name as "all data"
            return ALL_DATA_DATA_STREAM_NAME;
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
    @Override
    public StringColumn createDataStreamNameColumn(StringColumn[] dataStreamLevelColumns, int rowCount) {
        StringColumn dataStreamNameColumn = StringColumn.create(CommonColumnNames.DATA_STREAM_NAME_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            String dataStreamName = calculateDataStreamNameForRow(dataStreamLevelColumns, i);
            dataStreamNameColumn.set(i, dataStreamName);
        }

        return dataStreamNameColumn;
    }

    /**
     * Creates and populates a time_series_uuid column that is a hash of the check hash (or profiler hash) and the data_stream_hash and uniquely identifies a time series.
     * @param sortedDataStreamHashColumn Column with data stream hashes for each row.
     * @param checkOrProfilerHash Check hash (or a profiler hash) that should be hashed into the time_series_uuid.
     * @param tableHash Table hash.
     * @param columnHash Column hash (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return Time series uuid column, filled with values.
     */
    @Override
    public StringColumn createTimeSeriesUuidColumn(LongColumn sortedDataStreamHashColumn,
                                                   long checkOrProfilerHash,
                                                   long tableHash,
                                                   long columnHash,
                                                   int rowCount) {
        StringColumn timeSeriesUuidColumn = StringColumn.create(CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataStreamHash = sortedDataStreamHashColumn.get(i);
            UUID uuid = new UUID(checkOrProfilerHash, dataStreamHash ^ tableHash ^ columnHash);
            String timeSeriesUuidString = uuid.toString();
            timeSeriesUuidColumn.set(i, timeSeriesUuidString);
        }

        return timeSeriesUuidColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes.
     * @param sortedDataStreamHashColumn Data stream hashes column.
     * @param sortedTimePeriodColumn Time period column.
     * @param checkHash Check hash value.
     * @param tableHash Table hash value.
     * @param columnHash Column hash value (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return ID column, filled with values.
     */
    @Override
    public StringColumn createRowIdColumn(LongColumn sortedDataStreamHashColumn,
                                          DateTimeColumn sortedTimePeriodColumn,
                                          long checkHash,
                                          long tableHash,
                                          long columnHash,
                                          int rowCount) {
        StringColumn idColumn = StringColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataStreamHash = sortedDataStreamHashColumn.get(i);
            long timePeriodLong = sortedTimePeriodColumn.getLongInternal(i);
            long timePeriodHashed = Hashing.sipHash24().hashLong(timePeriodLong).asLong();
            UUID uuid = new UUID(checkHash ^ timePeriodHashed, dataStreamHash ^ tableHash ^ columnHash ^ ~timePeriodHashed);
            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }

    /**
     * Creates and fills the "id" column by combining hashes.
     * @param sortedDataStreamHashColumn Data stream hashes column.
     * @param sortedTimePeriodColumn Time period column.
     * @param checkHash Check hash value.
     * @param tableHash Table hash value.
     * @param columnHash Column hash value (or 0L when the check is not on a column level).
     * @param rowCount Row count.
     * @return ID column, filled with values.
     */
    @Override
    public StringColumn createRowIdColumn(LongColumn sortedDataStreamHashColumn,
                                          InstantColumn sortedTimePeriodColumn,
                                          long checkHash,
                                          long tableHash,
                                          long columnHash,
                                          int rowCount) {
        StringColumn idColumn = StringColumn.create(CommonColumnNames.ID_COLUMN_NAME, rowCount);

        for (int i = 0; i < rowCount ; i++) {
            Long dataStreamHash = sortedDataStreamHashColumn.get(i);
            long timePeriodLong = sortedTimePeriodColumn.getLongInternal(i);
            long timePeriodHashed = Hashing.sipHash24().hashLong(timePeriodLong).asLong();
            UUID uuid = new UUID(checkHash ^ timePeriodHashed, dataStreamHash ^ tableHash ^ columnHash ^ ~timePeriodHashed);
            String idString = uuid.toString();
            idColumn.set(i, idString);
        }

        return idColumn;
    }
}
