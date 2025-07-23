/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.sources.TableSpec;

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
