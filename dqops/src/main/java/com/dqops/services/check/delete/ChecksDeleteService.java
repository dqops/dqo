/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.delete;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.search.CheckSearchFilters;

/**
 * Deleted selected configured checks from the matched hierarchy node.
 */
public interface ChecksDeleteService {

    /**
     * Deletes selected checks that match the filters.
     * @param filters CheckSearchFilters which matches will be deleted.
     * @param userIdentity User identity
     */
    void deleteSelectedChecks(CheckSearchFilters filters, UserDomainIdentity userIdentity);
}
