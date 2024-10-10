/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
