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

import com.dqops.rest.server.authentication.DqoRoleNames;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @JsonProperty("lic")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String licenseType;

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

    @JsonProperty("rl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role = DqoRoleNames.ADMIN;

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
     * Returns the DQO Cloud API Key version.
     * @return DQO Cloud API Key version.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the DQO Cloud API Key version.
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
     * Returns the license type.
     * @return License type.
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the license type.
     * @param licenseType License type.
     */
    public void setLicenseType(String licenseType) {
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
     * Returns the role of the user. Roles are defined in {@link com.dqops.rest.server.authentication.DqoRoleNames} class.
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * @param role User role.
     */
    public void setRole(String role) {
        this.role = role;
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
}

