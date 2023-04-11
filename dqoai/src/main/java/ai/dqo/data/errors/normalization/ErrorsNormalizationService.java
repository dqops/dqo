/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
