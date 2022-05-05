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
package ai.dqo.data.readings.normalization;

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;

/**
 * Service that parses datasets with results returned by a sensor query.
 * Detects column types (dimension columns), describes the metadata of the result. Also fixes missing information, adds a dimension_id column with a hash of all grouping dimensions.
 */
public interface SensorResultNormalizeService {
    /**
     * Analyzes a given dataset, fixes wrong column types, calculates a dimension hash, sorts the data,
     * prepares the data for using in a sensor. Returns a new table with fixed column types.
     *
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param timeSeriesGradient    Time series gradient.
     * @param sensorRunParameters   Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    SensorNormalizedResult normalizeResults(SensorExecutionResult sensorExecutionResult,
                                            TimeSeriesGradient timeSeriesGradient,
                                            SensorExecutionRunParameters sensorRunParameters);
}
