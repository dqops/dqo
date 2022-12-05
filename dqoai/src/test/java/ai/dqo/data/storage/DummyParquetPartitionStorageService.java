package ai.dqo.data.storage;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dummy parquet storage service.
 */
public class DummyParquetPartitionStorageService implements ParquetPartitionStorageService {
    /**
     * Reads the data of one monthly partition.
     *
     * @param partitionId     Partition id.
     * @param storageSettings Storage settings that identify the target table type that is loaded.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    @Override
    public LoadedMonthlyPartition loadPartition(ParquetPartitionId partitionId, FileStorageSettings storageSettings) {
        return new LoadedMonthlyPartition(partitionId, 0L, null);
    }

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more rows than expected, because it operates on full months.
     *
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param start           Start date, that is truncated to the beginning of the first loaded month.
     * @param end             End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end, FileStorageSettings storageSettings) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);
            }
        }

        return resultPartitions;
    }

    /**
     * Saves the data for a single monthly partition. Finds the range of data for that month in the <code>tableDataChanges</code>.
     * Also deletes rows that should be deleted. In case that the file was modified since it was loaded into the loaded partition
     * snapshot (parameter: <code>loadedPartition</code>), the partition is reloaded using an exclusive write lock and the changes
     * are applied to the most recent data.
     *
     * @param loadedPartition  Loaded partition, identifies the partition id. The loaded partition may contain no data.
     * @param tableDataChanges Table data changes to be applied.
     * @param storageSettings  Storage settings to identify the target folder, file names and column names used for matching.
     */
    @Override
    public void savePartition(LoadedMonthlyPartition loadedPartition, TableDataChanges tableDataChanges, FileStorageSettings storageSettings) {
        // do nothing
    }
}
