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
package com.dqops.metadata.storage.localfiles.checkdefinitions;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File based custom check definition wrapper. Loads and writes the custom check to a yaml file.
 */
public class FileCheckDefinitionWrapperImpl extends CheckDefinitionWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode customCheckFolderNode;
    @JsonIgnore
    private final String checkFileNameBaseName;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a custom check spec wrapper that is file based.
     * @param customCheckFolderNode Custom check folder with yaml files.
     * @param checkName Full check name as used to store in the database.
     * @param checkFileNameBaseName Check module name. This is the check specification file name inside the checks folder without the .dqocheck.yaml extension.
     * @param yamlSerializer Yaml serializer.
     */
    public FileCheckDefinitionWrapperImpl(FolderTreeNode customCheckFolderNode, String checkName, String checkFileNameBaseName, YamlSerializer yamlSerializer) {
        super(checkName);
        this.customCheckFolderNode = customCheckFolderNode;
        this.checkFileNameBaseName = checkFileNameBaseName;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Returns the folder that contains the check definition files.
     * @return Checks folder.
     */
    @JsonIgnore
    public FolderTreeNode getCheckFolderNode() {
        return customCheckFolderNode;
    }

    /**
     * Returns the check module name inside the folder. This is the bare file name without the .dqocheck.yaml extensions.
     * @return Bare check name inside the folder.
     */
    public String getCheckFileNameBaseName() {
        return checkFileNameBaseName;
    }

    /**
     * Loads the rule specification (check parameters) from a .dqocheck.yaml file.
     * @return Loaded custom check specification.
     */
    @Override
    public CheckDefinitionSpec getSpec() {
        CheckDefinitionSpec spec = super.getSpec();
        if (spec == null) {
            String specFileName = this.checkFileNameBaseName + SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML;
            FileTreeNode fileNode = this.customCheckFolderNode.getChildFileByFileName(specFileName);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                CheckDefinitionSpec deserializedSpec = (CheckDefinitionSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    CheckDefinitionYaml deserialized = this.yamlSerializer.deserialize(textContent, CheckDefinitionYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserializedSpec == null) {
                        deserializedSpec = new CheckDefinitionSpec();
                    }

                    if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                        throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                    }
                    if (deserialized.getKind() != SpecificationKind.CHECK) {
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
            else {
                CheckDefinitionSpec newEmptySpec = new CheckDefinitionSpec();
				this.setSpec(newEmptySpec);
				this.clearDirty(true);
                return newEmptySpec;
            }
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

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is null (not present)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        CheckDefinitionYaml checkDefinitionYaml = new CheckDefinitionYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(checkDefinitionYaml);
        FileContent newSpecFileContent = new FileContent(specAsYaml);
        String specFileNameWithExt = this.checkFileNameBaseName + SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML;

        switch (this.getStatus()) {
            case ADDED:
				this.customCheckFolderNode.addChildFile(specFileNameWithExt, newSpecFileContent);
				this.getSpec().clearDirty(true);
            case MODIFIED:
                FileTreeNode modifiedFileNode = this.customCheckFolderNode.getChildFileByFileName(specFileNameWithExt);
                modifiedFileNode.changeContent(newSpecFileContent);
				this.getSpec().clearDirty(true);
                break;
            case TO_BE_DELETED:
				this.customCheckFolderNode.deleteChildFile(specFileNameWithExt);
                break;
        }

        super.flush(); // change the statuses
    }
}
