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

/**
 * Implement by objects that are tracking their model status (added, deleted). Provides methods to retrieve the status
 * or change the status.
 */
public interface InstanceStatusTracking {
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
}
