package ai.dqo.data.profilingresults.snapshot;

import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Profiling results snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface ProfilingResultsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the profiling result storage service that will load requested months on demand.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @return Profiling results snapshot connected to a storage service.
     */
    ProfilingResultsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName);
}
