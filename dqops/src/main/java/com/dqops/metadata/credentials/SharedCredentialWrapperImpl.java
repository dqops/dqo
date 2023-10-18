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
package com.dqops.metadata.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.basespecs.AbstractPojoElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Shared credential file wrapper.
 */
public class SharedCredentialWrapperImpl extends AbstractPojoElementWrapper<String, FileContent> implements SharedCredentialWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<SharedCredentialWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractPojoElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private String credentialName;

    /**
     * Creates a default shared credential wrapper.
     */
    public SharedCredentialWrapperImpl() {
    }

    /**
     * Creates a shared credential wrapper given a credential name.
     * @param credentialName Credential name.
     */
    public SharedCredentialWrapperImpl(String credentialName) {
        this();
        this.credentialName = credentialName;
    }

    /**
     * Gets the shared credential name.
     * @return Shared credential name.
     */
    public String getCredentialName() {
        return credentialName;
    }

    /**
     * Sets a shared credential name.
     * @param credentialName Shared credential name.
     */
    public void setCredentialName(String credentialName) {
        assert this.credentialName == null || Objects.equals(this.credentialName, credentialName); // cannot change the name
        this.credentialName = credentialName;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getCredentialName();
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
    public SharedCredentialWrapper clone() {
        return (SharedCredentialWrapper) super.deepClone();
    }
}
