package ai.dqo.data.normalization;

import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import tech.tablesaw.api.*;

/**
 * Table normalization service that provides basic features used for normalization of sensor readout results or profiler results.
 */
public interface CommonTableNormalizationService {
    /**
     * The data stream name that covers the whole table, without dividing the table into named data streams.
     */
    String ALL_DATA_DATA_STREAM_NAME = "all data";

    /**
     * Finds all data stream level columns and returns them as an array of columns. The array length is 9 elements (the number of data stream levels supported).
     * Data stream level columns are returned at their respective index, shifted one index down (because the array indexes start at 0).
     * The stream_level_1 column (if present) is returned at result[0] index.
     * All columns are also converted to a string column.
     *
     * @param resultsTable Sensor results table to analyze.
     * @param dataStreamMappingSpec Data stream mapping configuration (optional) used to retrieve the tags if the data stream columns were not returned (due to an error in sensor execution).
     * @param rowCount Number of rows in the result table or 1 when the table is null.
     * @return Array of data stream level columns that were found.
     */
    StringColumn[] extractAndNormalizeDataStreamLevelColumns(Table resultsTable, DataStreamMappingSpec dataStreamMappingSpec, int rowCount);

    /**
     * Calculates a data_stream_hash hash from all the data stream level columns. Returns 0 when there are no stream levels.
     *
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowIndex               Row index to calculate.
     * @return Data stream hash.
     */
    long calculateDataStreamHashForRow(StringColumn[] dataStreamLevelColumns, int rowIndex);

    /**
     * Creates and calculates a data_stream_hash column from all stream_level_X columns (stream_level_1, stream_level_2, ..., stream_level_9).
     *
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowCount               Count of rows to process.
     * @return Data stream hash column.
     */
    LongColumn createDataStreamHashColumn(StringColumn[] dataStreamLevelColumns, int rowCount);

    /**
     * Calculates a data_stream_name name from all the data stream level columns. Returns 0 when there are no stream levels.
     *
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowIndex               Row index to calculate.
     * @return Data stream name.
     */
    String calculateDataStreamNameForRow(StringColumn[] dataStreamLevelColumns, int rowIndex);

    /**
     * Creates and calculates a data_stream_name column from all stream_level_X columns (stream_level_1, stream_level_2, ..., stream_level_9).
     * The data stream name is in the form [stream_level_1] / [stream_level_2] / [stream_level_3] / ...
     *
     * @param dataStreamLevelColumns Array of data stream level columns.
     * @param rowCount               Count of rows to process.
     * @return Data stream name column.
     */
    StringColumn createDataStreamNameColumn(StringColumn[] dataStreamLevelColumns, int rowCount);

    /**
     * Creates and populates a time_series_uuid column that is a hash of the check hash (or profiler hash) and the data_stream_hash and uniquely identifies a time series.
     *
     * @param sortedDataStreamHashColumn Column with data stream hashes for each row.
     * @param checkOrProfilerHash        Check hash (or a profiler hash) that should be hashed into the time_series_uuid.
     * @param tableHash                  Table hash.
     * @param columnHash                 Column hash (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return Time series uuid column, filled with values.
     */
    StringColumn createTimeSeriesUuidColumn(LongColumn sortedDataStreamHashColumn,
                                            long checkOrProfilerHash,
                                            long tableHash,
                                            long columnHash,
                                            int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes.
     *
     * @param sortedDataStreamHashColumn Data stream hashes column.
     * @param sortedTimePeriodColumn     Time period column.
     * @param checkHash                  Check hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    StringColumn createRowIdColumn(LongColumn sortedDataStreamHashColumn,
                                   DateTimeColumn sortedTimePeriodColumn,
                                   long checkHash,
                                   long tableHash,
                                   long columnHash,
                                   int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes.
     *
     * @param sortedDataStreamHashColumn Data stream hashes column.
     * @param sortedTimePeriodColumn     Time period column.
     * @param checkHash                  Check hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    StringColumn createRowIdColumn(LongColumn sortedDataStreamHashColumn,
                                   InstantColumn sortedTimePeriodColumn,
                                   long checkHash,
                                   long tableHash,
                                   long columnHash,
                                   int rowCount);
}
