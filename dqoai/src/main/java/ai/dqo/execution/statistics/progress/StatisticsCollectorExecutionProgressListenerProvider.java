package ai.dqo.execution.statistics.progress;

/**
 * Statistics collectors execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
public interface StatisticsCollectorExecutionProgressListenerProvider {
    /**
     * Returns a statistics collectors execution progress listener for the requested reporting level.
     *
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the profiler to the console.
     * @return Statistics collectors execution progress listener.
     */
    StatisticsCollectorExecutionProgressListener getProgressListener(StatisticsCollectorExecutionReportingMode reportingMode,
                                                                     boolean writeSummaryToConsole);
}
