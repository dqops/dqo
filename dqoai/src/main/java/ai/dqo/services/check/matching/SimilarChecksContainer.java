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
package ai.dqo.services.check.matching;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.models.UICheckModel;
import ai.dqo.services.check.mapping.models.UIQualityCategoryModel;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Object that aggregates models (information) about all similar checks in other categories.
 */
public class SimilarChecksContainer {
    private Map<SimilarCheckSensorRuleKey, SimilarChecksGroup> checkGroups = new LinkedHashMap<>();

    /**
     * Returns a similar check group for a given key (the key contains the sensor name and rule names).
     * Creates and returns a new empty group, if the group as not found.
     * @param groupMatchingKey Group matching key with the sensor name and rule names.
     * @return Similar checks group.
     */
    public SimilarChecksGroup getSimilarCheckGroup(SimilarCheckSensorRuleKey groupMatchingKey) {
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
     * @param uiAllChecksModel All UI checks.
     * @param checkType Check type.
     * @param timeScale Check's time scale or null.
     */
    public void appendAllChecks(UIAllChecksModel uiAllChecksModel, CheckType checkType, CheckTimeScale timeScale) {
        for (UIQualityCategoryModel categoryModel : uiAllChecksModel.getCategories()) {
            for (UICheckModel checkModel : categoryModel.getChecks()) {
                SimilarCheckSensorRuleKey similarCheckMatchKey = checkModel.createSimilarCheckMatchKey();

                SimilarChecksGroup similarCheckGroup = this.getSimilarCheckGroup(similarCheckMatchKey);
                SimilarCheckModel similarCheckModel = new SimilarCheckModel(checkType, timeScale, categoryModel.getCategory(), checkModel);
                similarCheckGroup.addSimilarCheck(similarCheckModel);
            }
        }
    }
}
