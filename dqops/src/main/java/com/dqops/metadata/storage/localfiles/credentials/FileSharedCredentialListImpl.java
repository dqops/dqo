/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.credentials.SharedCredentialListImpl;
import com.dqops.metadata.credentials.SharedCredentialWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Shared credential collection that uses a local file system (the user's home folder) to read and write credential files in the user's home .credentials folder.
 */
public class FileSharedCredentialListImpl extends SharedCredentialListImpl {
    @JsonIgnore
    private final FolderTreeNode credentialsFolderNode;

    /**
     * Creates a shared credentials collection using a given .credentials folder.
     * @param credentialsFolderNode Credentials folder node.
     * @param readOnly Make the list read-only.
     */
    public FileSharedCredentialListImpl(FolderTreeNode credentialsFolderNode, boolean readOnly) {
        super(readOnly);
        this.credentialsFolderNode = credentialsFolderNode;
    }

    /**
     * Returns the virtual folder node to the .credentials folder.
     * @return Credentials folder node.
     */
    public FolderTreeNode getCredentialsFolderNode() {
        return credentialsFolderNode;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for (FileTreeNode fileTreeNode : this.credentialsFolderNode.getFiles()) {
            String baseFileName = fileTreeNode.getFilePath().getFileName();
            String decodedFileName = FileNameSanitizer.decodeFileSystemName(baseFileName);
            this.addWithoutFullLoad(new FileSharedCredentialWrapperImpl(this.credentialsFolderNode, decodedFileName, this.isReadOnly()));
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param credentialName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected SharedCredentialWrapperImpl createNewElement(String credentialName) {
        FileSharedCredentialWrapperImpl sharedCredentialWrapper =
                new FileSharedCredentialWrapperImpl(this.credentialsFolderNode, credentialName, this.isReadOnly());
        sharedCredentialWrapper.setObject(new FileContent());
        return sharedCredentialWrapper;
    }
}
