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

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DQO authentication token factory that creates Spring Security {@link org.springframework.security.core.Authentication} token
 * with a user principal.
 */
@Component
public class DqoAuthenticationTokenFactoryImpl implements DqoAuthenticationTokenFactory {
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;

    @Autowired
    public DqoAuthenticationTokenFactoryImpl(DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
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
        DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey();
        if (dqoCloudApiKey == null) {
            // user not authenticated to DQO Cloud, so we use a default token
            DqoUserPrincipal dqoUserPrincipalLocal = new DqoUserPrincipal();
            List<GrantedAuthority> adminRoles = DqoRoleAuthorities.getExpandedRoles(DqoRoleAuthorities.ADMIN);
            UsernamePasswordAuthenticationToken adminAuthenticationTokenLocal = new UsernamePasswordAuthenticationToken(
                    dqoUserPrincipalLocal, null, adminRoles);
            return adminAuthenticationTokenLocal;
        }

        DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(apiKeyPayload.getSubject());
        List<GrantedAuthority> roles = new ArrayList<>();
        if (!Strings.isNullOrEmpty(apiKeyPayload.getRole())) {
            SimpleGrantedAuthority authorityForRole = DqoRoleAuthorities.getAuthorityForRole(apiKeyPayload.getRole());
            roles = DqoRoleAuthorities.getExpandedRoles(authorityForRole);
        }

        UsernamePasswordAuthenticationToken apiKeyAuthenticationToken = new UsernamePasswordAuthenticationToken(
                dqoUserPrincipal, dqoCloudApiKey.getApiKeyToken(), roles);
        return apiKeyAuthenticationToken;
    }
}
