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
package com.dqops.metadata.storage.localfiles.sources;

import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableListImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Tables collection that uses a local file system (the user's home folder) to read yaml files.
 */
public class FileTableListImpl extends TableListImpl {
    @JsonIgnore
    private final FolderTreeNode connectionFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a table collection using a given parent connection folder.
     * @param connectionFolder Parent connection folder node.
     * @param yamlSerializer  Yaml serializer.
     */
    public FileTableListImpl(FolderTreeNode connectionFolder, YamlSerializer yamlSerializer) {
        this.connectionFolder = connectionFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for (FileTreeNode fileTreeNode : this.connectionFolder.getFiles()) {
            if (!fileTreeNode.getFilePath().getFileName().endsWith(SpecFileNames.TABLE_SPEC_FILE_EXT_YAML)) {
                continue; // not a table file
            }
            String baseFileName = truncateFileExtension(fileTreeNode.getFilePath().getFileName());
            PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName(baseFileName);
			this.addWithoutFullLoad(new FileTableWrapperImpl(this.connectionFolder, baseFileName, physicalTableName, this.yamlSerializer));
        }
    }

    /**
     * Truncates the file name extension and leaves the base file name.
     * @param fileName Full file name with the table specification yaml, including the file extension.
     * @return Base file name used for the object indexing.
     */
    public static String truncateFileExtension(String fileName) {
        assert fileName.endsWith(SpecFileNames.TABLE_SPEC_FILE_EXT_YAML);
        return fileName.substring(0, fileName.length() - SpecFileNames.TABLE_SPEC_FILE_EXT_YAML.length());
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param physicalTableName Physical table name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected TableWrapperImpl createNewElement(PhysicalTableName physicalTableName) {
        FileTableWrapperImpl tableWrapper = new FileTableWrapperImpl(this.connectionFolder,
                physicalTableName.toBaseFileName(), physicalTableName, this.yamlSerializer);
        tableWrapper.setSpec(new TableSpec());
        return tableWrapper;
    }
}
