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
 * Rule basic model that is returned by the REST API. Describes a single unique rule name.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleBasicModel", description = "Rule  basic model")
public class RuleBasicModel {

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

    public RuleBasicModel withRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public RuleBasicModel withRulePythonModuleContent(String rulePythonModuleContent) {
        this.rulePythonModuleContent = rulePythonModuleContent;
        return this;
    }

    public RuleBasicModel withType(RuleRunnerType type) {
        this.type = type;
        return this;
    }

    public RuleBasicModel withJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
        return this;
    }

    public RuleBasicModel withMode(RuleTimeWindowMode mode) {
        this.mode = mode;
        return this;
    }

    public RuleBasicModel withTimeWindow(RuleTimeWindowSettingsSpec timeWindow) {
        this.timeWindow = timeWindow;
        return this;
    }

    public RuleBasicModel withFields(ParameterDefinitionsListSpec fields) {
        this.fields = fields;
        return this;
    }

    public RuleBasicModel withParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }
}
