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
package com.dqops.core.dqocloud.apikey;

import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * API key payload that is stored and signed inside an API key.
 */
public class DqoCloudApiKeyPayload {
    /**
     * API Key format version.
     */
    public static final long CURRENT_API_KEY_VERSION = 12;

    @JsonProperty("sub")
    private String subject;

    @JsonProperty("ver")
    private Long version;

    @JsonProperty("tid")
    private String tenantId;

    @JsonProperty("tg")
    private int tenantGroup;

    @JsonProperty("ac")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountName;

    @JsonProperty("idp")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean idpTenant;

    @JsonProperty("lic")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DqoCloudLicenseType licenseType;

    @JsonProperty("lm")
    private Map<DqoCloudLimit, Integer> limits = new HashMap<>();

    @JsonProperty("dp")
    private CloudDqoApiKeyDisposition disposition;

    @JsonProperty("exp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant expiresAt;

    @JsonProperty("dm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String domain;

    @JsonProperty("reg")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String region;

    @JsonProperty("arl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DqoUserRole accountRole = DqoUserRole.ADMIN;

    /**
     * Collection of ignored properties that were present in the YAML specification file, but were not present on the node.
     * The user has added invalid properties. We only want to know the names of these properties for validation purposes.
     */
    @JsonIgnore
    private Map<String, Object> ignoredProperties;


    public DqoCloudApiKeyPayload() {
    }

    public DqoCloudApiKeyPayload(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Returns the subject (the user email) for whom the API Key was issues.
     * @return Subject (user email).
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject (the user email) for whom the API Key was issues.
     * @param subject New subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the DQOps Cloud API Key version.
     * @return DQOps Cloud API Key version.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the DQOps Cloud API Key version.
     * @param version API Key version.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Returns the tenant id.
     * @return Tenant id.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the tenant id.
     * @param tenantId Tenant id.
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Returns the tenant group id.
     * @return Tenant group id.
     */
    public int getTenantGroup() {
        return tenantGroup;
    }

    /**
     * Sets the tenant group id.
     * @param tenantGroup Tenant group id.
     */
    public void setTenantGroup(int tenantGroup) {
        this.tenantGroup = tenantGroup;
    }

    /**
     * Returns the account name for personal, team and enterprise accounts.
     * @return Account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the account name for persona, team and enterprise accounts.
     * @param accountName Account name.
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Returns true if the account supports managing additional users.
     * @return True when additional users could be added to the account, false when it is a free standalone account.
     */
    public boolean isIdpTenant() {
        return idpTenant;
    }

    /**
     * Sets a boolean flag to identify accounts that are capable of user management.
     * @param idpTenant True when the account supports additional users (even if the limit is 1 user).
     */
    public void setIdpTenant(boolean idpTenant) {
        this.idpTenant = idpTenant;
    }

    /**
     * Returns the license type.
     * @return License type.
     */
    public DqoCloudLicenseType getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the license type.
     * @param licenseType License type.
     */
    public void setLicenseType(DqoCloudLicenseType licenseType) {
        this.licenseType = licenseType;
    }

    /**
     * Returns a dictionary of limits.
     * @return Dictionary of limits.
     */
    public Map<DqoCloudLimit, Integer> getLimits() {
        return limits;
    }

    /**
     * Sets a dictionary of limits.
     * @param limits Dictionary of limits.
     */
    public void setLimits(Map<DqoCloudLimit, Integer> limits) {
        this.limits = limits;
    }

    /**
     * Api key disposition - the purpose of the key, that will limit possible usages.
     * @return API Key usage limits.
     */
    public CloudDqoApiKeyDisposition getDisposition() {
        return disposition;
    }

    /**
     * Sets the API Key usage limits (disposition).
     * @param disposition New api key disposition (purpose).
     */
    public void setDisposition(CloudDqoApiKeyDisposition disposition) {
        this.disposition = disposition;
    }

    /**
     * Returns the expiration timestamp for a short lived api key.
     * @return Expiration timestamp.
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * Sets the expiration time for a short lived api key.
     * @param expiresAt Expires at.
     */
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Returns the domain name when the data for a tenant is stored in separate domains. The api key enables access to a single domain.
     * @return Domain name or null when the default domain is used.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the name of the data domain when the data for a separate domain is in use.
     * @param domain Domain name or null when the default domain is used.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Returns the region where the tenant's data is stored. It is a name of a GCP region.
     * @return GCP region where the tenant's data is stored.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the name of a GCP region where the tenant's data is stored.
     * @param region The region where the tenant's data is stored.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns the role of the user at the account (tenant) level.
     * @return The role of the user at the account level.
     */
    public DqoUserRole getAccountRole() {
        return accountRole;
    }

    /**
     * Sets the role of the user at the account level.
     * @param accountRole User's role at the account level.
     */
    public void setAccountRole(DqoUserRole accountRole) {
        this.accountRole = accountRole;
    }

    /**
     * Called by Jackson property when an undeclared property was present in the deserialized YAML or JSON text.
     * @param name Undeclared (and ignored) property name.
     * @param value Property value.
     */
    @JsonAnySetter
    public void handleUndeclaredProperty(String name, Object value) {
        if (this.ignoredProperties == null) {
            this.ignoredProperties = new LinkedHashMap<>();
        }
        this.ignoredProperties.put(name, value);
    }

    /**
     * Create a user principal from the api key.
     * @return User principal copied from the user.
     */
    public DqoUserPrincipal createUserPrincipal() {
        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(this.accountRole);
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(this.subject, this.accountRole, grantedPrivileges,this);
        return dqoUserPrincipal;
    }
}

