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

package com.dqops.rest.server.authentication;

import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * DQO authentication token factory that creates Spring Security {@link org.springframework.security.core.Authentication} token
 * with a user principal.
 */
@Component
public class DqoAuthenticationTokenFactoryImpl implements DqoAuthenticationTokenFactory {
    private final DqoCloudApiKeyPrincipalProvider dqoCloudApiKeyPrincipalProvider;

    /**
     * Dependency injection constructor.
     * @param dqoCloudApiKeyPrincipalProvider DQO principal provider that creates a principal for a single user, having direct access to DQO.
     */
    @Autowired
    public DqoAuthenticationTokenFactoryImpl(DqoCloudApiKeyPrincipalProvider dqoCloudApiKeyPrincipalProvider) {
        this.dqoCloudApiKeyPrincipalProvider = dqoCloudApiKeyPrincipalProvider;
    }

    /**
     * Creates an anonymous user token with no roles assigned and no identity.
     *
     * @return Anonymous user token.
     */
    @Override
    public Authentication createAnonymousToken() {
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal();
        ArrayList<GrantedAuthority> emptyListOfRoles = new ArrayList<>();
        UsernamePasswordAuthenticationToken anonymousAuthenticationToken = new UsernamePasswordAuthenticationToken(dqoUserPrincipal, null, emptyListOfRoles);
        return anonymousAuthenticationToken;
    }

    /**
     * Creates an authenticated DQO user principal that is identified by the DQO Cloud API key stored in the local DQO instance.
     * This type of authentication is used when authentication via DQO Cloud is not used and a single user is accessing a local DQO instance, having
     * full (ADMIN) access rights when the DQO Cloud API key is not present (not working with a DQO Cloud connection) or limited the role in the DQO Cloud API Key.
     * @return Authenticated user principal, based on the identity stored in the DQO Cloud API Key.
     */
    @Override
    public Authentication createAuthenticatedWithDefaultDqoCloudApiKey() {
        DqoUserPrincipal userPrincipal = this.dqoCloudApiKeyPrincipalProvider.createUserPrincipal();
        UsernamePasswordAuthenticationToken localUserAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, userPrincipal.getApiKeyPayload(), userPrincipal.getPrivileges());
        return localUserAuthenticationToken;
    }

    /**
     * Creates an authentication principal from a DQO Cloud issued user token. User tokens are issued for multi-user accounts.
     *
     * @param userTokenPayload User token payload.
     * @return Authenticated user principal, based on the user token.
     */
    @Override
    public Authentication createAuthenticatedWithUserToken(DqoUserTokenPayload userTokenPayload) {
        DqoUserPrincipal userPrincipalFromApiKey = userTokenPayload.createUserPrincipal();

        UsernamePasswordAuthenticationToken apiKeyAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipalFromApiKey, userTokenPayload, userPrincipalFromApiKey.getPrivileges());
        return apiKeyAuthenticationToken;
    }
}
