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
     */
    public FileDictionaryListImpl(FolderTreeNode dictionariesFolderNode) {
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
            this.addWithoutFullLoad(new FileDictionaryWrapperImpl(this.dictionariesFolderNode, decodedFileName));
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
                new FileDictionaryWrapperImpl(this.dictionariesFolderNode, dictionaryName);
        sharedCredentialWrapper.setObject(new FileContent());
        return sharedCredentialWrapper;
    }
}
