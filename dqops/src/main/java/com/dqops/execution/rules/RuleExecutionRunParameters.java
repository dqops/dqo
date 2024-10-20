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
package com.dqops.execution.rules;

import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * Rule execution parameters. This object is passed as an argument to the python rule.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class RuleExecutionRunParameters {
    private Double actualValue;
    private Double expectedValue;
    private AbstractRuleParametersSpec parameters;
    private long timePeriodLocalEpoch;
    private HistoricDataPoint[] previousReadouts;
    private RuleTimeWindowSettingsSpec timeWindow;
    private Map<String, String> configurationParameters;

    /**
     * Default empty constructor.
     */
    public RuleExecutionRunParameters() {
    }

    /**
     * Constructor that fills the actual sensor value and the rule parameters.
     * @param actualValue Sensor actual value.
     * @param expectedValue Optional expected value returned by the sensor.
     * @param parameters Rule parameters.
     * @param timePeriodLocal Time period of the readouts as a local date time.
     * @param previousReadouts Array of previous sensor readouts (may have a null value).
     * @param timeWindow Rule threshold time window configuration.
     * @param configurationParameters Optional configuration parameters that are configured in the rule configuration.
     */
    public RuleExecutionRunParameters(Double actualValue,
                                      Double expectedValue,
									  AbstractRuleParametersSpec parameters,
									  LocalDateTime timePeriodLocal,
									  HistoricDataPoint[] previousReadouts,
									  RuleTimeWindowSettingsSpec timeWindow,
                                      Map<String, String> configurationParameters) {
        this.actualValue = actualValue;
        this.expectedValue = expectedValue;
        this.parameters = parameters;
        this.timePeriodLocalEpoch = timePeriodLocal != null ? timePeriodLocal.toEpochSecond(ZoneOffset.UTC) : 0L;
        this.previousReadouts = previousReadouts;
        this.timeWindow = timeWindow;
        this.configurationParameters = configurationParameters;
    }

    /**
     * Actual sensor value that was returned by a sensor.
     * @return Sensor value.
     */
    public Double getActualValue() {
        return actualValue;
    }

    /**
     * Sets a sensor value.
     * @param actualValue Actual sensor value returned by a sensor.
     */
    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    /**
     * Returns an optional expected value that is returned by a sensor.
     * @return Optional expected value.
     */
    public Double getExpectedValue() {
        return expectedValue;
    }

    /**
     * Sets an optional expected value returned by the sensor.
     * @param expectedValue Optional expected value.
     */
    public void setExpectedValue(Double expectedValue) {
        this.expectedValue = expectedValue;
    }

    /**
     * Rule parameters configured for a single threshold level.
     * @return Rule parameters.
     */
    public AbstractRuleParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the rule configuration parameters.
     * @param parameters Rule configuration parameters.
     */
    public void setParameters(AbstractRuleParametersSpec parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns the time period of the readout.
     * @return Timestamp (in the local time zone) of the readout.
     */
    public long getTimePeriodLocalEpoch() {
        return timePeriodLocalEpoch;
    }

    /**
     * Sets the local date time of the readout (without the time zone).
     * @param timePeriodLocalEpoch Local date time of the readout.
     */
    public void setTimePeriodLocalEpoch(long timePeriodLocalEpoch) {
        this.timePeriodLocalEpoch = timePeriodLocalEpoch;
    }

    /**
     * Array of historic sensor readouts for rules that require historic values for a number of past time periods to perform prediction.
     * @return Previous readouts.
     */
    public HistoricDataPoint[] getPreviousReadouts() {
        return previousReadouts;
    }

    /**
     * Sets the array of historic sensor readouts for predictive rules.
     * @param previousReadouts Array of previous readouts.
     */
    public void setPreviousReadouts(HistoricDataPoint[] previousReadouts) {
        this.previousReadouts = previousReadouts;
    }

    /**
     * Time window configuration retrieved from the sensor definition or overridden in the rule threshold configuration.
     * @return Time window configuration.
     */
    public RuleTimeWindowSettingsSpec getTimeWindow() {
        return timeWindow;
    }

    /**
     * Sets the time window configuration.
     * @param timeWindow Time window configuration.
     */
    public void setTimeWindow(RuleTimeWindowSettingsSpec timeWindow) {
        this.timeWindow = timeWindow;
    }

    /**
     * Returns the additional rule configuration parameters.
     * @return Rule configuration parameters.
     */
    public Map<String, String> getConfigurationParameters() {
        return configurationParameters;
    }

    /**
     * Sets the additional rule configuration parameters.
     * @param configurationParameters Additional rule configuration parameters.
     */
    public void setConfigurationParameters(Map<String, String> configurationParameters) {
        this.configurationParameters = configurationParameters;
    }
}
