/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.normalization;

import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;

/**
 * Error normalization service that creates normalized error tables for errors, filling all possible fields.
 */
public interface ErrorsNormalizationService {
    /**
     * Creates an error result with one row for a sensor execution error.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data. Must be an error result with an exception.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    ErrorsNormalizedResult createNormalizedSensorErrorResults(SensorExecutionResult sensorExecutionResult,
                                                              SensorExecutionRunParameters sensorRunParameters);

    /**
     * Creates an error result with one row for a rule execution error.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param sensorRunParameters Sensor run parameters.
     * @param ruleEvaluationException Exception thrown at the rule evaluation.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    ErrorsNormalizedResult createNormalizedRuleErrorResults(SensorExecutionResult sensorExecutionResult,
                                                            SensorExecutionRunParameters sensorRunParameters,
                                                            Throwable ruleEvaluationException);
}
