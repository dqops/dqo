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
package com.dqops.metadata.definitions.checks;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;

import java.util.Collection;
import java.util.List;

/**
 * List of custom check definitions.
 */
public interface CheckDefinitionList extends Iterable<CheckDefinitionWrapper> {
    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    int size();

    /**
     * Finds an existing object given the object name.
     * @param checkName Check name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    CheckDefinitionWrapper getByObjectName(String checkName, boolean loadAllWhenMissing);

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param checkName Check name.
     * @return Created object instance.
     */
    CheckDefinitionWrapper createAndAddNew(String checkName);

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     * @param checkName Check name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    boolean remove(String checkName);

    /**
     * Returns the collection as an immutable list.
     * @return List of custom check definitions.
     */
    List<CheckDefinitionWrapper> toList();

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    void flush();

    /**
     * Finds a check specification for the given check. Returns a check specification if the check was found or null, when the check is unknown.
     * @param checkTarget Check target (table or column).
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param checkTimeScale Optional check scale (daily, monthly). Null for profiling checks.
     * @param category Check category name.
     * @param checkName Check name.
     * @return Check specification when the check was found or null when the check is unknown.
     */
    CheckDefinitionSpec getCheckDefinitionSpec(CheckTarget checkTarget,
                                               CheckType checkType,
                                               CheckTimeScale checkTimeScale,
                                               String category,
                                               String checkName);

    /**
     * Finds all checks defined for the given check target (table or column), check type (profiling, recurring, partitioned) and optionally a time scale.
     * @param checkTarget    Check target (table or column).
     * @param checkType      Check type (profiling, recurring, partitioned).
     * @param checkTimeScale Optional check scale (daily, monthly). Null for profiling checks.
     * @param category       Check category.
     * @return Collection of checks defined at that level.
     */
    Collection<CheckDefinitionSpec> getChecksAtLevel(CheckTarget checkTarget,
                                                     CheckType checkType,
                                                     CheckTimeScale checkTimeScale,
                                                     String category);

    /**
     * Creates a folder path for checks at a given level.
     * @param checkTarget Check target (table or column).
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param timeScale Time scale (daily, weekly) for monitoring and partitioned checks.
     * @return Full check name.
     */
    static String makeCheckFolderPrefix(CheckTarget checkTarget, CheckType checkType, CheckTimeScale timeScale) {
        StringBuilder checkNameBuilder = new StringBuilder();
        checkNameBuilder.append(checkTarget);
        checkNameBuilder.append('/');
        checkNameBuilder.append(checkType.getDisplayName());
        checkNameBuilder.append('/');
        if (timeScale != null) {
            checkNameBuilder.append(timeScale);
            checkNameBuilder.append('/');
        }

        String checkFolderPrefix = checkNameBuilder.toString();
        return checkFolderPrefix;
    }

    /**
     * Creates a folder path for checks at a given level, including also the category name.
     * @param checkTarget Check target (table or column).
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param timeScale Time scale (daily, weekly) for monitoring and partitioned checks.
     * @param category Check category name.
     * @return Check name prefix for all checks in the given category.
     */
    static String makeCheckFolderPrefix(CheckTarget checkTarget, CheckType checkType, CheckTimeScale timeScale, String category) {
        StringBuilder checkNameBuilder = new StringBuilder();
        checkNameBuilder.append(checkTarget);
        checkNameBuilder.append('/');
        checkNameBuilder.append(checkType.getDisplayName());
        checkNameBuilder.append('/');
        if (timeScale != null) {
            checkNameBuilder.append(timeScale);
            checkNameBuilder.append('/');
        }
        checkNameBuilder.append(category);
        checkNameBuilder.append('/');

        String fullCheckName = checkNameBuilder.toString();
        return fullCheckName;
    }

    /**
     * Creates a check name given all components on the check path.
     * @param checkTarget Check target (table or column).
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param timeScale Time scale (daily, weekly) for recurring and partitioned checks.
     * @param category Check category name.
     * @param checkName Check name.
     * @return Full check name.
     */
    static String makeCheckName(CheckTarget checkTarget, CheckType checkType, CheckTimeScale timeScale, String category, String checkName) {
        String fullCheckName = makeCheckFolderPrefix(checkTarget, checkType, timeScale, category) + checkName;
        return fullCheckName;
    }
}
