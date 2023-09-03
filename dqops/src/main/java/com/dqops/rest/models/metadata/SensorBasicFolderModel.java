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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.*;

/**
 * Sensor basic folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorBasicFolderModel", description = "Sensor basic folder model")
public class SensorBasicFolderModel {
    @JsonPropertyDescription("A map of folder-level children sensors.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, SensorBasicFolderModel> folders;

    @JsonPropertyDescription("Whether the sensor is a User Home sensor.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SensorBasicModel> sensors;

    /**
     * Creates a new instance of SensorBasicFolderModel with empty lists.
     */
    public SensorBasicFolderModel() {
        sensors = new ArrayList<>();
        folders = new HashMap<>();
    }

    /**
     * Adds a sensor to this folder based on the given path.
     * @param fullSensorName the path of the sensor
     * @param providerSensorBasicModelList  List of provider specific sensor definitions.
     * @param sensorDefinitionSource Sensor source (custom or built-in).
     * @param canEdit The current user can edit the sensor definition.
     */
    public void addSensor(String fullSensorName,
                          List<ProviderSensorBasicModel> providerSensorBasicModelList,
                          SensorDefinitionSource sensorDefinitionSource,
                          boolean canEdit) {

        String[] sensorFolders = fullSensorName.split("/");
        String sensorName = sensorFolders[sensorFolders.length - 1];
        SensorBasicFolderModel folderModel = this;


        for (int i = 0; i < sensorFolders.length - 1; i++) {
            String name = sensorFolders[i];
            SensorBasicFolderModel nextSensorFolder = folderModel.folders.get(name);
            if (nextSensorFolder == null) {
                nextSensorFolder = new SensorBasicFolderModel();
                folderModel.folders.put(name, nextSensorFolder);
            }
            folderModel = nextSensorFolder;
        }

        boolean sensorExists = false;
        if (folderModel.sensors != null) {
            for (SensorBasicModel sensor : folderModel.sensors) {
                if (Objects.equals(sensor.getSensorName(), sensorName)) {
                    sensor.setSensorSource(sensorDefinitionSource);
                    sensor.addProviderSensorBasicModel(providerSensorBasicModelList);
                    sensorExists = true;
                    break;
                }
            }
        } else {
            folderModel.sensors = new ArrayList<>();
        }

        if (!sensorExists) {
            SensorBasicModel sensorBasicModel = new SensorBasicModel();
            sensorBasicModel.setSensorName(sensorName);
            sensorBasicModel.setFullSensorName(fullSensorName);
            sensorBasicModel.setSensorSource(sensorDefinitionSource);
            sensorBasicModel.setProviderSensorBasicModels(providerSensorBasicModelList);
            sensorBasicModel.setCanEdit(canEdit);
            folderModel.sensors.add(sensorBasicModel);
        }
    }

    /**
     * Collects all sensors from all tree levels.
     * @return A list of all sensors.
     */
    public List<SensorBasicModel> getAllSensors() {
        List<SensorBasicModel> allSensors = new ArrayList<>(this.getSensors());
        for (SensorBasicFolderModel folder : this.folders.values()) {
            allSensors.addAll(folder.getAllSensors());
        }

        return allSensors;
    }
}
