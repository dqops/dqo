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
package ai.dqo.execution.rules;

import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Rule execution parameters. This object is passed as an argument to the python rule.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class RuleExecutionRunParameters {
    private Double actualValue;
    private AbstractRuleParametersSpec parameters;
    private LocalDateTime timePeriodLocal;
    private HistoricDataPoint[] previousReadings;
    private RuleTimeWindowSettingsSpec timeWindow;

    /**
     * Default empty constructor.
     */
    public RuleExecutionRunParameters() {
    }

    /**
     * Constructor that fills the actual sensor value and the rule parameters.
     * @param actualValue Sensor actual value.
     * @param parameters Rule parameters.
     * @param timePeriodLocal Time period of the reading as a local date time.
     * @param previousReadings Array of previous sensor readings (could be null).
     * @param timeWindow Rule threshold time window configuration.
     */
    public RuleExecutionRunParameters(Double actualValue,
									  AbstractRuleParametersSpec parameters,
									  LocalDateTime timePeriodLocal,
									  HistoricDataPoint[] previousReadings,
									  RuleTimeWindowSettingsSpec timeWindow) {
        this.actualValue = actualValue;
        this.parameters = parameters;
        this.timePeriodLocal = timePeriodLocal;
        this.previousReadings = previousReadings;
        this.timeWindow = timeWindow;
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
     * Returns the time period of the reading.
     * @return Timestamp (in the local time zone) of the reading.
     */
    public LocalDateTime getTimePeriodLocal() {
        return timePeriodLocal;
    }

    /**
     * Sets the local date time of the reading (without the time zone).
     * @param timePeriodLocal Local date time of the reading.
     */
    public void setTimePeriodLocal(LocalDateTime timePeriodLocal) {
        this.timePeriodLocal = timePeriodLocal;
    }

    /**
     * Array of historic sensor readings for rules that require historic values for a number of past time periods to perform prediction.
     * @return Previous readings.
     */
    public HistoricDataPoint[] getPreviousReadings() {
        return previousReadings;
    }

    /**
     * Sets the array of historic sensor readings for predictive rules.
     * @param previousReadings Array of previous readings.
     */
    public void setPreviousReadings(HistoricDataPoint[] previousReadings) {
        this.previousReadings = previousReadings;
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
}
