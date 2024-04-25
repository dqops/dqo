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

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Table collection. Tracks the status of the child elements (addition, removal).
 */
public class TableListImpl extends AbstractIndexingList<PhysicalTableName, TableWrapper> implements TableList {
    public TableListImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param physicalTableName Physical table name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected TableWrapperImpl createNewElement(PhysicalTableName physicalTableName) {
        TableWrapperImpl tableWrapper = new TableWrapperImpl();
        tableWrapper.setPhysicalTableName(physicalTableName);
        tableWrapper.setSpec(new TableSpec());
        return tableWrapper;
    }

    /**
     * Creates a new table that is marked as new and will be saved on flush.
     *
     * @param physicalTableName Physical table name.
     * @return Table wrapper.
     */
    @Override
    public TableWrapper createAndAddNew(PhysicalTableName physicalTableName) {
        TableWrapper newTable = super.createAndAddNew(physicalTableName);
        return newTable;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param physicalTableName Physical table name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(PhysicalTableName physicalTableName) {
        TableWrapper tableWrapper = this.getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            return false;
        }
        return this.remove(tableWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of tables.
     */
    @Override
    public List<TableWrapper> toList() {
        return List.copyOf(this);
    }

    /**
     * Adds missing tables or missing columns from the source. Overwrites also the last known column data type.
     *
     * @param sourceTableSpecs Source tables.
     */
    @Override
    public void importTables(List<TableSpec> sourceTableSpecs, DataGroupingConfigurationSpec defaultDataGroupingConfiguration) {
        for (TableSpec sourceTableSpec : sourceTableSpecs) {
            PhysicalTableName sourceTablePhysicalName = sourceTableSpec.getPhysicalTableName();

            TableWrapper existingTableWrapper = this.getByObjectName(sourceTablePhysicalName, true);
            if (existingTableWrapper == null) {
                TableWrapper newTableWrapper = this.createAndAddNew(sourceTablePhysicalName);
                newTableWrapper.setSpec(sourceTableSpec);
                if (defaultDataGroupingConfiguration != null) {
                    final String dataGroupingConfigurationName = DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME;
                    sourceTableSpec.getGroupings().put(dataGroupingConfigurationName, defaultDataGroupingConfiguration);
                    sourceTableSpec.setDefaultGroupingName(dataGroupingConfigurationName);
                }
            }
            else {
                // merge columns and update
                TableSpec existingTableSpec = existingTableWrapper.getSpec();
                existingTableSpec.mergeColumnsFrom(sourceTableSpec);
            }
        }
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
