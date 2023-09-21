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
package com.dqops.metadata.basespecs;

import com.dqops.metadata.id.HierarchyNode;

/**
 * Base pure java object wrapper interface. Used to store java objects.
 */
public interface PojoElementWrapper<V> extends Flushable, InstanceStatusTracking, HierarchyNode {
    /**
     * Returns an object instance that is stored in the model wrapper. Derived classes should override this method
     * and load the object from a persistent storage.
     * @return Object specification.
     */
    V getObject();

    /**
     * Sets a object. The first call (when the model was null) simply stores the object.
     * The next call will change the status to {@link InstanceStatus#MODIFIED}.
     * @param object New object.
     */
    void setObject(V object);

    /**
     * Returns the status of the node.
     * @return Object status (added, modified, deleted, etc).
     */
    InstanceStatus getStatus();

    /**
     * Changes the status of the model.
     * @param status New status.
     */
    void setStatus(InstanceStatus status);

    /**
     * Marks the object for deletion. The status changes to {@link InstanceStatus#TO_BE_DELETED}.
     */
    void markForDeletion();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();
}
