package ai.dqo.data.statistics.factory;

import tech.tablesaw.api.Table;

/**
 * Tablesaw table factory that creates a tabular object used to store the statistics results.
 */
public interface StatisticsResultsTableFactory {
    /**
     * Creates an empty normalized statistics results (basic profiles) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty statistics results table.
     */
    Table createEmptyStatisticsTable(String tableName);
}
