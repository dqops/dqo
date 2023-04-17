/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.models.metadata;

import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Sensor model returned from REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorModel", description = "Sensor model.")
public class SensorModel {

    @JsonPropertyDescription("Full sensor name")
    private String fullSensorName;

    @JsonPropertyDescription("Sensor definition spec")
    private SensorDefinitionSpec sensorDefinitionSpec;

    @JsonPropertyDescription("Provider sensors list")
    private List<ProviderSensorModel> providerSensorList;

    @JsonPropertyDescription("Whether the sensor is a User Home sensor")
    private boolean custom;

    public SensorModel() {
        this.providerSensorList = new ArrayList<>();
    }


    /**
     * Adds provider sensor models to the provider sensor list.
     * @param providerSensorBasicModels the provider sensor models to add
     */
    public void addProviderSensorModel(List<ProviderSensorModel> providerSensorBasicModels) {
        for (ProviderSensorModel newModel : providerSensorBasicModels) {
            boolean modelExists = false;
            for (ProviderSensorModel model : this.providerSensorList) {
                if (model.getProviderType() == newModel.getProviderType()) {
                    model.setCustom(newModel.isCustom());
                    modelExists = true;
                    break;
                }
            }
            if (!modelExists) {
                this.providerSensorList.add(newModel);
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

        if (!sensorDefinitionWrapper.getName().equals(fullSensorName)) {
            return false;
        }

        if (!sensorDefinitionWrapper.getSpec().equals(sensorDefinitionSpec)) {
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
                            .filter(dto -> dto.getProviderType().equals(sensor.getProvider()))
                            .findFirst()
                            .orElse(null);
                    return providerSensorModel != null && providerSensorModel.equalsProviderSensorDqo(sensor);
                });
    }
}
