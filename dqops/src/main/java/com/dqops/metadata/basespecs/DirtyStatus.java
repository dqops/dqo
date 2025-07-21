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
 * Interface implemented by objects that are tracking a dirty status.
 */
public interface DirtyStatus {
    /**
     * Check if the object is dirty (has changes).
     * @return True when the object is dirty and has modifications.
     */
    boolean isDirty();

    /**
     * Sets the dirty flag to true.
     */
    void setDirty();

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    void clearDirty(boolean propagateToChildren);
}
