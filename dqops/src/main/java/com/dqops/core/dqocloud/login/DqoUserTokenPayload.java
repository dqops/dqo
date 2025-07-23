/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.login;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DQOps login token payload returned by the DQOp Cloud or issued locally.
 */
@Data
public class DqoUserTokenPayload implements Cloneable {
    /**
     * The login (email) of the user.
     */
    @JsonPropertyDescription("The login (email) of the user.")
    @JsonProperty("usr")
    private String user;

    /**
     * Tenant id.
     */
    @JsonPropertyDescription("Tenant id.")
    @JsonProperty("tid")
    private String tenantId;

    /**
     * Tenant group.
     */
    @JsonPropertyDescription("Tenant group.")
    @JsonProperty("tg")
    private int tenantGroup;

    /**
     * The key purpose.
     */
    @JsonPropertyDescription("The key purpose.")
    @JsonProperty("dp")
    private DqoUserAuthenticationTokenDisposition disposition;

    /**
     * The expiration date (UTC) of the key.
     */
    @JsonPropertyDescription("The expiration date (UTC) of the key.")
    @JsonProperty("exp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant expiresAt;

    /**
     * The user roles within the domains.
     */
    @JsonProperty("dr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<String, DqoUserRole> domainRoles;

    /**
     * User's role at the account level.
     */
    @JsonProperty("arl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DqoUserRole accountRole;

    /**
     * Active (selected) data domain.
     */
    @JsonProperty("dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String activeDataDomain;

    public DqoUserTokenPayload() {
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DqoUserTokenPayload clone() {
        try {
            DqoUserTokenPayload cloned = (DqoUserTokenPayload) super.clone();
            if (cloned.domainRoles != null) {
                cloned.domainRoles = (LinkedHashMap<String, DqoUserRole>) cloned.domainRoles.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a local DQOps instance authentication token from the DQOps Cloud API Key that identifies the instance and the main user.
     * @param cloudApiKey Cloud API key.
     * @param userDomainIdentity User domain identity, to select the data domain.
     * @return User token payload for a local authentication.
     */
    public static DqoUserTokenPayload createFromCloudApiKey(DqoCloudApiKeyPayload cloudApiKey, UserDomainIdentity userDomainIdentity) {
        DqoUserTokenPayload dqoUserTokenPayload = new DqoUserTokenPayload();
        dqoUserTokenPayload.setUser(cloudApiKey.getSubject());
        dqoUserTokenPayload.setExpiresAt(cloudApiKey.getExpiresAt());
        dqoUserTokenPayload.setDisposition(DqoUserAuthenticationTokenDisposition.API_KEY);
        dqoUserTokenPayload.setTenantId(cloudApiKey.getTenantId());
        dqoUserTokenPayload.setTenantGroup(cloudApiKey.getTenantGroup());
        dqoUserTokenPayload.setAccountRole(cloudApiKey.getAccountRole());
        dqoUserTokenPayload.setActiveDataDomain(userDomainIdentity.getDataDomainCloud());

        return dqoUserTokenPayload;
    }
}
