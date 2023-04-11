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

package ai.dqo.rest.models.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Sensor folder model returned from REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SensorFileModel", description = "Sensor file model")
public class SensorFolderModel {

    @JsonPropertyDescription("Folder name")
    private String folderName;

    @JsonPropertyDescription("Sensor map")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private SensorModel sensor;

    @JsonPropertyDescription("Sub-folders")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, SensorFolderModel> folders;

    public SensorFolderModel(String folderName) {
        this.folderName = folderName;
        this.sensor = null;
        this.folders = new HashMap<>();
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolders(Map<String, SensorFolderModel> folders) {
        this.folders = folders;
    }

    public void setSensor(SensorModel sensor) {
        this.sensor = sensor;
    }

    public String getFolderName() {
        return folderName;
    }
    public Map<String, SensorFolderModel> getFolders() {
        return folders;
    }

    public SensorModel getSensor() {
        return sensor;
    }

    /**
     * Returns a sensor model located in a specific folder identified by an array of folder names.
     * @param folderNames an array of folder names representing the path to the desired sensor model
     * @return the sensor model if found, or null if not found
     */
    public SensorModel getSensorModel(String[] folderNames) {
        SensorFolderModel current = this;
        for (String folder : folderNames) {
            if (current.folders.containsKey(folder)) {
                current = current.folders.get(folder);
            } else {
                return null;
            }
        }
        return current.sensor;
    }

    /**
     * Adds a sensor model to the sensor folder model hierarchy.
     * @param sensorModel the sensor model to be added
     * @return the updated sensor folder model
     */
    public SensorFolderModel addSensor(SensorModel sensorModel) {
        if (sensorModel == null || sensorModel.getSensorName() == null || sensorModel.getSensorName().isEmpty()) {
            return this;
        }

        String[] sensorPath = sensorModel.getSensorName().split("/");
        SensorFolderModel currentFolder = this;

        for (int i = 0; i < sensorPath.length; i++) {
            String folderName = sensorPath[i];
            if (i == sensorPath.length - 1) {
                if (!currentFolder.folders.containsKey(folderName)) {
                    currentFolder.folders.put(folderName, new SensorFolderModel(folderName));
                }
                currentFolder = currentFolder.folders.get(folderName);

                if (currentFolder.sensor != null) {
                    currentFolder.sensor.setCustom(sensorModel.isCustom());
                    currentFolder.sensor.setSensorDefinitionSpec(sensorModel.getSensorDefinitionSpec());
                    currentFolder.sensor.getSensors().putAll(sensorModel.getSensors());
                } else {
                    currentFolder.sensor = sensorModel;
                }
            } else {
                if (!currentFolder.folders.containsKey(folderName)) {
                    currentFolder.folders.put(folderName, new SensorFolderModel(folderName));
                }
                currentFolder = currentFolder.folders.get(folderName);
            }
        }
        return this;
    }
}