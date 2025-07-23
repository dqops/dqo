/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.models.CheckResultsFragmentFilter;
import com.dqops.data.models.DeleteStoredDataResult;

/**
 * Service that deletes outdated results of a check.
 */
public interface CheckResultsDeleteService {

    /**
     * Deletes the results from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the result fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    DeleteStoredDataResult deleteSelectedCheckResultsFragment(CheckResultsFragmentFilter filter, UserDomainIdentity userIdentity);
}
