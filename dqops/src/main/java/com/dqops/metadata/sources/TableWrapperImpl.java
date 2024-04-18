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
package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Table spec wrapper.
 */
public class TableWrapperImpl extends AbstractElementWrapper<PhysicalTableName, TableSpec> implements TableWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<TableWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private PhysicalTableName physicalTableName;

    /**
     * Creates a new table wrapper.
     */
    public TableWrapperImpl() {
    }

    /**
     * Creates a new table wrapper, configuring the read-only mode.
     * @param readOnly The instance is in read only mode.
     */
    public TableWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new table wrapper given a table file name.
     * @param physicalTableName Physical table name that is converted to a file name used to store the table specification.
     * @param readOnly Make the wrapper read-only.
     */
    public TableWrapperImpl(PhysicalTableName physicalTableName, boolean readOnly) {
        this(readOnly);
        this.physicalTableName = physicalTableName;
    }

    /**
     * Gets the physical table name that was decoded from the spec file name.
     *
     * @return Physical table name decoded from the file name.
     */
    @Override
    public PhysicalTableName getPhysicalTableName() {
        return this.physicalTableName;
    }

    /**
     * Sets a physical table name that is used to generate a file name.
     *
     * @param physicalTableName Physical table name used to generate a yaml file.
     */
    @Override
    public void setPhysicalTableName(PhysicalTableName physicalTableName) {
        assert this.physicalTableName == null || Objects.equals(this.physicalTableName, physicalTableName); // cannot change the name
        this.physicalTableName = physicalTableName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public PhysicalTableName getObjectName() {
        return this.getPhysicalTableName();
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
