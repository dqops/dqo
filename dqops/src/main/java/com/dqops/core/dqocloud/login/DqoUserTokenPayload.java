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

package com.dqops.core.dqocloud.login;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
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
    private LinkedHashMap<String, DqoUserRole> domainRoles = new LinkedHashMap<>();

    /**
     * User's role at the account level.
     */
    @JsonProperty("arl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DqoUserRole accountRole;

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
     * Create a user principal from the user token issued by DQOps Cloud.
     * @return User principal copied from the user token issued by DQOps Cloud.
     */
    public DqoUserPrincipal createUserPrincipal() {
        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(this.accountRole);
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(this.user, this.accountRole, grantedPrivileges, this);
        return dqoUserPrincipal;
    }

    /**
     * Creates a local DQOps instance authentication token from the DQOps Cloud API Key that identifies the instance and the main user.
     * @param cloudApiKey Cloud API key.
     * @return User token payload for a local authentication.
     */
    public static DqoUserTokenPayload createFromCloudApiKey(DqoCloudApiKeyPayload cloudApiKey) {
        DqoUserTokenPayload dqoUserTokenPayload = new DqoUserTokenPayload();
        dqoUserTokenPayload.setUser(cloudApiKey.getSubject());
        dqoUserTokenPayload.setExpiresAt(cloudApiKey.getExpiresAt());
        dqoUserTokenPayload.setDisposition(DqoUserAuthenticationTokenDisposition.API_KEY);
        dqoUserTokenPayload.setTenantId(cloudApiKey.getTenantId());
        dqoUserTokenPayload.setTenantGroup(cloudApiKey.getTenantGroup());
        dqoUserTokenPayload.setAccountRole(cloudApiKey.getAccountRole());

        return dqoUserTokenPayload;
    }
}
