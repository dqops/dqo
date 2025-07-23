/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.statistics.models.StatisticsResultsFragmentFilter;

/**
 * Service that deletes outdated statistics results.
 */
public interface StatisticsDeleteService {
    /**
     * Deletes the statistics results from a table, applying specific filters to get the fragment (if necessary).
     * @param userIdentity User identity that specifies the data domain.
     * @param filter Filter for the statistics results fragment that is of interest.
     * @return Data delete operation summary.
     */
    DeleteStoredDataResult deleteSelectedStatisticsResultsFragment(
            StatisticsResultsFragmentFilter filter,
            UserDomainIdentity userIdentity);
}
