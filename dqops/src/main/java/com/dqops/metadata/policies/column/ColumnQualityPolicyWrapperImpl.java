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
package com.dqops.metadata.policies.column;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Column-level default checks pattern (quality policy) spec wrapper.
 */
public class ColumnQualityPolicyWrapperImpl extends AbstractElementWrapper<String, ColumnQualityPolicySpec> implements ColumnQualityPolicyWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<ColumnQualityPolicyWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String policyName;

    /**
     * Creates a default check configuration pattern wrapper.
     */
    public ColumnQualityPolicyWrapperImpl() {
    }

    public ColumnQualityPolicyWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a default checks pattern wrapper given a policy name.
     * @param policyName Policy name.
     */
    public ColumnQualityPolicyWrapperImpl(String policyName, boolean readOnly) {
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
    public ColumnQualityPolicyWrapper clone() {
        return (ColumnQualityPolicyWrapper) super.deepClone();
    }
}
