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
package com.dqops.metadata.definitions.checks;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Custom data quality check specification. Provides the custom check configuration which is a pair of a sensor name and a rule name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CheckDefinitionSpec extends AbstractSpec implements InvalidYamlStatusHolder {
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

    @JsonPropertyDescription("Sensor name. It is a folder name inside the user's home 'sensors' folder or the DQOps Home (DQOps distribution) home/sensors folder. " +
            "Sample sensor name: table/volume/row_count.")
    private String sensorName;

    @JsonPropertyDescription("Rule name used for the check. It is a path to a custom rule python module that starts at the user's home 'rules' folder. " +
            "The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule.")
    private String ruleName;

    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    @JsonPropertyDescription("An alternative check's name that is shown on the check editor.")
    private String friendlyName;

    @JsonPropertyDescription("This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean standard;

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

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
     * Returns the help text that is shown around the real check name on the check editor.
     * @return Friendly name (optional).
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * Sets a friendly name of the check that is shown on the check editor.
     * @param friendlyName Friendly name.
     */
    public void setFriendlyName(String friendlyName) {
        this.setDirtyIf(!Objects.equals(this.friendlyName, friendlyName));
        this.friendlyName = friendlyName;
    }

    /**
     * Return true when this is a standard data quality check.
     * @return True when it is a standard data quality check, always shown.
     */
    public boolean isStandard() {
        return standard;
    }

    /**
     * Sets the 'standard' flag to identify data quality checks that should be always shown in UI.
     * @param standard True when it is a standard check.
     */
    public void setStandard(boolean standard) {
        this.setDirtyIf(this.standard != standard);
        this.standard = standard;
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

    /**
     * Returns the full check name, including the target, check type, time scale, category and the check name.
     * @return Full check name.
     */
    @JsonIgnore
    public String getFullCheckName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.getLast().toString();
    }

    /**
     * Returns the check name, without the category.
     * @return Check name, without the category or type.
     */
    @JsonIgnore
    public String getCheckName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        String fullCheckName = (String)hierarchyId.get(hierarchyId.size() - 2);
        String[] checkNameElements = StringUtils.split(fullCheckName, '/');

        return checkNameElements[checkNameElements.length - 1];
    }

    /**
     * Returns the check category extracted from the full check name.
     * @return Check category.
     */
    @JsonIgnore
    public String getCheckCategory() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        String fullCheckName = (String)hierarchyId.get(hierarchyId.size() - 2);
        String[] checkNameElements = StringUtils.split(fullCheckName, '/');

        return checkNameElements[checkNameElements.length - 2];
    }
}
