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
 * Interface implemented by the model items that are indexed by name. This interface returns the object name retrieved from the model.
 */
public interface ObjectName<T> {
    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     * @return Object name;
     */
    T getObjectName();
}
