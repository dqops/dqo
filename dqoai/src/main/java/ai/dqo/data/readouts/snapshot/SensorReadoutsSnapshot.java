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
package ai.dqo.data.readouts.snapshot;

import ai.dqo.data.ChangeDeltaMode;
import ai.dqo.data.readouts.filestorage.SensorReadoutsFileStorageService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableMergeUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.table.TableSlice;
import tech.tablesaw.table.TableSliceGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Sensor readouts snapshot that contains an in-memory sensor readout snapshot
 * for a single table and selected time ranges.
 */
public class SensorReadoutsSnapshot {
    private final String connection;
    private final PhysicalTableName tableName;
    private LocalDate firstLoadedMonth;
    private LocalDate lastLoadedMonth;
    private final SensorReadoutsFileStorageService storageService;
    private Table historicResults;
    private final Table newResults;
    private SensorReadoutsTimeSeriesMap timeSeriesMap;

    /**
     * Default constructor.
     * @param connection Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new sensor readouts (captured during the current sensor execution).
     */
    public SensorReadoutsSnapshot(String connection,
                                  PhysicalTableName tableName,
                                  SensorReadoutsFileStorageService storageService,
                                  Table newResults) {
        this.connection = connection;
        this.tableName = tableName;
        this.storageService = storageService;
        this.newResults = newResults;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Returns the physical table name.
     * @return Physical table name.
     */
    public PhysicalTableName getTableName() {
        return tableName;
    }

    /**
     * Returns the storage service that was used to load this snapshot.
     * @return Storage service.
     */
    public SensorReadoutsFileStorageService getStorageService() {
        return storageService;
    }

    /**
     * Returns the date of the first day of the first month that is loaded into a snapshot.
     * @return First day of the first month loaded.
     */
    public LocalDate getFirstLoadedMonth() {
        return firstLoadedMonth;
    }

    /**
     * Returns the date of the first day of the last month that is loaded into a snapshot. The whole month (until the last day of that month) is loaded.
     * @return First day of the last month loaded.
     */
    public LocalDate getLastLoadedMonth() {
        return lastLoadedMonth;
    }

    /**
     * Returns a dataset (in-memory table) with the historic sensor readouts for the whole loaded range.
     * @return Table with historic sensor readouts.
     */
    public Table getHistoricResults() {
        return historicResults;
    }

    /**
     * Returns a dataset (in-memory) table where new sensor readouts should be appended.
     * @return New results table.
     */
    public Table getNewResults() {
        return newResults;
    }

    /**
     * Ensures that all the months within the time range between <code>startMonth</code> and <code>endMonth</code> are loaded.
     * Loads missing months to extend the time range of sensor readouts that are kept in a snapshot.
     * @param startMonth The date of the start month. It could be any date within the month, because the whole month is always loaded.
     * @param endMonth The date fo the end month. It could be any date within the month, because the whole month is always loaded.
     */
    public void ensureMonthsAreLoaded(LocalDate startMonth, LocalDate endMonth) {
        if (this.firstLoadedMonth == null) {
            // no data ever loaded

			this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);
			this.lastLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
			this.timeSeriesMap = null;
			this.historicResults = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, this.firstLoadedMonth, this.lastLoadedMonth);

            return;
        }

        if (startMonth.isBefore(this.firstLoadedMonth)) {
            // we need to load a few months before
            LocalDate lastMonthToLoad = this.firstLoadedMonth.minus(1, ChronoUnit.MONTHS);
			this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);

            Table loadedRows = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, this.firstLoadedMonth, lastMonthToLoad);
            if (loadedRows != null) {
                if (this.historicResults == null) {
                    this.historicResults = loadedRows;
                } else {
                    this.historicResults.append(loadedRows);
                }
				this.timeSeriesMap = null;
            }
        }

        LocalDate truncatedEndMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
        if (truncatedEndMonth.isAfter(this.lastLoadedMonth)) {
            // we need to load a few months after
            LocalDate firstMonthToLoad = this.lastLoadedMonth.plus(1, ChronoUnit.MONTHS);
			this.lastLoadedMonth = truncatedEndMonth;

            Table loadedRows = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, firstMonthToLoad, this.lastLoadedMonth);
            if (loadedRows != null) {
                if (this.historicResults == null) {
                    this.historicResults = loadedRows;
                } else {
                    this.historicResults.append(loadedRows);
                }
				this.timeSeriesMap = null;
            }
        }
    }

    /**
     * Saves all results to a persistent storage (like files).
     */
    public void save() {
        assert this.storageService.getDeltaMode() == ChangeDeltaMode.REPLACE_ALL : "only replace mode supported right now";

        DateTimeColumn newResultsTimePeriodColumn = (DateTimeColumn) this.newResults.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME);
        LocalDateTime minDateNewResults = newResultsTimePeriodColumn.min();
        LocalDateTime maxDateNewResults = newResultsTimePeriodColumn.max();

        if (this.historicResults == null) {
            // no historic data was loaded (or not present)
            if (this.newResults.rowCount() == 0) {
                return; // nothing to write
            }

            // save only the new readouts
			this.storageService.saveTableInMonthsRange(this.newResults, this.connection, this.tableName,
                    LocalDateTimeTruncateUtility.truncateMonth(minDateNewResults.toLocalDate()),
                    LocalDateTimeTruncateUtility.truncateMonth(maxDateNewResults.toLocalDate()));
        }
        else {
            if (this.newResults.rowCount() == 0) {
                // nothing to save, no new rows, only rows that were already loaded
                return;
            }

            String[] joinColumns = {
                    SensorReadoutsNormalizedResult.ID_COLUMN_NAME
            };
            Table mergedResults = TableMergeUtility.mergeNewResults(this.historicResults, this.newResults, joinColumns);

			this.storageService.saveTableInMonthsRange(mergedResults, this.connection, this.tableName,
                    LocalDateTimeTruncateUtility.truncateMonth(minDateNewResults.toLocalDate()),
                    LocalDateTimeTruncateUtility.truncateMonth(maxDateNewResults.toLocalDate()));
        }
    }

    /**
     * Checks if the new table has any rows that should be merged into the persistent store.
     * @return True when there are new rows, false when there is no data to be saved.
     */
    public boolean hasNewReadouts() {
        return this.newResults != null && this.newResults.rowCount() > 0;
    }

    /**
     * Creates or returns a cached split of historic sensor readouts, divided by time series.
     * A single time series is a subset of sensor readouts for a single check (which maps 1-to-1 to a sensor) and a data stream id.
     */
    public SensorReadoutsTimeSeriesMap getHistoricReadoutsTimeSeries() {
        if (this.timeSeriesMap != null) {
            return this.timeSeriesMap;
        }

		this.timeSeriesMap = new SensorReadoutsTimeSeriesMap();

        if (this.historicResults != null) {
            TableSliceGroup tableSlices = this.historicResults.splitOn(SensorReadoutsNormalizedResult.CHECK_HASH_COLUMN_NAME, SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME);
            for (TableSlice tableSlice : tableSlices) {
                Table timeSeriesTable = tableSlice.asTable();
                LongColumn checkHashColumn = (LongColumn) timeSeriesTable.column(SensorReadoutsNormalizedResult.CHECK_HASH_COLUMN_NAME);
                LongColumn dataStreamHashColumn = (LongColumn) timeSeriesTable.column(SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME);
                long checkHashId = checkHashColumn.get(0); // the first row has the value
                long dimensionId = dataStreamHashColumn.get(0);

                SensorReadoutTimeSeriesKey timeSeriesKey = new SensorReadoutTimeSeriesKey(checkHashId, dimensionId);
                Table sortedTimeSeriesTable = timeSeriesTable.sortOn(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME);
                SensorReadoutsTimeSeriesData timeSeriesData = new SensorReadoutsTimeSeriesData(timeSeriesKey, sortedTimeSeriesTable);
				this.timeSeriesMap.add(timeSeriesData);
            }
        }

        return this.timeSeriesMap;
    }
}
