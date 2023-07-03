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
