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

import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Custom check definition collection that uses a local file system (the user's home folder) to read .dqocheck.yaml files with the check definitions.
 */
public class FileCheckDefinitionListImpl extends CheckDefinitionListImpl {
    @JsonIgnore
    private final FolderTreeNode checksFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a rule definition collection using a given checks folder.
     * @param checksFolder    Checks folder node.
     * @param yamlSerializer  Yaml serializer.
     * @param readOnly        Make the list read-only.
     */
    public FileCheckDefinitionListImpl(FolderTreeNode checksFolder, YamlSerializer yamlSerializer, boolean readOnly) {
        super(readOnly);
        this.checksFolder = checksFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Checks folder.
     * @return Checks folder.
     */
    public FolderTreeNode getChecksFolder() {
        return checksFolder;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        // first: find the folders with .dqocheck.yaml check spec files (when a rule has a configuration file)
        for (FolderTreeNode ruleSpecFolderNode : this.checksFolder.findNestedSubFoldersWithFiles(SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML, true)) {
            String checksFolderName = ruleSpecFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "checks" folder

            for (FileTreeNode specFileNode : ruleSpecFolderNode.getFiles()) {
                String specFileName = specFileNode.getFilePath().getFileName();
                if (!specFileName.endsWith(SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML)) {
                    // not a spec file, skipping
                    continue;
                }

                String decodedSpecFileName = FileNameSanitizer.decodeFileSystemName(specFileName);
                String checkModuleName = decodedSpecFileName.substring(0, decodedSpecFileName.length() - SpecFileNames.CUSTOM_CHECK_SPEC_FILE_EXT_YAML.length());
                String checkName = (!checksFolderName.equals("") ? (checksFolderName + "/") : "") + checkModuleName;

                if (this.getByObjectName(checkName, false) != null) {
                    continue; // was already added
                }
				this.addWithoutFullLoad(new FileCheckDefinitionWrapperImpl(ruleSpecFolderNode, checkName, checkModuleName, this.yamlSerializer, this.isReadOnly()));
            }
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param checkName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected CheckDefinitionWrapperImpl createNewElement(String checkName) {
        String checkModuleName = checkName;
        String checkFolderPath = "";
        int indexOfFileLocation = checkName.lastIndexOf('/');
        if (indexOfFileLocation >= 0) {
            checkModuleName = checkName.substring(indexOfFileLocation + 1);
            checkFolderPath = checkName.substring(0, indexOfFileLocation);
        }
        FolderTreeNode ruleParentFolderNode = this.checksFolder.getOrAddFolderPath(checkFolderPath);
        FileCheckDefinitionWrapperImpl checkModelWrapper =
                new FileCheckDefinitionWrapperImpl(ruleParentFolderNode, checkName, checkModuleName, this.yamlSerializer, this.isReadOnly());
        checkModelWrapper.setSpec(new CheckDefinitionSpec());
        return checkModelWrapper;
    }
}
