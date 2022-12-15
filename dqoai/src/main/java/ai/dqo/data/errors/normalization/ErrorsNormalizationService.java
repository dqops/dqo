package ai.dqo.data.errors.normalization;

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;

/**
 * Error normalization service that creates normalized error tables for errors, filling all possible fields.
 */
public interface ErrorsNormalizationService {
    /**
     * Creates an error result with one row for a sensor execution error.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data. Must be an error result with an exception.
     * @param timeSeriesGradient    Time series gradient.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    ErrorsNormalizedResult createNormalizedSensorErrorResults(SensorExecutionResult sensorExecutionResult,
                                                              TimeSeriesGradient timeSeriesGradient,
                                                              SensorExecutionRunParameters sensorRunParameters);

    /**
     * Creates an error result with one row for a rule execution error.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param timeSeriesGradient Time series gradient.
     * @param sensorRunParameters Sensor run parameters.
     * @param ruleEvaluationException Exception thrown at the rule evaluation.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    ErrorsNormalizedResult createNormalizedRuleErrorResults(SensorExecutionResult sensorExecutionResult,
                                                            TimeSeriesGradient timeSeriesGradient,
                                                            SensorExecutionRunParameters sensorRunParameters,
                                                            Exception ruleEvaluationException);
}
