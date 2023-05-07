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

import org.apache.commons.codec.DecoderException;
import org.jetbrains.annotations.NotNull;

/**
 * Service that retrieves the active DQO Cloud API key for the current user.
 * The api key could be enforced by setting an environment variable DQO_CLOUD_APIKEY or is stored in the settings
 * after the user executed the "login" CLI command.
 */
public interface DqoCloudApiKeyProvider {
    /**
     * Returns the api key for the DQO Cloud.
     * @return DQO Cloud api key or null when the key was not yet configured.
     */
    DqoCloudApiKey getApiKey();

    /**
     * Invalidates the cached api key.
     */
    void invalidate();

    /**
     * Decodes a given API Key.
     * @param apiKey API key to decode.
     * @return Decoded api key.
     * @throws DecoderException When the api key is invalid.
     */
    @NotNull
    DqoCloudApiKey decodeApiKey(String apiKey);
}
