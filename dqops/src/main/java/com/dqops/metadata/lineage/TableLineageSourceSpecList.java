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

package com.dqops.metadata.lineage;

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;

import java.util.List;

/**
 * List of source tables of the current table to build the data lineage report.
 */
public class TableLineageSourceSpecList extends AbstractIndexingList<TableLineageSource, TableLineageSourceSpec> implements YamlNotRenderWhenDefault {
    /**
     * Creates a new, mutable list.
     */
    public TableLineageSourceSpecList() {
    }

    /**
     * Creates a new list, configuring the read-only status.
     *
     * @param readOnly Make the list read-only.
     */
    public TableLineageSourceSpecList(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected TableLineageSourceSpec createNewElement(TableLineageSource objectName) {
        TableLineageSourceSpec tableLineageSourceSpec = new TableLineageSourceSpec();
        tableLineageSourceSpec.setObjectName(objectName);

        return tableLineageSourceSpec;
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of tables.
     */
    public List<TableLineageSourceSpec> toList() {
        return List.copyOf(this);
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return this.isEmpty();
    }
}
