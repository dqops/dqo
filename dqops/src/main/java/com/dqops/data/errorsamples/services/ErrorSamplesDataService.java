/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
