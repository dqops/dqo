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

import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.column.TargetColumnPatternSpec;
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
@ApiModel(value = "DefaultColumnChecksPatternListModel", description = "Default column-level checks pattern list model")
public class DefaultColumnChecksPatternListModel {
    /**
     * Pattern name.
     */
    @JsonPropertyDescription("Pattern name.")
    private String patternName;

    @JsonPropertyDescription("The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.")
    private int priority;

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

    public DefaultColumnChecksPatternListModel() {
    }

    /**
     * Creates a default table-level checks pattern model from a specification by cherry-picking relevant fields.
     * @param checksPatternSpec Source default checks pattern specification.
     * @param isEditor       The current user has the editor permission.
     * @return Default checks pattern list model.
     */
    public static DefaultColumnChecksPatternListModel fromPatternSpecification(
            ColumnDefaultChecksPatternSpec checksPatternSpec,
            boolean isEditor) {
        return new DefaultColumnChecksPatternListModel() {{
            setPatternName(checksPatternSpec.getPatternName());
            setPriority(checksPatternSpec.getPriority());
            setTargetColumn(checksPatternSpec.getTarget());
            setCanEdit(isEditor);
            setYamlParsingError(checksPatternSpec.getYamlParsingError());
        }};
    }

    public static class ColumnDefaultChecksPatternListModelSampleFactory implements SampleValueFactory<DefaultColumnChecksPatternListModel> {
        @Override
        public DefaultColumnChecksPatternListModel createSample() {
            return new DefaultColumnChecksPatternListModel() {{
                setPatternName(SampleStringsRegistry.getPatternName());
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
