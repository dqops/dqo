/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Collection of shared credentials. Tracks the status of the child elements (addition, removal).
 */
public class SharedCredentialListImpl extends AbstractIndexingList<String, SharedCredentialWrapper> implements SharedCredentialList {
    public SharedCredentialListImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param credentialName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected SharedCredentialWrapper createNewElement(String credentialName) {
        SharedCredentialWrapper sharedCredentialWrapper = new SharedCredentialWrapperImpl();
        sharedCredentialWrapper.setCredentialName(credentialName);
        sharedCredentialWrapper.setObject(new FileContent());
        return sharedCredentialWrapper;
    }

    /**
     * Removes a shared credential file. The file is marked for deletion and will be removed on flush.
     *
     * @param credentialName Shared credential file name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(String credentialName) {
        SharedCredentialWrapper sharedCredentialWrapper = this.getByObjectName(credentialName, true);
        if (sharedCredentialWrapper == null) {
            return false;
        }
        return this.remove(sharedCredentialWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of shared credentials (wrappers).
     */
    @Override
    public List<SharedCredentialWrapper> toList() {
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
