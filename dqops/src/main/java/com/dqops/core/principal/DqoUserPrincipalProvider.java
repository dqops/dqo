/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.principal;

/**
 * Service that returns the user principal of the user identified by the DQOps Cloud API Key.
 * This provider should be called by operations executed from the DQOps command line to obtain the principal or when DQOps is running in a single user mode.
 */
public interface DqoUserPrincipalProvider {
    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     *
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    DqoUserPrincipal createLocalInstanceAdminPrincipal();

    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     *
     * @param dataDomainName Data domain name.
     *
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    DqoUserPrincipal createLocalDomainAdminPrincipal(String dataDomainName);

    /**
     * Returns the principal of the local user who has direct access to the command line and runs operations on the DQOps shell.
     * @return The principal of the local user who is using DQOps from the terminal.
     */
    DqoUserPrincipal getLocalUserPrincipal();
}
