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

package ai.dqo.execution.checks;

import ai.dqo.rules.RuleSeverityLevel;

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
