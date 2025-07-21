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

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Table-level default checks pattern spec wrapper.
 */
public class TableQualityPolicyWrapperImpl extends AbstractElementWrapper<String, TableQualityPolicySpec>
        implements TableQualityPolicyWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<TableQualityPolicyWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String policyName;

    /**
     * Creates a default check configuration pattern wrapper.
     */
    public TableQualityPolicyWrapperImpl() {
    }

    /**
     * Create a default check configuration pattern wrapper in read-only mode.
     * @param readOnly Make the wrapper read-only.
     */
    public TableQualityPolicyWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a default checks pattern wrapper given a pattern name.
     * @param policyName Pattern name.
     * @param readOnly Make the wrapper read-only.
     */
    public TableQualityPolicyWrapperImpl(String policyName, boolean readOnly) {
        this(readOnly);
        this.policyName = policyName;
    }

    /**
     * Gets the default checks pattern name.
     * @return Default checks pattern name.
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * Sets a default checks pattern name.
     * @param policyName Default pecks pattern name.
     */
    public void setPolicyName(String policyName) {
        assert this.policyName == null || Objects.equals(this.policyName, policyName); // cannot change the name
        this.policyName = policyName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getPolicyName();
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
    public TableQualityPolicyWrapper clone() {
        return (TableQualityPolicyWrapper) super.deepClone();
    }
}
