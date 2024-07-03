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

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import tech.tablesaw.api.*;

/**
 * Table normalization service that provides basic features used for normalization of sensor readout results or profiler results.
 */
public interface CommonTableNormalizationService {
    /**
     * The data group name that covers the whole table, without dividing the table into data groups.
     */
    String NO_GROUPING_DATA_GROUP_NAME = "no grouping";

    /**
     * Finds all data grouping dimension level columns and returns them as an array of columns. The array length is 9 elements (the number of data stream levels supported).
     * Data grouping dimension level columns are returned at their respective index, shifted one index down (because the array indexes start at 0).
     * The grouping_level_1 column (if present) is returned at result[0] index.
     * All columns are also converted to a string column.
     *
     * @param resultsTable Sensor results table to analyze.
     * @param dataGroupingConfigurationSpec Data grouping  configuration (optional) used to retrieve the tags if the data stream columns were not returned (due to an error in sensor execution).
     * @param rowCount Number of rows in the result table or 1 when the table is null.
     * @return Array of data grouping dimension level columns that were found.
     */
    TextColumn[] extractAndNormalizeDataGroupingDimensionColumns(Table resultsTable, DataGroupingConfigurationSpec dataGroupingConfigurationSpec, int rowCount);

    /**
     * Calculates a data_grouping_hash hash from all the data stream level columns. Returns 0 when there are no grouping dimension levels.
     *
     * @param dataGroupingLevelColumns Array of data grouping dimension level columns.
     * @param dataGroupingConfigurationSpec Data grouping configuration used to decide which data grouping dimension levels are configured and will be included in the data group hash.
     * @param rowIndex               Row index to calculate.
     * @return Data grouping hash.
     */
    long calculateDataGroupingHashForRow(TextColumn[] dataGroupingLevelColumns, DataGroupingConfigurationSpec dataGroupingConfigurationSpec, int rowIndex);

    /**
     * Creates and calculates a data_grouping_hash column from all grouping_level_X columns (grouping_level_1, grouping_level_2, ..., grouping_level_9).
     *
     * @param dataGroupingLevelColumns Array of data grouping dimension level columns.
     * @param dataGroupingConfigurationSpec Data grouping configuration used to decide which data grouping dimension levels are configured and will be included in the data group hash.
     * @param rowCount               Count of rows to process.
     * @return Data grouping hash column.
     */
    LongColumn createDataGroupingHashColumn(TextColumn[] dataGroupingLevelColumns, DataGroupingConfigurationSpec dataGroupingConfigurationSpec, int rowCount);

    /**
     * Calculates a data_grouping_name name from all the data grouping level columns. Returns 0 when there are no data grouping levels.
     *
     * @param dataGroupingLevelColumns Array of data grouping level columns.
     * @param rowIndex               Row index to calculate.
     * @return Data grouping name.
     */
    String calculateDataGroupingNameForRow(TextColumn[] dataGroupingLevelColumns, int rowIndex);

    /**
     * Creates and calculates a data_grouping_name column from all grouping_level_X columns (grouping_level_1, grouping_level_2, ..., grouping_level_9).
     * The data grouping name is in the form [grouping_level_1] / [grouping_level_2] / [grouping_level_3] / ...
     *
     * @param dataGroupingLevelColumns Array of data grouping dimension level columns.
     * @param rowCount               Count of rows to process.
     * @return Data grouping name column.
     */
    TextColumn createDataGroupingNameColumn(TextColumn[] dataGroupingLevelColumns, int rowCount);

    /**
     * Creates and populates a time_series_uuid column that is a hash of the check hash (or profiler hash) and the data_group_hash and uniquely identifies a time series.
     *
     * @param sortedDataGroupingHashColumn Column with data grouping hashes for each row.
     * @param checkOrProfilerHash        Check hash (or a profiler hash) that should be hashed into the time_series_uuid.
     * @param tableHash                  Table hash.
     * @param columnHash                 Column hash (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return Time series uuid column, filled with values.
     */
    TextColumn createTimeSeriesUuidColumn(LongColumn sortedDataGroupingHashColumn,
                                          long checkOrProfilerHash,
                                          long tableHash,
                                          long columnHash,
                                          int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes.
     *
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param sortedTimePeriodColumn     Time period column.
     * @param checkHash                  Check hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    TextColumn createStatisticsRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                           DateTimeColumn sortedTimePeriodColumn,
                                           long checkHash,
                                           long tableHash,
                                           long columnHash,
                                           int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes for the error table.
     *
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param errorMessageColumn         Error message column.
     * @param checkHash                  Check hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    TextColumn createErrorRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                      TextColumn errorMessageColumn,
                                      long checkHash,
                                      long tableHash,
                                      long columnHash,
                                      int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes.
     * Also when a hash was already seen, assigns a new index to it
     * and updates the <code>sampleIndexColumn</code> with the next available index.
     *
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param sortedTimePeriodColumn     Time period column.
     * @param sampleIndexColumn          Optional sample index column.
     * @param collectorHash              Collector hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    TextColumn createStatisticsRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                           InstantColumn sortedTimePeriodColumn,
                                           IntColumn sampleIndexColumn,
                                           long collectorHash,
                                           long tableHash,
                                           long columnHash,
                                           int rowCount);

    /**
     * Creates and fills the "id" column by combining hashes.
     * Also when a hash was already seen, assigns a new index to it
     * and updates the <code>sampleIndexColumn</code> with the next available index.
     *
     * @param sortedDataGroupingHashColumn Data grouping hashes column.
     * @param sortedTimePeriodColumn     Time period column.
     * @param sampleIndexColumn          Optional sample index column.
     * @param checkHash                  Check hash value.
     * @param tableHash                  Table hash value.
     * @param columnHash                 Column hash value (or 0L when the check is not on a column level).
     * @param rowCount                   Row count.
     * @return ID column, filled with values.
     */
    TextColumn createErrorSampleRowIdColumn(LongColumn sortedDataGroupingHashColumn,
                                            DateTimeColumn sortedTimePeriodColumn,
                                            IntColumn sampleIndexColumn,
                                            long checkHash,
                                            long tableHash,
                                            long columnHash,
                                            int rowCount);
}
