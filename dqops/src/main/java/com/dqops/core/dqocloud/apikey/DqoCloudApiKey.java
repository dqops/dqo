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

/**
 * DQOps Cloud API key, both as a token and parsed.
 */
public class DqoCloudApiKey {
    private String apiKeyToken;
    private DqoCloudApiKeyPayload apiKeyPayload;

    /**
     * Creates an API Key holder.
     * @param apiKeyToken API Key used as a Bearer token in the Authentication header.
     * @param apiKeyPayload API Key payload.
     */
    public DqoCloudApiKey(String apiKeyToken, DqoCloudApiKeyPayload apiKeyPayload) {
        this.apiKeyToken = apiKeyToken;
        this.apiKeyPayload = apiKeyPayload;
    }

    /**
     * Gets the API Key used as a Bearer token in the Authentication header.
     * @return API Key.
     */
    public String getApiKeyToken() {
        return apiKeyToken;
    }

    /**
     * Decoded API key.
     * @return Decoded API Key.
     */
    public DqoCloudApiKeyPayload getApiKeyPayload() {
        return apiKeyPayload;
    }
}
