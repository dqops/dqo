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
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.credentials.SharedCredentialWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;

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
     * @param readOnly Make the wrapper read-only.
     */
    public FileSharedCredentialWrapperImpl(FolderTreeNode credentialsFolderNode, String credentialName, boolean readOnly) {
        super(credentialName, readOnly);
        this.credentialsFolderNode = credentialsFolderNode;
    }

    /**
     * Returns the folder that contains the credential files.
     * @return Credentials folder (.credentials).
     */
    @JsonIgnore
    public FolderTreeNode getCredentialsFolderNode() {
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
            String fileName = FileNameSanitizer.encodeForFileSystem(this.getObjectName());
            FileTreeNode fileNode = this.credentialsFolderNode.getChildFileByFileName(fileName);
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
        if (this.getStatus() == InstanceStatus.DELETED || this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            return; // do nothing
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getObject() == null) {
            return; // nothing to do, the instance is empty
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getObject() != null && super.getObject().isDirty()) {
            super.getObject().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        FileContent newFileContent = this.getObject().clone();
        String fileName = FileNameSanitizer.encodeForFileSystem(this.getObjectName());

        switch (this.getStatus()) {
            case ADDED:
				this.credentialsFolderNode.addChildFile(fileName, newFileContent);
                this.getObject().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.credentialsFolderNode.getChildFileByFileName(fileName);
                modifiedFileNode.changeContent(newFileContent);
				this.getObject().clearDirty(true);
                break;

            case TO_BE_DELETED:
				this.credentialsFolderNode.deleteChildFile(fileName);
                break;
        }

        super.flush(); // change the statuses
    }

    /**
     * Extracts an absolute file path to the credential file. This method returns null if the credentials are not stored on the disk, but using an in-memory user home instance.
     *
     * @return Absolut path to the file or null when it is not possible to find the file.
     */
    @Override
    public Path toAbsoluteFilePath() {
        String fileName = FileNameSanitizer.encodeForFileSystem(this.getObjectName());
        FileTreeNode childFileByFileName = this.credentialsFolderNode.getChildFileByFileName(fileName);
        if (childFileByFileName == null) {
            return null;
        }

        if (childFileByFileName.isLocalFileSystem()) {
            return childFileByFileName.getPhysicalAbsolutePath();
        }

        return null;
    }
}
