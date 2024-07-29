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

package com.dqops.services.check.mining;

import com.dqops.rules.DefaultRuleSeverityLevel;
import com.dqops.rules.RuleSeverityLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data quality check rule mining parameters. Configure what type of checks should be configured.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckMiningParametersModel", description = "Data quality check rule mining parameters. Configure what type of checks should be configured.")
public class CheckMiningParametersModel {
    /**
     * The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.
     */
    @JsonPropertyDescription("The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.")
    private DefaultRuleSeverityLevel severityLevel = DefaultRuleSeverityLevel.error;
}
