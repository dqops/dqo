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

import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.TargetColumnPatternSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * The listing model of column-level default check patterns that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnQualityPolicyListModel", description = "Default column-level checks pattern (data quality policy) list model")
public class ColumnQualityPolicyListModel {
    /**
     * Quality policy name.
     */
    @JsonPropertyDescription("Quality policy name.")
    private String policyName;

    /**
     *
     * The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.
     */
    @JsonPropertyDescription("The priority of the policy. Policies with lower values are applied before policies with higher priority values.")
    private int priority;

    /**
     * Disables this data quality check configuration. The checks will not be activated.
     */
    @JsonPropertyDescription("Disables this data quality check configuration. The checks will not be activated.")
    private boolean disabled;

    /**
     * The description (documentation) of this data quality check configuration.
     */
    @JsonPropertyDescription("The description (documentation) of this data quality check configuration.")
    private String description;

    /**
     * Target column filters.
     */
    @JsonPropertyDescription("The filters for the target column.")
    private TargetColumnPatternSpec targetColumn;

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

    public ColumnQualityPolicyListModel() {
    }

    /**
     * Creates a default table-level checks pattern model from a specification by cherry-picking relevant fields.
     * @param checksPatternSpec Source default checks pattern specification.
     * @param isEditor       The current user has the editor permission.
     * @return Default checks pattern list model.
     */
    public static ColumnQualityPolicyListModel fromPatternSpecification(
            ColumnQualityPolicySpec checksPatternSpec,
            boolean isEditor) {
        return new ColumnQualityPolicyListModel() {{
            setPolicyName(checksPatternSpec.getPolicyName());
            setPriority(checksPatternSpec.getPriority());
            setDisabled(checksPatternSpec.isDisabled());
            setDescription(checksPatternSpec.getDescription());
            setTargetColumn(checksPatternSpec.getTarget());
            setCanEdit(isEditor);
            setYamlParsingError(checksPatternSpec.getYamlParsingError());
        }};
    }

    public static class ColumnDefaultChecksPatternListModelSampleFactory implements SampleValueFactory<ColumnQualityPolicyListModel> {
        @Override
        public ColumnQualityPolicyListModel createSample() {
            return new ColumnQualityPolicyListModel() {{
                setPolicyName(SampleStringsRegistry.getPatternName());
                setPriority(100);
                setTargetColumn(new TargetColumnPatternSpec() {{
                    setConnection("dwh");
                    setColumn("id");
                }});
                setCanEdit(true);
            }};
        }
    }
}
