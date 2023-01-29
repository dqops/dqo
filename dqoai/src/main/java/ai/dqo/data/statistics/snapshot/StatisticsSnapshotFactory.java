package ai.dqo.data.statistics.snapshot;

import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Statistics results snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface StatisticsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the statistics result storage service that will load requested months on demand.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @return Statistics results snapshot connected to a storage service.
     */
    StatisticsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName);

    /**
     * Creates an empty, read-only snapshot that is connected to the statistics results storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames Array of column names to load from parquet files. Other columns will not be loaded.
     * @return Statistics results snapshot connected to a storage service.
     */
    StatisticsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames);
}
