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

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Base class for a list of data quality thresholds that are using the same data quality check definition.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRuleThresholdsSpec<R extends AbstractRuleParametersSpec> extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractRuleThresholdsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("high", o -> o.getHigh());
			put("medium", o -> o.getMedium());
			put("low", o -> o.getLow());
			put("time_window", o -> o.timeWindow);
        }
    };

    /**
     * Default constructor. Creates a default time window if a rule requires a time window (implements {@link TimeWindowedRule}).
     */
    public AbstractRuleThresholdsSpec() {
        if (this instanceof TimeWindowedRule) {
			this.timeWindow = new RuleTimeWindowSettingsSpec(); // create the default time window
        }
    }

    /**
     * Alerting rules configuration that raise "HIGH" severity alerts for unsatisfied rules.
     * @return High severity alert rule parameters.
     */
    public abstract R getHigh();

    /**
     * Alerting rules configuration that raise "MEDIUM" severity alerts for unsatisfied rules.
     * @return Medium severity rule parameters.
     */
    public abstract R getMedium();

    /**
     * Alerting rules configuration that raise "LOW" severity alerts for unsatisfied rules.
     * @return Low severity rule parameters.
     */
    public abstract R getLow();

    @JsonPropertyDescription("Time window configuration for rules that require historic data for evaluation. The time window is configured as the number of previous time periods that are required to evaluate a sensor. The time period granularity (day, hour, etc.) is configured as a time_series configuration on the sensor.")
    private RuleTimeWindowSettingsSpec timeWindow;

    @JsonPropertyDescription("Disable the rule at all severity levels.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disable;

    /**
     * Disable the quality rule and prevent it from executing.
     * @return Rule is disabled.
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * Changes the disabled flag of a quality rule.
     * @param disable When true, the test will be disabled and will not be executed.
     */
    public void setDisable(boolean disable) {
		this.setDirtyIf(this.disable != disable);
        this.disable = disable;
    }

    /**
     * Returns the time window configuration.
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
		this.setDirtyIf(!Objects.equals(this.timeWindow, timeWindow));
        this.timeWindow = timeWindow;
		propagateHierarchyIdToField(timeWindow, "time_window");
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Checks if the rule is enabled and at least one severity level threshold is also enabled.
     * @return True when at least one rule can be executed, false when no rules are configured or all rules are disabled.
     */
    @JsonIgnore
    public boolean isEnabled() {
        if (this.disable) {
            return false;
        }

        R high = getHigh();
        if (high != null && !high.isDisable()) {
            return true;
        }

        R medium = getMedium();
        if (medium != null && !medium.isDisable()) {
            return true;
        }

        R low = getLow();
		return low != null && !low.isDisable();
	}

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        if (!super.isDefault()) {
            return false;
        }

        return !this.disable;
    }

    /**
     * Returns a rule name returned from the hierarchy id.
     * @return Rule name.
     */
    @JsonIgnore
    public String getRuleName() {
        return this.getHierarchyId().getLast().toString();
    }
}
