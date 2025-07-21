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

import com.dqops.connectors.ConnectionQueryException;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerStub;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for DataQualitySensorRunner.
 */
public class DataQualitySensorRunnerObjectMother {
    /**
     * Returns the dfault data quality sensor runner that runs a single sensor on a real connection.
     * @return Data quality sensor runner.
     */
    public static DataQualitySensorRunnerImpl getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return (DataQualitySensorRunnerImpl) beanFactory.getBean(DataQualitySensorRunner.class);
    }

    /**
     * Executes a sensor given the execution context (both the user home and dqo home) and the check run parameters.
     * @param executionContext Check execution context (user home and dqo home).
     * @param sensorRunParameters Sensor run parameters.
     * @return Sensor execution result.
     */
    public static SensorExecutionResult executeSensor(ExecutionContext executionContext, SensorExecutionRunParameters sensorRunParameters) {
        DataQualitySensorRunnerImpl sensorRunner = getDefault();
        CheckExecutionProgressListenerStub progressListener = new CheckExecutionProgressListenerStub();
        SensorPrepareResult sensorPrepareResult = sensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);
        if (!sensorPrepareResult.isSuccess()) {
            throw new ConnectionQueryException("Failed to prepare a sensor (render the SQL): " + sensorPrepareResult.getPrepareException().getMessage(),
                    sensorPrepareResult.getPrepareException());
        }

        SensorExecutionResult sensorExecutionResult = sensorRunner.executeSensor(executionContext, sensorPrepareResult, progressListener, false,
                JobCancellationToken.createDummyJobCancellationToken());
        if (!sensorExecutionResult.isSuccess()) {
            throw new ConnectionQueryException("Failed to execute a sensor: " + sensorExecutionResult.getException().getMessage(), sensorExecutionResult.getException());
        }
        return sensorExecutionResult;
    }

    /**
     * Executes a sensor given the user home and the sensor run parameters. Uses the default dqo home (built-in sensors) at DQO_HOME.
     * @param userHomeContext User home context.
     * @param sensorRunParameters Sensor run parameters.
     * @return Sensor execution result.
     */
    public static SensorExecutionResult executeSensor(UserHomeContext userHomeContext, SensorExecutionRunParameters sensorRunParameters) {
        ExecutionContext executionContext = new ExecutionContext(userHomeContext, DqoHomeContextObjectMother.getRealDqoHomeContext());
        SensorExecutionResult sensorExecutionResult = executeSensor(executionContext, sensorRunParameters);
        if (!sensorExecutionResult.isSuccess()) {
            throw new ConnectionQueryException("Failed to execute a sensor: " + sensorExecutionResult.getException().getMessage(), sensorExecutionResult.getException());
        }
        return sensorExecutionResult;
    }
}
