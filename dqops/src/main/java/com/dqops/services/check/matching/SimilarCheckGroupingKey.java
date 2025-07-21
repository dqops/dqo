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

/**
 * Key object used to find similar checks in other check types.
 * It should support equals and hashcode.
 */
public interface SimilarCheckGroupingKey {
    /**
     * Predicate method to determine whether the check belongs to the similar check group identified by this key.
     * @param checkSpec Check spec to match to the group.
     * @return True if provided check matches the group.
     */
    boolean matches(AbstractCheckSpec<?, ?, ?, ?> checkSpec);
}
