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

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File based quality sensor definition wrapper. Loads and writes a data quality sensor definition to a yaml file in the user's home folder or in the system home folder.
 */
public class FileSensorDefinitionWrapperImpl extends SensorDefinitionWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode sensorFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a new wrapper for a sensor definition that uses yaml files for storage.
     * @param sensorFolderNode Sensor folder with files.
     * @param yamlSerializer Yaml serializer.
     */
    public FileSensorDefinitionWrapperImpl(FolderTreeNode sensorFolderNode, YamlSerializer yamlSerializer) {
        super(sensorFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName());
        this.sensorFolderNode = sensorFolderNode;
        this.yamlSerializer = yamlSerializer;
		this.setProviderSensors(new FileProviderSensorDefinitionListImpl(sensorFolderNode, yamlSerializer));
		this.setStatus(InstanceStatus.UNCHANGED);
    }

    /**
     * Returns the folder that contains the sensor files.
     * @return Sensor folder.
     */
    @JsonIgnore
    public FolderTreeNode getSensorFolderNode() {
        return sensorFolderNode;
    }

    /**
     * Loads the data quality sensor definition spec with the sensor configuration details.
     * @return Loaded quality sensor spec.
     */
    @Override
    public SensorDefinitionSpec getSpec() {
        SensorDefinitionSpec spec = super.getSpec();
        if (spec == null) {
            FileTreeNode fileNode = this.sensorFolderNode.getChildFileByFileName(SpecFileNames.SENSOR_SPEC_FILE_NAME_YAML);
            FileContent fileContent = fileNode.getContent();
            String textContent = fileContent.getTextContent();
            SensorDefinitionSpec deserializedSpec = (SensorDefinitionSpec) fileContent.getCachedObjectInstance();

            if (deserializedSpec == null) {
                SensorDefinitionYaml deserialized = this.yamlSerializer.deserialize(textContent, SensorDefinitionYaml.class, fileNode.getPhysicalAbsolutePath());
                deserializedSpec = deserialized.getSpec();
                if (deserializedSpec == null) {
                    deserializedSpec = new SensorDefinitionSpec();
                }

                if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                    throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                }
                if (deserialized.getKind() != SpecificationKind.SENSOR) {
                    throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                }

                fileContent.setCachedObjectInstance(deserializedSpec.deepClone());
            } else {
                deserializedSpec = deserializedSpec.deepClone();
            }
			this.setSpec(deserializedSpec);
			this.clearDirty(true);
            return deserializedSpec;
        }
        return spec;
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
        if (this.getStatus() == InstanceStatus.DELETED || this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            return; // do nothing
        }

		this.getProviderSensors().flush(); // the first call to flush, maybe all provider checks are deleted and the check is deleted right after

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is empty (no file)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        SensorDefinitionYaml sensorDefinitionYaml = new SensorDefinitionYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(sensorDefinitionYaml);
        FileContent newFileContent = new FileContent(specAsYaml);

        switch (this.getStatus()) {
            case ADDED:
				this.sensorFolderNode.addChildFile(SpecFileNames.SENSOR_SPEC_FILE_NAME_YAML, newFileContent);
				this.getSpec().clearDirty(true);
            case MODIFIED:
                FileTreeNode modifiedFileNode = this.sensorFolderNode.getChildFileByFileName(SpecFileNames.SENSOR_SPEC_FILE_NAME_YAML);
                modifiedFileNode.changeContent(newFileContent);
				this.getSpec().clearDirty(true);
                break;
            case TO_BE_DELETED:
				this.sensorFolderNode.deleteChildFile(SpecFileNames.SENSOR_SPEC_FILE_NAME_YAML);
				this.sensorFolderNode.setDeleteOnFlush(true); // will remove the whole folder for the source
                break;
        }

        super.flush(); // change the statuses
    }
}
