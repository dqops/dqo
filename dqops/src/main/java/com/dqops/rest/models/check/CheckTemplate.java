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
package com.dqops.rest.models.check;

import com.dqops.checks.CheckTarget;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.FieldModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckTemplate", description = "Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.")
public class CheckTemplate {
    @JsonPropertyDescription("Check target (table, column)")
    private CheckTarget checkTarget;

    @JsonPropertyDescription("Data quality check category.")
    private String checkCategory;

    @JsonPropertyDescription("Data quality check name that is used in YAML.")
    private String checkName;

    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    @JsonPropertyDescription("Check type with time-scale.")
    private CheckContainerTypeModel checkContainerType;

    @JsonPropertyDescription("Full sensor name.")
    private String sensorName;

    @JsonPropertyDescription("Template of the check model with the sensor parameters and rule parameters")
    private CheckModel checkModel;

    @Deprecated
    @JsonPropertyDescription("List of sensor parameter fields definitions.")
    private List<ParameterDefinitionSpec> sensorParametersDefinitions = new ArrayList<>();

    @Deprecated
    @JsonPropertyDescription("List of threshold (alerting) rule's parameters definitions (for a single rule, regardless of severity).")
    private List<ParameterDefinitionSpec> ruleParametersDefinitions;


    /**
     * Creates a {@link CheckTemplate} instance based on <code>uiCheckModel</code>.
     * @param checkModel              Base uiCheckModel.
     * @param checkCategory             Check category.
     * @param uiCheckContainerTypeModel Check container type parameter for the new instance.
     * @param checkTarget               Check target.
     * @return New instance of {@link CheckTemplate}.
     */
    public static CheckTemplate fromUiCheckModel(CheckModel checkModel,
                                                 String checkCategory,
                                                 CheckContainerTypeModel uiCheckContainerTypeModel,
                                                 CheckTarget checkTarget) {
        CheckTemplate checkTemplate = new CheckTemplate();
        checkTemplate.setCheckName(checkModel.getCheckName());
        checkTemplate.setHelpText(checkModel.getHelpText());
        checkTemplate.setSensorName(checkModel.getSensorName());
        checkTemplate.setCheckTarget(checkTarget);
        checkTemplate.setCheckCategory(checkCategory);
        checkTemplate.setCheckModel(checkModel);

        CheckContainerTypeModel checkContainerTypeModel = new CheckContainerTypeModel(
                uiCheckContainerTypeModel.getCheckType(), uiCheckContainerTypeModel.getCheckTimeScale());
        checkTemplate.setCheckContainerType(checkContainerTypeModel);

        List<ParameterDefinitionSpec> sensorParameterDefinitions = checkModel.getSensorParameters().stream()
                .map(FieldModel::getDefinition)
                .map(ParameterDefinitionSpec::deepClone)
                .collect(Collectors.toList());
        checkTemplate.setSensorParametersDefinitions(sensorParameterDefinitions);

        List<ParameterDefinitionSpec> ruleParameterDefinitions = checkModel.getRule().findFirstNotNullRule().getRuleParameters().stream()
                .map(FieldModel::getDefinition)
                .map(ParameterDefinitionSpec::deepClone)
                .collect(Collectors.toList());
        checkTemplate.setRuleParametersDefinitions(ruleParameterDefinitions);
        return checkTemplate;
    }
}
