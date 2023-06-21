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

import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised after a rule was executed and it failed (returned an exception).
 */
public class RuleFailedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorExecutionResult sensorResult;
    private Throwable ruleExecutionException;
    private String ruleDefinitionName;

    /**
     * Creates an event.
     *
     * @param tableSpec              Target table.
     * @param sensorRunParameters    Sensor run parameters.
     * @param sensorResult           Sensor results (raw, not normalized).
     * @param ruleExecutionException Exception thrown by the sensor.
     * @param ruleDefinitionName     Rule definition name.
     */
    public RuleFailedEvent(TableSpec tableSpec,
                           SensorExecutionRunParameters sensorRunParameters,
                           SensorExecutionResult sensorResult,
                           Throwable ruleExecutionException,
                           String ruleDefinitionName) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.sensorResult = sensorResult;
        this.ruleExecutionException = ruleExecutionException;
        this.ruleDefinitionName = ruleDefinitionName;
    }

    /**
     * Table specification.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters that were given to the sensor.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Raw sensor results that were returned from the sensor, probably columns use different data types.
     *
     * @return Raw sensor results.
     */
    public SensorExecutionResult getSensorResult() {
        return sensorResult;
    }

    /**
     * Returns the exception that was thrown by the rule.
     * @return Exception thrown by the rule.
     */
    public Throwable getRuleExecutionException() {
        return ruleExecutionException;
    }

    /**
     * Returns the rule definition name of the rule that failed.
     * @return Rule definition name.
     */
    public String getRuleDefinitionName() {
        return ruleDefinitionName;
    }
}
