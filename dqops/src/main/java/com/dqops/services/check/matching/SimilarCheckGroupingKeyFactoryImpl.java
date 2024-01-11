/*
 * Copyright © 2024 DQOps (support@dqops.com)
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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.services.check.mapping.models.CheckModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class SimilarCheckGroupingKeyFactoryImpl implements SimilarCheckGroupingKeyFactory {
    private final Set<String> checkTimeScaleValues;
    private final Set<String> checkTypeValues;

    public SimilarCheckGroupingKeyFactoryImpl() {
        this.checkTimeScaleValues = Arrays.stream(CheckTimeScale.values())
                .map(Enum::toString)
                .collect(Collectors.toSet());
        this.checkTypeValues = Set.of("profile", "partition");
    }

    @Override
    public SimilarCheckGroupingKey createSimilarCheckGroupingKey(CheckModel checkModel) {
        String checkGroupingRoot = getGroupingRootNameFromCheckName(checkModel.getCheckName());
        return new SimilarCheckCheckNameRootKey(checkGroupingRoot);
    }

    /**
     * Create a matching key to separate checks into groups.
     *
     * @param checkSpec Check spec for which to match the similar check grouping key.
     * @return Similar check grouping key.
     */
    @Override
    public SimilarCheckCheckNameRootKey createSimilarCheckGroupingKey(AbstractCheckSpec<?, ?, ?, ?> checkSpec) {
        String checkGroupingRoot = getGroupingRootNameFromCheckName(checkSpec.getCheckName());
        return new SimilarCheckCheckNameRootKey(checkGroupingRoot);
    }

    private LinkedList<String> filterCheckTimeScalePrefix(LinkedList<String> checkNameComponents) {
        if (checkNameComponents == null || checkNameComponents.isEmpty()) {
            return checkNameComponents;
        }

        String firstComponent = checkNameComponents.getFirst();
        if (checkTimeScaleValues.contains(firstComponent)) {
            checkNameComponents.removeFirst();
        }

        return checkNameComponents;
    }

    private LinkedList<String> filterCheckTypePrefix(LinkedList<String> checkNameComponents) {
        if (checkNameComponents == null || checkNameComponents.isEmpty()) {
            return checkNameComponents;
        }

        String firstComponent = checkNameComponents.getFirst();
        if (checkTypeValues.contains(firstComponent)) {
            checkNameComponents.removeFirst();
        }

        return checkNameComponents;
    }

    private String getGroupingRootNameFromCheckName(String checkName) {
        String checkNameDelimiter = "_";

        LinkedList<String> checkNameSplit = new LinkedList<>(Arrays.asList(checkName.split(checkNameDelimiter)));
        checkNameSplit = filterCheckTimeScalePrefix(checkNameSplit);
        checkNameSplit = filterCheckTypePrefix(checkNameSplit);

        return String.join(checkNameDelimiter, checkNameSplit);
    }
}
