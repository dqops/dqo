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

import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.principal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DQOps authentication token factory that creates Spring Security {@link org.springframework.security.core.Authentication} token
 * with a user principal.
 */
@Component
public class DqoAuthenticationTokenFactoryImpl implements DqoAuthenticationTokenFactory {
    private final DqoUserPrincipalProvider dqoUserPrincipalProvider;
    private final UserDomainIdentityFactory userDomainIdentityFactory;

    /**
     * Dependency injection constructor.
     * @param dqoUserPrincipalProvider DQOps principal provider that creates a principal for a single user, having direct access to DQOps.
     * @param userDomainIdentityFactory User data domain factory, used to look up the data domain name.
     */
    @Autowired
    public DqoAuthenticationTokenFactoryImpl(
            DqoUserPrincipalProvider dqoUserPrincipalProvider,
            UserDomainIdentityFactory userDomainIdentityFactory) {
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
    }

    /**
     * Creates an anonymous user token with no roles assigned and no identity.
     *
     * @return Anonymous user token.
     */
    @Override
    public Authentication createAnonymousToken() {
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(UserDomainIdentity.DEFAULT_DATA_DOMAIN, UserDomainIdentity.DEFAULT_DATA_DOMAIN);
        ArrayList<GrantedAuthority> emptyListOfRoles = new ArrayList<>();
        UsernamePasswordAuthenticationToken anonymousAuthenticationToken = new UsernamePasswordAuthenticationToken(dqoUserPrincipal, null, emptyListOfRoles);
        return anonymousAuthenticationToken;
    }

    /**
     * Creates an authenticated DQOps user principal that is identified by the DQOps Cloud API key stored in the local DQOps instance.
     * This type of authentication is used when authentication via DQOps Cloud is not used and a single user is accessing a local DQOps instance, having
     * full (ADMIN) access rights when the DQOps Cloud API key is not present (not working with a DQOps Cloud connection) or limited the role in the DQOps Cloud API Key.
     * @return Authenticated user principal, based on the identity stored in the DQOps Cloud API Key.
     */
    @Override
    public Authentication createAuthenticatedWithDefaultDqoCloudApiKey() {
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        UsernamePasswordAuthenticationToken localUserAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, userPrincipal.getApiKeyPayload(), userPrincipal.getPrivileges());
        return localUserAuthenticationToken;
    }

    /**
     * Creates an authentication principal from a DQOps Cloud issued user token. User tokens are issued for multi-user accounts.
     *
     * @param userTokenPayload User token payload.
     * @param dataDomain Data domain name.
     * @return Authenticated user principal, based on the user token.
     */
    @Override
    public Authentication createAuthenticatedWithUserToken(DqoUserTokenPayload userTokenPayload, String dataDomain) {
        String effectiveCloudDataDomainName = dataDomain == null ? UserDomainIdentity.DEFAULT_DATA_DOMAIN : dataDomain;
        String dataDomainFolderName = this.userDomainIdentityFactory.mapDataDomainCloudNameToFolder(effectiveCloudDataDomainName);

        DqoUserRole effectiveRole = userTokenPayload.getAccountRole();

        if (userTokenPayload.getDomainRoles() != null) {
            DqoUserRole dataDomainRole = userTokenPayload.getDomainRoles().get(dataDomain);
            effectiveRole = DqoUserRole.strongest(userTokenPayload.getAccountRole(), dataDomainRole);
        }

        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(effectiveRole);
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(userTokenPayload.getUser(), userTokenPayload.getAccountRole(),
                grantedPrivileges, userTokenPayload, dataDomainFolderName, effectiveCloudDataDomainName, userTokenPayload.getTenantId(), userTokenPayload.getTenantGroup());

        UsernamePasswordAuthenticationToken apiKeyAuthenticationToken = new UsernamePasswordAuthenticationToken(
                dqoUserPrincipal, userTokenPayload, dqoUserPrincipal.getPrivileges());
        return apiKeyAuthenticationToken;
    }
}
