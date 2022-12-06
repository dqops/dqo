package ai.dqo.data.storage;

import tech.tablesaw.api.Table;

/**
 * Container for the data loaded from a single partition.
 */
public class LoadedMonthlyPartition {
    private ParquetPartitionId partitionId;
    private long lastModified;  // file modification timestamp of the loaded file
    private Table data;

    /**
     * Creates a wrapper over one loaded partition.
     * @param partitionId Partition id - identifies the partition (month, schema.table, month).
     * @param lastModified Last parquet file modification timestamp. Used to detect concurrent file modifications.
     * @param data Table data or null when the partition is empty (no file).
     */
    public LoadedMonthlyPartition(ParquetPartitionId partitionId, long lastModified, Table data) {
        this.partitionId = partitionId;
        this.lastModified = lastModified;
        this.data = data;
    }

    /**
     * Creates a wrapper over one empty partition with no data.
     * @param partitionId Partition id - identifies the partition (month, schema.table, month).
     */
    public LoadedMonthlyPartition(ParquetPartitionId partitionId) {
        this.partitionId = partitionId;
        this.lastModified = 0L;
        this.data = null;
    }

    /**
     * Returns the partition identifier (month, schema.table, month).
     * @return Partition identifier.
     */
    public ParquetPartitionId getPartitionId() {
        return partitionId;
    }

    /**
     * Returns the last modification timestamp of the file that was loaded from disk.
     * @return Last modification date.
     */
    public long getLastModified() {
        return lastModified;
    }

    /**
     * Returns the data from the partition, as a tablesaw table. Returns null if the partition is empty.
     * @return Partition's data that was loaded from the disk or null when the partition was not found.
     */
    public Table getData() {
        return data;
    }
}
