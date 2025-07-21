/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.metadata;

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data grouping on a table model with trimmed access path.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DataGroupingConfigurationTrimmedModel", description = "Data grouping configuration model with trimmed path")
public class DataGroupingConfigurationTrimmedModel {
    @JsonPropertyDescription("Data grouping configuration name.")
    private String dataGroupingConfigurationName;

    @JsonPropertyDescription("Data grouping configuration specification.")
    private DataGroupingConfigurationSpec spec;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    public DataGroupingConfigurationTrimmedModel() {
    }

    public static class DataGroupingConfigurationTrimmedModelSampleFactory implements SampleValueFactory<DataGroupingConfigurationTrimmedModel> {
        @Override
        public DataGroupingConfigurationTrimmedModel createSample() {
            return new DataGroupingConfigurationTrimmedModel() {{
                setDataGroupingConfigurationName(SampleStringsRegistry.getDataGrouping());
                setSpec(new DataGroupingConfigurationSpec.DataGroupingConfigurationSpecSampleFactory().createSample());
                setCanEdit(true);
            }};
        }
    }
}
