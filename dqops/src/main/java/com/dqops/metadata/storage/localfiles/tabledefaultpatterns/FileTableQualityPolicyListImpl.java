/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.tabledefaultpatterns;

import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.policies.table.TableQualityPolicyListImpl;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicyWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Default table-level checks pattern collection that uses a local file system (the user's home folder) to read .dqotablechecks.yaml files with the default checks by a named pattern.
 */
public class FileTableQualityPolicyListImpl extends TableQualityPolicyListImpl {
    @JsonIgnore
    private final FolderTreeNode defaultsFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a default checks pattern collection using a given "defaults" folder.
     * @param defaultsFolder  Defaults folder node.
     * @param yamlSerializer  Yaml serializer.
     * @param readOnly        Make the list read-only.
     */
    public FileTableQualityPolicyListImpl(FolderTreeNode defaultsFolder, YamlSerializer yamlSerializer, boolean readOnly) {
        super(readOnly);
        this.defaultsFolder = defaultsFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Defaults folder.
     * @return Defaults folder.
     */
    public FolderTreeNode getDefaultsFolder() {
        return defaultsFolder;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        // first: find the folders with .dqotablechecks.yaml default check patterns spec files
        for (FolderTreeNode defaultChecksSpecFolderNode : this.defaultsFolder.findNestedSubFoldersWithFiles(SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML, true)) {
            String defaultsFolderName = defaultChecksSpecFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "defaults" folder

            for (FileTreeNode specFileNode : defaultChecksSpecFolderNode.getFiles()) {
                String specFileName = specFileNode.getFilePath().getFileName();
                if (!specFileName.endsWith(SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML)) {
                    // not a spec file, skipping
                    continue;
                }

                String decodedSpecFileName = FileNameSanitizer.decodeFileSystemName(specFileName);
                String defaultChecksPatternModuleName = decodedSpecFileName.substring(0, decodedSpecFileName.length() - SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML.length());
                String patternName = (!defaultsFolderName.equals("") ? (defaultsFolderName + "/") : "") + defaultChecksPatternModuleName;

                if (this.getByObjectName(patternName, false) != null) {
                    continue; // was already added
                }
				this.addWithoutFullLoad(new FileTableQualityPolicyWrapperImpl(defaultChecksSpecFolderNode, patternName,
                        defaultChecksPatternModuleName, this.yamlSerializer, this.isReadOnly()));
            }
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param patternName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected TableQualityPolicyWrapperImpl createNewElement(String patternName) {
        String patternModuleName = patternName;
        String patternFolderPath = "";
        int indexOfFileLocation = patternName.lastIndexOf('/');
        if (indexOfFileLocation >= 0) {
            patternModuleName = patternName.substring(indexOfFileLocation + 1);
            patternFolderPath = patternName.substring(0, indexOfFileLocation);
        }
        FolderTreeNode ruleParentFolderNode = this.defaultsFolder.getOrAddFolderPath(patternFolderPath);
        FileTableQualityPolicyWrapperImpl checkModelWrapper =
                new FileTableQualityPolicyWrapperImpl(ruleParentFolderNode, patternName, patternModuleName, this.yamlSerializer, this.isReadOnly());
        checkModelWrapper.setSpec(new TableQualityPolicySpec());
        return checkModelWrapper;
    }
}
