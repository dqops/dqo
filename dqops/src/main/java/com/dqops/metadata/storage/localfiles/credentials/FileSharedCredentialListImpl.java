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
package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
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
     */
    public FileSharedCredentialListImpl(FolderTreeNode credentialsFolderNode) {
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
            this.addWithoutFullLoad(new FileSharedCredentialWrapperImpl(this.credentialsFolderNode, baseFileName));
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
                new FileSharedCredentialWrapperImpl(this.credentialsFolderNode, credentialName);
        sharedCredentialWrapper.setObject(new FileContent());
        return sharedCredentialWrapper;
    }
}
