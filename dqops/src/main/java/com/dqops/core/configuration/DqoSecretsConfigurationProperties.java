/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
