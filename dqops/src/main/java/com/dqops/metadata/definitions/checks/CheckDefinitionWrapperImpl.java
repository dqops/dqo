/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.checks;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Custom check definition spec wrapper.
 */
public class CheckDefinitionWrapperImpl extends AbstractElementWrapper<String, CheckDefinitionSpec> implements CheckDefinitionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<CheckDefinitionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String checkName;

    /**
     * Creates a default check definition wrapper.
     */
    public CheckDefinitionWrapperImpl() {
    }

    public CheckDefinitionWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a check definition wrapper given a check name.
     * @param checkName Check name.
     * @param readOnly Make the wrapper read-only.
     */
    public CheckDefinitionWrapperImpl(String checkName, boolean readOnly) {
        this(readOnly);
        this.checkName = checkName;
    }

    /**
     * Gets the custom check name.
     * @return Custom check name.
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets a custom check name.
     * @param checkName Custom check name.
     */
    public void setCheckName(String checkName) {
        assert this.checkName == null || Objects.equals(this.checkName, checkName); // cannot change the name
        this.checkName = checkName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getCheckName();
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
    public CheckDefinitionWrapper clone() {
        return (CheckDefinitionWrapper) super.deepClone();
    }
}
