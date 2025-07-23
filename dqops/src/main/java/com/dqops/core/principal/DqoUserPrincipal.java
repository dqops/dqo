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

import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.login.DqoUserAuthenticationTokenDisposition;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * DQOps user principal that identifies the user.
 */
public class DqoUserPrincipal {
    /**
     * Name used for an unauthenticated principal.
     */
    public static final String UNAUTHENTICATED_PRINCIPAL_NAME = "";

    private final DqoUserRole accountRole;
    private final UserDomainIdentity dataDomainIdentity;
    private DqoCloudApiKeyPayload apiKeyPayload;
    private DqoUserTokenPayload userTokenPayload;
    private Collection<GrantedAuthority> privileges;
    private LinkedHashMap<String, DqoUserRole> domainRoles;


    public DqoUserPrincipal(String dataDomainFolder, String dataDomainCloud) {
        this.accountRole = DqoUserRole.NONE;
        this.privileges = Collections.unmodifiableList(new ArrayList<>());
        this.dataDomainIdentity = new UserDomainIdentity(UNAUTHENTICATED_PRINCIPAL_NAME, DqoUserRole.NONE, dataDomainFolder, dataDomainCloud, null, null, null);
    }

    /**
     * Creates a user principal.
     * @param name User's email (principal name).
     * @param accountRole Account role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     * @param dataDomainFolder The data domain folder name.
     * @param dataDomainCloud The real data domain on DQOps cloud that is mounted.
     * @param tenantOwner Tenant owner's email.
     * @param tenantId Tenant id.
     * @param tenantGroupId Tenant group id.
     * @param domainRoles Roles in other data domains.
     */
    public DqoUserPrincipal(String name,
                            DqoUserRole accountRole,
                            Collection<GrantedAuthority> privileges,
                            String dataDomainFolder,
                            String dataDomainCloud,
                            String tenantOwner,
                            String tenantId,
                            Integer tenantGroupId,
                            LinkedHashMap<String, DqoUserRole> domainRoles) {
        this.accountRole = accountRole;
        this.privileges = privileges;
        this.domainRoles = domainRoles;
        this.dataDomainIdentity = new UserDomainIdentity(name, accountRole, dataDomainFolder, dataDomainCloud, tenantOwner, tenantId, tenantGroupId);
    }

    /**
     * Creates a principal from an api key payload.
     * @param name Principal name (login).
     * @param accountRole Account level role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     * @param apiKeyPayload Source DQOps Cloud Api key payload.
     * @param dataDomainFolder The data domain folder name.
     * @param dataDomainCloud The real data domain on DQOps cloud that is mounted.
     * @param domainRoles Roles in other data domains.
     */
    public DqoUserPrincipal(String name,
                            DqoUserRole accountRole,
                            Collection<GrantedAuthority> privileges,
                            DqoCloudApiKeyPayload apiKeyPayload,
                            String dataDomainFolder,
                            String dataDomainCloud,
                            LinkedHashMap<String, DqoUserRole> domainRoles) {
        this(name, accountRole, privileges, dataDomainFolder, dataDomainCloud,
                apiKeyPayload != null ? apiKeyPayload.getSubject() : null,
                apiKeyPayload != null ? apiKeyPayload.getTenantId() : null,
                apiKeyPayload != null ? apiKeyPayload.getTenantGroup() : null,
                domainRoles);
        this.apiKeyPayload = apiKeyPayload;
    }

    /**
     * Creates a principal from a user's identity token issued by DQOps Cloud for multi-user environments.
     * @param name Principal name (login).
     * @param accountRole Account level role.
     * @param privileges Collection of privileges (permissions) granted to the principal.
     * @param userTokenPayload Source DQOps Cloud user's identity token payload.
     * @param dataDomainFolder The data domain folder name.
     * @param dataDomainCloud The real data domain on DQOps cloud that is mounted.
     * @param tenantId Tenant id.
     * @param tenantGroupId Tenant group id.
     * @param domainRoles Roles in other data domains.
     */
    public DqoUserPrincipal(String name,
                            DqoUserRole accountRole,
                            Collection<GrantedAuthority> privileges,
                            DqoUserTokenPayload userTokenPayload,
                            String dataDomainFolder,
                            String dataDomainCloud,
                            String tenantId,
                            Integer tenantGroupId,
                            LinkedHashMap<String, DqoUserRole> domainRoles) {
        this(name, accountRole, privileges, dataDomainFolder, dataDomainCloud, name, tenantId, tenantGroupId, domainRoles);
        this.userTokenPayload = userTokenPayload;
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
     * Returns the DQOps Cloud Api key for which the principal was issued.
     * @return The API Key payload or null when the principal was not issued from an API Key.
     */
    public DqoCloudApiKeyPayload getApiKeyPayload() {
        return apiKeyPayload;
    }

    /**
     * Returns the DQOps Cloud user token from which the principal is issued. DQOps Cloud user tokens are used to authenticate users in a multi-user environment.
     * @return DQOps Cloud user token from which the principal was issued or null, when the principal was not issued from an identity token.
     */
    public DqoUserTokenPayload getUserTokenPayload() {
        return userTokenPayload;
    }

    /**
     * Returns a dictionary of roles for different data domains.
     * @return List of roles in all data domains.
     */
    public LinkedHashMap<String, DqoUserRole> getDomainRoles() {
        return domainRoles;
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

    /**
     * Returns a string representation of the user and his role.
     * @return User and role.
     */
    @Override
    public String toString() {
        return this.dataDomainIdentity.getUserName() + " (" + accountRole + ")";
    }

    /**
     * Creates a user identity object that identifies the user and the current data domain. Returns also the role within the data domain.
     * @return User identity and the active data domain name.
     */
    public UserDomainIdentity getDataDomainIdentity() {
        return this.dataDomainIdentity;
    }
}
