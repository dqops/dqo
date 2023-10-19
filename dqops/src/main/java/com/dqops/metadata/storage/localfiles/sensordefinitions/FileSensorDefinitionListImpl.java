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
package com.dqops.metadata.storage.localfiles.sensordefinitions;

import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.sensors.SensorDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data quality sensor definition collection that uses a local file system (the user's home folder) to read yaml files.
 */
public class FileSensorDefinitionListImpl extends SensorDefinitionListImpl {
    @JsonIgnore
    private final FolderTreeNode sensorsFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a sensor definition collection using a given sensor's folder.
     * @param sensorsFolder Sensor definitions folder node.
     * @param yamlSerializer Yaml serializer.
     */
    public FileSensorDefinitionListImpl(FolderTreeNode sensorsFolder, YamlSerializer yamlSerializer) {
        this.sensorsFolder = sensorsFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Sensor's folder.
     * @return Sensor's folder.
     */
    public FolderTreeNode getSensorsFolder() {
        return sensorsFolder;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for(FolderTreeNode sensorDefinitionFolderNode : this.sensorsFolder.findNestedSubFoldersWithFiles(SpecFileNames.SENSOR_SPEC_FILE_NAME_YAML, false)) {
            String sensorName = sensorDefinitionFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "sensors" folder
            if (this.getByObjectName(sensorName, false) != null) {
                continue; // was already added
            }
			this.addWithoutFullLoad(new FileSensorDefinitionWrapperImpl(sensorDefinitionFolderNode, this.yamlSerializer));
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param sensorName Object name (sensor name).
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected SensorDefinitionWrapperImpl createNewElement(String sensorName) {
        FolderTreeNode newSensorFolderNode = this.sensorsFolder.getOrAddFolderPath(sensorName);
        FileSensorDefinitionWrapperImpl sensorDefinitionModelWrapper = new FileSensorDefinitionWrapperImpl(newSensorFolderNode, this.yamlSerializer);
        sensorDefinitionModelWrapper.setName(sensorName);
        sensorDefinitionModelWrapper.setSpec(new SensorDefinitionSpec());
        sensorDefinitionModelWrapper.setStatus(InstanceStatus.ADDED);
        return sensorDefinitionModelWrapper;
    }
}
