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

import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.sources.ConnectionListImpl;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data sources collection that uses a local file system (the user's home folder) to read yaml files.
 */
public class FileConnectionListImpl extends ConnectionListImpl {
    @JsonIgnore
    private final FolderTreeNode sourcesFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a data source collection using a given sources folder.
     * @param sourcesFolder Sources folder node.
     * @param yamlSerializer  Yaml serializer.
     * @param readOnly Make the list read-only.
     */
    public FileConnectionListImpl(FolderTreeNode sourcesFolder, YamlSerializer yamlSerializer, boolean readOnly) {
        super(readOnly);
        this.sourcesFolder = sourcesFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Sources folder.
     * @return Sources folder.
     */
    public FolderTreeNode getSourcesFolder() {
        return sourcesFolder;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        for (FolderTreeNode sourceFolderNode : this.sourcesFolder.findNestedSubFoldersWithFiles(SpecFileNames.CONNECTION_SPEC_FILE_NAME_YAML, false)) {
            String connectionName = sourceFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "sources" folder
            if (this.getByObjectName(connectionName, false) != null) {
                continue; // was already added
            }
			this.addWithoutFullLoad(new FileConnectionWrapperImpl(sourceFolderNode, this.yamlSerializer, this.isReadOnly()));
        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected ConnectionWrapperImpl createNewElement(String objectName) {
        FolderTreeNode newSourceFolderNode = this.sourcesFolder.getOrAddFolderPath(objectName);
        FileConnectionWrapperImpl dataSourceModelWrapper = new FileConnectionWrapperImpl(newSourceFolderNode, this.yamlSerializer, this.isReadOnly());
        dataSourceModelWrapper.setName(objectName);
        dataSourceModelWrapper.setSpec(new ConnectionSpec());
        return dataSourceModelWrapper;
    }
}
