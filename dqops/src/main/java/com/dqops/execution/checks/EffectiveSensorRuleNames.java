/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks;

/**
 * Model object that contains the sensor name and rule name that will be used to execute a check or a statistics profiler.
 */
public class EffectiveSensorRuleNames {
    private String sensorName;
    private String ruleName;

    public EffectiveSensorRuleNames() {
    }

    /**
     * Creates a pair of a sensor name and rule name used for the check execution.
     * @param sensorName Sensor name.
     * @param ruleName Rule name .
     */
    public EffectiveSensorRuleNames(String sensorName, String ruleName) {
        this.sensorName = sensorName;
        this.ruleName = ruleName;
    }

    /**
     * Returns the sensor name.
     * @return Sensor name.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets the sensor name.
     * @param sensorName Sensor name.
     */
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    /**
     * Returns the rule name.
     * @return Rule name.
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets a rule name.
     * @param ruleName Rule name.
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
