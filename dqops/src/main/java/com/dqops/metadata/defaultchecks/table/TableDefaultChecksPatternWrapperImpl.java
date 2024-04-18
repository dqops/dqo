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
package com.dqops.metadata.defaultchecks.table;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Table-level default checks pattern spec wrapper.
 */
public class TableDefaultChecksPatternWrapperImpl extends AbstractElementWrapper<String, TableDefaultChecksPatternSpec>
        implements TableDefaultChecksPatternWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<TableDefaultChecksPatternWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String patternName;

    /**
     * Creates a default check configuration pattern wrapper.
     */
    public TableDefaultChecksPatternWrapperImpl() {
    }

    /**
     * Create a default check configuration pattern wrapper in read-only mode.
     * @param readOnly Make the wrapper read-only.
     */
    public TableDefaultChecksPatternWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a default checks pattern wrapper given a pattern name.
     * @param patternName Pattern name.
     * @param readOnly Make the wrapper read-only.
     */
    public TableDefaultChecksPatternWrapperImpl(String patternName, boolean readOnly) {
        this(readOnly);
        this.patternName = patternName;
    }

    /**
     * Gets the default checks pattern name.
     * @return Default checks pattern name.
     */
    public String getPatternName() {
        return patternName;
    }

    /**
     * Sets a default checks pattern name.
     * @param patternName Default pecks pattern name.
     */
    public void setPatternName(String patternName) {
        assert this.patternName == null || Objects.equals(this.patternName, patternName); // cannot change the name
        this.patternName = patternName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getPatternName();
    }

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

    /**
     * Creates a deep clone of the object.
     *
     * @return Deeply cloned object.
     */
    @Override
    public TableDefaultChecksPatternWrapper clone() {
        return (TableDefaultChecksPatternWrapper) super.deepClone();
    }
}
