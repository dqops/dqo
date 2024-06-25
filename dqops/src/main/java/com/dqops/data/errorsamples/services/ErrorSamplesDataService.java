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

package com.dqops.data.errorsamples.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.models.ErrorSamplesListModel;

/**
 * Service that returns data from error samples.
 */
public interface ErrorSamplesDataService {
    /**
     * Retrieves the complete model of the error samples related to a check.
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity within the data domain.
     * @return Complete model of the error samples.
     */
    ErrorSamplesListModel[] readErrorSamplesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     ErrorSamplesFilterParameters loadParameters,
                                                     UserDomainIdentity userDomainIdentity);
}
