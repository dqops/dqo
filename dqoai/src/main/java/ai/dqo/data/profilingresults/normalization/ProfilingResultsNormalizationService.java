package ai.dqo.data.profilingresults.normalization;

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;

import java.time.LocalDateTime;

/**
 * Normalization service that adapts the results received from a profiler into a normalized "profiler results" table.
 */
public interface ProfilingResultsNormalizationService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the profiler results storage. Returns a new table with fixed column types.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param profiledAt            Timestamp of the start of the profiler session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the profiler results table. Contains also a normalized results table.
     */
    ProfilingResultsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                      LocalDateTime profiledAt,
                                                      SensorExecutionRunParameters sensorRunParameters);
}
