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

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File index wrapper.
 */
public class FileIndexWrapperImpl extends AbstractElementWrapper<FileIndexName, FileIndexSpec> implements FileIndexWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<FileIndexWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private FileIndexName indexName;

    /**
     * Creates a new file index wrapper.
     */
    public FileIndexWrapperImpl() {
    }

    public FileIndexWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new file index wrapper given an index name.
     * @param indexName File index name.
     */
    public FileIndexWrapperImpl(FileIndexName indexName, boolean readOnly) {
        this(readOnly);
        this.indexName = indexName;
    }

    /**
     * Gets the file index name.
     * @return File index name.
     */
    public FileIndexName getIndexName() {
        return indexName;
    }

    /**
     * Sets a file index name.
     * @param indexName File index name.
     */
    public void setIndexName(FileIndexName indexName) {
        assert this.indexName == null || Objects.equals(this.indexName, indexName); // cannot change the name
        this.indexName = indexName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public FileIndexName getObjectName() {
        return this.getIndexName();
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
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
