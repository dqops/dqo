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
 * Test stub for returning a user principal.
 */
public class DqoUserPrincipalProviderStub implements DqoUserPrincipalProvider {
    private DqoUserPrincipal principal;

    /**
     * Creates a test stub instance that returns a hardcoded user principal.
     * @param principal User principal to return.
     */
    public DqoUserPrincipalProviderStub(DqoUserPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Creates a DQO user principal for the user who has direct access to DQO instance, running operations from CLI
     * or using the DQO shell directly.
     *
     * @return User principal that has full admin rights when the instance is not authenticated to DQO Cloud or limited to the role in the DQO Cloud Api key.
     */
    @Override
    public DqoUserPrincipal createLocalInstanceAdminPrincipal() {
        return this.principal;
    }

    /**
     * Returns the principal of the local user who has direct access to the command line and runs operations on the DQOps shell.
     *
     * @return The principal of the local user who is using DQOps from the terminal.
     */
    @Override
    public DqoUserPrincipal getLocalUserPrincipal() {
        return this.principal;
    }

    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     *
     * @param dataDomainName Data domain name.
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    @Override
    public DqoUserPrincipal createLocalDomainAdminPrincipal(String dataDomainName) {
        return this.principal;
    }
}
