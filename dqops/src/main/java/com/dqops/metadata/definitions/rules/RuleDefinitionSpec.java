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
package com.dqops.metadata.definitions.rules;

import com.dqops.execution.rules.runners.python.PythonRuleRunner;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;
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
            put("fields", o -> o.fields);
        }
    };

    @JsonPropertyDescription("Rule runner type")
    private RuleRunnerType type = RuleRunnerType.python;

    @JsonPropertyDescription("Java class name for a rule runner that will execute the sensor. The \"type\" must be \"java_class\".")
    private String javaClassName = PythonRuleRunner.CLASS_NAME;

    @JsonPropertyDescription("Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. " +
            "The number of time windows is configured in the time_window setting.")
    private RuleTimeWindowMode mode = RuleTimeWindowMode.current_value;

    @JsonPropertyDescription("Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that " +
            "are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.")
    private RuleTimeWindowSettingsSpec timeWindow;

    @JsonPropertyDescription("List of fields that are parameters of a custom rule. Those fields are used by the DQOps UI to display the data quality check editing screens " +
            "with proper UI controls for all required fields.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ParameterDefinitionsListSpec fields;

    @JsonPropertyDescription("Additional rule parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> parameters;

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
     * Gets the mode how the rule uses historic data (previous sensor readouts).
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
     * Returns a list of parameters (fields) used on this rule. Those parameters are shown by the DQOps UI.
     * @return List of parameters.
     */
    public ParameterDefinitionsListSpec getFields() {
        return fields;
    }

    /**
     * Sets the new list of fields.
     * @param fields List of fields.
     */
    public void setFields(ParameterDefinitionsListSpec fields) {
        setDirtyIf(!Objects.equals(this.fields, fields));
        this.fields = fields;
        propagateHierarchyIdToField(fields, "fields");
    }

    /**
     * Returns a key/value map of additional rule parameters.
     * @return Key/value dictionary of additional parameters passed to the rule.
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets a dictionary of parameters passed to the rule.
     * @param parameters Key/value dictionary with extra parameters.
     */
    public void setParameters(Map<String, String> parameters) {
		setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters != null ? Collections.unmodifiableMap(parameters) : null;
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

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public RuleDefinitionSpec deepClone() {
        RuleDefinitionSpec cloned = (RuleDefinitionSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * @return Trimmed version of this object.
     */
    public RuleDefinitionSpec trim() {
        RuleDefinitionSpec cloned = (RuleDefinitionSpec)super.deepClone();
        cloned.fields = null;
        return cloned;
    }

    /**
     * Returns the rule name, retrieved from the hierarchy id.
     * @return Full rule name.
     */
    @JsonIgnore
    public String getRuleName() {
        if (this.getHierarchyId() == null) {
            return null;
        }

        return (String)this.getHierarchyId().getLast();
    }
}
