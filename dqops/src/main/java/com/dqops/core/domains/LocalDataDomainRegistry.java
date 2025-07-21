/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.domains;

import com.dqops.metadata.settings.domains.LocalDataDomainSpec;

import java.util.Collection;
import java.util.List;

/**
 * Data domain registry that maintains the list of active data domains. When the list of domains is changed, it asks the data domain manager to update the domains.
 */
public interface LocalDataDomainRegistry {
    /**
     * Starts the service and loads the data domain list.
     *
     * @param localDataDomainManager Back reference to the data domain manager that will be used to manage local domains. It is provided a back reference to avoid circular references.
     */
    void start(LocalDataDomainManager localDataDomainManager);

    /**
     * Return a collection of nested data domain names (excluding the default domain).
     *
     * @return List of nested domains or null when data domains are not supported.
     */
    Collection<LocalDataDomainSpec> getNestedDataDomains();

    /**
     * Returns a list all local data domains, including the default domain (if it is loaded).
     * @return List of local data domains.
     */
    Collection<LocalDataDomainSpec> getAllLocalDataDomains();

    /**
     * Replaces the current list of data domains with a new list of domains. Some domains are deleted, other created locally.
     * @param newDataDomainList New list of data domains.
     */
    void replaceDataDomainList(List<LocalDataDomainSpec> newDataDomainList);

    /**
     * Adds a local data domain. Saves the domain to the local settings and starts the data domain locally.
     * @param localDataDomainSpec Data domain specification.
     */
    void addNestedDataDomain(LocalDataDomainSpec localDataDomainSpec);

    /**
     * Deletes a local data domain. Operations for the domain (job scheduling) is stopped, but the local data is preserved.
     * @param dataDomainName Data domain name.
     */
    boolean deleteNestedDataDomain(String dataDomainName);

    /**
     * Returns a data domain given the technical domain name.
     * @param dataDomainName Data domain name.
     * @return Data domain or null, when this domain is not maintained locally.
     */
    LocalDataDomainSpec getNestedDomain(String dataDomainName);

    /**
     * Changes the status of running the job scheduler on this data domain.
     * @param dataDomainName Data domain name.
     * @param enableJobScheduling Enable job scheduling.
     */
    void changeJobSchedulerStatusForDomain(String dataDomainName, boolean enableJobScheduling);
}
