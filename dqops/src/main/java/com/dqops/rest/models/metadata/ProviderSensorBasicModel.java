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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Provider sensor basic model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ProviderSensorBasicModel", description = "Provider sensor basic model")
public class ProviderSensorBasicModel {
    @JsonPropertyDescription("Provider type.")
    private ProviderType providerType;

    @JsonPropertyDescription("This connection specific template is a custom sensor template or was customized by the user.")
    private boolean custom;

    @JsonPropertyDescription("This connection specific template is provided with DQO as a built-in sensor.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    public ProviderSensorBasicModel() {
    }

    /**
     * Sets the custom or builtIn flag to true to match the source of the sensor definition.
     * @param sensorDefinitionSource Source sensor definition.
     */
    public void setSensorSource(SensorDefinitionSource sensorDefinitionSource) {
        if (sensorDefinitionSource == SensorDefinitionSource.CUSTOM) {
            this.setCustom(true);
        }
        else if (sensorDefinitionSource == SensorDefinitionSource.BUILT_IN) {
            this.setBuiltIn(true);
        }
    }
}
