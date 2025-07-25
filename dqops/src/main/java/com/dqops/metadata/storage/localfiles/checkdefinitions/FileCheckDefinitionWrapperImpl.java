/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.checkdefinitions;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapperImpl;
import com.dqops.metadata.id.HierarchyId;
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
     * @param readOnly Make the wrapper read-only.
     */
    public FileCheckDefinitionWrapperImpl(FolderTreeNode customCheckFolderNode,
                                          String checkName,
                                          String checkFileNameBaseName,
                                          YamlSerializer yamlSerializer,
                                          boolean readOnly) {
        super(checkName, readOnly);
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
            String specFileName = FileNameSanitizer.encodeForFileSystem(this.checkFileNameBaseName) + SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML;
            FileTreeNode fileNode = this.customCheckFolderNode.getChildFileByFileName(specFileName);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                this.setLastModified(fileContent.getLastModified());
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
                    if (deserialized.getKind() != SpecificationKind.check) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    CheckDefinitionSpec cachedObjectInstance = deserializedSpec.deepClone();
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
        String specFileNameWithExt = FileNameSanitizer.encodeForFileSystem(this.checkFileNameBaseName) + SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML;

        switch (this.getStatus()) {
            case ADDED:
				this.customCheckFolderNode.addChildFile(specFileNameWithExt, newSpecFileContent);
				this.getSpec().clearDirty(true);
                break;

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
