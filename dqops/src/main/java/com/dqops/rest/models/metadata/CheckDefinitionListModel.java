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

import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Check list model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckDefinitionListModel", description = "Data quality check definition list model.")
public class CheckDefinitionListModel {
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
    private boolean custom;

    /**
     * True when this check is provided with DQOps as a built-in check.
     */
    @JsonPropertyDescription("This check is provided with DQOps as a built-in check.")
    private boolean builtIn;

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

    public CheckDefinitionListModel() {
    }

    public static class CheckDefinitionListModelSampleFactory implements SampleValueFactory<CheckDefinitionListModel> {
        @Override
        public CheckDefinitionListModel createSample() {
            return new CheckDefinitionListModel() {{
                setCheckName(SampleStringsRegistry.getCheckName());
                setFullCheckName(SampleStringsRegistry.getFullCheckName());
                setCustom(false);
                setBuiltIn(false);
                setCanEdit(true);
            }};
        }
    }
}
