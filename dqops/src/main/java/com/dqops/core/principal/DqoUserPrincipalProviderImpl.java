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

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.login.DqoUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service that returns the user principal of the user identified by the DQOps Cloud API Key.
 * This provider should be called by operations executed from the DQOps command line to obtain the principal or when DQOps is running in a single user mode.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoUserPrincipalProviderImpl implements DqoUserPrincipalProvider {
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;

    /**
     * Dependency injection constructor.
     * @param dqoCloudApiKeyProvider DQOps Cloud API Key provider service.
     */
    @Autowired
    public DqoUserPrincipalProviderImpl(DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
    }

    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    @Override
    public DqoUserPrincipal createUserPrincipalForAdministrator() {
        /******** Uncomment the following code and comment the rest to use a hardcoded principal with limited access rights for testing purposes */
    //    DqoUserRole testedRole = DqoUserRole.VIEWER;
    //    List<GrantedAuthority> testPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(testedRole);
    //    DqoUserPrincipal dqoUserPrincipalTest = new DqoUserPrincipal("", testedRole, testPrivileges);
    //    return dqoUserPrincipalTest;
        /******** end of testing code */

        DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey(null);
        if (dqoCloudApiKey == null) {
            // user not authenticated to DQOps Cloud, so we use a default token
            List<GrantedAuthority> adminPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(DqoUserRole.ADMIN);
            DqoUserPrincipal dqoUserPrincipalLocal = new DqoUserPrincipal("", DqoUserRole.ADMIN, adminPrivileges);
            return dqoUserPrincipalLocal;
        }

        DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
        DqoUserPrincipal userPrincipalFromApiKey = apiKeyPayload.createUserPrincipal();
        return userPrincipalFromApiKey;
    }

    /**
     * Returns the principal of the local user who has direct access to the command line and runs operations on the DQOps shell.
     *
     * @return The principal of the local user who is using DQOps from the terminal.
     */
    @Override
    public DqoUserPrincipal getLocalUserPrincipal() {
        return createUserPrincipalForAdministrator();
    }
}
