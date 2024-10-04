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
package com.dqops.metadata.similarity;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Connection similarity index of its tables (wrapper implementation).
 */
public class ConnectionSimilarityIndexWrapperImpl extends AbstractElementWrapper<String, ConnectionSimilarityIndexSpec> implements ConnectionSimilarityIndexWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionSimilarityIndexWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String connectionName;

    /**
     * Creates a new connection similarity index wrapper.
     */
    public ConnectionSimilarityIndexWrapperImpl() {
    }

    public ConnectionSimilarityIndexWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new file index wrapper given an index name.
     * @param connectionName File index name.
     */
    public ConnectionSimilarityIndexWrapperImpl(String connectionName, boolean readOnly) {
        this(readOnly);
        this.connectionName = connectionName;
    }

    /**
     * Gets the file index name.
     * @return File index name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets a file index name.
     * @param connectionName File index name.
     */
    public void setConnectionName(String connectionName) {
        assert this.connectionName == null || Objects.equals(this.connectionName, connectionName); // cannot change the name
        this.connectionName = connectionName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getConnectionName();
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
