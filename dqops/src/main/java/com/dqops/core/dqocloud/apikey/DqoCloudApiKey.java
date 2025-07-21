/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
