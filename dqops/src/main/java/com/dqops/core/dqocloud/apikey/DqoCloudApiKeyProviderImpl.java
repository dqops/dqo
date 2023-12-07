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

import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.utils.serialization.JsonSerializer;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service that retrieves the active DQOps Cloud API key for the current user.
 * The api key could be enforced by setting an environment variable DQO_CLOUD_APIKEY or is stored in the settings
 * after the user executed the "login" CLI command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoCloudApiKeyProviderImpl implements DqoCloudApiKeyProvider {
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private UserHomeContextFactory userHomeContextFactory;
    private SecretValueProvider secretValueProvider;
    private JsonSerializer jsonSerializer;
    private Map<String, DqoCloudApiKey> cachedApiKey = new LinkedHashMap<>();
    private final Object lock = new Object();

    /**
     * Default injection constructor.
     * @param dqoCloudConfigurationProperties DQOps Cloud configuration properties.
     * @param userHomeContextFactory User home context factory - required to load the user settings.
     * @param secretValueProvider Secret value provider - used to resolve expressions in the settings.
     * @param jsonSerializer Json serializer - used to decode the API key.
     */
    @Autowired
    public DqoCloudApiKeyProviderImpl(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                      UserHomeContextFactory userHomeContextFactory,
                                      SecretValueProvider secretValueProvider,
                                      JsonSerializer jsonSerializer) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.userHomeContextFactory = userHomeContextFactory;
        this.secretValueProvider = secretValueProvider;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Returns the api key for the DQOps Cloud.
     * @param userIdentity User identity, used to find the data domain name for which we need the DQOps Cloud synchronization key.
     * @return DQOps Cloud api key or null when the key was not yet configured.
     */
    @Override
    public DqoCloudApiKey getApiKey(DqoUserIdentity userIdentity) {
        try {
            synchronized (this.lock) {
                DqoCloudApiKey cachedApiKeyPerDomain = userIdentity != null ? this.cachedApiKey.get(userIdentity.getDataDomain()) : null;

                if (cachedApiKeyPerDomain != null) {
                    return cachedApiKeyPerDomain;
                }

                DqoUserIdentity userIdentityForRootHome = userIdentity != null ? userIdentity : DqoUserIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY;
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentityForRootHome);
                SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
                LocalSettingsSpec localSettingsSpec = settingsWrapper.getSpec();
                String apiKey = null;

                if (localSettingsSpec != null) {
                    SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
                    LocalSettingsSpec settings = localSettingsSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
                    apiKey = settings.getApiKey();
                }

                if (Strings.isNullOrEmpty(apiKey)) {
                    apiKey = this.dqoCloudConfigurationProperties.getApiKey(); // take the api keys from configuration, it could be pulled from a secret manager or environment variables
                }

                if (Strings.isNullOrEmpty(apiKey)) {
                    return null;
                }

                DqoCloudApiKey dqoCloudApiKey = decodeApiKey(apiKey);
                String apiKeyDomain = dqoCloudApiKey.getApiKeyPayload().getDomain();
                if (Strings.isNullOrEmpty(apiKeyDomain)) {
                    apiKeyDomain = DqoUserIdentity.DEFAULT_DATA_DOMAIN;
                }
                this.cachedApiKey.put(apiKeyDomain, dqoCloudApiKey);
                return dqoCloudApiKey;
            }
        } catch (Exception e) {
            throw new DqoCloudInvalidKeyException("API Key is invalid", e);
        }
    }

    /**
     * Invalidates the cached api key.
     */
    @Override
    public void invalidate() {
        synchronized (this.lock) {
            this.cachedApiKey = null;
        }
    }

    /**
     * Decodes a given API Key.
     * @param apiKey API key to decode.
     * @return Decoded api key.
     * @throws DecoderException When the api key is invalid.
     */
    @NotNull
    @Override
    public DqoCloudApiKey decodeApiKey(String apiKey) {
        try {
            byte[] messageBytes = Hex.decodeHex(apiKey);
            byte[] payloadBytes = new byte[messageBytes.length - 32];
            System.arraycopy(messageBytes, 0, payloadBytes, 0, payloadBytes.length);

            String payloadJsonString = new String(payloadBytes, StandardCharsets.UTF_8);
            DqoCloudApiKeyPayload deserializedApiKeyPayload = this.jsonSerializer.deserialize(payloadJsonString, DqoCloudApiKeyPayload.class);

            DqoCloudApiKey dqoCloudApiKey = new DqoCloudApiKey(apiKey, deserializedApiKeyPayload);
            return dqoCloudApiKey;
        }
        catch (DecoderException ex) {
            throw new DqoCloudInvalidKeyException("Failed to decode a DQOps Cloud API Key, error: " + ex.getMessage(), ex);
        }
    }
}
