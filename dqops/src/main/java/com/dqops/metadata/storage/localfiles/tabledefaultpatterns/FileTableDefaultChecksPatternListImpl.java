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
package com.dqops.metadata.storage.localfiles.tabledefaultpatterns;

import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternListImpl;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Default table-level checks pattern collection that uses a local file system (the user's home folder) to read .dqotablechecks.yaml files with the default checks by a named pattern.
 */
public class FileTableDefaultChecksPatternListImpl extends TableDefaultChecksPatternListImpl {
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
    public FileTableDefaultChecksPatternListImpl(FolderTreeNode defaultsFolder, YamlSerializer yamlSerializer, boolean readOnly) {
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
				this.addWithoutFullLoad(new FileTableDefaultChecksPatternWrapperImpl(defaultChecksSpecFolderNode, patternName,
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
    protected TableDefaultChecksPatternWrapperImpl createNewElement(String patternName) {
        String patternModuleName = patternName;
        String patternFolderPath = "";
        int indexOfFileLocation = patternName.lastIndexOf('/');
        if (indexOfFileLocation >= 0) {
            patternModuleName = patternName.substring(indexOfFileLocation + 1);
            patternFolderPath = patternName.substring(0, indexOfFileLocation);
        }
        FolderTreeNode ruleParentFolderNode = this.defaultsFolder.getOrAddFolderPath(patternFolderPath);
        FileTableDefaultChecksPatternWrapperImpl checkModelWrapper =
                new FileTableDefaultChecksPatternWrapperImpl(ruleParentFolderNode, patternName, patternModuleName, this.yamlSerializer, this.isReadOnly());
        checkModelWrapper.setSpec(new TableDefaultChecksPatternSpec());
        return checkModelWrapper;
    }
}
