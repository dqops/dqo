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
package ai.dqo.rest.models.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIRuleThresholdsModel", description = "UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).")
public class UIRuleThresholdsModel {
    /**
     * Field name that matches the field name (snake_case) used in the YAML specification.
     */
    @JsonPropertyDescription("Rule field name (snake_case) used in the YAML specification to configure the rule in the code.")
    @Deprecated
    private String fieldName;

    @JsonPropertyDescription("Field display name that should be shown as a user friendly name of the rule.")
    @Deprecated
    private String displayName;

    @JsonPropertyDescription("Help text (full description) that will be shown to the user as a hint when the user clicks a help (question mark) icon.")
    @Deprecated
    private String helpHext;

    @JsonPropertyDescription("Relative path to the rule definition (a python file name) within the rules folder in the DQO Home or User Home.")
    private String rulePath;

    @JsonPropertyDescription("Rule parameters for the error severity rule.")
    private UIRuleParametersModel error;

    @JsonPropertyDescription("Rule parameters for the warning severity rule.")
    private UIRuleParametersModel warning;

    @JsonPropertyDescription("Rule parameters for the fatal severity rule.")
    private UIRuleParametersModel fatal;
}
