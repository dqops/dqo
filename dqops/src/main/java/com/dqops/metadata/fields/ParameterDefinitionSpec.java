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
package com.dqops.metadata.fields;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Defines a single field that is a sensor parameter or a rule parameter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ParameterDefinitionSpec", description = "Defines a single field that is a sensor parameter or a rule parameter.")
public class ParameterDefinitionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ParameterDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Field name that matches the field name (snake_case) used in the YAML specification.
     */
    @JsonPropertyDescription("Field name that matches the field name (snake_case) used in the YAML specification.")
    private String fieldName;

    @JsonPropertyDescription("Field display name that should be shown as a label for the control.")
    private String displayName;

    @JsonPropertyDescription("Help text (full description) that will be shown to the user as a hint when the cursor is moved over the control.")
    private String helpText;

    @JsonPropertyDescription("Parameter data type.")
    private ParameterDataType dataType;

    @JsonPropertyDescription("UI control display hint.")
    private DisplayHint displayHint;

    @JsonPropertyDescription("True when the value for the parameter must be provided.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean required;

    /**
     * List of allowed values for a field that is of an enum type.
     */
    @JsonPropertyDescription("List of allowed values for a field that is of an enum type.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> allowedValues;

    @JsonPropertyDescription("The default value for a parameter in a custom check or a custom rule.")
    private String defaultValue;

    /**
     * List of sample values for a field. Sample values are used when generating example YAML files for the documentation.
     */
    @JsonPropertyDescription("List of sample values. The sample values are used in the documentation or help messages.")
    @JsonIgnore
    private List<String> sampleValues;

    /**
     * Returns the field type as used in the yaml file.
     * @return Field name.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name.
     * @param fieldName Field name.
     */
    public void setFieldName(String fieldName) {
        this.setDirtyIf(!Objects.equals(this.fieldName, fieldName));
        this.fieldName = fieldName;
    }

    /**
     * Returns the display name of the field. It is a control label on the data quality check editing screen.
     * @return Display name of the field (field's label).
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name used as a label on the editing screen.
     * @param displayName New display name.
     */
    public void setDisplayName(String displayName) {
        this.setDirtyIf(!Objects.equals(this.displayName, displayName));
        this.displayName = displayName;
    }

    /**
     * Returns the help text (hint) that will be shown to the user.
     * @return Help text.
     */
    public String getHelpText() {
        return helpText;
    }

    /**
     * Sets the help text (hint) to be shown to the user.
     * @param helpText New help text.
     */
    public void setHelpText(String helpText) {
        this.setDirtyIf(!Objects.equals(this.helpText, helpText));
        this.helpText = helpText;
    }

    /**
     * Returns true when the field is required.
     * @return True when the value of the field must be provided.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the status if the parameter value must be provided.
     * @param required True when the parameter value must be provided.
     */
    public void setRequired(boolean required) {
        this.setDirtyIf(this.required != required);
        this.required = required;
    }

    /**
     * Returns the field data type.
     * @return Field data type.
     */
    public ParameterDataType getDataType() {
        return dataType;
    }

    /**
     * Sets the field data type.
     * @param dataType Field data type.
     */
    public void setDataType(ParameterDataType dataType) {
        this.setDirtyIf(this.dataType != dataType);
        this.dataType = dataType;
    }

    /**
     * Returns an additional display hint for the type of control to use in UI.
     * @return Display hint.
     */
    public DisplayHint getDisplayHint() {
        return displayHint;
    }

    /**
     * Sets an additional display hint used to display the field in UI.
     * @param displayHint Additional display hint.
     */
    public void setDisplayHint(DisplayHint displayHint) {
        this.setDirtyIf(this.displayHint != displayHint);
        this.displayHint = displayHint;
    }

    /**
     * Returns a list of allowed values for a field that is an enum.
     * @return List of allowed values. It is a read-only list.
     */
    public List<String> getAllowedValues() {
        return allowedValues;
    }

    /**
     * Sets a list of allowed field values for an enum field. The list is converted to an unmodifiable list.
     * @param allowedValues List of allowed fields.
     */
    public void setAllowedValues(List<String> allowedValues) {
        this.setDirtyIf(!Objects.equals(this.allowedValues, allowedValues));
        this.allowedValues = allowedValues != null ? Collections.unmodifiableList(allowedValues) : null;
    }

    /**
     * Returns the default value.
     * @return Default value.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value.
     * @param defaultValue Default value.
     */
    public void setDefaultValue(String defaultValue) {
        this.setDirtyIf(!Objects.equals(this.defaultValue, defaultValue));
        this.defaultValue = defaultValue;
    }

    /**
     * Returns an optional array of sample values for the field.
     * @return List of sample values.
     */
    public List<String> getSampleValues() {
        return sampleValues;
    }

    /**
     * Sets a list of sample values. The list is converted to an unmodifiable list.
     * @param sampleValues List of sample values.
     */
    public void setSampleValues(List<String> sampleValues) {
        this.setDirtyIf(!Objects.equals(this.sampleValues, sampleValues));
        this.sampleValues = sampleValues != null ? Collections.unmodifiableList(sampleValues) : null;
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
     * Creates and returns a copy of this object.
     */
    @Override
    public ParameterDefinitionSpec deepClone() {
        ParameterDefinitionSpec cloned = (ParameterDefinitionSpec)super.deepClone();
        return cloned;
    }
}
