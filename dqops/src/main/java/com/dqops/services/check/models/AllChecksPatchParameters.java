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
package com.dqops.services.check.models;

import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "AllChecksPatchParameters", description = "Parameter object for creating pruned patch trees of all checks that fit the filters.")
public class AllChecksPatchParameters {
    @JsonPropertyDescription("Filters addressing basic tree search parameters. These filters takes precedence over other selectors.")
    @NotNull
    CheckSearchFilters checkSearchFilters;

    @JsonPropertyDescription("Sample configured check model which will pasted onto selected checks.")
    CheckModel checkModelPatch;

    @JsonPropertyDescription("List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.")
    Map<String, List<String>> selectedTablesToColumns;

    @JsonPropertyDescription("Override existing configurations if they're present. If false, apply updates only to the fields for which no configuration exists.")
    boolean overrideConflicts;

    public static class AllChecksPatchParametersSampleFactory implements SampleValueFactory<AllChecksPatchParameters> {
        @Override
        public AllChecksPatchParameters createSample() {
            return new AllChecksPatchParameters() {{
                setCheckSearchFilters(new CheckSearchFilters.CheckSearchFiltersSampleFactory().createSample());
                setCheckModelPatch(new CheckModel.CheckModelSampleFactory().createSample());
                setOverrideConflicts(true);
            }};
        }
    }
}
