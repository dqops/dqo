package ai.dqo.data.profilingresults.services;

import ai.dqo.data.profilingresults.services.models.ProfilerResultsForTableModel;
import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Service that provides access to read requested data profiling results.
 */
public interface ProfilerDataService {
    /**
     * Retrieves the most recent table profiler results for a given table.
     * @param connectionName Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param dataStreamName Data stream name.
     * @return Profiler results for the given table.
     */
    ProfilerResultsForTableModel getMostRecentProfilerMetricsForTable(String connectionName,
                                                                      PhysicalTableName physicalTableName,
                                                                      String dataStreamName);
}
