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


/**
 * Service that finds similar checks on a table or on a column.
 */
public interface SimilarCheckMatchingService {
    /**
     * Find similar checks in all check types for a table.
     *
     * @return List of similar checks.
     */
    SimilarChecksContainer findSimilarTableChecks();

    /**
     * Find similar checks in all check types for a column.
     *
     * @return List of similar checks.
     */
    SimilarChecksContainer findSimilarColumnChecks();
}
