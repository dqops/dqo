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
package ai.dqo.metadata.definitions.checks;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Custom data quality check specification. Provides the custom check configuration which is a pair of a sensor name and a rule name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CheckDefinitionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<CheckDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    public CheckDefinitionSpec() {
    }

    /**
     * Create a check definition specification.
     * @param sensorName Sensor name.
     * @param ruleName Rule name.
     * @param helpText Help text.
     */
    public CheckDefinitionSpec(String sensorName, String ruleName, String helpText) {
        this.sensorName = sensorName;
        this.ruleName = ruleName;
        this.helpText = helpText;
    }

    @JsonPropertyDescription("Sensor name. It is a folder name inside the user's home 'sensors' folder or the DQO Home (DQO distribution) home/sensors folder. Sample sensor name: table/standard/row_count.")
    private String sensorName;

    @JsonPropertyDescription("Rule name used for the check. It is a path to a custom rule python module that starts at the user's home 'rules' folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule.")
    private String ruleName;

    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    /**
     * Returns a sensor name.
     * @return Sensor name.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets a sensor name.
     * @param sensorName Sensor name.
     */
    public void setSensorName(String sensorName) {
        this.setDirtyIf(!Objects.equals(this.sensorName, sensorName));
        this.sensorName = sensorName;
    }

    /**
     * Returns a rule name.
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
        this.setDirtyIf(!Objects.equals(this.ruleName, ruleName));
        this.ruleName = ruleName;
    }

    /**
     * Returns the help text that is show for the check in the check editor.
     * @return Help text.
     */
    public String getHelpText() {
        return helpText;
    }

    /**
     * Sets the help text that is shown for the check.
     * @param helpText Help text.
     */
    public void setHelpText(String helpText) {
        this.setDirtyIf(!Objects.equals(this.helpText, helpText));
        this.helpText = helpText;
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
    public CheckDefinitionSpec deepClone() {
        CheckDefinitionSpec cloned = (CheckDefinitionSpec)super.deepClone();
        return cloned;
    }
}
