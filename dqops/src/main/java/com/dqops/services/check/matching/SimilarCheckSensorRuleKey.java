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
package com.dqops.services.check.matching;

import java.util.Objects;

/**
 * Key object that stores a sensor name and rule names. It is used to find similar checks in other check types.
 * It supports equals and hashcode.
 */
public class SimilarCheckSensorRuleKey {
    private String sensorName;
    private Class<?> sensorParametersClass;
    private String warningRuleName;
    private String errorRuleName;
    private String fatalRuleName;

    /**
     * Creates a check sensor and rule names object.
     * @param sensorName Sensor name.
     * @param sensorParametersClass Sensor parameters class, used to identify alternative versions of sensor parameters (for the mean sensor).
     * @param warningRuleName Rule name for the warning rule.
     * @param errorRuleName Rule name for the error rule.
     * @param fatalRuleName Rule name for the fatal rule.
     */
    public SimilarCheckSensorRuleKey(String sensorName, Class<?> sensorParametersClass, String warningRuleName, String errorRuleName, String fatalRuleName) {
        this.sensorName = sensorName;
        this.sensorParametersClass = sensorParametersClass;
        this.warningRuleName = warningRuleName;
        this.errorRuleName = errorRuleName;
        this.fatalRuleName = fatalRuleName;
    }

    /**
     * Returns the sensor name.
     * @return Sensor name.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * The class type that implements the sensor parameters.
     * @return Sensor parameters class.
     */
    public Class<?> getSensorParametersClass() {
        return sensorParametersClass;
    }

    /**
     * Returns the rule name used for the "warning" severity.
     * @return Warning rule name.
     */
    public String getWarningRuleName() {
        return warningRuleName;
    }

    /**
     * Returns the rule name fo the error severity.
     * @return Rule name for the error severity.
     */
    public String getErrorRuleName() {
        return errorRuleName;
    }

    /**
     * Return the rule name for the fatal severity.
     * @return Rule name for the fatal severity.
     */
    public String getFatalRuleName() {
        return fatalRuleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimilarCheckSensorRuleKey that = (SimilarCheckSensorRuleKey) o;

        if (!Objects.equals(sensorName, that.sensorName)) return false;
        if (!Objects.equals(sensorParametersClass, that.sensorParametersClass)) return false;
        if (!Objects.equals(warningRuleName, that.warningRuleName))
            return false;
        if (!Objects.equals(errorRuleName, that.errorRuleName))
            return false;
        return Objects.equals(fatalRuleName, that.fatalRuleName);
    }

    @Override
    public int hashCode() {
        int result = sensorName != null ? sensorName.hashCode() : 0;
        result = 31 * result + (sensorParametersClass != null ? sensorParametersClass.hashCode() : 0);
        result = 31 * result + (warningRuleName != null ? warningRuleName.hashCode() : 0);
        result = 31 * result + (errorRuleName != null ? errorRuleName.hashCode() : 0);
        result = 31 * result + (fatalRuleName != null ? fatalRuleName.hashCode() : 0);
        return result;
    }
}
