package ai.dqo.data.statistics.normalization;

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;

import java.time.LocalDateTime;

/**
 * Normalization service that adapts the results received from a statistics collector into a normalized "statistics" results table.
 */
public interface StatisticsResultsNormalizationService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the statistics results storage. Returns a new table with fixed column types.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param profiledAt            Timestamp of the start of the statistics collector session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the profiler results table. Contains also a normalized results table.
     */
    StatisticsResultsNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                       LocalDateTime profiledAt,
                                                       SensorExecutionRunParameters sensorRunParameters);
}
