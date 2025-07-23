/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.dictionaries.DictionaryWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data dictionary collection that uses a local file system (the user's home folder) to read and write data dictionary cSV files in the user's home "dictionaries" folder.
 */
public class FileDictionaryListImpl extends DictionaryListImpl {
    @JsonIgnore
    private final FolderTreeNode dictionariesFolderNode;

    /**
     * Creates a data dictionaries collection using a given "dictionaries" folder.
     * @param dictionariesFolderNode Data dictionaries folder node.
     * @param readOnly Make the list read-only.
     */
    public FileDictionaryListImpl(FolderTreeNode dictionariesFolderNode, boolean readOnly) {
        super(readOnly);
        this.dictionariesFolderNode = dictionariesFolderNode;
    }

    /**
     * Returns the virtual folder node to the "dictionaries" folder.
     * @return Dictionaries folder node.
     */
    public FolderTreeNode getDictionariesFolderNode() {
        return dictionariesFolderNode;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for (FileTreeNode fileTreeNode : this.dictionariesFolderNode.getFiles()) {
            String baseFileName = fileTreeNode.getFilePath().getFileName();
            String decodedFileName = FileNameSanitizer.decodeFileSystemName(baseFileName);
            this.addWithoutFullLoad(new FileDictionaryWrapperImpl(this.dictionariesFolderNode, decodedFileName, this.isReadOnly()));
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param dictionaryName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected DictionaryWrapperImpl createNewElement(String dictionaryName) {
        FileDictionaryWrapperImpl sharedCredentialWrapper =
                new FileDictionaryWrapperImpl(this.dictionariesFolderNode, dictionaryName, this.isReadOnly());
        sharedCredentialWrapper.setObject(new FileContent());
        return sharedCredentialWrapper;
    }
}
