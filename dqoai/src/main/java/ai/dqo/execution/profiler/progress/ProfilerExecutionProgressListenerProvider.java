package ai.dqo.execution.profiler.progress;

/**
 * Profiler execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
public interface ProfilerExecutionProgressListenerProvider {
    /**
     * Returns a profiler execution progress listener for the requested reporting level.
     *
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the profiler to the console.
     * @return Profiler execution progress listener.
     */
    ProfilerExecutionProgressListener getProgressListener(ProfilerExecutionReportingMode reportingMode,
                                                          boolean writeSummaryToConsole);
}
