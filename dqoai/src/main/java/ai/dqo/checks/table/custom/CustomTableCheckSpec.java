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
package ai.dqo.checks.table.custom;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.basespecs.InvalidSpecificationException;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.rules.AllRulesThresholdsSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.table.AllTableSensorsSpec;
import ai.dqo.sensors.table.CustomTableSensorParametersSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Custom table check that accepts any built-in sensor (that reads a value) and any rule to validate the result.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CustomTableCheckSpec extends AbstractCheckDeprecatedSpec {
    public static final ChildHierarchyNodeFieldMapImpl<CustomTableCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckDeprecatedSpec.FIELDS) {
        {
			put("builtin", o -> o.builtin);
			put("custom", o -> o.custom);
			put("rules", o -> o.rules);
        }
    };

    @JsonPropertyDescription("Selection of all builtin table level sensors. Just configure only one and it will be the enabled sensor.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AllTableSensorsSpec builtin = new AllTableSensorsSpec();

    @JsonPropertyDescription("Selection and configuration of a custom sensor defined in the USER_HOME. Do not select any builtin sensor when a custom sensor is configured.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomTableSensorParametersSpec custom;

    @JsonPropertyDescription("Rules that should be applied ")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AllRulesThresholdsSpec rules = new AllRulesThresholdsSpec();

    /**
     * Returns an object with all built-in (typed) sensor definitions. Just fill only one sensor that is required.
     * @return Helper object with all possible sensor types.
     */
    public AllTableSensorsSpec getBuiltin() {
        return builtin;
    }

    /**
     * Sets a new object with all possible built-in sensor types.
     * @param builtInSensors Built-in table level sensor types.
     */
    public void setBuiltin(AllTableSensorsSpec builtInSensors) {
		this.setDirtyIf(!Objects.equals(this.builtin, builtInSensors));
		this.builtin = builtInSensors;
		this.propagateHierarchyIdToField(builtInSensors, "builtin");
    }

    /**
     * Returns a configuration of a custom sensor.
     * @return Custom sensor configuration.
     */
    public CustomTableSensorParametersSpec getCustom() {
        return custom;
    }

    /**
     * Sets a custom sensor configuration.
     * @param custom Custom sensor configuration.
     */
    public void setCustom(CustomTableSensorParametersSpec custom) {
		this.setDirtyIf(!Objects.equals(this.custom, custom));
        this.custom = custom;
		this.propagateHierarchyIdToField(custom, "custom");
    }

    /**
     * Returns a selection of rules that are applied.
     * @return Selection of rules to be applied.
     */
    public AllRulesThresholdsSpec getRules() {
        return rules;
    }

    /**
     * Sets a collection of rules to be used.
     * @param rules Rules.
     */
    public void setRules(AllRulesThresholdsSpec rules) {
		this.setDirtyIf(!Objects.equals(this.rules, rules));
        this.rules = rules;
		this.propagateHierarchyIdToField(rules, "rules");
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
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     *
     * @return Sensor parameters.
     */
    @Override
    @JsonIgnore
    public AbstractSensorParametersSpec getSensorParameters() {
        ArrayList<AbstractSensorParametersSpec> parametersSpecs = new ArrayList<>();

        for (HierarchyNode childNode : this.getBuiltin().children()) {
            if (!(childNode instanceof AbstractSensorParametersSpec)) {
                continue;
            }

            AbstractSensorParametersSpec parametersSpec = (AbstractSensorParametersSpec)childNode;
            if (!parametersSpec.isDisabled()) {
                parametersSpecs.add(parametersSpec);
            }
        }

        if (this.custom != null && !this.custom.isDisabled()) {
            if (parametersSpecs.size() == 0) {
                return this.custom;
            }

            throw new InvalidSpecificationException("Both a custom rule and at least one builtin rule is configured. Disable all builtin rules and keep the custom rule or disable the custom rule (remove the yaml node) and use just one builtin rule.", this.getBuiltin());
        }


        if (parametersSpecs.size() == 1) {
            return parametersSpecs.get(0);
        }

        if (parametersSpecs.size() == 0) {
            // no enabled sensors
            return null;
        }

        throw new InvalidSpecificationException("Custom check has multiple builtin sensors enabled, only one sensor may be enabled. Please disable the other sensors and create a separate custom check to run those sensors.", this.getBuiltin());
    }

    /**
     * Returns a rule set for this check.
     *
     * @return Rule set.
     */
    @Override
    public AbstractRuleSetSpec getRuleSet() {
        return this.rules;
    }
}
