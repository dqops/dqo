/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.matching;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.services.check.mapping.models.CheckModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Similar check grouping key factory. Creates keys used to find similar checks.
 */
@Component
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
