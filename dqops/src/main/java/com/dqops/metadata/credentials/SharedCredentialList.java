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

import java.util.List;

/**
 * List of shared credentials.
 */
public interface SharedCredentialList extends Iterable<SharedCredentialWrapper> {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param credentialName Shared credential name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    SharedCredentialWrapper getByObjectName(String credentialName, boolean loadAllWhenMissing);

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param credentialName Shared credential name.
     * @return Created object instance.
     */
    SharedCredentialWrapper createAndAddNew(String credentialName);

    /**
     * Removes a shared credential wrapper. The source is marked for deletion and will be removed on flush.
     * @param credentialName Shared credential name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    boolean remove(String credentialName);

    /**
     * Returns the collection as an immutable list.
     * @return List of shared credential wrappers.
     */
    List<SharedCredentialWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();
}
