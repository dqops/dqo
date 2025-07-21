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

import java.util.List;

/**
 * Data domain management service that synchronizes data domain with the DQOps Cloud, creates, deletes and synchronizes the list of data domains.
 */
public interface DataDomainsService {
    /**
     * Downloads the list of data domains from DQOps cloud and configures the domains in the local DQOps instance.
     * @param silent When synchronization fails, just move silently on, without throwing an exception.
     */
    void synchronizeDataDomainList(boolean silent);

    /**
     * Returns a list of all local data domains. It also returns the default data domain.
     *
     * @return Return all data domains.
     */
    List<LocalDataDomainModel> getAllDataDomains();

    /**
     * Creates a new data domain on the server and then create a local data domain.
     *
     * @param dataDomainDisplayName Data domain display name.
     * @return Data domain model.
     */
    LocalDataDomainModel createDataDomain(String dataDomainDisplayName);

    /**
     * Deletes a data domain from the server and locally.
     *
     * @param dataDomainName Data domain name.
     */
    void deleteDataDomain(String dataDomainName);
}
