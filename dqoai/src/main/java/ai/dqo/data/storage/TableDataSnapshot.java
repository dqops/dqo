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
package ai.dqo.data.storage;

import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.InstantColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Contains a snapshot of data for one parquet table (such as sensor_readouts or rule_results) that was loaded
 * from parquet files. Contains also changes that should be applied (new rows, updated rows, deleted rows).
 */
public class TableDataSnapshot {
    private final String connectionName;
    private final PhysicalTableName tableName;
    private final ParquetPartitionStorageService storageService;
    private final FileStorageSettings storageSettings;
    private final TableDataChanges tableDataChanges;
    private final Table newResultsTable;
    private final String[] columnNames;
    private Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions;
    private LocalDate firstLoadedMonth;
    private LocalDate lastLoadedMonth;

    /**
     * Creates a new writable snapshot of data for a single parquet table with results for one connection and physical table.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param storageService Storage service dependency.
     * @param storageSettings Configuration of the storage settings (folder names, parquet file names, column names).
     * @param newResults Empty tableaw table with new or updated rows. Changes to this object will be persisted on save.
     */
    public TableDataSnapshot(String connectionName,
                             PhysicalTableName tableName,
                             ParquetPartitionStorageService storageService,
                             FileStorageSettings storageSettings,
                             Table newResults) {
        this.connectionName = connectionName;
        this.tableName = tableName;
        this.storageService = storageService;
        this.storageSettings = storageSettings;
        this.newResultsTable = newResults;
        this.tableDataChanges = new TableDataChanges(newResults);
        this.columnNames = null;
    }

    /**
     * Creates a new read-only snapshot of data for a single parquet table with results for one connection and physical table.
     * The tables loaded from parquet files will be limited to the set of columns in the <code>columnNames</code> list.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param storageService Storage service dependency.
     * @param storageSettings Configuration of the storage settings (folder names, parquet file names, column names).
     * @param columnNames Array of column names that will be loaded.
     */
    public TableDataSnapshot(String connectionName,
                             PhysicalTableName tableName,
                             ParquetPartitionStorageService storageService,
                             FileStorageSettings storageSettings,
                             String[] columnNames,
                             Table newResultsTemplate) {
        assert columnNames != null && columnNames.length > 0;
        this.connectionName = connectionName;
        this.tableName = tableName;
        this.storageService = storageService;
        this.storageSettings = storageSettings;
        this.newResultsTable = newResultsTemplate;
        this.tableDataChanges = null;
        this.columnNames = columnNames;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Returns the physical table name.
     * @return Physical table name.
     */
    public PhysicalTableName getTableName() {
        return tableName;
    }

    /**
     * Returns an optional list of column names that should be loaded. Only read-only snapshot could use a subset of named columns.
     * When the array of column names is null then all columns are loaded.
     * @return Array of column names to load in read-only snapshots.
     */
    public String[] getColumnNames() {
        return columnNames;
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
     * Returns the table data changes container where new rows or updated rows should be added.
     * @return Table data changes container.
     */
    public TableDataChanges getTableDataChanges() {
        return tableDataChanges;
    }

    /**
     * Returns a map of loaded partitions, keyed by the partition id.
     * @return Map of loaded partitions.
     */
    public Map<ParquetPartitionId, LoadedMonthlyPartition> getLoadedMonthlyPartitions() {
        return loadedMonthlyPartitions;
    }

    /**
     * Returns the read-only state of the table.
     * @return True if table is read-only, false otherwise.
     */
    public boolean isReadOnly() {
        return columnNames != null;
    }

    /**
     * Creates a table with a combined data from all loaded partitions as a single tablesaw table.
     * The order of partitions whose data is appended to the result table is not ensured.
     * @return Table with the data from all partitions or null when no partitions were loaded or all loaded partitions were empty.
     */
    public Table getAllData() {
        Table allData = null;
        if (loadedMonthlyPartitions != null) {
            for (LoadedMonthlyPartition loadedMonthlyPartition : this.loadedMonthlyPartitions.values()) {
                if (loadedMonthlyPartition.getData() != null) {
                    if (allData == null) {
                        allData = loadedMonthlyPartition.getData().copy();
                    }
                    else {
                        allData.append(loadedMonthlyPartition.getData());
                    }
                }
            }
        }

        return allData;
    }

    /**
     * Fixes the schema of loaded parquet files to match the current schema.
     * Supports only read-write parquet files (without a column list filter). Does not convert the data types of columns.
     * @param loadedPartitions Dictionary of loaded partitions.
     */
    public void updateSchemaForLoadedPartitions(Map<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitions) {
        if (this.columnNames != null) {
            for (LoadedMonthlyPartition loadedMonthlyPartition : loadedPartitions.values()) {
                Table partitionData = loadedMonthlyPartition.getData();
                if (partitionData == null) {
                    continue;
                }

                HashSet<String> columnNamesInPartitionData = new HashSet<>(partitionData.columnNames());

                for (String expectedColumnName : this.columnNames) {
                    if (!columnNamesInPartitionData.contains(expectedColumnName)) {
                        Column<?> expectedColumn = this.newResultsTable.column(expectedColumnName);
                        Column<?> emptyColumnToAdd = expectedColumn.emptyCopy(partitionData.rowCount());
                        partitionData.addColumns(emptyColumnToAdd);
                    }
                }
            }
        }
        else {
            for (LoadedMonthlyPartition loadedMonthlyPartition : loadedPartitions.values()) {
                Table partitionData = loadedMonthlyPartition.getData();
                if (partitionData == null) {
                    continue;
                }

                HashSet<String> columnNamesInPartitionData = new HashSet<>(partitionData.columnNames());

                for (Column<?> expectedColumn : this.getTableDataChanges().getNewOrChangedRows().columns()) {
                    if (!columnNamesInPartitionData.contains(expectedColumn.name())) {
                        Column<?> emptyColumnToAdd = expectedColumn.emptyCopy(partitionData.rowCount());
                        partitionData.addColumns(emptyColumnToAdd);
                    }
                }
            }
        }
    }

    /**
     * Ensures that all the months (monthly partitions) within the time range between <code>startMonth</code> and <code>endMonth</code> are loaded.
     * Loads missing months to extend the time range of monthly partitions that are kept in a snapshot.
     * @param start The date of the start month. It could be any date within the month, because the whole month is always loaded.
     * @param end The date of the end month. It could be any date within the month, because the whole month is always loaded.
     * @return true when additional months were loaded, false when all months in the requested range were already loaded
     */
    public boolean ensureMonthsAreLoaded(@NotNull LocalDate start, @NotNull LocalDate end) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);
        boolean anyMonthLoaded = false;

        if (this.firstLoadedMonth == null) {
            // no data ever loaded

            this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);
            this.lastLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, this.firstLoadedMonth, this.lastLoadedMonth, this.storageSettings, this.columnNames);
            if (loadedPartitions != null) {
                if (this.loadedMonthlyPartitions == null) {
                    this.loadedMonthlyPartitions = new LinkedHashMap<>();
                }
                updateSchemaForLoadedPartitions(loadedPartitions);
                this.loadedMonthlyPartitions.putAll(loadedPartitions);
                return true;
            }
        }

        if (startMonth.isBefore(this.firstLoadedMonth)) {
            // we need to load a few months before
            LocalDate lastMonthToLoad = this.firstLoadedMonth.minus(1, ChronoUnit.MONTHS);
            this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedEarlierPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, this.firstLoadedMonth, lastMonthToLoad, this.storageSettings, this.columnNames);

            if (loadedEarlierPartitions != null) {
                updateSchemaForLoadedPartitions(loadedEarlierPartitions);
                this.loadedMonthlyPartitions.putAll(loadedEarlierPartitions);
                anyMonthLoaded = true;
            }
        }

        LocalDate truncatedEndMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
        if (truncatedEndMonth.isAfter(this.lastLoadedMonth)) {
            // we need to load a few months after
            LocalDate firstMonthToLoad = this.lastLoadedMonth.plus(1, ChronoUnit.MONTHS);
            this.lastLoadedMonth = truncatedEndMonth;

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedLaterPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, firstMonthToLoad, this.lastLoadedMonth, this.storageSettings, this.columnNames);
            if (loadedLaterPartitions != null) {
                updateSchemaForLoadedPartitions(loadedLaterPartitions);
                this.loadedMonthlyPartitions.putAll(loadedLaterPartitions);
                anyMonthLoaded = true;
            }
        }

        return anyMonthLoaded;
    }

    /**
     * Ensures that the months (monthly partitions) within the time range between <code>startMonth</code> and <code>endMonth</code> are loaded,
     * up to the point when the snapshot contains <code>monthCount</code> loaded partitions.
     * Loads missing months to extend the time range of monthly partitions that are kept in a snapshot.
     * @param start The date of the start month. If null, the date range is not left-bounded.
     * @param end The date of the end month. If null, the date range is not right-bounded.
     * @param monthCount Maximum number of months to have loaded.
     */
    public void ensureNRecentMonthsAreLoaded(LocalDate start, LocalDate end, int monthCount) {
        int currentlyLoadedPartitions = loadedMonthlyPartitions == null ? 0 : loadedMonthlyPartitions.size();
        int needToLoad = monthCount - currentlyLoadedPartitions;
        if (needToLoad <= 0) {
            // no need to load more partitions
            return;
        }

        if (this.firstLoadedMonth == null) {
            // no data ever loaded
            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitions = this.storageService.loadRecentPartitionsForMonthsRange(
                    this.connectionName, this.tableName, start, end, this.storageSettings, this.columnNames, monthCount);

            if (loadedPartitions != null) {
                this.firstLoadedMonth = loadedPartitions.keySet().stream()
                        .map(ParquetPartitionId::getMonth)
                        .min(LocalDate::compareTo)
                        .orElse(null);
                this.lastLoadedMonth = loadedPartitions.keySet().stream()
                        .map(ParquetPartitionId::getMonth)
                        .max(LocalDate::compareTo)
                        .orElse(null);

                if (this.loadedMonthlyPartitions == null) {
                    this.loadedMonthlyPartitions = new LinkedHashMap<>();
                }
                updateSchemaForLoadedPartitions(loadedPartitions);
                this.loadedMonthlyPartitions.putAll(loadedPartitions);
            }
        }
        else {
            if (end == null || end.isAfter(this.lastLoadedMonth)) {
                // we need to load a few months after
                LocalDate firstMonthToLoad;
                if (start == null || start.isBefore(this.lastLoadedMonth)) {
                    firstMonthToLoad = this.lastLoadedMonth.plusMonths(1L);
                }
                else {
                    firstMonthToLoad = start;
                }

                Map<ParquetPartitionId, LoadedMonthlyPartition> loadedLaterPartitions = this.storageService.loadRecentPartitionsForMonthsRange(
                        this.connectionName, this.tableName, firstMonthToLoad, end, this.storageSettings, this.columnNames, needToLoad);
                Optional<LocalDate> lastLoadedLaterMonth = loadedLaterPartitions.keySet().stream().map(ParquetPartitionId::getMonth).max(LocalDate::compareTo);
                this.lastLoadedMonth = lastLoadedLaterMonth.orElse(this.lastLoadedMonth);
                updateSchemaForLoadedPartitions(loadedLaterPartitions);
                this.loadedMonthlyPartitions.putAll(loadedLaterPartitions);
            }
            currentlyLoadedPartitions = loadedMonthlyPartitions == null ? 0 : loadedMonthlyPartitions.size();
            needToLoad = monthCount - currentlyLoadedPartitions;
            if (needToLoad <= 0) {
                // no need to load more partitions
                return;
            }

            if (start == null || start.isBefore(this.firstLoadedMonth)) {
                // we need to load a few months before
                LocalDate lastMonthToLoad;
                if (end == null || end.isAfter(this.firstLoadedMonth)) {
                    lastMonthToLoad = this.firstLoadedMonth.minusMonths(1L);
                } else {
                    lastMonthToLoad = end;
                }

                Map<ParquetPartitionId, LoadedMonthlyPartition> loadedEarlierPartitions = this.storageService.loadRecentPartitionsForMonthsRange(
                        this.connectionName, this.tableName, start, lastMonthToLoad, this.storageSettings, this.columnNames, needToLoad);
                Optional<LocalDate> lastLoadedEarlierMonth = loadedEarlierPartitions.keySet().stream().map(ParquetPartitionId::getMonth).max(LocalDate::compareTo);
                this.firstLoadedMonth = lastLoadedEarlierMonth.orElse(this.firstLoadedMonth);
                updateSchemaForLoadedPartitions(loadedEarlierPartitions);
                this.loadedMonthlyPartitions.putAll(loadedEarlierPartitions);
            }
        }
    }

    /**
     * Retrieves a loaded partition. The partition that is requested must be already loaded
     * @param date Date to be loaded.
     * @param load True when the partition for the given date should be loaded if it is not loaded. False when we are just performing a lookup
     *             in the dictionary of already loaded partitions.
     * @return Loaded partition or null. If the partition has no data (which was confirmed by trying to read the parquet file),
     *         then the returned object is not null, but its internal tablesaw data is null.
     */
    public LoadedMonthlyPartition getMonthPartition(LocalDate date, boolean load) {
        LocalDate monthDate = LocalDateTimeTruncateUtility.truncateMonth(date);

        if (load) {
            this.ensureMonthsAreLoaded(monthDate, monthDate);
        }

        if (this.loadedMonthlyPartitions == null) {
            return null;
        }

        ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, monthDate);
        return this.loadedMonthlyPartitions.get(partitionId);
    }

    /**
     * Marks id strings to delete upon saving the snapshot, based on a selection date range and specific equals conditions on column values.
     * To mark a row for deletion it has to match all conditions.
     * [NOT ON READ-ONLY]
     * @param startDate Start of the date range (to the day).
     * @param endDate End of the date range (to the day).
     * @param columnConditions Column name to column values conditions. Column name must be a name of a valid string column in the table. Column values are the values that can be present in a row for that column.
     */
    public void markSelectedForDeletion(LocalDate startDate,
                                        LocalDate endDate,
                                        Map<String, Set<String>> columnConditions) {
        if (this.isReadOnly()) {
            throw new DataStorageIOException("Read-only snapshots do not support deleting.");
        }

        LocalDate startMonth = startDate == null ? null : LocalDateTimeTruncateUtility.truncateMonth(startDate);
        LocalDate endMonth = endDate == null ? null : LocalDateTimeTruncateUtility.truncateMonth(endDate);
        this.ensureNRecentMonthsAreLoaded(startMonth, endMonth, Integer.MAX_VALUE);
        if (this.loadedMonthlyPartitions == null) {
            // No data to delete
            return;
        }

        List<String> idsToDelete = new ArrayList<>();

        startDate = Objects.requireNonNullElse(startDate, this.firstLoadedMonth);
        endDate = Objects.requireNonNullElse(endDate, this.lastLoadedMonth.plusMonths(1).minusDays(1));
        startMonth = LocalDateTimeTruncateUtility.truncateMonth(startDate);
        endMonth = LocalDateTimeTruncateUtility.truncateMonth(endDate);

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {

            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition loadedMonthlyPartition = this.loadedMonthlyPartitions.get(partitionId);
            Table monthlyPartitionTable = loadedMonthlyPartition.getData();
            if (monthlyPartitionTable == null) {
                continue;
            }

            Selection toDelete = Selection.withRange(0, monthlyPartitionTable.rowCount());

            // Filter by date.
            toDelete = toDelete.and(
                    monthlyPartitionTable.dateTimeColumn(this.storageSettings.getTimePeriodColumnName()).date()
                            .isBetweenIncluding(startDate, endDate));

            // Filter by string columns' conditions.
            try {
                for (Map.Entry<String, Set<String>> columnCondition : columnConditions.entrySet()) {
                    String colName = columnCondition.getKey();
                    Set<String> colValues = columnCondition.getValue();
                    if (colValues == null || colValues.isEmpty()) {
                        continue;
                    }

                    toDelete = toDelete.and(
                            monthlyPartitionTable.stringColumn(colName)
                                    .isIn(colValues));
                }
            }
            catch (IllegalStateException e) {
                throw new DataStorageIOException("Condition on column that doesn't exist", e);
            }

            List<String> idsToDeleteInPartition = monthlyPartitionTable
                    .stringColumn(this.storageSettings.getIdStringColumnName())
                    .where(toDelete).asList();
            idsToDelete.addAll(idsToDeleteInPartition);
        }

        // Assuming not read-only.
        tableDataChanges.getDeletedIds().addAll(idsToDelete);
    }

    /**
     * Saves all results to a persistent storage (like files). New rows are added, rows with matching IDs are updated.
     * Rows identified by an ID column are deleted.
     * [NOT ON READ-ONLY]
     */
    public void save() {
        if (this.isReadOnly()) {
            throw new DataStorageIOException("Read-only snapshots do not support saving.");
        }

        if (this.loadedMonthlyPartitions == null && this.tableDataChanges.getNewOrChangedRows().rowCount() == 0) {
            if (this.tableDataChanges.getDeletedIds().size() == 0) {
                return; // an empty snapshot, not loaded and has no changes
            }
            else {
                throw new IllegalStateException("Cannot save a snapshot that contains only row Ids ot delete, but the snapshot did not load any partitions from which the rows should be deleted.");
            }
        }

        Column<?> datePartitioningColumnOriginal = this.tableDataChanges.getNewOrChangedRows()
                .column(this.storageSettings.getTimePeriodColumnName());
        DateTimeColumn newResultsTimePeriodColumn = null;
        if (datePartitioningColumnOriginal instanceof DateTimeColumn) {
            newResultsTimePeriodColumn = (DateTimeColumn) datePartitioningColumnOriginal;
        }
        else if (datePartitioningColumnOriginal instanceof InstantColumn) {
            newResultsTimePeriodColumn = ((InstantColumn)datePartitioningColumnOriginal).asLocalDateTimeColumn(ZoneOffset.UTC);
        }
        else if (datePartitioningColumnOriginal instanceof DateColumn) {
            newResultsTimePeriodColumn = ((DateColumn)datePartitioningColumnOriginal).atTime(LocalTime.MIDNIGHT);
        }
        else {
            throw new DqoRuntimeException("Time partitioning column " + storageSettings.getTimePeriodColumnName() + " is not a date/datetime/instant column.");
        }

        LocalDateTime minDateNewResults = newResultsTimePeriodColumn.min();
        LocalDate startMonth = minDateNewResults != null ?
                LocalDateTimeTruncateUtility.truncateMonth(minDateNewResults.toLocalDate()) : this.firstLoadedMonth;

        LocalDateTime maxDateNewResults = newResultsTimePeriodColumn.max();
        LocalDate endMonth = maxDateNewResults != null ?
                LocalDateTimeTruncateUtility.truncateMonth(maxDateNewResults.toLocalDate()) : this.lastLoadedMonth;

        if (this.loadedMonthlyPartitions == null) {
            // no historic data was loaded

            if (!this.tableDataChanges.hasChanges()) {
                return; // nothing to write
            }

            // we need to load old parquet files to perform merging
            this.ensureMonthsAreLoaded(startMonth, endMonth);
        }

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {

            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition loadedMonthlyPartition = this.loadedMonthlyPartitions.get(partitionId);
            this.storageService.savePartition(loadedMonthlyPartition, this.tableDataChanges, this.storageSettings);
        }
    }
}
