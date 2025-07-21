/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fileindices;

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Data source collection. Tracks the status of the child elements (addition, removal).
 */
public class FileIndexListImpl extends AbstractIndexingList<FileIndexName, FileIndexWrapper> implements FileIndexList {
    public FileIndexListImpl() {
    }

    public FileIndexListImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected FileIndexWrapper createNewElement(FileIndexName objectName) {
        FileIndexWrapperImpl fileIndexWrapper = new FileIndexWrapperImpl();
        fileIndexWrapper.setIndexName(objectName);
        fileIndexWrapper.setSpec(new FileIndexSpec());
        return fileIndexWrapper;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param sourceName Source model to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(FileIndexName sourceName) {
        FileIndexWrapper fileIndexWrapper = this.getByObjectName(sourceName, true);
        if (fileIndexWrapper == null) {
            return false;
        }
        return this.remove(fileIndexWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of connections.
     */
    @Override
    public List<FileIndexWrapper> toList() {
        return List.copyOf(this);
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
