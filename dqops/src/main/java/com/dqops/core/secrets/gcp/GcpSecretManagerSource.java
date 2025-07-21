/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.secrets.gcp;

import com.dqops.core.configuration.DqoSecretsConfigurationProperties;
import com.dqops.core.secrets.CurrentSecretValueLookupContext;
import com.dqops.core.secrets.SecretExpandFailedException;
import com.dqops.core.secrets.SecretValueProvider;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretManagerServiceSettings;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Property source that reads secrets from the GCP Secret Manager.
 */
@Component
public class GcpSecretManagerSource {
    private static final Logger LOG = LoggerFactory.getLogger(GcpSecretManagerSource.class);

    private final DqoSecretsConfigurationProperties secretsConfigurationProperties;
    private SecretManagerServiceClient secretManagerServiceClient;

    @Autowired
    @Lazy
    private SecretValueProvider secretValueProvider;

    /**
     * Default injection constructor.
     * @param secretsConfigurationProperties Settings provider.
     */
    @Autowired
    public GcpSecretManagerSource(DqoSecretsConfigurationProperties secretsConfigurationProperties) {
        this.secretsConfigurationProperties = secretsConfigurationProperties;
    }

    /**
     * Returns or creates (on the first call) a GCP Secret Manager client.
     * @return GCP Secret manager client.
     */
    public synchronized SecretManagerServiceClient getSecretManagerClient() {
        try {
            if (this.secretManagerServiceClient == null) {
                SecretManagerServiceSettings.Builder settingsBuilder = SecretManagerServiceSettings.newBuilder();
                SecretManagerServiceSettings serviceSettings = settingsBuilder.build();
				this.secretManagerServiceClient = SecretManagerServiceClient.create(serviceSettings);
            }

            return this.secretManagerServiceClient;
        }
        catch (Exception ex) {
            throw new SecretExpandFailedException("Cannot initialize access to GCP Secret Manager", ex);
        }
    }

    /**
     * Retrieves a secret from the GCP secret manager.
     * @param propertyName Property name.
     * @return Property value (secret).
     */
    public Object getProperty(String propertyName) {
        int indexOfDefaultValue = propertyName.indexOf(':');
        final String corePropertyName = (indexOfDefaultValue > 0) ?
                propertyName.substring(0, indexOfDefaultValue) : propertyName;
        final String defaultValue = (indexOfDefaultValue > 0) ?
                propertyName.substring(indexOfDefaultValue + 1) : null;

        if (this.secretsConfigurationProperties.isEnableGcpSecretManager()) {
            try {
                SecretManagerServiceClient secretManagerClient = getSecretManagerClient();
                SecretVersionName secretVersionName = SecretVersionName.newBuilder()
                        .setProject(this.secretsConfigurationProperties.getGcpProjectId())
                        .setSecret(corePropertyName)
                        .setSecretVersion("latest")
                        .build();
                AccessSecretVersionResponse accessSecretVersionResponse = secretManagerClient.accessSecretVersion(secretVersionName);
                return accessSecretVersionResponse.getPayload().getData().toStringUtf8();
            }
            catch (Exception ex) {
				LOG.warn("Cannot find a GCP secret " + corePropertyName, ex);
                return this.secretValueProvider.expandValue(defaultValue, CurrentSecretValueLookupContext.getCurrentLookupContext());
            }
        }
        else {
            return this.secretValueProvider.expandValue(defaultValue, CurrentSecretValueLookupContext.getCurrentLookupContext());
        }
    }
}
