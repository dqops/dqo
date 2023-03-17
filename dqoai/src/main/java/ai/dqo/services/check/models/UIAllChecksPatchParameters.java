/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.check.models;

import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIAllChecksPatchParameters", description = "Parameter object for creating pruned patch trees of all fitting checks.")
public class UIAllChecksPatchParameters {
    @JsonPropertyDescription("Filters addressing basic tree search parameters.")
    @NotNull
    CheckSearchFilters checkSearchFilters;

    @JsonPropertyDescription("Options for warning level rules on checks, example of an entry: \"min_count\" -> 20, etc. If null, warning level will be ignored.")
    Map<String, String> warningLevelOptions;

    @JsonPropertyDescription("Options for error level rules on checks, example of an entry: \"min_count\" -> 10, etc. If null, error level will be ignored.")
    Map<String, String> errorLevelOptions;

    @JsonPropertyDescription("Options for fatal level rules on checks, example of an entry: \"min_count\" -> 5, etc. If null, fatal level will be ignored.")
    Map<String, String> fatalLevelOptions;
}
