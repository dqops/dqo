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
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.credentials.SharedCredentialWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based shared credential file wrapper. Loads and writes shared credential files in the user's home .credentials/ folder.
 */
public class FileSharedCredentialWrapperImpl extends SharedCredentialWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode credentialsFolderNode;

    /**
     * Creates a credential file wrapper that is file based.
     * @param credentialsFolderNode Credentials (.credentials) folder to store credentials files.
     * @param credentialName Credential file name.
     */
    public FileSharedCredentialWrapperImpl(FolderTreeNode credentialsFolderNode, String credentialName) {
        super(credentialName);
        this.credentialsFolderNode = credentialsFolderNode;
    }

    /**
     * Returns the folder that contains the credential files.
     * @return Credentials folder (.credentials).
     */
    @JsonIgnore
    public FolderTreeNode getRuleFolderNode() {
        return credentialsFolderNode;
    }

    /**
     * Loads the credentials content file.
     * @return Loaded file content with the credentials.
     */
    @Override
    public FileContent getObject() {
        FileContent object = super.getObject();
        if (object == null) {
            FileTreeNode fileNode = this.credentialsFolderNode.getChildFileByFileName(this.getObjectName());
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                object = fileContent.clone();
                this.setObject(object);
            }
            else {
                object = new FileContent();
                this.setObject(object);
                return object;
            }
        }
        return object;
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
        if (this.getStatus() == InstanceStatus.DELETED) {
            return; // do nothing
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getObject() == null) {
            return; // nothing to do, the instance was never touched
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getObject() != null && super.getObject().isDirty()) {
            super.getObject().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        FileContent newFileContent = this.getObject().clone();

        switch (this.getStatus()) {
            case ADDED:
				this.credentialsFolderNode.addChildFile(this.getObjectName(), newFileContent);
            case MODIFIED:
                FileTreeNode modifiedFileNode = this.credentialsFolderNode.getChildFileByFileName(this.getObjectName());
                modifiedFileNode.changeContent(newFileContent);
				this.getObject().clearDirty(true);
                break;
            case TO_BE_DELETED:
				this.credentialsFolderNode.deleteChildFile(this.getObjectName());
                break;
        }

        super.flush(); // change the statuses
    }
}
