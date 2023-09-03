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

import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sensor model returned from REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorModel", description = "Sensor model.")
public class SensorModel {

    @JsonPropertyDescription("Full sensor name.")
    private String fullSensorName;

    @JsonPropertyDescription("Sensor definition specification.")
    private SensorDefinitionSpec sensorDefinitionSpec;

    @JsonPropertyDescription("Provider sensors list with provider specific sensor definitions.")
    private List<ProviderSensorModel> providerSensorList = new ArrayList<>();

    @JsonPropertyDescription("Whether the sensor is a User Home sensor")
    private boolean custom;

    @JsonPropertyDescription("This is a DQO built-in sensor, whose parameters cannot be changed.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    public SensorModel() {
    }

    /**
     * Adds provider sensor models to the provider sensor list. Replaces an already added model.
     * @param providerSensorBasicModels the provider sensor models to add
     */
    public void addProviderSensorModel(List<ProviderSensorModel> providerSensorBasicModels) {
        for (ProviderSensorModel newSensorProviderModel : providerSensorBasicModels) {
            boolean modelExists = false;
            for (int i = 0; i < this.providerSensorList.size(); i++) {
                ProviderSensorModel alreadyAddedProvider = this.providerSensorList.get(i);
                if (Objects.equals(newSensorProviderModel.getProviderType(), alreadyAddedProvider.getProviderType())) {
                    this.providerSensorList.set(i, newSensorProviderModel);
                    modelExists = true;
                    break;
                }
            }

            if (!modelExists) {
                this.providerSensorList.add(newSensorProviderModel);
            }
        }
    }

    /**
     * Checks whether the SensorDefinitionWrapper is equal to this SensorModel.
     * @param sensorDefinitionWrapper the SensorDefinitionWrapper to compare
     * @return true if the SensorDefinitionWrapper is equal to this SensorModel, otherwise false
     */
    public boolean equalsSensorDqo(SensorDefinitionWrapper sensorDefinitionWrapper) {
        if (sensorDefinitionWrapper == null) {
            return false;
        }

        if (!Objects.equals(sensorDefinitionWrapper.getName(), fullSensorName)) {
            return false;
        }

        if (!Objects.equals(sensorDefinitionWrapper.getSpec(), sensorDefinitionSpec)) {
            return false;
        }

        if(!equalsProviderSensorsList(sensorDefinitionWrapper.getProviderSensors())){
            return false;
        }

        return true;
    }

    /**
     * Checks whether the ProviderSensorDefinitionList
     * is equal to the provider sensor list of this SensorModel.
     * @param providerSensorDefinitionList the ProviderSensorDefinitionList to compare
     */
    public boolean equalsProviderSensorsList(ProviderSensorDefinitionList providerSensorDefinitionList){
        return providerSensorDefinitionList
                .toList()
                .stream()
                .allMatch(sensor -> {
                    ProviderSensorModel providerSensorModel = providerSensorList.stream()
                            .filter(dto -> Objects.equals(dto.getProviderType(), sensor.getProvider()))
                            .findFirst()
                            .orElse(null);
                    return providerSensorModel != null && providerSensorModel.equalsProviderSensorDqo(sensor);
                });
    }
}
