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
import com.dqops.core.principal.UserDomainIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * DQOps Cloud API client factory.
 */
@Component
public class DqoCloudApiClientFactoryImpl implements DqoCloudApiClientFactory {
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private RestTemplate restTemplate;

    /**
     * Default injection constructor.
     * @param dqoCloudConfigurationProperties DQOps Cloud configuration properties.
     * @param dqoCloudApiKeyProvider DQOps Cloud api key provider.
     * @param restTemplate Rest template instance.
     */
    @Autowired
    public DqoCloudApiClientFactoryImpl(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                        DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                        RestTemplate restTemplate) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.restTemplate = restTemplate;
    }

    /**
     * Creates an unauthenticated API client for the DQOps Cloud.
     * @return Unauthenticated client.
     */
    @Override
    public ApiClient createUnauthenticatedClient() {
        ApiClient apiClient = new ApiClient(this.restTemplate);
        apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
        return apiClient;
    }

    /**
     * Creates an authenticated API client for the DQOps Cloud. The authentication uses the API Key that must be obtained by running the "login" CLI command.
     * @param userIdentity Calling user identity, required to select the data domain.
     * @return Authenticated client.
     */
    @Override
    public ApiClient createAuthenticatedClient(UserDomainIdentity userIdentity) {
        ApiClient apiClient = new ApiClient(this.restTemplate);
        apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(userIdentity);
        apiClient.setApiKey(apiKey.getApiKeyToken());
//        apiClient.setDebugging(true);

        return apiClient;
    }
}
