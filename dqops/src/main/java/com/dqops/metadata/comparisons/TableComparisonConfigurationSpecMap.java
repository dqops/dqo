/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.comparisons;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.Map;

/**
 * Dictionary of data comparison configurations between the current table (the parent of this node) and another reference table (the source of truth)
 * to which we are comparing the tables to measure the accuracy of the data.
 */
public class TableComparisonConfigurationSpecMap extends AbstractDirtyTrackingSpecMap<TableComparisonConfigurationSpec> {
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
     * Creates and returns a copy of this object.
     */
    @Override
    public TableComparisonConfigurationSpecMap deepClone() {
        TableComparisonConfigurationSpecMap cloned = new TableComparisonConfigurationSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (Map.Entry<String, TableComparisonConfigurationSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }
}
