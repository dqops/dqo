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

import ai.dqo.connectors.ProviderType;
import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindService;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sensors.runners.SensorRunnerFactory;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Data quality sensor run service. Executes a sensor, reads the sensor values and returns it for further processing (rule evaluation).
 */
@Component
public class DataQualitySensorRunnerImpl implements DataQualitySensorRunner {
    private final SensorDefinitionFindService sensorDefinitionFindService;
    private final SensorRunnerFactory sensorRunnerFactory;

    /**
     * Creates a sensor runner.
     * @param sensorDefinitionFindService Sensor definition finder that finds the correct sensor definition.
     */
    @Autowired
    public DataQualitySensorRunnerImpl(SensorDefinitionFindService sensorDefinitionFindService, SensorRunnerFactory sensorRunnerFactory) {
        this.sensorDefinitionFindService = sensorDefinitionFindService;
        this.sensorRunnerFactory = sensorRunnerFactory;
    }

    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param sensorRunParameters Sensor run parameters (connection, table, column, sensor parameters).
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to cancel a running sensor query.
     * @return Sensor execution result with the query result from the sensor.
     */
    public SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                               SensorExecutionRunParameters sensorRunParameters,
                                               SensorExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               JobCancellationToken jobCancellationToken) {
        String sensorName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        ProviderType providerType = sensorRunParameters.getConnection().getProviderType();

        SensorDefinitionFindResult sensorDefinition = this.sensorDefinitionFindService.findProviderSensorDefinition(
                executionContext, sensorName, providerType);
        ProviderSensorDefinitionSpec providerSensorSpec = sensorDefinition.getProviderSensorDefinitionSpec();
        AbstractSensorRunner sensorRunner = this.sensorRunnerFactory.getSensorRunner(providerSensorSpec.getType(),
                providerSensorSpec.getJavaClassName());

        SensorExecutionResult result = sensorRunner.executeSensor(executionContext, sensorRunParameters,
                sensorDefinition, progressListener, dummySensorExecution, jobCancellationToken);
        return result;
    }
}
