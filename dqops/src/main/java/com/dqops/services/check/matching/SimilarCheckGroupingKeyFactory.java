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
import com.dqops.services.check.mapping.models.CheckModel;

public interface SimilarCheckGroupingKeyFactory {
    /**
     * Create a matching key to separate checks into groups.
     * @param checkModel Check model for which to match the similar check grouping key.
     * @return Similar check grouping key.
     */
    SimilarCheckGroupingKey createSimilarCheckGroupingKey(CheckModel checkModel);

    /**
     * Create a matching key to separate checks into groups.
     * @param checkSpec Check spec for which to match the similar check grouping key.
     * @return Similar check grouping key.
     */
    SimilarCheckGroupingKey createSimilarCheckGroupingKey(AbstractCheckSpec<?, ?, ?, ?> checkSpec);
}
