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
@ApiModel(value = "BulkCheckDeactivateParameters", description = "Parameter object for deactivating all checks that fit the filters.")
public class BulkCheckDeactivateParameters {
    /**
     * Filters addressing basic tree search parameters. These filters takes precedence over other selectors.
     */
    @JsonPropertyDescription("Filters addressing basic tree search parameters. These filters takes precedence over other selectors.")
    @NotNull
    private CheckSearchFilters checkSearchFilters = new CheckSearchFilters();

    /**
     * List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.
     */
    @JsonPropertyDescription("List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> selectedTablesToColumns = null;

    public static class BulkCheckDisableParametersSampleFactory implements SampleValueFactory<BulkCheckDeactivateParameters> {
        @Override
        public BulkCheckDeactivateParameters createSample() {
            return new BulkCheckDeactivateParameters() {{
                setCheckSearchFilters(new CheckSearchFilters.CheckSearchFiltersSampleFactory().createSample());
            }};
        }
    }
}
