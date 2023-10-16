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
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps. Properties are mapped to the "dqo.secrets." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.secrets")
@EqualsAndHashCode(callSuper = false)
public class DqoSecretsConfigurationProperties implements Cloneable {
    private String gcpProjectId;
    private boolean enableGcpSecretManager;

    /**
     * GCP project ID used to access the GCP Secret Manager.
     * @return GCP Project id.
     */
    public String getGcpProjectId() {
        return gcpProjectId;
    }

    /**
     * Sets the GCP project ID.
     * @param gcpProjectId GCP project id.
     */
    public void setGcpProjectId(String gcpProjectId) {
        this.gcpProjectId = gcpProjectId;
    }

    /**
     * Checks if the GCP Secret Manager is enabled.
     * @return GCP Secret manager is enabled.
     */
    public boolean isEnableGcpSecretManager() {
        return enableGcpSecretManager;
    }

    /**
     * Sets a flag to enable/disable the GCP Secret Manager as a property source.
     * @param enableGcpSecretManager Enable/disable.
     */
    public void setEnableGcpSecretManager(boolean enableGcpSecretManager) {
        this.enableGcpSecretManager = enableGcpSecretManager;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoSecretsConfigurationProperties clone() {
        try {
            return (DqoSecretsConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
