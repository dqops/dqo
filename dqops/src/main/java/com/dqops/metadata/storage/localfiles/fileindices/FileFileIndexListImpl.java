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
package com.dqops.metadata.storage.localfiles.fileindices;

import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.fileindices.FileIndexListImpl;
import com.dqops.metadata.fileindices.FileIndexName;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.fileindices.FileIndexWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File index that uses a local file system (the user's home folder) to read json files with file indexes.
 */
public class FileFileIndexListImpl extends FileIndexListImpl {
    @JsonIgnore
    private final FolderTreeNode indicesFolder;
    @JsonIgnore
    private final JsonSerializer jsonSerializer;

    /**
     * Creates a file index collection using a given parent connection folder.
     * @param indicesFolder Parent file connection folder node.
     * @param jsonSerializer Json serializer.
     * @param readOnly Make the list read-only.
     */
    public FileFileIndexListImpl(FolderTreeNode indicesFolder, JsonSerializer jsonSerializer, boolean readOnly) {
        super(readOnly);
        this.indicesFolder = indicesFolder;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for(FileTreeNode fileTreeNode : this.indicesFolder.getFiles()) {
            if (!fileTreeNode.getFilePath().getFileName().endsWith(SpecFileNames.FILE_INDEX_SPEC_FILE_EXT_JSON)) {
                continue; // not a file index file
            }
            String baseFileName = truncateFileExtension(fileTreeNode.getFilePath().getFileName());
            FileIndexName fileIndexName = FileIndexName.fromBaseFileName(baseFileName);
            if (fileIndexName != null) {
                this.addWithoutFullLoad(new FileFileIndexWrapperImpl(this.indicesFolder, fileIndexName, this.jsonSerializer, this.isReadOnly()));
            }
        }
    }

    /**
     * Truncates the file name extension and leaves the base file name.
     * @param fileName Full file name with the file index specification json, including the file extension.
     * @return Base file name used for the object indexing.
     */
    public static String truncateFileExtension(String fileName) {
        assert fileName.endsWith(SpecFileNames.FILE_INDEX_SPEC_FILE_EXT_JSON);
        return fileName.substring(0, fileName.length() - SpecFileNames.FILE_INDEX_SPEC_FILE_EXT_JSON.length());
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param indexName Base index name (the name before the file extension).
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected FileIndexWrapperImpl createNewElement(FileIndexName indexName) {
        FileFileIndexWrapperImpl fileIndexWrapper = new FileFileIndexWrapperImpl(this.indicesFolder,
               indexName, this.jsonSerializer, this.isReadOnly());
        fileIndexWrapper.setSpec(new FileIndexSpec());
        return fileIndexWrapper;
    }
}
