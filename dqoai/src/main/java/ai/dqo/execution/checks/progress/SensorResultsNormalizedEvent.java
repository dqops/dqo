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
package ai.dqo.execution.checks.progress;

import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised after sensor results returned from the sensor were normalized to a standard tabular format.
 */
public class SensorResultsNormalizedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorExecutionResult sensorResult;
    private final SensorReadoutsNormalizedResult normalizedSensorResults;

    /**
     * Creates a progress event.
     *
     * @param tableSpec               Target table.
     * @param sensorRunParameters     Sensor run parameters with a direct identification of the sensor.
     * @param sensorResult            Raw sensor results (before normalization).
     * @param normalizedSensorResults Normalized sensor results.
     */
    public SensorResultsNormalizedEvent(TableSpec tableSpec,
										SensorExecutionRunParameters sensorRunParameters,
										SensorExecutionResult sensorResult,
										SensorReadoutsNormalizedResult normalizedSensorResults) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.sensorResult = sensorResult;
        this.normalizedSensorResults = normalizedSensorResults;
    }

    /**
     * Target table.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters that were used to run the sensor.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sensor results (raw).
     *
     * @return Raw sensor results.
     */
    public SensorExecutionResult getSensorResult() {
        return sensorResult;
    }

    /**
     * Normalized sensor results that will be passed to the rule evaluator.
     *
     * @return Normalized sensor results.
     */
    public SensorReadoutsNormalizedResult getNormalizedSensorResults() {
        return normalizedSensorResults;
    }
}
