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
import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapperImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
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
    @JsonIgnore
    private boolean sqlTemplateLoaded;
    @JsonIgnore
    private boolean errorSamplingTemplateLoaded;

    /**
     * Creates a file based provider specific sensor definition wrapper.
     * @param sensorDefinitionFolderNode Folder in the virtual file system that holds the sensor definition.
     * @param providerType Provider type.
     * @param yamlSerializer Yaml serializer.
     * @param readOnly Make the list read-only.
     */
    public FileProviderSensorDefinitionWrapperImpl(FolderTreeNode sensorDefinitionFolderNode,
                                                   ProviderType providerType,
                                                   YamlSerializer yamlSerializer,
                                                   boolean readOnly) {
        super(providerType, readOnly);
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
                ProviderSensorDefinitionSpec deserializedSpec = (ProviderSensorDefinitionSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    ProviderSensorYaml deserialized = this.yamlSerializer.deserialize(textContent, ProviderSensorYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserializedSpec == null) {
                        deserializedSpec = new ProviderSensorDefinitionSpec();
                    }
                    if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                        throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                    }
                    if (deserialized.getKind() != SpecificationKind.provider_sensor) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    ProviderSensorDefinitionSpec cachedObjectInstance = deserializedSpec.deepClone();
                    cachedObjectInstance.makeReadOnly(true);
                    if (this.getHierarchyId() != null) {
                        cachedObjectInstance.setHierarchyId(new HierarchyId(this.getHierarchyId(), "spec"));
                    }
                    fileContent.setCachedObjectInstance(cachedObjectInstance);
                } else {
                    deserializedSpec = this.isReadOnly() ? deserializedSpec : deserializedSpec.deepClone();
                }

				this.setSpec(deserializedSpec);
				this.clearDirty(false);
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
        if (sqlTemplate == null && !this.sqlTemplateLoaded) {
            String providerNameLowerCase = this.getProvider().name().toLowerCase(Locale.ROOT);
            String fileNameWithExt = providerNameLowerCase + SpecFileNames.PROVIDER_SENSOR_SQL_TEMPLATE_EXT;
            FileTreeNode fileNode = this.sensorDefinitionFolderNode.getChildFileByFileName(fileNameWithExt);
            this.sqlTemplateLoaded = true;

            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                boolean wasDirty = this.isDirty();
				this.setSqlTemplate(textContent);
                this.setSqlTemplateLastModified(fileContent.getLastModified());
				if (wasDirty) {
                    clearDirty(false);
                }

               return textContent;
            }
        }

        return sqlTemplate;
    }

    /**
     * Returns the file modification timestamp when the SQL template was modified for the last time.
     *
     * @return Last file modification timestamp.
     */
    @Override
    public Instant getSqlTemplateLastModified() {
        Instant sqlTemplateLastModified = super.getSqlTemplateLastModified();
        if (sqlTemplateLastModified == null && !this.sqlTemplateLoaded) {
            this.getSqlTemplate(); // trigger loading
        } else {
            return sqlTemplateLastModified;
        }

        return super.getSqlTemplateLastModified();
    }

    /**
     * Returns the error sampling SQL template for the template. A sensor may not have an SQL template and could be implemented
     * as a python module instead.
     *
     * @return Error sampling SQL Template.
     */
    @Override
    public String getErrorSamplingTemplate() {
        String errorSamplingTemplate = super.getErrorSamplingTemplate();
        if (errorSamplingTemplate == null && !this.errorSamplingTemplateLoaded) {
            String providerNameLowerCase = this.getProvider().name().toLowerCase(Locale.ROOT);
            String fileNameWithExt = providerNameLowerCase + SpecFileNames.PROVIDER_SENSOR_ERROR_SAMPLING_TEMPLATE_EXT;
            FileTreeNode fileNode = this.sensorDefinitionFolderNode.getChildFileByFileName(fileNameWithExt);
            this.errorSamplingTemplateLoaded = true;

            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                boolean wasDirty = this.isDirty();
                this.setErrorSamplingTemplate(textContent);
                this.setErrorSamplingTemplateLastModified(fileContent.getLastModified());
                if (wasDirty) {
                    clearDirty(false);
                }

                return textContent;
            }
        }

        return errorSamplingTemplate;
    }

    /**
     * Returns the file modification timestamp when the SQL template was modified for the last time.
     *
     * @return Last file modification timestamp.
     */
    @Override
    public Instant getErrorSamplingTemplateLastModified() {
        Instant errorSamplingTemplateLastModified = super.getErrorSamplingTemplateLastModified();
        if (errorSamplingTemplateLastModified == null && !this.errorSamplingTemplateLoaded) {
            this.getErrorSamplingTemplate(); // trigger loading
        } else {
            return errorSamplingTemplateLastModified;
        }

        return super.getErrorSamplingTemplateLastModified();
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

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance was never touched
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
        String errorSamplingTemplateFileNameWithExt = this.getProvider().name().toLowerCase(Locale.ROOT) + SpecFileNames.PROVIDER_SENSOR_ERROR_SAMPLING_TEMPLATE_EXT;

        switch (this.getStatus()) {
            case ADDED:
				this.sensorDefinitionFolderNode.addChildFile(specFileNameWithExt, newSpecFileContent);
				this.getSpec().clearDirty(true);
                if (this.getSqlTemplate() != null) {
					this.sensorDefinitionFolderNode.addChildFile(templateFileNameWithExt, new FileContent(this.getSqlTemplate()));
                }
                if (this.getErrorSamplingTemplate() != null) {
                    this.sensorDefinitionFolderNode.addChildFile(errorSamplingTemplateFileNameWithExt, new FileContent(this.getErrorSamplingTemplate()));
                }
                break;

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

                if (this.getErrorSamplingTemplate() != null) {
                    FileTreeNode currentErrorSamplingTemplateFile = this.sensorDefinitionFolderNode.getChildFileByFileName(errorSamplingTemplateFileNameWithExt);
                    if (currentErrorSamplingTemplateFile != null) {
                        currentErrorSamplingTemplateFile.changeContent(new FileContent(this.getErrorSamplingTemplate()));
                    }
                    else {
                        this.sensorDefinitionFolderNode.addChildFile(errorSamplingTemplateFileNameWithExt, new FileContent(this.getErrorSamplingTemplate()));
                    }
                }
                else {
                    FileTreeNode currentErrorSamplingTemplateFileToDelete = this.sensorDefinitionFolderNode.getChildFileByFileName(errorSamplingTemplateFileNameWithExt);
                    if (currentErrorSamplingTemplateFileToDelete != null) {
                        currentErrorSamplingTemplateFileToDelete.markForDeletion();
                    }
                }

                break;

            case TO_BE_DELETED:
				this.sensorDefinitionFolderNode.deleteChildFile(specFileNameWithExt);
                FileTreeNode sqlTemplateToDelete = this.sensorDefinitionFolderNode.getChildFileByFileName(templateFileNameWithExt);
                if (sqlTemplateToDelete != null) {
                    sqlTemplateToDelete.markForDeletion();
                }

                FileTreeNode errorSamplingTemplateToDelete = this.sensorDefinitionFolderNode.getChildFileByFileName(errorSamplingTemplateFileNameWithExt);
                if (errorSamplingTemplateToDelete != null) {
                    errorSamplingTemplateToDelete.markForDeletion();
                }
                break;
        }

        super.flush(); // change the statuses
    }
}
