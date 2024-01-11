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
package com.dqops.services.check.matching;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.utils.exceptions.DqoRuntimeException;

import java.util.*;

/**
 * Object that aggregates models (information) about all similar checks in other categories.
 */
public class SimilarChecksContainer {
    private final Map<SimilarCheckGroupingKey, SimilarChecksGroup> checkGroups = new LinkedHashMap<>();
    private final Map<String, SimilarChecksGroup> similarChecksByCheckName = new HashMap<>();

    /**
     * Returns a similar check group for a given key.
     * Creates and returns a new empty group, if the group as not found.
     * @param groupMatchingKey Group matching key.
     * @return Similar checks group.
     */
    public SimilarChecksGroup getSimilarCheckGroup(SimilarCheckGroupingKey groupMatchingKey) {
        SimilarChecksGroup similarChecksGroup = this.checkGroups.get(groupMatchingKey);
        if (similarChecksGroup == null) {
            similarChecksGroup = new SimilarChecksGroup(groupMatchingKey);
            this.checkGroups.put(groupMatchingKey, similarChecksGroup);
        }

        return similarChecksGroup;
    }

    /**
     * Return a collection of similar check groups.
     * @return Groups of similar checks. Each group contains similar checks that are using the same sensor and rules.
     */
    public Collection<SimilarChecksGroup> getSimilarCheckGroups() {
        return Collections.unmodifiableCollection(checkGroups.values());
    }

    /**
     * Appends all checks from a category.
     * @param similarCheckGroupingKeyFactory Similar check grouping key factory to use for grouping key extraction.
     * @param checkContainerModel All UI checks.
     * @param checkTarget Check target (table or column).
     * @param checkType Check type.
     * @param timeScale Check's time scale or null.
     */
    public void appendAllChecks(SimilarCheckGroupingKeyFactory similarCheckGroupingKeyFactory,
                                CheckContainerModel checkContainerModel,
                                CheckTarget checkTarget,
                                CheckType checkType,
                                CheckTimeScale timeScale) {
        for (QualityCategoryModel categoryModel : checkContainerModel.getCategories()) {
            for (CheckModel checkModel : categoryModel.getChecks()) {
                SimilarCheckGroupingKey similarCheckMatchKey = similarCheckGroupingKeyFactory.createSimilarCheckGroupingKey(checkModel);

                SimilarChecksGroup similarCheckGroup = this.getSimilarCheckGroup(similarCheckMatchKey);
                String categoryName = categoryModel.getCategory();
                if (categoryName.startsWith(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME)) {
                    categoryName = AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME;
                }
                SimilarCheckModel similarCheckModel = new SimilarCheckModel(checkTarget, checkType, timeScale, categoryName, checkModel);
                similarCheckGroup.addSimilarCheck(similarCheckModel);

                if (this.similarChecksByCheckName.containsKey(checkModel.getCheckName())) {
                    throw new DqoRuntimeException("Duplicate check name found, the built-in data quality checks must have unique names, check name: " +
                            checkModel.getCheckName());
                }
                this.similarChecksByCheckName.put(checkModel.getCheckName(), similarCheckGroup);
            }
        }
    }

    /**
     * Returns a view of the checks, grouped by the category name of the first check.
     * @return Dictionary keyed by the category name, containing a collection of checks in that category.
     */
    public Map<String, Collection<SimilarChecksGroup>> getChecksPerGroup() {
        Map<String, Collection<SimilarChecksGroup>> checksByCategory = new LinkedHashMap<>();

        for (SimilarChecksGroup similarChecksGroup : checkGroups.values()) {
            String category = similarChecksGroup.getFirstCheckCategory();
            Collection<SimilarChecksGroup> checksInCategory = checksByCategory.computeIfAbsent(category, k -> new ArrayList<>());
            checksInCategory.add(similarChecksGroup);
        }

        return checksByCategory;
    }

    /**
     * Finds a group of similar checks that are similar to a check in question.
     * @param checkName Check name.
     * @return A group of similar checks to this check. The check itself will also be included in the result.
     */
    public SimilarChecksGroup getSimilarChecksTo(String checkName) {
        return this.similarChecksByCheckName.get(checkName);
    }
}
