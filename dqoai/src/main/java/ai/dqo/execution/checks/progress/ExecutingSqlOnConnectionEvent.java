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

import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.metadata.sources.ConnectionSpec;

/**
 * Progress event raised before a sensor SQL is executed on a connection.
 */
public class ExecutingSqlOnConnectionEvent extends CheckExecutionProgressEvent {
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorDefinitionFindResult sensorDefinitions;
    private final ConnectionSpec connectionSpec;
    private final String renderedSql;

    /**
     * Creates an event object.
     *
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinitions   Sensor definition to identify the right sensor.
     * @param connectionSpec      Connection specification.
     * @param renderedSql         Rendered sensor sql that will be executed.
     */
    public ExecutingSqlOnConnectionEvent(SensorExecutionRunParameters sensorRunParameters,
										 SensorDefinitionFindResult sensorDefinitions,
										 ConnectionSpec connectionSpec,
										 String renderedSql) {
        this.sensorRunParameters = sensorRunParameters;
        this.sensorDefinitions = sensorDefinitions;
        this.connectionSpec = connectionSpec;
        this.renderedSql = renderedSql;
    }

    /**
     * Sensor run parameters.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sensor definition to identify the right sensor.
     *
     * @return Sensor definition to identify the right sensor.
     */
    public SensorDefinitionFindResult getSensorDefinitions() {
        return sensorDefinitions;
    }

    /**
     * Connection specification.
     *
     * @return Connection specification.
     */
    public ConnectionSpec getConnectionSpec() {
        return connectionSpec;
    }

    /**
     * Rendered sensor sql that will be executed.
     *
     * @return Rendered sensor sql that will be executed.
     */
    public String getRenderedSql() {
        return renderedSql;
    }
}
