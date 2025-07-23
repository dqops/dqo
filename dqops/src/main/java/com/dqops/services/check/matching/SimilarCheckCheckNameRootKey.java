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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Key object that stores a check name root (i.e. daily_partitioned_row_count -> row_count). It is used to find similar checks in other check types.
 * It supports equals and hashcode.
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SimilarCheckCheckNameRootKey implements SimilarCheckGroupingKey {
    private String checkNameRoot;

    /**
     * Predicate method to determine whether the check belongs to the similar check group identified by this key.
     * @param checkSpec Check spec to match to the group.
     * @return True if provided check matches the group.
     */
    @Override
    public boolean matches(AbstractCheckSpec<?, ?, ?, ?> checkSpec) {
        return checkSpec.getCheckName() != null && checkSpec.getCheckName().contains(checkNameRoot);
    }
}
