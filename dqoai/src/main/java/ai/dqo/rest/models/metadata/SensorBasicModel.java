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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Sensor basic model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorBasicModel", description = "Sensor basic model")
public class SensorBasicModel {

    @JsonPropertyDescription("Sensor name")
    private String sensorName;

    @JsonPropertyDescription("Full sensor name")
    private String fullSensorName;

    @JsonPropertyDescription("This sensor has is a built-in sensor.")
    private boolean custom;

    @JsonPropertyDescription("Provider sensor basic model list")
    private List<ProviderSensorBasicModel> providerSensorBasicModels;

    public SensorBasicModel(){}

    public SensorBasicModel(String sensorName, String fullSensorName, boolean custom, List<ProviderSensorBasicModel> providerSensorBasicModels) {
        this.sensorName = sensorName;
        this.fullSensorName = fullSensorName;
        this.custom = custom;
        this.providerSensorBasicModels = providerSensorBasicModels;
    }

    public void addProviderSensorBasicModel(List<ProviderSensorBasicModel> providerSensorBasicModels) {
        for (ProviderSensorBasicModel newModel : providerSensorBasicModels) {
            boolean modelExists = false;
            for (ProviderSensorBasicModel model : this.providerSensorBasicModels) {
                if (model.getProviderType() == newModel.getProviderType()) {
                    model.setCustom(newModel.isCustom());
                    modelExists = true;
                    break;
                }
            }
            if (!modelExists) {
                this.providerSensorBasicModels.add(newModel);
            }
        }
    }
}
