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

import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * DQO user principal that identifies the user.
 */
public class DqoUserPrincipal {
    /**
     * Name used for an unauthenticated principal.
     */
    public static final String UNAUTHENTICATED_PRINCIPAL_NAME = "";

    private final String name;
    private final DqoUserRole accountRole;
    private DqoCloudApiKeyPayload apiKeyPayload;
    private DqoUserTokenPayload userTokenPayload;
    private Collection<GrantedAuthority> privileges;

    public DqoUserPrincipal() {
        this.name = UNAUTHENTICATED_PRINCIPAL_NAME;
        this.accountRole = DqoUserRole.NONE;
        this.privileges = Collections.unmodifiableList(new ArrayList<>());
    }

    /**
     * Creates a user principal.
     * @param name User's email (principal name).
     * @param accountRole Account role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     */
    public DqoUserPrincipal(String name, DqoUserRole accountRole, Collection<GrantedAuthority> privileges) {
        this.name = name;
        this.accountRole = accountRole;
        this.privileges = privileges;
    }

    /**
     * Creates a principal from an api key payload.
     * @param name Principal name (login).
     * @param accountRole Account level role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     * @param apiKeyPayload Source DQO Cloud Api key payload.
     */
    public DqoUserPrincipal(String name, DqoUserRole accountRole, Collection<GrantedAuthority> privileges, DqoCloudApiKeyPayload apiKeyPayload) {
        this(name, accountRole, privileges);
        this.apiKeyPayload = apiKeyPayload;
    }

    /**
     * Creates a principal from a user's identity token issued by DQO Cloud for multi-user environments.
     * @param name Principal name (login).
     * @param accountRole Account level role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     * @param userTokenPayload Source DQO Cloud user's identity token payload.
     */
    public DqoUserPrincipal(String name, DqoUserRole accountRole, Collection<GrantedAuthority> privileges, DqoUserTokenPayload userTokenPayload) {
        this(name, accountRole, privileges);
        this.userTokenPayload = userTokenPayload;
    }

    /**
     * Returns the email that identifies the user. It is the email used to log in to DQO cloud.
     * @return User email.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the role of the user at the account level.
     * @return User role at the account level.
     */
    public DqoUserRole getAccountRole() {
        return accountRole;
    }

    /**
     * Returns the collection of privileges granted to the principal.
     * @return Collection of privileges.
     */
    public Collection<GrantedAuthority> getPrivileges() {
        return privileges;
    }

    /**
     * Returns the DQO Cloud Api key for which the principal was issued.
     * @return The API Key payload or null when the principal was not issued from an API Key.
     */
    public DqoCloudApiKeyPayload getApiKeyPayload() {
        return apiKeyPayload;
    }

    /**
     * Returns the DQO Cloud user token from which the principal is issued. DQO Cloud user tokens are used to authenticate users in a multi-user environment.
     * @return DQO Cloud user token from which the principal was issued or null, when the principal was not issued from an identity token.
     */
    public DqoUserTokenPayload getUserTokenPayload() {
        return userTokenPayload;
    }

    /**
     * Checks if the principal has the <code>privilege</code> (can perform the given operation).
     * @param privilege Privilege to test.
     * @return True when the principal can perform the given action, false when the action is denied.
     */
    public boolean hasPrivilege(GrantedAuthority privilege) {
        return this.privileges.contains(privilege);
    }

    /**
     * Checks if the principal has the required permission. Throws an exception if the principal has no right to execute the given operation.
     * @param requiredPrivilege Required privilege to test.
     */
    public void throwIfNotHavingPrivilege(GrantedAuthority requiredPrivilege) {
        if (!hasPrivilege(requiredPrivilege)) {
            throw new DqoAccessDeniedException(requiredPrivilege);
        }
    }
}
