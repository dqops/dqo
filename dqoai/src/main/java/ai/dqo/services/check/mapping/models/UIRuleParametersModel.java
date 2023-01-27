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
package ai.dqo.services.check.mapping.models;

import ai.dqo.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIRuleParametersModel", description = "UI model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).")
public class UIRuleParametersModel {
    @JsonPropertyDescription("Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.")
    private String ruleName;

    /**
     * Rule parameters specification, returned for reference and debugging. Used by the document generation utilities.
     */
    @JsonIgnore
    private AbstractRuleParametersSpec ruleParametersSpec;

    @JsonPropertyDescription("List of fields for editing the rule parameters like thresholds.")
    private List<UIFieldModel> ruleParameters;

    @JsonPropertyDescription("Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.")
    private boolean disabled;

    @JsonPropertyDescription("Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).")
    private boolean configured;
}
