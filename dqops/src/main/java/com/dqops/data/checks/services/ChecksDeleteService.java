package com.dqops.data.checks.services;

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
