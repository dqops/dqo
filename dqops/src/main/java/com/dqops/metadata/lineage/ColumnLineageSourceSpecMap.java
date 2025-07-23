/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.lineage;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;

import java.util.Map;

/**
 * Dictionary of mapping of source columns to the columns in the current table.
 * The keys in this dictionary are the column names in the current table.
 */
public class ColumnLineageSourceSpecMap extends AbstractDirtyTrackingSpecMap<ColumnLineageSourceSpec> implements YamlNotRenderWhenDefault {
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
    public ColumnLineageSourceSpecMap deepClone() {
        ColumnLineageSourceSpecMap cloned = new ColumnLineageSourceSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (Map.Entry<String, ColumnLineageSourceSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }
}
