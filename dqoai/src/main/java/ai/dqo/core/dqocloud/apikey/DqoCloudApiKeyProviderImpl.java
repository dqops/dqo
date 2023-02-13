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

import ai.dqo.core.configuration.DqoCloudConfigurationProperties;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.settings.SettingsSpec;
import ai.dqo.metadata.settings.SettingsWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.utils.serialization.JsonSerializer;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Service that retrieves the active DQO Cloud API key for the current user.
 * The api key could be enforced by setting an environment variable DQO_CLOUD_APIKEY or is stored in the settings
 * after the user executed the "login" CLI command.
 */
@Component
public class DqoCloudApiKeyProviderImpl implements DqoCloudApiKeyProvider {
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private UserHomeContextFactory userHomeContextFactory;
    private SecretValueProvider secretValueProvider;
    private JsonSerializer jsonSerializer;

    /**
     * Default injection constructor.
     * @param dqoCloudConfigurationProperties DQO Cloud configuration properties.
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
     * Returns the api key for the DQO Cloud.
     * @return DQO Cloud api key or null when the key was not yet configured.
     */
    public DqoCloudApiKey getApiKey() {
        try {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
            SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
            SettingsSpec settingsSpec = settingsWrapper.getSpec();
            String apiKey = null;

            if (settingsSpec != null) {
                SettingsSpec settings = settingsSpec.expandAndTrim(this.secretValueProvider);
                apiKey = settings.getApiKey();
            }

            if (Strings.isNullOrEmpty(apiKey)) {
                apiKey = this.dqoCloudConfigurationProperties.getApiKey(); // take the api keys from configuration, it could be pulled from a secret manager or environment variables
            }

            if (Strings.isNullOrEmpty(apiKey)) {
                return null;
            }

            DqoCloudApiKey dqoCloudApiKey = decodeApiKey(apiKey);
            return dqoCloudApiKey;
        } catch (DecoderException e) {
            throw new DqoCloudInvalidKeyException("API Key is invalid", e);
        }
    }

    /**
     * Decodes a given API Key.
     * @param apiKey API key to decode.
     * @return Decoded api key.
     * @throws DecoderException When the api key is invalid.
     */
    @NotNull
    public DqoCloudApiKey decodeApiKey(String apiKey) throws DecoderException {
        byte[] messageBytes = Hex.decodeHex(apiKey);
        byte[] payloadBytes = new byte[messageBytes.length - 32];
        System.arraycopy(messageBytes, 0, payloadBytes, 0, payloadBytes.length);

        String payloadJsonString = new String(payloadBytes, StandardCharsets.UTF_8);
        DqoCloudApiKeyPayload deserializedApiKeyPayload = this.jsonSerializer.deserialize(payloadJsonString, DqoCloudApiKeyPayload.class);

        DqoCloudApiKey dqoCloudApiKey = new DqoCloudApiKey(apiKey, deserializedApiKeyPayload);
        return dqoCloudApiKey;
    }
}
