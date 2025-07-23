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
 * Interface implemented by objects that are tracking if the object was frozen for modifications and is read-only.
 */
public interface ReadOnlyStatus {
    /**
     * Check if the object is frozen (read only). A read-only object cannot be modified.
     * @return True when the object is read-only and trying to apply a change will return an error.
     */
    boolean isReadOnly();

    /**
     * Sets the read-only flag on the current object, and optionally on child objects.
     * @param propagateToChildren When true, makes also the child objects as read-only.
     */
    void makeReadOnly(boolean propagateToChildren);
}
