/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.runners;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import tech.tablesaw.api.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Base abstract class for sensor runners. Executes a sensor given a target data quality check.
 */
public abstract class AbstractSensorRunner {
    protected DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Dependency injection constructor.
     * @param defaultTimeZoneProvider The default time zone provider.
     */
    protected AbstractSensorRunner(DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Executes a sensor and returns the sensor result.
     * @param executionContext      Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult   Sensor preparation result, contains the sensor definition, rendered SQL template, sensor run parameters (connection, table, etc.).
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken  Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    @Deprecated
    public abstract SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                                        SensorPrepareResult sensorPrepareResult,
                                                        SensorExecutionProgressListener progressListener,
                                                        boolean dummySensorExecution,
                                                        JobCancellationToken jobCancellationToken);

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition    Sensor definition that was found in the dqo home or the user home.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    public abstract SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                                      SensorExecutionRunParameters sensorRunParameters,
                                                      SensorDefinitionFindResult sensorDefinition,
                                                      SensorExecutionProgressListener progressListener);

    /**
     * Transforms the sensor result that was captured by the sensor executor. This method performs de-grouping of grouped sensors that were executed as multiple SQL queries merged into one big query.
     * @param executionContext      Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param groupedSensorExecutionResult Sensor execution result with the data retrieved from the data source. It will be adapted to a sensor result for one sensor.
     * @param sensorPrepareResult   Original sensor prepare results for this sensor. Contains also the sensor run parameters.
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param jobCancellationToken  Job cancellation token, may cancel a running query.
     * @return Sensor result for one sensor.
     */
    public abstract SensorExecutionResult extractSensorResults(ExecutionContext executionContext,
                                                               GroupedSensorExecutionResult groupedSensorExecutionResult,
                                                               SensorPrepareResult sensorPrepareResult,
                                                               SensorExecutionProgressListener progressListener,
                                                               JobCancellationToken jobCancellationToken);
}
