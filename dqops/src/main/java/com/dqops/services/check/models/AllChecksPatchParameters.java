/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.models;

import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.utils.docs.generators.SampleValueFactory;
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
    /**
     * Filters addressing basic tree search parameters. These filters takes precedence over other selectors.
     */
    @JsonPropertyDescription("Filters addressing basic tree search parameters. These filters takes precedence over other selectors.")
    @NotNull
    private CheckSearchFilters checkSearchFilters;

    /**
     * Sample configured check model which will pasted onto selected checks.
     */
    @JsonPropertyDescription("Sample configured check model which will pasted onto selected checks.")
    private CheckModel checkModelPatch;

    /**
     * List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.
     */
    @JsonPropertyDescription("List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.")
    private Map<String, List<String>> selectedTablesToColumns;

    /**
     * Override existing configurations if they're present. If false, apply updates only to the fields for which no configuration exists.
     */
    @JsonPropertyDescription("Override existing configurations if they're present. If false, apply updates only to the fields for which no configuration exists.")
    private boolean overrideConflicts;

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
