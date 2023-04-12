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
package ai.dqo.rest.models.metadata;

import ai.dqo.execution.rules.runners.python.PythonRuleRunner;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleRunnerType;
import ai.dqo.metadata.definitions.rules.RuleTimeWindowMode;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

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

    @JsonPropertyDescription("Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.")
    private RuleTimeWindowMode mode = RuleTimeWindowMode.current_value;

    @JsonPropertyDescription("Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.")
    private RuleTimeWindowSettingsSpec timeWindow;

    @JsonPropertyDescription("List of fields that are parameters of a custom rule. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ParameterDefinitionsListSpec fields;

    @JsonPropertyDescription("Additional rule parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> parameters;

    @JsonPropertyDescription("This rule has a custom (user level) definition.")
    public boolean custom;

    @JsonPropertyDescription("This rule has is a built-in rule.")
    public boolean builtIn;

    public RuleModel withRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public RuleModel withRulePythonModuleContent(String rulePythonModuleContent) {
        this.rulePythonModuleContent = rulePythonModuleContent;
        return this;
    }

    public RuleModel withType(RuleRunnerType type) {
        this.type = type;
        return this;
    }

    public RuleModel withJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
        return this;
    }

    public RuleModel withMode(RuleTimeWindowMode mode) {
        this.mode = mode;
        return this;
    }

    public RuleModel withTimeWindow(RuleTimeWindowSettingsSpec timeWindow) {
        this.timeWindow = timeWindow;
        return this;
    }

    public RuleModel withFields(ParameterDefinitionsListSpec fields) {
        this.fields = fields;
        return this;
    }

    public RuleModel withParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public boolean equalsRuleDqo(RuleDefinitionWrapper ruleDefinitionWrapper) {
        if (ruleDefinitionWrapper == null) {
            return false;
        }

        if (!ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent().equals(rulePythonModuleContent)) {
            return false;
        }

        if (!ruleDefinitionWrapper.getSpec().getType().equals(type)) {
            return false;
        }

        if (!ruleDefinitionWrapper.getSpec().getJavaClassName().equals(javaClassName)) {
            return false;
        }

        if (!ruleDefinitionWrapper.getSpec().getMode().equals(mode)) {
            return false;
        }

        if(ruleDefinitionWrapper.getSpec().getParameters() == null && parameters != null){
            return false;
        }
        if(ruleDefinitionWrapper.getSpec().getParameters() != null && !ruleDefinitionWrapper.getSpec().getParameters().equals(parameters)){
            return false;
        }

        if(!ruleDefinitionWrapper.getSpec().getFields().equals(fields)){
            return false;
        }

        if(!ruleDefinitionWrapper.getSpec().getTimeWindow().equals(timeWindow)){
            return false;
        }

        return true;
    }
}
