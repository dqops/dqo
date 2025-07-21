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

import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.TargetColumnPatternSpec;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Default column-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on columns that match the filter.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnQualityPolicyModel", description = "Default column-level checks pattern (data quality policy) model")
public class ColumnQualityPolicyModel {
    @JsonPropertyDescription("Quality policy name")
    private String policyName;

    /**
     * The quality policy specification.
     */
    @JsonPropertyDescription("The quality policy specification.")
    private ColumnQualityPolicySpec policySpec;

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

    /**
     * Default constructor for TableDefaultChecksPatternEditModel.
     */
    public ColumnQualityPolicyModel() {
    }


    public static class ColumnDefaultChecksPatternEditModelSampleFactory implements SampleValueFactory<ColumnQualityPolicyModel> {
        @Override
        public ColumnQualityPolicyModel createSample() {
            return new ColumnQualityPolicyModel() {{
                setPolicyName("id columns not null");
                setPolicySpec(new ColumnQualityPolicySpec() {{
                    setTarget(new TargetColumnPatternSpec() {{
                        setColumn("id");
                    }});
                    setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec() {{
                        setDaily(new ColumnDailyMonitoringCheckCategoriesSpec() {{
                            setNulls(new ColumnNullsDailyMonitoringChecksSpec() {{
                                setDailyNullsCount(new ColumnNullsCountCheckSpec() {{
                                    setError(new MaxCountRule0ErrorParametersSpec());
                                }});
                            }});
                        }});
                    }});
                }});
                setCanEdit(true);
            }};
        }
    }
}
