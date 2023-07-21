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

    @JsonPropertyDescription("Provider type.")
    private ProviderType providerType;

    @JsonPropertyDescription("Provider Sensor definition spec")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ProviderSensorDefinitionSpec providerSensorDefinitionSpec;

    @JsonPropertyDescription("Provider Sql template")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String sqlTemplate;

    @JsonPropertyDescription("Whether the provider sensor is a User Home provider sensor")
    private boolean custom;

    @JsonPropertyDescription("This is a DQO built-in provider sensor, whose parameters cannot be changed.")
    public boolean builtIn;

    public ProviderSensorModel(){}

    public ProviderSensorModel(ProviderType providerType,
                               ProviderSensorDefinitionSpec providerSensorDefinitionSpec,
                               String sqlTemplate,
                               boolean custom,
                               boolean builtIn) {
        this.providerType = providerType;
        this.providerSensorDefinitionSpec = providerSensorDefinitionSpec;
        this.sqlTemplate = sqlTemplate;
        this.custom = custom;
        this.builtIn = builtIn;
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
