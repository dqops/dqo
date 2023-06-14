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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * CheckSpec basic model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckSpecBasicModel", description = "Check spec basic model")
public class CheckSpecBasicModel {
    /**
     * Check name at the leaf level of the check tree.
     */
    @JsonPropertyDescription("Check name")
    private String checkName;

    /**
     * Full check name.
     */
    @JsonPropertyDescription("Full check name")
    private String fullCheckName;

    /**
     * True when the check is a custom check or is customized by the user.
     */
    @JsonPropertyDescription("This check has is a custom check or was customized by the user.")
    public boolean custom;

    /**
     * True when this check is provided with DQO as a built-in check.
     */
    @JsonPropertyDescription("This check is provided with DQO as a built-in check.")
    public boolean builtIn;

    public CheckSpecBasicModel() {
    }
}
