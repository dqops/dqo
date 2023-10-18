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
package com.dqops.core.dqocloud.client;

import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DQOps Cloud API client factory.
 */
@Component
public class DqoCloudApiClientFactoryImpl implements DqoCloudApiClientFactory {
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;

    /**
     * Default injection constructor.
     * @param dqoCloudConfigurationProperties DQOps Cloud configuration properties.
     * @param dqoCloudApiKeyProvider DQOps CLoud api key provider.
     */
    @Autowired
    public DqoCloudApiClientFactoryImpl(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                        DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
    }

    /**
     * Creates an unauthenticated API client for the DQOps Cloud.
     * @return Unauthenticated client.
     */
    public ApiClient createUnauthenticatedClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
        return apiClient;
    }

    /**
     * Creates an authenticated API client for the DQOps Cloud. The authentication uses the API Key that must be obtained by running the "login" CLI command.
     * @return Authenticated client.
     */
    public ApiClient createAuthenticatedClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
        apiClient.setApiKey(apiKey.getApiKeyToken());
//        apiClient.setDebugging(true);

        return apiClient;
    }
}
