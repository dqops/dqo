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
package ai.dqo.metadata.storage.localfiles.sensordefinitions;

import ai.dqo.connectors.ProviderType;
import ai.dqo.core.filesystem.ApiVersion;
import ai.dqo.core.filesystem.localfiles.LocalFileSystemException;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.core.filesystem.virtual.FileTreeNode;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapperImpl;
import ai.dqo.metadata.storage.localfiles.SpecFileNames;
import ai.dqo.metadata.storage.localfiles.SpecificationKind;
import ai.dqo.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Locale;
import java.util.Objects;

/**
 * File based provider sensor spec wrapper. Loads and writes the provider sensor configuration to a yaml file in the user's home folder. Writes also the SQL template if present.
 */
public class FileProviderSensorDefinitionWrapperImpl extends ProviderSensorDefinitionWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode sensorDefinitionFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a file based provider specific sensor definition wrapper.
     * @param sensorDefinitionFolderNode Folder in the virtual file system that holds the sensor definition.
     * @param providerType Provider type.
     * @param yamlSerializer Yaml serializer.
     */
    public FileProviderSensorDefinitionWrapperImpl(FolderTreeNode sensorDefinitionFolderNode, ProviderType providerType, YamlSerializer yamlSerializer) {
        super(providerType);
        this.sensorDefinitionFolderNode = sensorDefinitionFolderNode;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads the provider sensor spec with the provider check configuration details.
     * @return Loaded provider configuration specification.
     */
    @Override
    public ProviderSensorDefinitionSpec getSpec() {
        ProviderSensorDefinitionSpec spec = super.getSpec();
        if (spec == null) {
            String providerNameLowerCase = this.getProvider().name().toLowerCase(Locale.ROOT);
            String fileNameWithExt = providerNameLowerCase + SpecFileNames.PROVIDER_SENSOR_SPEC_FILE_EXT_YAML;
            FileTreeNode fileNode = this.sensorDefinitionFolderNode.getChildFileByFileName(fileNameWithExt);

            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                ProviderSensorYaml deserialized = this.yamlSerializer.deserialize(textContent, ProviderSensorYaml.class, fileNode.getPhysicalAbsolutePath());
                ProviderSensorDefinitionSpec deserializedSpec = deserialized.getSpec();
                if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                    throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                }
                if (deserialized.getKind() != SpecificationKind.PROVIDER_SENSOR) {
                    throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                }
				this.setSpec(deserializedSpec);
				this.clearDirty(true);
                return deserializedSpec;
            }
        }
        return spec;
    }

    /**
     * Returns the SQL template for the template. A sensor may not have an SQL template and could be implemented
     * as a python module instead.
     *
     * @return Sql Template.
     */
    @Override
    public String getSqlTemplate() {
        String sqlTemplate = super.getSqlTemplate();
        if (sqlTemplate == null) {
            String providerNameLowerCase = this.getProvider().name().toLowerCase(Locale.ROOT);
            String fileNameWithExt = providerNameLowerCase + SpecFileNames.PROVIDER_SENSOR_SQL_TEMPLATE_EXT;
            FileTreeNode fileNode = this.sensorDefinitionFolderNode.getChildFileByFileName(fileNameWithExt);

            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
				this.setSqlTemplate(textContent);
				clearDirty(false);

               return textContent;
            }
        }

        return sqlTemplate;
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
        if (this.getStatus() == InstanceStatus.DELETED) {
            return; // do nothing
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        ProviderSensorYaml tableYaml = new ProviderSensorYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(tableYaml);
        FileContent newSpecFileContent = new FileContent(specAsYaml);
        String specFileNameWithExt = this.getProvider().name().toLowerCase(Locale.ROOT) + SpecFileNames.PROVIDER_SENSOR_SPEC_FILE_EXT_YAML;
        String templateFileNameWithExt = this.getProvider().name().toLowerCase(Locale.ROOT) + SpecFileNames.PROVIDER_SENSOR_SQL_TEMPLATE_EXT;

        switch (this.getStatus()) {
            case ADDED:
				this.sensorDefinitionFolderNode.addChildFile(specFileNameWithExt, newSpecFileContent);
				this.getSpec().clearDirty(true);
                if (this.getSqlTemplate() != null) {
					this.sensorDefinitionFolderNode.addChildFile(templateFileNameWithExt, new FileContent(this.getSqlTemplate()));
                }
            case MODIFIED:
                FileTreeNode modifiedFileNode = this.sensorDefinitionFolderNode.getChildFileByFileName(specFileNameWithExt);
                modifiedFileNode.changeContent(newSpecFileContent);
				this.getSpec().clearDirty(true);
                if (this.getSqlTemplate() != null) {
                    FileTreeNode currentSqlTemplateFile = this.sensorDefinitionFolderNode.getChildFileByFileName(templateFileNameWithExt);
                    if (currentSqlTemplateFile != null) {
                        currentSqlTemplateFile.changeContent(new FileContent(this.getSqlTemplate()));
                    }
                    else {
						this.sensorDefinitionFolderNode.addChildFile(templateFileNameWithExt, new FileContent(this.getSqlTemplate()));
                    }
                }
                else {
                    FileTreeNode currentSqlTemplateFileToDelete = this.sensorDefinitionFolderNode.getChildFileByFileName(templateFileNameWithExt);
                    if (currentSqlTemplateFileToDelete != null) {
                        currentSqlTemplateFileToDelete.markForDeletion();
                    }
                }
                break;
            case TO_BE_DELETED:
				this.sensorDefinitionFolderNode.deleteChildFile(specFileNameWithExt);
                FileTreeNode sqlTemplateToDelete = this.sensorDefinitionFolderNode.getChildFileByFileName(templateFileNameWithExt);
                if (sqlTemplateToDelete != null) {
                    sqlTemplateToDelete.markForDeletion();
                }
                break;
        }

        super.flush(); // change the statuses
    }
}
