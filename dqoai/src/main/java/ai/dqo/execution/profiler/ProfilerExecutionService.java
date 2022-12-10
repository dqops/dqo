package ai.dqo.execution.profiler;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.profiler.progress.ProfilerExecutionProgressListener;
import ai.dqo.metadata.search.ProfilerSearchFilters;

/**
 * Profiler execution service. Executes profilers on tables and columns.
 */
public interface ProfilerExecutionService {
    /**
     * Executes data profilers on tables and columns. Reports progress and saves the results.
     *
     * @param executionContext      Check/profiler execution context with access to the user home and dqo home.
     * @param profilerSearchFilters Profiler search filters to find the right checks.
     * @param progressListener      Progress listener that receives progress calls.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Profiler summary table with the count of executed and successful profile executions for each table.
     */
    ProfilerExecutionSummary executeProfilers(ExecutionContext executionContext,
                                              ProfilerSearchFilters profilerSearchFilters,
                                              ProfilerExecutionProgressListener progressListener,
                                              boolean dummySensorExecution);
}
