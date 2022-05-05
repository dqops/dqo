/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.definitions.rules;

import java.util.List;

/**
 * List of custom rule definitions.
 */
public interface RuleDefinitionList extends Iterable<RuleDefinitionWrapper> {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param customRuleName Custom rule name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    RuleDefinitionWrapper getByObjectName(String customRuleName, boolean loadAllWhenMissing);

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param customRuleName Custom rule name.
     * @return Created object instance.
     */
    RuleDefinitionWrapper createAndAddNew(String customRuleName);

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     * @param customRuleName Custom rule name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    boolean remove(String customRuleName);

    /**
     * Returns the collection as an immutable list.
     * @return List of custom rule definitions.
     */
    List<RuleDefinitionWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();
}
