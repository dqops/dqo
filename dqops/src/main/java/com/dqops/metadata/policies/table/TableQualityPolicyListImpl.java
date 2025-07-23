/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.policies.table;

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Collection of table-level default checks patterns definitions. Tracks the status of the child elements (addition, removal).
 */
public class TableQualityPolicyListImpl extends AbstractIndexingList<String, TableQualityPolicyWrapper> implements TableQualityPolicyList {
    public TableQualityPolicyListImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected TableQualityPolicyWrapper createNewElement(String objectName) {
        TableQualityPolicyWrapper defaultChecksPatternWrapper = new TableQualityPolicyWrapperImpl();
        defaultChecksPatternWrapper.setPolicyName(objectName);
        defaultChecksPatternWrapper.setSpec(new TableQualityPolicySpec());
        return defaultChecksPatternWrapper;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param patternName Pattern name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(String patternName) {
        TableQualityPolicyWrapper defaultChecksPatternWrapper = this.getByObjectName(patternName, true);
        if (defaultChecksPatternWrapper == null) {
            return false;
        }
        return this.remove(defaultChecksPatternWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of default checks patterns definitions (wrappers).
     */
    @Override
    public List<TableQualityPolicyWrapper> toList() {
        return List.copyOf(this);
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
