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

import ai.dqo.connectors.ProviderType;
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
    private Map<ProviderType, SensorBasicModel> sensors;

    @JsonPropertyDescription("Sub-folders")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, SensorFolderModel> folders;

    public SensorFolderModel(String folderName) {
        this.folderName = folderName;
        this.sensors = new HashMap<>();
        this.folders = new HashMap<>();
    }

    public SensorFolderModel(String folderName, Map<ProviderType, SensorBasicModel> sensors, Map<String, SensorFolderModel> folders) {
        this.folderName = folderName;
        this.sensors = sensors;
        this.folders = folders;
    }


    public void setSensors(Map<ProviderType, SensorBasicModel> sensors) {
        this.sensors = sensors;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolders(Map<String, SensorFolderModel> folders) {
        this.folders = folders;
    }

    public String getFolderName() {
        return folderName;
    }

    public Map<ProviderType, SensorBasicModel> getSensors() {
        return sensors;
    }

    public Map<String, SensorFolderModel> getFolders() {
        return folders;
    }

    /**
     * Adds a DQO sensor to the Sensor Folder Model.
     * @param sensorPath The path of the DQO sensor to add.
     * @param sensors A map containing the ProviderType and SensorBasicModel of each sensor to add.
     * @return The updated Sensor Folder Model.
     */
    public SensorFolderModel withDqoSensor(String sensorPath, Map<ProviderType, SensorBasicModel> sensors) {
        if (sensorPath == null || sensorPath.isEmpty()) {
            return this;
        }

        String[] folders = sensorPath.split("/");
        SensorFolderModel current = this;

        for (int i = 0; i < folders.length - 1; i++) {
            String folder = folders[i];

            if (!current.folders.containsKey(folder)) {
                current.folders.put(folder, new SensorFolderModel(folder));
            }

            current = current.folders.get(folder);
        }

        String lastFolder = folders[folders.length - 1];
        if (!current.folders.containsKey(lastFolder)) {
            current.folders.put(lastFolder, new SensorFolderModel(lastFolder));
        }

        current = current.folders.get(lastFolder);

        for (ProviderType provider : sensors.keySet()) {
            if (!current.sensors.containsKey(provider)) {
                current.sensors.put(provider, sensors.get(provider));
            }
        }

        return this;
    }

    /**
     Retrieves the SensorBasicModel of the specified folder(s) within the Sensor Folder Model.
     @param folderNames The names of the folders to retrieve the SensorBasicModel from.
     @return A map containing the ProviderType and SensorBasicModel of each sensor in the specified folder(s),
     or null if the folder(s) does not exist.
     */
    public Map<ProviderType, SensorBasicModel> getSensorBasicModel(String[] folderNames) {
        SensorFolderModel current = this;
        for (String folder : folderNames) {
            if (current.folders.containsKey(folder)) {
                current = current.folders.get(folder);
            } else {
                return null;
            }
        }
        return current.sensors;
    }
}