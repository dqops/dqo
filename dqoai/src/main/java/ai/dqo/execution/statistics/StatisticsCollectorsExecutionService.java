package ai.dqo.execution.statistics;

import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;

/**
 * Statistics collector execution service. Executes statistics collectors on tables and columns.
 */
public interface StatisticsCollectorsExecutionService {
    /**
     * Executes data statistics collectors on tables and columns. Reports progress and saves the results.
     *
     * @param executionContext      Check/collector execution context with access to the user home and dqo home.
     * @param statisticsCollectorSearchFilters Collector search filters to find the right checks.
     * @param progressListener      Progress listener that receives progress calls.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Collector summary table with the count of executed and successful profile executions for each table.
     */
    StatisticsCollectionExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                                     StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                     StatisticsCollectorExecutionProgressListener progressListener,
                                                                     StatisticsDataScope statisticsDataScope,
                                                                     boolean dummySensorExecution);
}
