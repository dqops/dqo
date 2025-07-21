/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Connection spec wrapper.
 */
public class ConnectionWrapperImpl extends AbstractElementWrapper<String, ConnectionSpec> implements ConnectionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
			put("tables", o -> o.tables);
        }
    };

    @JsonIgnore
    private String name;
    @JsonIgnore
    protected TableListImpl tables;

    /**
     * Creates a new connection wrapper.
     */
    public ConnectionWrapperImpl() {
        this.tables = new TableListImpl(false);
    }

    public ConnectionWrapperImpl(boolean readOnly) {
        super(readOnly);
        this.tables = new TableListImpl(readOnly);
    }

    /**
     * Creates a new connection wrapper given a connection name.
     * @param name Connection name.
     * @param readOnly Make the connection read-only.
     */
    public ConnectionWrapperImpl(String name, boolean readOnly) {
        this(readOnly);
        this.name = name;
    }

    /**
     * Gets the connection name.
     * @return Connection name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a connection name.
     * @param name Connection name.
     */
    public void setName(String name) {
        assert this.name == null || Objects.equals(this.name, name); // cannot change the name
        this.name = name;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getName();
    }

    /**
     * Returns a list of tables.
     * @return List of tables.
     */
    @Override
    public TableList getTables() {
        return tables;
    }

    /**
     * Sets an instance of the table collection.
     * @param tables New tables collection.
     */
    public void setTables(TableListImpl tables) {
		this.setDirtyIf(this.tables != null && this.tables != tables); // special condition
        this.tables = tables;
		this.propagateHierarchyIdToField(tables, "tables");
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
		this.getTables().flush();
        super.flush();
    }

    @Override
    public void markForDeletion() {
        this.getTables().forEach(TableWrapper::markForDeletion);
        super.markForDeletion();
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
