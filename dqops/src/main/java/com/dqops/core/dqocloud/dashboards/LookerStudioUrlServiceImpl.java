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
package com.dqops.core.dqocloud.dashboards;

import com.dqops.cloud.rest.api.AccessTokenIssueApi;
import com.dqops.cloud.rest.api.LookerStudioKeyRequestApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.TenantQueryAccessTokenModel;
import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.apikey.DqoCloudInvalidKeyException;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.metadata.dashboards.DashboardSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Service that creates authenticated URLS for Looker studio dashboards.
 */
@Service
public class LookerStudioUrlServiceImpl implements LookerStudioUrlService {
    private final DqoCloudApiClientFactory dqoCloudApiClientFactory;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private final Object lock = new Object();
    private String lookerStudioApiKey = null;
    private Instant lookerStudioApiKeyValidUntil = null;
    private TenantQueryAccessTokenModel queryAccessTokenModel = null;
    private Instant accessTokenValidUntil = null;

    /**
     * Dependency injection constructor that receives all required dependencies.
     * @param dqoCloudApiClientFactory Creates a rest api client for contacting the cloud.dqops.com using the API key.
     * @param dqoCloudApiKeyProvider DQO Cloud api key provider.
     * @param dqoCloudConfigurationProperties DQO Cloud configuration properties.
     */
    @Autowired
    public LookerStudioUrlServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory,
                                      DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                      DqoCloudConfigurationProperties dqoCloudConfigurationProperties) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
    }

    /**
     * Contacts the Cloud dqo server and issues a short-lived refresh token scoped to access data quality dashboards using Looker Studio.
     * @return API key scoped for accessing dashboards for the client's credentials.
     */
    @Override
    public String getLookerStudioQueryApiKey() {
        try {
            synchronized (this.lock) {
                if (this.lookerStudioApiKeyValidUntil != null && this.lookerStudioApiKeyValidUntil.isAfter(Instant.now())) {
                    return this.lookerStudioApiKey;
                }
            }

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient();
            LookerStudioKeyRequestApi lookerStudioKeyRequestApi = new LookerStudioKeyRequestApi(authenticatedClient);
            String queryApiKey = lookerStudioKeyRequestApi.issueLookerStudioApiKey();

            synchronized (this.lock) {
                DqoCloudApiKey decodedApiKey = this.dqoCloudApiKeyProvider.decodeApiKey(queryApiKey);
                this.lookerStudioApiKeyValidUntil = decodedApiKey.getApiKeyPayload().getExpiresAt().minus(10, ChronoUnit.MINUTES);
                this.lookerStudioApiKey = queryApiKey;
            }

            return queryApiKey;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQO shell to receive a current DQO Cloud API Key.");
            }

            throw new DqoRuntimeException("Failed to receive a refresh token from DQO Cloud", httpClientErrorException);
        }
    }

    /**
     * Returns a looker studio access token that could be sent to the connector to speed up setting up a connection to the quality data warehouse.
     * @return Looker studio access token model.
     */
    public TenantQueryAccessTokenModel getLookerStudioAccessToken() {
        String lookerStudioQueryApiKey = getLookerStudioQueryApiKey();

        try {
            synchronized (this.lock) {
                if (this.accessTokenValidUntil != null && this.accessTokenValidUntil.isAfter(Instant.now())) {
                    return this.queryAccessTokenModel;
                }
            }

            ApiClient apiClient = new ApiClient();
            apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
            apiClient.setApiKey(lookerStudioQueryApiKey);
            apiClient.getAuthentication("api_key");
            AccessTokenIssueApi accessTokenIssueApi = new AccessTokenIssueApi(apiClient);
            TenantQueryAccessTokenModel tenantQueryAccessTokenModel = accessTokenIssueApi.issueTenantDataROQueryAccessToken();

            Instant expiresAt = Instant.now().plus(1, ChronoUnit.HOURS);
            try {
                expiresAt = Instant.parse(tenantQueryAccessTokenModel.getExpiresAt());
            } catch (DateTimeParseException pe) {
                throw new DqoRuntimeException("Cannot parse expires at: " + tenantQueryAccessTokenModel.getExpiresAt(), pe);
            }

            synchronized (this.lock) {
                this.accessTokenValidUntil = expiresAt.minus(30, ChronoUnit.MINUTES);
                this.queryAccessTokenModel = tenantQueryAccessTokenModel;
            }

            return tenantQueryAccessTokenModel;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQO shell to receive a current DQO Cloud API Key.");
            }

            throw new DqoRuntimeException("Failed to receive an access token from DQO Cloud", httpClientErrorException);
        }
    }

    /**
     * Creates an authenticated URL for a looker studio dashboard.
     * @param dashboardSpec Dashboard specification.
     * @param dqoWindowLocationOrigin URL to the DQO instance (the window.location.origin value).
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    @Override
    public String makeAuthenticatedDashboardUrl(DashboardSpec dashboardSpec, String dqoWindowLocationOrigin) {
        String refreshToken = this.getLookerStudioQueryApiKey();
        TenantQueryAccessTokenModel lookerStudioAccessToken = this.getLookerStudioAccessToken();
        String token = refreshToken +
                ",t=" + lookerStudioAccessToken.getAccessToken() +
                ",p=" + lookerStudioAccessToken.getBillingProjectId() +
                ",d=" + lookerStudioAccessToken.getDataSetName() +
                ",e=" + this.accessTokenValidUntil.toEpochMilli();

        StringBuilder stringBuilder = new StringBuilder();
        String jsonParameters = formatDashboardParameters(dashboardSpec.getParameters(), token, dqoWindowLocationOrigin);
        String urlEncodedLookerStudioParameters = URLEncoder.encode(jsonParameters, StandardCharsets.UTF_8);
        stringBuilder.append(dashboardSpec.getUrl());
        stringBuilder.append("?params=");
        stringBuilder.append(urlEncodedLookerStudioParameters);
        String authenticatedUrl = stringBuilder.toString();

        return authenticatedUrl;
    }

    /**
     * Generates the Looker Studio JSON parameter string.
     * @param parameters Optional dictionary of key/value parameter pairs.
     * @param refreshToken Refresh token.
     * @param dqoWindowLocationOrigin URL to the DQO instance (the window.location.origin value).
     * @return JSON string to be submitted as a parameter.
     */
    public String formatDashboardParameters(Map<String, String> parameters, String refreshToken, String dqoWindowLocationOrigin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        boolean isFirst = true;
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, String> paramValuePair : parameters.entrySet()) {
                if (!isFirst) {
                    stringBuilder.append(',');
                }
                stringBuilder.append('"');
                stringBuilder.append(paramValuePair.getKey());
                stringBuilder.append('"');
                stringBuilder.append(':');
                stringBuilder.append('"');
                String parameterValue = paramValuePair.getValue()
                        .replace("%DQO_CLOUD_TOKEN%", refreshToken)
                        .replace("%DQO_WINDOW_LOCATION_ORIGIN%", dqoWindowLocationOrigin);
                stringBuilder.append(parameterValue);
                stringBuilder.append('"');
                isFirst = false;
            }
        }
        
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
