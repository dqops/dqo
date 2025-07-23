/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.fileindices;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.fileindices.FileIndexName;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.fileindices.FileIndexWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File based file index spec wrapper. Loads and writes the file index to a json file in the user's home folder.
 */
public class FileFileIndexWrapperImpl extends FileIndexWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode indicesFolderNode;
    @JsonIgnore
    private final JsonSerializer jsonSerializer;

    /**
     * Creates a table wrapper for a file index specification that uses json files for storage.
     * @param indicesFolderNode Folder with json files for file index specifications.
     * @param indexName Real base file name, it is the actual file name before the table spec file extension.
     * @param jsonSerializer Json serializer.
     * @param readOnly Make the list read-only.
     */
    public FileFileIndexWrapperImpl(FolderTreeNode indicesFolderNode, FileIndexName indexName, JsonSerializer jsonSerializer, boolean readOnly) {
        super(indexName, readOnly);
        this.indicesFolderNode = indicesFolderNode;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Returns the file index folder name.
     * @return File index folder name.
     */
    public FolderTreeNode getIndicesFolderNode() {
        return indicesFolderNode;
    }

    /**
     * Loads the file index spec with the list of indexed files.
     * @return Loaded file index specification.
     */
    @Override
    public FileIndexSpec getSpec() {
        FileIndexSpec spec = super.getSpec();
        if (spec == null) {
            String fileNameWithExt = this.getIndexName().toBaseFileName() + SpecFileNames.FILE_INDEX_SPEC_FILE_EXT_JSON;
            FileTreeNode fileNode = this.indicesFolderNode.getChildFileByFileName(fileNameWithExt);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                this.setLastModified(fileContent.getLastModified());
                String textContent = fileContent.getTextContent();
                FileIndexJson deserialized = this.jsonSerializer.deserialize(textContent, FileIndexJson.class);
                FileIndexSpec deserializedSpec = deserialized.getSpec();
                if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                    throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                }
                if (deserialized.getKind() != SpecificationKind.file_index) {
                    throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                }

                this.setSpec(deserializedSpec);
                this.clearDirty(true);
                return deserializedSpec;
            } else {
                this.setSpec(null);
            }
        }
        return spec;
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

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is empty (no file)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        FileIndexJson fileIndexJson = new FileIndexJson(this.getSpec());
        String specAsJson = this.jsonSerializer.serialize(fileIndexJson);
        FileContent newFileContent = new FileContent(specAsJson);
        String fileNameWithExt = this.getIndexName().toBaseFileName() + SpecFileNames.FILE_INDEX_SPEC_FILE_EXT_JSON;

        switch (this.getStatus()) {
            case ADDED:
				this.indicesFolderNode.addChildFile(fileNameWithExt, newFileContent);
				this.getSpec().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.indicesFolderNode.getChildFileByFileName(fileNameWithExt);
                modifiedFileNode.changeContent(newFileContent);
				this.getSpec().clearDirty(true);
                break;

            case TO_BE_DELETED:
				this.indicesFolderNode.deleteChildFile(fileNameWithExt);
                break;
        }

        super.flush(); // change the statuses
    }
}
