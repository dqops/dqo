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

import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Default table-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on tables that match the filter.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableQualityPolicyModel", description = "Default table-level checks pattern (data quality policy) model")
public class TableQualityPolicyModel {
    /**
     * Quality policy name
     */
    @JsonPropertyDescription("Quality policy name")
    private String policyName;

    /**
     * The default checks specification.
     */
    @JsonPropertyDescription("The quality policy specification.")
    private TableQualityPolicySpec policySpec;

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
    public TableQualityPolicyModel() {
    }


    public static class TableDefaultChecksPatternEditModelSampleFactory implements SampleValueFactory<TableQualityPolicyModel> {
        @Override
        public TableQualityPolicyModel createSample() {
            return new TableQualityPolicyModel() {{
                setPolicyName(SampleStringsRegistry.getPatternName());
                setPolicySpec(new TableQualityPolicySpec() {{
                    setMonitoringChecks(new TableMonitoringCheckCategoriesSpec() {{
                        setDaily(new TableDailyMonitoringCheckCategoriesSpec() {{
                            setVolume(new TableVolumeDailyMonitoringChecksSpec() {{
                                setDailyRowCount(new TableRowCountCheckSpec() {{
                                    setWarning(new MinCountRule1ParametersSpec());
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
