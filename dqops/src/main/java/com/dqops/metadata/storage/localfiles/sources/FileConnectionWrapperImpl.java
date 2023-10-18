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
package com.dqops.metadata.storage.localfiles.sources;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File based data source wrapper. Loads and writes the data source to a yaml file in the user's home folder.
 */
public class FileConnectionWrapperImpl extends ConnectionWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode connectionFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a connection spec wrapper that is file based.
     * @param connectionFolderNode Connection folder with yaml files.
     * @param yamlSerializer Yaml serializer.
     */
    public FileConnectionWrapperImpl(FolderTreeNode connectionFolderNode, YamlSerializer yamlSerializer) {
        super(connectionFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName());
        this.connectionFolderNode = connectionFolderNode;
        this.yamlSerializer = yamlSerializer;
		this.setTables(new FileTableListImpl(connectionFolderNode, yamlSerializer));
    }

    /**
     * Returns the folder that contains the connection files.
     * @return Connection folder.
     */
    @JsonIgnore
    public FolderTreeNode getConnectionFolderNode() {
        return connectionFolderNode;
    }

    /**
     * Loads the connection model with the connection details.
     * @return Loaded connection model.
     */
    @Override
    public ConnectionSpec getSpec() {
        ConnectionSpec spec = super.getSpec();
        if (spec == null) {
            FileTreeNode fileNode = this.connectionFolderNode.getChildFileByFileName(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                ConnectionSpec deserializedSpec = (ConnectionSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    ConnectionYaml deserialized = this.yamlSerializer.deserialize(textContent, ConnectionYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserializedSpec == null) {
                        deserializedSpec = new ConnectionSpec();
                    }

                    if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                        throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                    }
                    if (deserialized.getKind() != SpecificationKind.SOURCE) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    fileContent.setCachedObjectInstance(deserializedSpec.deepClone());
                } else {
                    deserializedSpec = deserializedSpec.deepClone();
                }
				this.setSpec(deserializedSpec);
                deserializedSpec.clearDirty(true);
				this.clearDirty(false);
                return deserializedSpec;
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

		this.getTables().flush(); // the first call to flush, maybe all tables are deleted and the connection is deleted right after

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is empty (no file)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
        }

        ConnectionYaml connectionYaml = new ConnectionYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(connectionYaml);
        FileContent newFileContent = new FileContent(specAsYaml);

        switch (this.getStatus()) {
            case ADDED:
				this.connectionFolderNode.addChildFile(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML, newFileContent);
				this.getSpec().clearDirty(true);
            case MODIFIED:
                FileTreeNode modifiedFileNode = this.connectionFolderNode.getChildFileByFileName(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML);
                modifiedFileNode.changeContent(newFileContent);
				this.getSpec().clearDirty(true);
                break;
            case TO_BE_DELETED:
				this.connectionFolderNode.deleteChildFile(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML);
				this.connectionFolderNode.setDeleteOnFlush(true); // will remove the whole folder for the source
                break;
        }

        super.flush(); // change the statuses
    }
}
