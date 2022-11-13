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
package ai.dqo.data.alerts.snapshot;

import ai.dqo.data.alerts.filestorage.RuleResultsFileStorageService;
import ai.dqo.data.delta.ChangeDeltaMode;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.tables.TableMergeUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Rule evaluation results snapshot that contains an in-memory rule results (alerts) snapshot
 * for a single table and selected time ranges.
 */
public class RuleResultsSnapshot {
    private final String connection;
    private final PhysicalTableName tableName;
    private LocalDate firstMonth;
    private LocalDate lastMonth;
    private final RuleResultsFileStorageService storageService;
    private Table historicResults;
    private final Table newResults;

    /**
     * Default constructor.
     * @param connection Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new sensor readings (captured during the current sensor execution).
     */
    public RuleResultsSnapshot(String connection,
							   PhysicalTableName tableName,
							   RuleResultsFileStorageService storageService,
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
    public RuleResultsFileStorageService getStorageService() {
        return storageService;
    }

    /**
     * Returns the date of the first day of the first month that is loaded into a snapshot.
     * @return First day of the first month loaded.
     */
    public LocalDate getFirstMonth() {
        return firstMonth;
    }

    /**
     * Returns the date of the first day of the last month that is loaded into a snapshot. The whole month (until the last day of that month) is loaded.
     * @return First day of the last month loaded.
     */
    public LocalDate getLastMonth() {
        return lastMonth;
    }

    /**
     * Returns a dataset (in-memory table) with the historic sensor readings for the whole loaded range.
     * @return Table with historic sensor readings.
     */
    public Table getHistoricResults() {
        return historicResults;
    }

    /**
     * Returns a dataset (in-memory) table where new sensor readings should be appended.
     * @return New results table.
     */
    public Table getNewResults() {
        return newResults;
    }

    /**
     * Ensures that all the months within the time range between <code>startMonth</code> and <code>endMonth</code> are loaded.
     * Loads missing months to extend the time range of sensor results that are kept in a snapshot.
     * @param startMonth The date of the start month. It could be any date within the month, because the whole month is always loaded.
     * @param endMonth The date fo the end month. It could be any date within the month, because the whole month is always loaded.
     */
    public void ensureMonthsAreLoaded(LocalDate startMonth, LocalDate endMonth) {
        if (this.firstMonth == null) {
            // no data ever loaded

			this.firstMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);
			this.lastMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
			this.historicResults = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, this.firstMonth, this.lastMonth);

            return;
        }

        assert this.historicResults != null;

        if (startMonth.isBefore(this.firstMonth)) {
            // we need to load a few months before
            LocalDate lastMonthToLoad = this.firstMonth.minus(1, ChronoUnit.MONTHS);
			this.firstMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);

            Table loadedRows = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, this.firstMonth, lastMonthToLoad);
            if (loadedRows != null) {
				this.historicResults.append(loadedRows);
            }
        }

        LocalDate truncatedEndMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
        if (truncatedEndMonth.isAfter(this.lastMonth)) {
            // we need to load a few months after
            LocalDate firstMonthToLoad = this.lastMonth.plus(1, ChronoUnit.MONTHS);
			this.lastMonth = truncatedEndMonth;

            Table loadedRows = this.storageService.loadForTableAndMonthsRange(this.connection, this.tableName, firstMonthToLoad, this.lastMonth);
            if (loadedRows != null) {
				this.historicResults.append(loadedRows);
            }
        }
    }

    /**
     * Saves all results to a persistent storage (like files).
     */
    public void save() {
        assert this.storageService.getDeltaMode() == ChangeDeltaMode.REPLACE_ALL : "only replace mode supported right now";

        DateTimeColumn newResultsTimePeriodColumn = (DateTimeColumn) this.newResults.column(SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME);
        LocalDateTime minDateNewResults = newResultsTimePeriodColumn.min();
        LocalDateTime maxDateNewResults = newResultsTimePeriodColumn.max();

        if (this.historicResults == null) {
            // no historic data was loaded (or not present)
            if (this.newResults.rowCount() == 0) {
                return; // nothing to write
            }

            // save only the new readings
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
                    SensorNormalizedResult.CHECK_HASH_COLUMN_NAME,
                    SensorNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME,
                    SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME
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
    public boolean hasNewAlerts() {
        return this.newResults != null && this.newResults.rowCount() > 0;
    }
}
