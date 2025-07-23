/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.similarity;

import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexListImpl;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexSpec;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Connection similarity index that uses a local file system (the user's home folder) to read json files with similarity indexes.
 */
public class FileConnectionSimilarityIndexListImpl extends ConnectionSimilarityIndexListImpl {
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
    public FileConnectionSimilarityIndexListImpl(FolderTreeNode indicesFolder, JsonSerializer jsonSerializer, boolean readOnly) {
        super(readOnly);
        this.indicesFolder = indicesFolder;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for (FileTreeNode fileTreeNode : this.indicesFolder.getFiles()) {
            if (!fileTreeNode.getFilePath().getFileName().endsWith(SpecFileNames.CONNECTION_SIMILARITY_INDEX_SPEC_FILE_EXT_JSON)) {
                continue; // not a file index file
            }
            String connectionName = FileNameSanitizer.decodeFileSystemName(truncateFileExtension(fileTreeNode.getFilePath().getFileName()));
            if (connectionName != null) {
                this.addWithoutFullLoad(new FileConnectionSimilarityIndexWrapperImpl(this.indicesFolder, connectionName, this.jsonSerializer, this.isReadOnly()));
            }
        }
    }

    /**
     * Truncates the file name extension and leaves the base file name.
     * @param fileName Full file name with the file index specification json, including the file extension.
     * @return Base file name used for the object indexing.
     */
    public static String truncateFileExtension(String fileName) {
        assert fileName.endsWith(SpecFileNames.CONNECTION_SIMILARITY_INDEX_SPEC_FILE_EXT_JSON);
        return fileName.substring(0, fileName.length() - SpecFileNames.CONNECTION_SIMILARITY_INDEX_SPEC_FILE_EXT_JSON.length());
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param connectionName Base index name (the name before the file extension).
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected ConnectionSimilarityIndexWrapperImpl createNewElement(String connectionName) {
        FileConnectionSimilarityIndexWrapperImpl connectionSimilarityIndexWrapper = new FileConnectionSimilarityIndexWrapperImpl(this.indicesFolder,
               connectionName, this.jsonSerializer, this.isReadOnly());
        connectionSimilarityIndexWrapper.setSpec(new ConnectionSimilarityIndexSpec());
        return connectionSimilarityIndexWrapper;
    }
}
