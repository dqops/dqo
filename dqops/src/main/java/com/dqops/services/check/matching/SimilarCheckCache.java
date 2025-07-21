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

import com.dqops.checks.CheckTarget;

import java.util.List;

/**
 * Singleton that stores a cache of similar checks, detecting only built-in default checks.
 * Returns the definitions of similar checks in on the table level and on the column level.
 */
public interface SimilarCheckCache {
    /**
     * Returns a container of similar table level checks.
     *
     * @return Similar table level checks.
     */
    SimilarChecksContainer getTableLevelSimilarChecks();

    /**
     * Returns a container of similar column level checks.
     *
     * @return Similar column level checks.
     */
    SimilarChecksContainer getColumnLevelSimilarChecks();

    /**
     * Finds checks similar to a given check.
     * @param checkTarget Check target (table or column).
     * @param checkName Check name.
     * @return List of similar checks to this one (including the requested check) or returns null when there are no similar checks.
     */
    List<SimilarCheckModel> findSimilarChecksTo(CheckTarget checkTarget, String checkName);
}
