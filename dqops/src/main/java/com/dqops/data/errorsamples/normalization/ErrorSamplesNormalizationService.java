/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.normalization;

import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;

import java.time.LocalDateTime;

/**
 * Normalization service that adapts the results received from error sampling queries into a normalized "error samples" results table.
 */
public interface ErrorSamplesNormalizationService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a data stream hash, sorts the data,
     * prepares the data for storing in the error samples results storage. Returns a new table with fixed column types.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param profiledAt            Timestamp of the start of the error sampler session. All profiled tables will get the same timestamp.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the error sampler results table. Contains also a normalized results table.
     */
    ErrorSamplesNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                                  LocalDateTime profiledAt,
                                                  SensorExecutionRunParameters sensorRunParameters);
}
