package ai.dqo.data.storage;

import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
    private Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions;
    private LocalDate firstLoadedMonth;
    private LocalDate lastLoadedMonth;

    /**
     * Creates a new snapshot of data for a single parquet table with results for one connection and physical table.
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
        this.tableDataChanges = new TableDataChanges(newResults);
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
     * Ensures that all the months (monthly partitions) within the time range between <code>startMonth</code> and <code>endMonth</code> are loaded.
     * Loads missing months to extend the time range of monthly partitions that are kept in a snapshot.
     * @param startMonth The date of the start month. It could be any date within the month, because the whole month is always loaded.
     * @param endMonth The date fo the end month. It could be any date within the month, because the whole month is always loaded.
     */
    public void ensureMonthsAreLoaded(LocalDate startMonth, LocalDate endMonth) {
        if (this.firstLoadedMonth == null) {
            // no data ever loaded

            this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);
            this.lastLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, this.firstLoadedMonth, this.lastLoadedMonth, this.storageSettings);
            if (this.loadedMonthlyPartitions == null) {
                this.loadedMonthlyPartitions = new LinkedHashMap<>();
            }
            this.loadedMonthlyPartitions.putAll(loadedPartitions);
            return;
        }

        if (startMonth.isBefore(this.firstLoadedMonth)) {
            // we need to load a few months before
            LocalDate lastMonthToLoad = this.firstLoadedMonth.minus(1, ChronoUnit.MONTHS);
            this.firstLoadedMonth = LocalDateTimeTruncateUtility.truncateMonth(startMonth);

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedEarlierPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, this.firstLoadedMonth, lastMonthToLoad, this.storageSettings);
            this.loadedMonthlyPartitions.putAll(loadedEarlierPartitions);
        }

        LocalDate truncatedEndMonth = LocalDateTimeTruncateUtility.truncateMonth(endMonth);
        if (truncatedEndMonth.isAfter(this.lastLoadedMonth)) {
            // we need to load a few months after
            LocalDate firstMonthToLoad = this.lastLoadedMonth.plus(1, ChronoUnit.MONTHS);
            this.lastLoadedMonth = truncatedEndMonth;

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedLaterPartitions = this.storageService.loadPartitionsForMonthsRange(
                    this.connectionName, this.tableName, firstMonthToLoad, this.lastLoadedMonth, this.storageSettings);
            this.loadedMonthlyPartitions.putAll(loadedLaterPartitions);
        }
    }

    /**
     * Saves all results to a persistent storage (like files). New rows are added, rows with matching IDs are updated.
     * Rows identified by an ID column are deleted.
     */
    public void save() {
        if (this.loadedMonthlyPartitions == null && this.tableDataChanges.getNewOrChangedRows().rowCount() == 0) {
            if (this.tableDataChanges.getDeletedIds().size() == 0) {
                return; // an empty snapshot, not loaded and has no changes
            }
            else {
                throw new IllegalStateException("Cannot save a snapshot that contains only row Ids ot delete, but the snapshot did not load any partitions from which the rows should be deleted.");
            }
        }

        DateTimeColumn newResultsTimePeriodColumn = (DateTimeColumn) this.tableDataChanges.getNewOrChangedRows()
                .column(this.storageSettings.getTimePeriodColumnName());
        LocalDateTime minDateNewResults = Optional.of(newResultsTimePeriodColumn.min())
                .orElse(LocalDateTime.of(this.firstLoadedMonth, LocalTime.MIDNIGHT));
        LocalDateTime maxDateNewResults = Optional.of(newResultsTimePeriodColumn.max())
                .orElse(LocalDateTime.of(this.lastLoadedMonth, LocalTime.MIDNIGHT));

        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(minDateNewResults.toLocalDate());
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(maxDateNewResults.toLocalDate());

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
