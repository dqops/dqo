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

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container of definitions of similar checks for different categories and check types.
 */
public class SimilarChecksGroup {
    /**
     * -- GETTER --
     *  Returns the similar checks matching key. The matching key uses the sensor name and rule names for matching.
     *
     * @return Similar checks matching key.
     */
    @Getter
    private SimilarCheckGroupingKey matchingKey;
    private List<SimilarCheckModel> similarChecks = new ArrayList<>();

    /**
     * Create a similar check set, given the matching key.
     * @param matchingKey Matching key (sensor name, rule names).
     */
    public SimilarChecksGroup(SimilarCheckGroupingKey matchingKey) {
        this.matchingKey = matchingKey;
    }

    /**
     * Returns a read-only collection of similar checks.
     */
    public List<SimilarCheckModel> getSimilarChecks() {
        return Collections.unmodifiableList(this.similarChecks);
    }

    /**
     * Adds a similar check model.
     * @param similarCheckModel Similar check model.
     */
    public void addSimilarCheck(SimilarCheckModel similarCheckModel) {
        this.similarChecks.add(similarCheckModel);
    }

    /**
     * Returns the category of the first similar sensor. We assume that all other similar sensors are in the same category (the built-in sensors should be).
     * @return The category name of the first sensor.
     */
    public String getFirstCheckCategory() {
        return this.similarChecks.get(0).getCategory();
    }
}
