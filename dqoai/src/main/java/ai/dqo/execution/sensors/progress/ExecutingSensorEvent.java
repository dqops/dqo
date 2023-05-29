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
package ai.dqo.execution.sensors.progress;

import ai.dqo.execution.checks.progress.CheckExecutionProgressEvent;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
 */
public class ExecutingSensorEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorPrepareResult sensorPrepareResult;

    /**
     * Creates an event.
     *
     * @param tableSpec           Target table.
     * @param sensorPrepareResult Sensor run parameters.
     */
    public ExecutingSensorEvent(TableSpec tableSpec, SensorPrepareResult sensorPrepareResult) {
        this.tableSpec = tableSpec;
        this.sensorPrepareResult = sensorPrepareResult;
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
     * Sensor execution parameters that will be passed to the sensor.
     *
     * @return Sensor execution parameters.
     */
    public SensorPrepareResult getSensorPrepareResult() {
        return sensorPrepareResult;
    }
}
