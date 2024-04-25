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

import com.dqops.connectors.ProviderType;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Provider specific sensor definition collection that uses a local file system (the user's home folder) to read yaml files.
 */
public class FileProviderSensorDefinitionListImpl extends ProviderSensorDefinitionListImpl {
    private static final Logger LOG = LoggerFactory.getLogger(FileProviderSensorDefinitionListImpl.class);

    @JsonIgnore
    private final FolderTreeNode sensorDefinitionFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a table collection using a given parent connection folder.
     * @param sensorDefinitionFolder Parent sensor definition folder node.
     * @param yamlSerializer  Yaml serializer.
     * @param readOnly Make the list read-only.
     */
    public FileProviderSensorDefinitionListImpl(FolderTreeNode sensorDefinitionFolder, YamlSerializer yamlSerializer, boolean readOnly) {
        super(readOnly);
        this.sensorDefinitionFolder = sensorDefinitionFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for(FileTreeNode fileTreeNode : this.sensorDefinitionFolder.getFiles()) {
            // TODO: support check definitions that have only an SQL template file, without a yaml configuration (simple definitions)

            if (!fileTreeNode.getFilePath().getFileName().endsWith(SpecFileNames.PROVIDER_SENSOR_SPEC_FILE_EXT_YAML)) {
                continue; // not a table file
            }
            String baseFileName = truncateFileExtension(fileTreeNode.getFilePath().getFileName());
            try {
                ProviderType providerType = ProviderType.valueOf(baseFileName.toLowerCase(Locale.ROOT));
				this.addWithoutFullLoad(new FileProviderSensorDefinitionWrapperImpl(this.sensorDefinitionFolder, providerType, this.yamlSerializer, this.isReadOnly()));
            }
            catch (Exception ex) {
				LOG.error("Failed to load " + fileTreeNode.getFilePath().toString() + " provider sensor definition, probably the provider name is unsupported", ex);
            }
        }
    }

    /**
     * Truncates the file name extension and leaves the base file name.
     * @param fileName Full file name with the table specification yaml, including the file extension.
     * @return Base file name used for the object indexing.
     */
    public static String truncateFileExtension(String fileName) {
        assert fileName.endsWith(SpecFileNames.PROVIDER_SENSOR_SPEC_FILE_EXT_YAML);
        return fileName.substring(0, fileName.length() - SpecFileNames.PROVIDER_SENSOR_SPEC_FILE_EXT_YAML.length());
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param providerType Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected ProviderSensorDefinitionWrapperImpl createNewElement(ProviderType providerType) {
        ProviderSensorDefinitionWrapperImpl tableWrapper = new FileProviderSensorDefinitionWrapperImpl(this.sensorDefinitionFolder, providerType, this.yamlSerializer, this.isReadOnly());
        tableWrapper.setProvider(providerType);
        tableWrapper.setSpec(new ProviderSensorDefinitionSpec());
        return tableWrapper;
    }
}
