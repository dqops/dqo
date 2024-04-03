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
package com.dqops.metadata.defaultchecks.column;

import java.util.List;

/**
 * List of column-level default checks patterns.
 */
public interface ColumnDefaultChecksPatternList extends Iterable<ColumnDefaultChecksPatternWrapper> {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param patternName Pattern name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    ColumnDefaultChecksPatternWrapper getByObjectName(String patternName, boolean loadAllWhenMissing);

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param patternName Pattern name.
     * @return Created object instance.
     */
    ColumnDefaultChecksPatternWrapper createAndAddNew(String patternName);

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     * @param patternName Pattern name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    boolean remove(String patternName);

    /**
     * Returns the collection as an immutable list.
     * @return List of check pattern definition wrappers.
     */
    List<ColumnDefaultChecksPatternWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();
}
