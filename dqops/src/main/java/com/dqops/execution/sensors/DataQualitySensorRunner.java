/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.PreparedSensorsGroup;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;

import java.util.Collection;
import java.util.List;

/**
 * Data quality sensor run service. Executes a sensor, reads the sensor values and returns it for further processing (rule evaluation).
 */
public interface DataQualitySensorRunner {
    /**
     * Prepare the sensor before it is executed on the data source.
     * @param executionContext DQOps execution context that provides access to the DQOps and user home.
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

    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param preparedSensorsGroup Object with a list of merged prepared sensors that will be executed by a single call to the data source.
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to cancel a running sensor query.
     * @return Collection of sensor group execution result with the query result from an executor. May contain results of multiple sensors that were merged.
     *         The result is a collection because when a sensor fails, then the query could be split and executed again.
     */
    List<GroupedSensorExecutionResult> executeGroupedSensors(ExecutionContext executionContext,
                                                             PreparedSensorsGroup preparedSensorsGroup,
                                                             SensorExecutionProgressListener progressListener,
                                                             boolean dummySensorExecution,
                                                             JobCancellationToken jobCancellationToken);
}
