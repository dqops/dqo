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

package com.dqops.execution.rules.training;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.id.HierarchyId;

/**
 * A rule training queue to request retraining a model for an ML rule.
 */
public interface RuleModelTrainingQueue {
    /**
     * Requests retraining a model for a given data quality check.
     *
     * @param userDomainIdentity User identity to identify the data domain.
     * @param checkHierarchyId   Data quality check hierarchy id to identify the check.
     */
    void queueModelRetraining(UserDomainIdentity userDomainIdentity, HierarchyId checkHierarchyId);
}
