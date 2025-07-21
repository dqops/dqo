/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.AllChecksModel;
import com.dqops.services.check.models.CheckConfigurationModel;

import java.util.List;

/**
 * Service for creating flat collections of self-contained check configuration models.
 */
public interface CheckFlatConfigurationFactory {
    /**
     * Flattens information contained in <code>allChecksModel</code>.
     * @param allChecksModel Partial model of checks on a single connection.
     * @return List of self-contained check configuration models.
     */
    List<CheckConfigurationModel> fromAllChecksModel(AllChecksModel allChecksModel);

    /**
     * Gets a collection of check configuration models that fit the provided filters.
     * @param checkSearchFilters Check search filters.
     * @param principal User principal.
     * @param limit Limit of results.
     * @return List of self-contained check configuration models that fit the filters.
     */
    List<CheckConfigurationModel> findAllCheckConfigurations(CheckSearchFilters checkSearchFilters,
                                                             DqoUserPrincipal principal,
                                                             int limit);
}
