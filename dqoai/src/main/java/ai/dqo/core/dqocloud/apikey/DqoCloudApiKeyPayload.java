/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.dqocloud.apikey;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API key payload that is stored and signed inside an API key.
 */
public class DqoCloudApiKeyPayload {
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
    private CloudDqoApiKeyDisposition disposition = CloudDqoApiKeyDisposition.ALL_PURPOSES;

    @JsonProperty("exp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant expiresAt;

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

