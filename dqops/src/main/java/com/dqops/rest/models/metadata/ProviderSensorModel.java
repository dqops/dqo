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

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Objects;

/**
 * Provider sensor model returned from REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ProviderSensorModel", description = "Provider sensor model")
public class ProviderSensorModel {
    /**
     * Provider type.
     */
    @JsonPropertyDescription("Provider type.")
    private ProviderType providerType;

    /**
     * Provider specific sensor definition specification
     */
    @JsonPropertyDescription("Provider specific sensor definition specification")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ProviderSensorDefinitionSpec providerSensorDefinitionSpec;

    /**
     * Provider specific Jinja2 SQL template
     */
    @JsonPropertyDescription("Provider specific Jinja2 SQL template")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String sqlTemplate;

    /**
     * Whether the provider sensor is a User Home provider sensor
     */
    @JsonPropertyDescription("Whether the provider sensor is a User Home provider sensor")
    private boolean custom;

    /**
     * This is a DQOps built-in provider sensor, whose parameters cannot be changed.
     */
    @JsonPropertyDescription("This is a DQOps built-in provider sensor, whose parameters cannot be changed.")
    public boolean builtIn;

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

    public ProviderSensorModel() {}

    public ProviderSensorModel(ProviderType providerType,
                               ProviderSensorDefinitionSpec providerSensorDefinitionSpec,
                               String sqlTemplate,
                               boolean custom,
                               boolean builtIn,
                               boolean canEdit) {
        this.providerType = providerType;
        this.providerSensorDefinitionSpec = providerSensorDefinitionSpec;
        this.sqlTemplate = sqlTemplate;
        this.custom = custom;
        this.builtIn = builtIn;
        this.canEdit = canEdit;
        this.yamlParsingError = providerSensorDefinitionSpec.getYamlParsingError();
    }

    /**
     * Checks whether this ProviderSensorDefinitionWrapper object is equal to the provided
     * ProviderSensorDefinitionWrapper object based on their SQL templates and specifications.
     * @param providerSensorDefinitionWrapper The ProviderSensorDefinitionWrapper object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean equalsProviderSensorDqo(ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper) {
        if (providerSensorDefinitionWrapper == null) {
            return false;
        }

        if (!Objects.equals(providerSensorDefinitionWrapper.getSqlTemplate(), sqlTemplate)) {
            return false;
        }

        if (!Objects.equals(providerSensorDefinitionWrapper.getSpec(), providerSensorDefinitionSpec)) {
            return false;
        }

        return true;
    }
}
