/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.AllChecksModel;

import java.util.List;

/**
 * Factory for {@link AllChecksModel}.
 */
public interface AllChecksModelFactory {
    /**
     * Creates a list of {@link AllChecksModel}s based on provided filters.
     * @param checkSearchFilters Check search filters.
     * @param principal User principal.
     * @return List of {@link AllChecksModel}s (by connections) fitting the filters.
     */
    List<AllChecksModel> findAllConfiguredAndPossibleChecks(CheckSearchFilters checkSearchFilters,
                                                            DqoUserPrincipal principal);

    /**
     * Generate one fake table and one fake column, capture all available checks that are supported on the connection.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param checkSearchFilters Additional check search filter to limit the list of possible checks.
     * @param principal Calling user principal.
     * @return Model of all possible checks, including both table and column level checks.
     */
    AllChecksModel createTemplatedCheckModelsAvailableOnConnection(
            String connectionName,
            String schemaName,
            CheckSearchFilters checkSearchFilters,
            DqoUserPrincipal principal);
}
