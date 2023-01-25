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
package ai.dqo.services.check.mapping.basicmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Simplistic UI model that returns a single data quality check, it's name and "configured" flag.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UICheckBasicModel", description = "Simplistic UI model that returns a single data quality check, it's name and \"configured\" flag")
public class UICheckBasicModel {
    /**
     * Data quality check name that is used in YAML file. Identifies the data quality check.
     */
    @JsonPropertyDescription("Data quality check name that is used in YAML.")
    private String checkName;

    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    @JsonPropertyDescription("True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.")
    private boolean configured;
}
