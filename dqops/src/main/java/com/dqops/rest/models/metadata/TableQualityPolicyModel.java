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
