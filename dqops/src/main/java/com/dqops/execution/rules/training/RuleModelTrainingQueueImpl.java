/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.rules.training;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.id.HierarchyId;
import org.springframework.stereotype.Component;

/**
 * A rule training queue to request retraining a model for an ML rule.
 */
@Component
public class RuleModelTrainingQueueImpl implements RuleModelTrainingQueue {
    /**
     * Requests retraining a model for a given data quality check.
     * @param userDomainIdentity User identity to identify the data domain.
     * @param checkHierarchyId Data quality check hierarchy id to identify the check.
     */
    @Override
    public void queueModelRetraining(UserDomainIdentity userDomainIdentity, HierarchyId checkHierarchyId) {
        // not supported in an open-source version of DQOps
    }
}
