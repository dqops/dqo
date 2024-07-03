/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
