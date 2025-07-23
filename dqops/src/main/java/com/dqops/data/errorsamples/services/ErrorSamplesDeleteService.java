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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.models.ErrorsSamplesFragmentFilter;
import com.dqops.data.models.DeleteStoredDataResult;

/**
 * Service that deletes error samples.
 */
public interface ErrorSamplesDeleteService {

    /**
     * Deletes the error samples from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the error samples fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    DeleteStoredDataResult deleteSelectedErrorSamplesFragment(ErrorsSamplesFragmentFilter filter, UserDomainIdentity userIdentity);
}
