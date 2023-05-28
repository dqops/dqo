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

import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Result object that stores all objects required to execute a data quality sensor before it can be executed on the data source connection.
 * For sensors based on SQL templates, it is a rendered SQL template.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SensorPrepareResult {
    private SensorExecutionRunParameters sensorRunParameters;
    private boolean success = true;
    private Throwable prepareException;
    private SensorDefinitionFindResult sensorDefinition;
    private AbstractSensorRunner sensorRunner;
    private String renderedSensorSql;

    public SensorPrepareResult() {
    }

    /**
     * Creates a sensor prepare result, before the sensor runner is asked to continue the sensor preparation and fill the remaining fields in this object.
     * @param sensorRunParameters Sensor run parameters used to run the sensor.
     * @param sensorDefinition Sensor definition with the sensor configuration and the SQL template (for templated sensors).
     * @param sensorRunner  Sensor runner instance that will run the sensor.
     */
    public SensorPrepareResult(SensorExecutionRunParameters sensorRunParameters,
                               SensorDefinitionFindResult sensorDefinition,
                               AbstractSensorRunner sensorRunner) {
        this.sensorRunParameters = sensorRunParameters;
        this.sensorDefinition = sensorDefinition;
        this.sensorRunner = sensorRunner;
    }

    /**
     * Returns the sensor execution parameters that will be used to execute the sensor.
     * @return Sensor execution run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sets the sensor execution run parameters with all details required to execute the sensor.
     * @param sensorRunParameters Sensor run parameters.
     */
    public void setSensorRunParameters(SensorExecutionRunParameters sensorRunParameters) {
        this.sensorRunParameters = sensorRunParameters;
    }

    /**
     * Returns true if the sensor managed to prepare (render the SQL template for templated sensors). When the success is false, the exception should provide more detailed information about the issue.
     * @return True when the sensor managed to prepare.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status if the check managed to prepare for execution. When the success is false, the exception should provide more detailed information about the issue.
     * @param success True when the sensor successfully prepared, false when it failed - a problem with the SQL sensor.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns an exception if the sensor failed to prepare.
     * @return An exception or null when no exception was raised.
     */
    public Throwable getPrepareException() {
        return prepareException;
    }

    /**
     * Stores an exception that was caught.
     * @param prepareException Exception that was caught.
     */
    public void setPrepareException(Throwable prepareException) {
        if (prepareException != null) {
            this.success = false;
        }
        this.prepareException = prepareException;
    }

    /**
     * Returns the sensor definition that was found in DQO.
     * @return Sensor definition that was found.
     */
    public SensorDefinitionFindResult getSensorDefinition() {
        return sensorDefinition;
    }

    /**
     * Sets the sensor definition that will be used.
     * @param sensorDefinition The sensor definition to be used.
     */
    public void setSensorDefinition(SensorDefinitionFindResult sensorDefinition) {
        this.sensorDefinition = sensorDefinition;
    }

    /**
     * Returns a sensor runner that will be used to execute the sensor.
     */
    public AbstractSensorRunner getSensorRunner() {
        return sensorRunner;
    }

    /**
     * Sets a reference to a sensor runner that will be used to run the sensor.
     * @param sensorRunner Sensor runner to be used.
     */
    public void setSensorRunner(AbstractSensorRunner sensorRunner) {
        this.sensorRunner = sensorRunner;
    }

    /**
     * Returns a rendered SQL template that should be executed on the monitored data source.
     * @return Rendered SQL.
     */
    public String getRenderedSensorSql() {
        return renderedSensorSql;
    }

    /**
     * Sets the rendered SQL that will be executed on the monitored data source.
     * @param renderedSensorSql Rendered sensor SQL.
     */
    public void setRenderedSensorSql(String renderedSensorSql) {
        this.renderedSensorSql = renderedSensorSql;
    }
}
