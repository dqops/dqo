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
import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Collection of shared credentials. Tracks the status of the child elements (addition, removal).
 */
public class SharedCredentialListImpl extends AbstractIndexingList<String, SharedCredentialWrapper> implements SharedCredentialList {
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
