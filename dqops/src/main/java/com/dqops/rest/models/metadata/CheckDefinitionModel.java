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
package com.dqops.rest.models.metadata;

import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Objects;

/**
 * Check model that is returned by the REST API. Describes a single unique data quality check.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckDefinitionModel", description = "Data quality check definition model")
public class CheckDefinitionModel {
    /**
     * Data quality check name.
     */
    @JsonPropertyDescription("Check name")
    private String checkName;

    /**
     * Full sensor name.
     */
    @JsonPropertyDescription("Sensor name")
    private String sensorName;

    /**
     * Full rule name.
     */
    @JsonPropertyDescription("Rule name")
    private String ruleName;

    /**
     * Help text for the check editor.
     */
    @JsonPropertyDescription("Help text that is shown in the check editor that describes the purpose and usage of the check")
    private String helpText;

    /**
     * True when the check is a custom check or is customized by the user.
     */
    @JsonPropertyDescription("This check has is a custom check or was customized by the user.")
    private boolean custom;

    /**
     * True when this check is provided with DQOps as a built-in check.
     */
    @JsonPropertyDescription("This check is provided with DQOps as a built-in check.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    /**
     * Default constructor for CheckModel.
     */
    public CheckDefinitionModel() {
    }

    /**
     * Constructor for a rule model with a given {@link CheckDefinitionWrapper}, custom flag, and built-in flag.
     * Sets the properties of the check based on the given {@link CheckDefinitionWrapper}.
     * @param checkDefinitionWrapper The check definition wrapper to use.
     * @param custom Whether the check is a custom check.
     * @param builtIn Whether the check is a built-in check.
     * @param canEdit The current user can edit the check definition.
     */
    public CheckDefinitionModel(CheckDefinitionWrapper checkDefinitionWrapper, boolean custom, boolean builtIn, boolean canEdit) {
        this.checkName = checkDefinitionWrapper.getCheckName();
        CheckDefinitionSpec checkDefinitionSpec = checkDefinitionWrapper.getSpec();
        this.sensorName = checkDefinitionSpec.getSensorName();
        this.ruleName = checkDefinitionSpec.getRuleName();
        this.helpText = checkDefinitionSpec.getHelpText();
        this.yamlParsingError = checkDefinitionSpec.getYamlParsingError();
        this.custom = custom;
        this.builtIn = builtIn;
        this.canEdit = canEdit;
    }

    /**
     * Checks whether the CheckModel is equal to a given {@link CheckDefinitionWrapper}.
     * @param checkDefinitionWrapper The CheckDefinitionWrapper to compare.
     * @return true if the rule model is equal to the CheckDefinitionWrapper, false otherwise.
     */
    public boolean equalsBuiltInCheck(CheckDefinitionWrapper checkDefinitionWrapper) {
        if (checkDefinitionWrapper == null) {
            return false;
        }

        if (!Objects.equals(checkDefinitionWrapper.getSpec().getSensorName(), this.sensorName)) {
            return false;
        }

        if (!Objects.equals(checkDefinitionWrapper.getSpec().getRuleName(), this.ruleName)) {
            return false;
        }

        if (!Objects.equals(checkDefinitionWrapper.getSpec().getHelpText(), this.helpText)) {
            return false;
        }

        return true;
    }

    /**
     * Returns the check definition spec object.
     * @return the check definition spec object.
     */
    public CheckDefinitionSpec toCheckDefinitionSpec() {
        CheckDefinitionSpec checkDefinitionSpec = new CheckDefinitionSpec();
        checkDefinitionSpec.setSensorName(this.sensorName);
        checkDefinitionSpec.setRuleName(this.ruleName);
        checkDefinitionSpec.setHelpText(this.helpText);

        return checkDefinitionSpec;
    }
}
