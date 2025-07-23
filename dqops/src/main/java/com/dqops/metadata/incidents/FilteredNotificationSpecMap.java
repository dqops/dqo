/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.incidents;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.Map;

/**
 * The map for the filtered notification specification.
 */
public class FilteredNotificationSpecMap extends AbstractDirtyTrackingSpecMap<FilteredNotificationSpec> {

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
     * Creates and returns a copy of this object.
     */
    @Override
    public FilteredNotificationSpecMap deepClone() {
        FilteredNotificationSpecMap cloned = new FilteredNotificationSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (Map.Entry<String, FilteredNotificationSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), (FilteredNotificationSpec)keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

}
