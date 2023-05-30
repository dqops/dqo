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

import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Data quality sensor run service. Executes a sensor, reads the sensor values and returns it for further processing (rule evaluation).
 */
public interface DataQualitySensorRunner {
    /**
     * Prepare the sensor before it is executed on the data source.
     * @param executionContext DQO execution context that provides access to the DQO and user home.
     * @param sensorRunParameters Sensor run parameters (connection, table, column, sensor parameters).
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @return Sensor preparation result with a rendered sensor.
     */
    SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                      SensorExecutionRunParameters sensorRunParameters,
                                      SensorExecutionProgressListener progressListener);

    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param sensorPrepareResult Sensor preparation object with the information prepared by the sensor runner before it can execute the sensor.
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to cancel a running sensor query.
     * @return Sensor execution result with the query result from the sensor.
     */
    SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                        SensorPrepareResult sensorPrepareResult,
                                        SensorExecutionProgressListener progressListener,
                                        boolean dummySensorExecution,
                                        JobCancellationToken jobCancellationToken);
}
