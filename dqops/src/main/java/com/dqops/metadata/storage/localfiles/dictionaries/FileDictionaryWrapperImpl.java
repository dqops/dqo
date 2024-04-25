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
package com.dqops.metadata.storage.localfiles.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.dictionaries.DictionaryWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;

/**
 * File based data dictionary file wrapper. Loads and writes data dictionary files in the user's home dictionaries/ folder.
 */
public class FileDictionaryWrapperImpl extends DictionaryWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode dictionariesFolderNode;

    /**
     * Creates a data dictionary file wrapper that is file based.
     * @param dictionariesFolderNode Data dictionaries (dictionaries/) folder to store dictionary CSV files.
     * @param dictionaryName Data dictionary CSV file name.
     * @param readOnly Make the list read-only.
     */
    public FileDictionaryWrapperImpl(FolderTreeNode dictionariesFolderNode, String dictionaryName, boolean readOnly) {
        super(dictionaryName, readOnly);
        this.dictionariesFolderNode = dictionariesFolderNode;
    }

    /**
     * Returns the folder that contains the dictionaries files.
     * @return Dictionaries folder (dictionaries/).
     */
    @JsonIgnore
    public FolderTreeNode getDictionariesFolderNode() {
        return dictionariesFolderNode;
    }

    /**
     * Loads the dictionary content file.
     * @return Loaded file content with the dictionary CSV file.
     */
    @Override
    public FileContent getObject() {
        FileContent object = super.getObject();
        if (object == null) {
            String fileName = FileNameSanitizer.encodeForFileSystem(this.getObjectName());
            FileTreeNode fileNode = this.dictionariesFolderNode.getChildFileByFileName(fileName);
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
				this.dictionariesFolderNode.addChildFile(fileName, newFileContent);
                this.getObject().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.dictionariesFolderNode.getChildFileByFileName(fileName);
                modifiedFileNode.changeContent(newFileContent);
				this.getObject().clearDirty(true);
                break;

            case TO_BE_DELETED:
				this.dictionariesFolderNode.deleteChildFile(fileName);
                break;
        }

        super.flush(); // change the statuses
    }

    /**
     * Extracts an absolute file path to the data dictionary CSV file. This method returns null if the dictionaries are not stored on the disk, but using an in-memory user home instance.
     *
     * @return Absolut path to the file or null when it is not possible to find the file.
     */
    @Override
    public Path toAbsoluteFilePath() {
        String fileName = FileNameSanitizer.encodeForFileSystem(this.getObjectName());
        FileTreeNode childFileByFileName = this.dictionariesFolderNode.getChildFileByFileName(fileName);
        if (childFileByFileName == null) {
            return null;
        }

        if (childFileByFileName.isLocalFileSystem()) {
            return childFileByFileName.getPhysicalAbsolutePath();
        }

        return null;
    }
}
