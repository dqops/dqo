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

import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.column.TargetColumnPatternSpec;
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
@ApiModel(value = "DefaultColumnChecksPatternModel", description = "Default column-level checks pattern model")
public class DefaultColumnChecksPatternModel {
    @JsonPropertyDescription("Pattern name")
    private String patternName;

    @JsonPropertyDescription("The default checks specification.")
    private ColumnDefaultChecksPatternSpec patternSpec;

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
    public DefaultColumnChecksPatternModel() {
    }


    public static class ColumnDefaultChecksPatternEditModelSampleFactory implements SampleValueFactory<DefaultColumnChecksPatternModel> {
        @Override
        public DefaultColumnChecksPatternModel createSample() {
            return new DefaultColumnChecksPatternModel() {{
                setPatternName("id columns not null");
                setPatternSpec(new ColumnDefaultChecksPatternSpec() {{
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
