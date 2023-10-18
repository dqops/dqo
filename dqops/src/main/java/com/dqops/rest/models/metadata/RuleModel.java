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

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.execution.rules.runners.python.PythonRuleRunner;
import com.dqops.metadata.definitions.rules.*;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Rule model that is returned by the REST API. Describes a single unique rule name.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleModel", description = "Rule model")
public class RuleModel {
    @JsonPropertyDescription("Rule name")
    private String ruleName;

    @JsonPropertyDescription("Rule Python module content")
    private String rulePythonModuleContent;

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

    @JsonPropertyDescription("List of fields that are parameters of a custom rule. Those fields are used by the DQOps UI to display the data quality check " +
            "editing screens with proper UI controls for all required fields.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ParameterDefinitionsListSpec fields;

    @JsonPropertyDescription("Additional rule parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> parameters;

    @JsonPropertyDescription("This rule has a custom (user level) definition.")
    private boolean custom;

    @JsonPropertyDescription("This rule has is a built-in rule.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Default constructor for RuleModel.
     */
    public RuleModel() {
    }

    /**
     * Constructor for RuleModel with a given {@link RuleDefinitionWrapper}, custom flag, and built-in flag.
     * Sets the properties of the rule based on the given {@link RuleDefinitionWrapper}.
     * @param ruleDefinitionWrapper The RuleDefinitionWrapper to use.
     * @param custom Whether the rule is a custom rule.
     * @param builtIn Whether the rule is a built-in rule.
     * @param canEdit The current user can edit the definition of the rule.
     */
    public RuleModel(RuleDefinitionWrapper ruleDefinitionWrapper, boolean custom, boolean builtIn, boolean canEdit) {
        this.ruleName = ruleDefinitionWrapper.getRuleName();
        this.rulePythonModuleContent = ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent();
        this.type = ruleDefinitionWrapper.getSpec().getType();
        this.javaClassName = ruleDefinitionWrapper.getSpec().getJavaClassName();
        this.mode = ruleDefinitionWrapper.getSpec().getMode();
        this.timeWindow = ruleDefinitionWrapper.getSpec().getTimeWindow();
        this.fields = ruleDefinitionWrapper.getSpec().getFields();
        this.parameters = ruleDefinitionWrapper.getSpec().getParameters();
        this.custom = custom;
        this.builtIn = builtIn;
        this.canEdit = canEdit;
    }

    /**
     * Checks whether the RuleModel is equal to a given {@link RuleDefinitionWrapper}.
     * @param ruleDefinitionWrapper The RuleDefinitionWrapper to compare.
     * @return true if the RuleModel is equal to the RuleDefinitionWrapper, false otherwise.
     */
    public boolean equalsBuiltInRule(RuleDefinitionWrapper ruleDefinitionWrapper) {
        if (ruleDefinitionWrapper == null) {
            return false;
        }

        if (!Objects.equals(Optional.of(ruleDefinitionWrapper.getRulePythonModuleContent()).orElse(new FileContent()).getTextContent(),
                rulePythonModuleContent)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getType(), type)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getJavaClassName(), javaClassName)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getMode(), mode)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getParameters() == null, parameters)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getFields(), fields)) {
            return false;
        }

        if (!Objects.equals(ruleDefinitionWrapper.getSpec().getTimeWindow(), timeWindow)) {
            return false;
        }

        return true;
    }

    /**
     * Returns the rule definition spec object.
     * @return the rule definition spec object.
     */
    public RuleDefinitionSpec toRuleDefinitionSpec() {

        RuleDefinitionSpec ruleDefinitionSpec = new RuleDefinitionSpec();
        ruleDefinitionSpec.setType(type);
        ruleDefinitionSpec.setJavaClassName(javaClassName);
        ruleDefinitionSpec.setMode(mode);
        ruleDefinitionSpec.setTimeWindow(timeWindow);
        ruleDefinitionSpec.setFields(fields);
        ruleDefinitionSpec.setParameters(parameters);

        return ruleDefinitionSpec;
    }

    /**
     * Returns the file content object with Python module content.
     * @return file content object with Python module content.
     */
    public FileContent makePythonModuleFileContent() {
        return new FileContent(rulePythonModuleContent);
    }
}
