/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.similarity;

import com.dqops.metadata.id.HierarchyNode;

import java.util.List;

/**
 * List of file connection similarity indices.
 */
public interface ConnectionSimilarityIndexList extends Iterable<ConnectionSimilarityIndexWrapper>, HierarchyNode {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param connectionName Object name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    ConnectionSimilarityIndexWrapper getByObjectName(String connectionName, boolean loadAllWhenMissing);

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param connectionName Object key (source name).
     * @return Created object instance.
     */
    ConnectionSimilarityIndexWrapper createAndAddNew(String connectionName);

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     * @param connectionName Index name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    boolean remove(String connectionName);

    /**
     * Returns the collection as an immutable list.
     * @return List of file indexes.
     */
    List<ConnectionSimilarityIndexWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();
}
