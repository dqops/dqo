/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.datadomains;

/**
 * Service that tracks the current data domain that is used by the command-line commands.
 */
public interface CliCurrentDataDomainService {
    /**
     * Returns the name of the current data domain. It is the name of the data domain in DQOps cloud, despite the name of the default data domain that was mounted to the DQOps user home root folder.
     *
     * @return Current data domain.
     */
    String getCurrentDataDomain();

    /**
     * Changes the current data domain.
     *
     * @param currentDataDomain New data domain.
     */
    void setCurrentDataDomain(String currentDataDomain);
}
