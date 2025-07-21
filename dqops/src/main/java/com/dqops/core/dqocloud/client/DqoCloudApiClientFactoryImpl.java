/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
        if (apiKey == null) {
            return null; // authentication with dqops cloud is disabled
        }
        apiClient.setApiKey(apiKey.getApiKeyToken());
//        apiClient.setDebugging(true);

        return apiClient;
    }
}
