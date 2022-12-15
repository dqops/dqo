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
package ai.dqo.execution.sensors.runners;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Base abstract class for sensor runners. Executes a sensor given a target data quality check.
 */
public abstract class AbstractSensorRunner {
    /**
     * Executes a sensor and returns the sensor result.
     * @param executionContext Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters   Sensor run parameters - connection, table, column, sensor parameters.
     * @param sensorDefinition      Sensor definition (both the core sensor definition and the provider specific sensor definition).
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Sensor result.
     */
    public abstract SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                                        SensorExecutionRunParameters sensorRunParameters,
                                                        SensorDefinitionFindResult sensorDefinition,
                                                        SensorExecutionProgressListener progressListener,
                                                        boolean dummySensorExecution);
}
