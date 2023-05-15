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
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "BulkCheckDisableParameters", description = "Parameter object for disabling all checks that fit the filters.")
public class BulkCheckDisableParameters {
    @JsonPropertyDescription("Filters addressing basic tree search parameters. These filters takes precedence over other selectors.")
    @NotNull
    CheckSearchFilters checkSearchFilters;

    @JsonPropertyDescription("List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.")
    Map<String, List<String>> selectedTablesToColumns;
}
