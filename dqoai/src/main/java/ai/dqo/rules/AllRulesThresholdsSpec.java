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
package ai.dqo.rules;

import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.rules.averages.PercentMovingAverageRuleThresholdsSpec;
import ai.dqo.rules.comparison.MinValueRuleThresholdsSpec;
import ai.dqo.rules.comparison.ValueEqualsRuleThresholdsSpec;
import ai.dqo.rules.custom.CustomRuleThresholdsMap;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container object that has a list of all built-in rules to pick just one.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AllRulesThresholdsSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AllRulesThresholdsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
			put("min_value", o -> o.minValue);
			put("value_equals", o -> o.valueEquals);
			put("percent_above_moving_average", o -> o.percentAboveMovingAverage);
			put("custom", o -> o.custom);
        }
    };

    @JsonPropertyDescription("Minimum accepted sensor value")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinValueRuleThresholdsSpec minValue;

    @JsonPropertyDescription("Sensor value equals a given value (within a delta range)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ValueEqualsRuleThresholdsSpec valueEquals;

    @JsonPropertyDescription("The current sensor reading is not more than X percent above the moving average of previous readings")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleThresholdsSpec percentAboveMovingAverage;

    @JsonPropertyDescription("Dictionary of custom rules. Use a business friendly name as a key and configure a custom rule for each entry.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomRuleThresholdsMap custom;

    /**
     * Gets the minimum accepted sensor value rule.
     * @return Rule.
     */
    public MinValueRuleThresholdsSpec getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum accepted value rule.
     * @param minValue Rule.
     */
    public void setMinValue(MinValueRuleThresholdsSpec minValue) {
		this.setDirtyIf(!Objects.equals(this.minValue, minValue));
        this.minValue = minValue;
		propagateHierarchyIdToField(minValue, "min_value");
    }

    /**
     * Value equals rule.
     * @return Rule.
     */
    public ValueEqualsRuleThresholdsSpec getValueEquals() {
        return valueEquals;
    }

    /**
     * Sets the value equals rule.
     * @param valueEquals Rule.
     */
    public void setValueEquals(ValueEqualsRuleThresholdsSpec valueEquals) {
		this.setDirtyIf(!Objects.equals(this.valueEquals, valueEquals));
        this.valueEquals = valueEquals;
		propagateHierarchyIdToField(valueEquals, "value_equals");
    }

    /**
     * Returns a rule that the current sensor reading is not more than X percent above the moving average of previous readings
     * @return Percent above moving average rule.
     */
    public PercentMovingAverageRuleThresholdsSpec getPercentAboveMovingAverage() {
        return percentAboveMovingAverage;
    }

    /**
     * Sets the rule that the value is not above the X percent of the moving average.
     * @param percentAboveMovingAverage Rule thresholds.
     */
    public void setPercentAboveMovingAverage(PercentMovingAverageRuleThresholdsSpec percentAboveMovingAverage) {
		this.setDirtyIf(!Objects.equals(this.percentAboveMovingAverage, percentAboveMovingAverage));
        this.percentAboveMovingAverage = percentAboveMovingAverage;
		propagateHierarchyIdToField(percentAboveMovingAverage, "percent_above_moving_average");
    }

    /**
     * Gets a dictionary of custom rules.
     * @return Dictionary of custom rules.
     */
    public CustomRuleThresholdsMap getCustom() {
        return custom;
    }

    /**
     * Sets a dictionary of custom rules.
     * @param custom Dictionary of custom rules.
     */
    public void setCustom(CustomRuleThresholdsMap custom) {
		this.setDirtyIf(!Objects.equals(this.custom, custom));
        this.custom = custom;
		propagateHierarchyIdToField(custom, "custom");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
