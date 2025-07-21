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

/**
 * Data domain manager that maintains the configuration and activation of local data domains.
 */
public interface LocalDataDomainManager {
    /**
     * Activates the data domains.
     */
    void start();

    /**
     * Activates a local data domain.
     *
     * @param dataDomainSpec Data domain specification.
     */
    void initializeLocalDataDomain(LocalDataDomainSpec dataDomainSpec);

    /**
     * Updates the configuration of a data domain.
     * @param existingDataDomainSpec Old data domain configuration.
     * @param updatedDataDomainSpec New data domain configuration or null, when the domain should be stopped.
     */

    void updateLocalDataDomain(LocalDataDomainSpec existingDataDomainSpec, LocalDataDomainSpec updatedDataDomainSpec);
}
