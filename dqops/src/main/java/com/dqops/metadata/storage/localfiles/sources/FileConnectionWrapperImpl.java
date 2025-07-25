/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.sources;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.id.HierarchyId;
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
     * @param readOnly Make the wrapper read-only.
     */
    public FileConnectionWrapperImpl(FolderTreeNode connectionFolderNode, YamlSerializer yamlSerializer, boolean readOnly) {
        super(connectionFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName(), readOnly);
        this.connectionFolderNode = connectionFolderNode;
        this.yamlSerializer = yamlSerializer;
		this.tables = new FileTableListImpl(connectionFolderNode, yamlSerializer, readOnly);
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
                this.setLastModified(fileContent.getLastModified());
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
                    if (deserialized.getKind() != SpecificationKind.source) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    ConnectionSpec cachedObjectInstance = deserializedSpec.deepClone();
                    cachedObjectInstance.makeReadOnly(true);
                    if (this.getHierarchyId() != null) {
                        cachedObjectInstance.setHierarchyId(new HierarchyId(this.getHierarchyId(), "spec"));
                    }
                    fileContent.setCachedObjectInstance(cachedObjectInstance);
                } else {
                    deserializedSpec = this.isReadOnly() ? deserializedSpec : deserializedSpec.deepClone();
                }
				this.setSpec(deserializedSpec);
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
        this.getTables().flush(); // the first call to flush, maybe all tables are deleted and the connection is deleted right after

        this.clearDirty(false);

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

        ConnectionYaml connectionYaml = new ConnectionYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(connectionYaml);
        FileContent newFileContent = new FileContent(specAsYaml);

        switch (this.getStatus()) {
            case ADDED:
				this.connectionFolderNode.addChildFile(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML, newFileContent);
				this.getSpec().clearDirty(true);
                break;

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
