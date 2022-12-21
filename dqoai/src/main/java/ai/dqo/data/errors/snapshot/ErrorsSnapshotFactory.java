package ai.dqo.data.errors.snapshot;

import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Errors snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface ErrorsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the errors storage service that will load requested months on demand.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @return Errors snapshot connected to a storage service.
     */
    ErrorsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName);

    /**
     * Creates an empty, read-only snapshot that is connected to the errors storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames Array of column names to load from parquet files. Other columns will not be loaded.
     * @return Errors snapshot connected to a storage service.
     */
    ErrorsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames);
}
