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

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.HierarchyNode;

import java.util.List;

/**
 * List of tables in a database.
 */
public interface TableList extends Iterable<TableWrapper>, HierarchyNode {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param physicalTableName Physical table name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    TableWrapper getByObjectName(PhysicalTableName physicalTableName, boolean loadAllWhenMissing);

    /**
     * Creates a new table that is marked as new and will be saved on flush.
     * @param physicalTableName Physical table name.
     * @return Table wrapper.
     */
    TableWrapper createAndAddNew(PhysicalTableName physicalTableName);

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     * @param physicalTableName Physical table name to remove.
     * @return True when the object will be removed, false otherwise.
     */
    boolean remove(PhysicalTableName physicalTableName);

    /**
     * Returns the collection as an immutable list.
     * @return List of connections.
     */
    List<TableWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();

    /**
     * Adds missing tables or missing columns from the source. Overwrites also the last known column data type.
     * @param sourceTableSpecs Source tables.
     * @param defaultDataGroupingConfiguration Default data grouping configuration (from the connection) to be added to all imported tables.
     */
    void importTables(List<TableSpec> sourceTableSpecs, DataGroupingConfigurationSpec defaultDataGroupingConfiguration);
}
