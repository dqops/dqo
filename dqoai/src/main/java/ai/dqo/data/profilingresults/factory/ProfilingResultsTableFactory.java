package ai.dqo.data.profilingresults.factory;

import tech.tablesaw.api.Table;

/**
 * Tablesaw table factory that creates a tabular object used to store the profiling results.
 */
public interface ProfilingResultsTableFactory {
    /**
     * Creates an empty normalized profiling results (profiles) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty profiling results table.
     */
    Table createEmptyProfilingResultsTable(String tableName);
}
