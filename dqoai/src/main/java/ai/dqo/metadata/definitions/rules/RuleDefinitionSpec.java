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
package ai.dqo.metadata.definitions.rules;

import ai.dqo.execution.rules.runners.python.PythonRuleRunner;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Custom data quality rule specification. Provides the custom rule configuration. For example, rules that require a range of historic values will have this configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class RuleDefinitionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<RuleDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("time_window", o -> o.timeWindow);
        }
    };

    @JsonPropertyDescription("Rule runner type")
    private RuleRunnerType type = RuleRunnerType.python;

    @JsonPropertyDescription("Java class name for a rule runner that will execute the sensor. The \"type\" must be \"java_class\".")
    private String javaClassName = PythonRuleRunner.CLASS_NAME;

    @JsonPropertyDescription("Rule historic (past) values mode. A rule may require just the current sensor reading or use sensor readings from past periods to perform prediction. The number of time windows is configured in the time_window setting.")
    private RuleTimeWindowMode mode = RuleTimeWindowMode.current_value;

    @JsonPropertyDescription("Rule time window configuration when the mode is previous_readings. Configures the number of past time windows (sensor readings) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.")
    private RuleTimeWindowSettingsSpec timeWindow;

    @JsonPropertyDescription("Additional rule parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> params = new LinkedHashMap<>();

    @JsonIgnore
    private LinkedHashMap<String, String> originalParams = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

    /**
     * Rule implementation type.
     * @return Rule implementation type.
     */
    public RuleRunnerType getType() {
        return type;
    }

    /**
     * Sets the rule implementation type.
     * @param type Rule type.
     */
    public void setType(RuleRunnerType type) {
		this.setDirtyIf(this.type != type);
        this.type = type;
    }

    /**
     * Returns a java class name for a rule runner.
     * @return Java class for a rule runner.
     */
    public String getJavaClassName() {
        return javaClassName;
    }

    /**
     * Sets a java class for a rule runner.
     * @param javaClassName Rule runner class name.
     */
    public void setJavaClassName(String javaClassName) {
		this.setDirtyIf(!Objects.equals(this.javaClassName, javaClassName));
        this.javaClassName = javaClassName;
    }

    /**
     * Gets the mode how the rule uses historic data (previous sensor readings).
     * @return Time window mode.
     */
    public RuleTimeWindowMode getMode() {
        return mode;
    }

    /**
     * Sets the time window support mode.
     * @param mode Time window mode.
     */
    public void setMode(RuleTimeWindowMode mode) {
		this.setDirtyIf(this.mode != mode);
        this.mode = mode;
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
     * Returns a key/value map of additional rule parameters.
     * @return Key/value dictionary of additional parameters passed to the rule.
     */
    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    /**
     * Sets a dictionary of parameters passed to the rule.
     * @param params Key/value dictionary with extra parameters.
     */
    public void setParams(LinkedHashMap<String, String> params) {
		setDirtyIf(!Objects.equals(this.params, params));
        this.params = params;
		this.originalParams = (LinkedHashMap<String, String>) params.clone();
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.params, this.originalParams);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalParams = (LinkedHashMap<String, String>) this.params.clone();
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

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return false;
    }
}
