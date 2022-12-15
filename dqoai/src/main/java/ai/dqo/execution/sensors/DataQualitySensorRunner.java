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
package ai.dqo.execution.sensors;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Data quality sensor run service. Executes a sensor, reads the sensor values and returns it for further processing (rule evaluation).
 */
public interface DataQualitySensorRunner {
    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param sensorRunParameters Sensor run parameters (connection, table, column, sensor parameters).
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Sensor execution result with the query result from the sensor.
     */
    SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                        SensorExecutionRunParameters sensorRunParameters,
                                        SensorExecutionProgressListener progressListener,
                                        boolean dummySensorExecution);
}
