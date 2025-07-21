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
 * Rule list model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleListModel", description = "Rule list model")
public class RuleListModel {
    /**
     * Rule name.
     */
    @JsonPropertyDescription("Rule name without the folder.")
    private String ruleName;

    /**
     * Full rule name, including the folder within the "rules" rule folder.
     */
    @JsonPropertyDescription("Full rule name, including the folder within the \"rules\" rule folder.")
    private String fullRuleName;

    /**
     * This rule has is a custom rule or was customized by the user. This is a read-only value.
     */
    @JsonPropertyDescription("This rule has is a custom rule or was customized by the user. This is a read-only value.")
    private boolean custom;

    /**
     * This rule is provided with DQOps as a built-in rule. This is a read-only value.
     */
    @JsonPropertyDescription("This rule is provided with DQOps as a built-in rule. This is a read-only value.")
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

    public RuleListModel() {
    }

    public static class RuleListModelSampleFactory implements SampleValueFactory<RuleListModel> {
        @Override
        public RuleListModel createSample() {
            return new RuleListModel() {{
                setRuleName(SampleStringsRegistry.getRuleName());
                setFullRuleName(SampleStringsRegistry.getFullRuleName());
                setCustom(false);
                setBuiltIn(true);
                setCanEdit(true);
            }};
        }
    }
}
