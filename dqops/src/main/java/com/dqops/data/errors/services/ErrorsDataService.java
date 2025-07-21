/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errors.models.ErrorsListModel;

/**
 * Service that returns data from sensor readouts.
 */
public interface ErrorsDataService {
    /**
     * Retrieves the complete model of the errors related to check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity within the data domain.
     * @return Complete model of the errors.
     */
    ErrorsListModel[] readErrorsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                         ErrorsDetailedFilterParameters loadParameters,
                                         UserDomainIdentity userDomainIdentity);
}
