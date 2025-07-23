/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
