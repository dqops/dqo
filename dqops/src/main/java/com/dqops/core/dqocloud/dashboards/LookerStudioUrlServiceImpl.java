/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.UserDomainIdentity;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service that creates authenticated URLS for Looker studio dashboards.
 */
@Service
public class LookerStudioUrlServiceImpl implements LookerStudioUrlService {
    private final DqoCloudApiClientFactory dqoCloudApiClientFactory;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private final Object lock = new Object();
    private final Map<String, String> lookerStudioApiKeyPerDomain = new LinkedHashMap<>();
    private final Map<String, Instant> lookerStudioApiKeyValidUntilPerDomain = new LinkedHashMap<>();
    private final Map<String, TenantQueryAccessTokenModel> queryAccessTokenModelPerDomain = new LinkedHashMap<>();
    private final Map<String, Instant> accessTokenValidUntilPerDomain = new LinkedHashMap<>();

    /**
     * Dependency injection constructor that receives all required dependencies.
     * @param dqoCloudApiClientFactory Creates a rest api client for contacting the cloud.dqops.com using the API key.
     * @param dqoCloudApiKeyProvider DQOps Cloud api key provider.
     * @param dqoCloudConfigurationProperties DQOps Cloud configuration properties.
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
     * Contacts the DQOps Cloud server and issues a short-lived refresh token scoped to access data quality dashboards using Looker Studio.
     * @param userPrincipal Calling user principal, identifies the data domain.
     * @return API key scoped for accessing dashboards for the client's credentials.
     */
    @Override
    public String getLookerStudioQueryApiKey(DqoUserPrincipal userPrincipal) {
        try {
            String dataDomainCloud = userPrincipal.getDataDomainIdentity().getDataDomainCloud();

            synchronized (this.lock) {
                Instant lookerStudioApiKeyValidUntil = this.lookerStudioApiKeyValidUntilPerDomain.get(dataDomainCloud);

                if (lookerStudioApiKeyValidUntil != null && lookerStudioApiKeyValidUntil.isAfter(Instant.now())) {
                    return this.lookerStudioApiKeyPerDomain.get(dataDomainCloud);
                }
            }

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getDataDomainIdentity());
            if (authenticatedClient == null) {
                throw new DqoCloudInvalidKeyException("Invalid DQOps Cloud Pairing API Key or synchronization with DQOps Cloud is disabled, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud Pairing API Key.");
            }

            LookerStudioKeyRequestApi lookerStudioKeyRequestApi = new LookerStudioKeyRequestApi(authenticatedClient);
            String queryApiKey = lookerStudioKeyRequestApi.issueLookerStudioApiKey(dataDomainCloud,
                    userPrincipal.getDataDomainIdentity().getTenantOwner(), userPrincipal.getDataDomainIdentity().getTenantId());

            synchronized (this.lock) {
                DqoCloudApiKey decodedApiKey = this.dqoCloudApiKeyProvider.decodeApiKey(queryApiKey);
                this.lookerStudioApiKeyValidUntilPerDomain.put(dataDomainCloud, decodedApiKey.getApiKeyPayload().getExpiresAt().minus(10, ChronoUnit.MINUTES));
                this.lookerStudioApiKeyPerDomain.put(dataDomainCloud,  queryApiKey);
            }

            return queryApiKey;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid DQOps Cloud Pairing API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud Pairing API Key.");
            }

            throw new DqoRuntimeException("Failed to receive a refresh token from DQOps Cloud", httpClientErrorException);
        }
    }

    /**
     * Returns a looker studio access token that can be sent to the connector to speed up configuration of the connection to the quality data warehouse.
     * @param userPrincipal Calling user principal, identifies the data domain.
     * @return Looker studio access token model.
     */
    public TenantQueryAccessTokenModel getLookerStudioAccessToken(DqoUserPrincipal userPrincipal) {
        String lookerStudioQueryApiKey = getLookerStudioQueryApiKey(userPrincipal);

        try {
            String dataDomainCloud = userPrincipal.getDataDomainIdentity().getDataDomainCloud();

            synchronized (this.lock) {
                Instant accessTokenValidUntil = this.accessTokenValidUntilPerDomain.get(dataDomainCloud);

                if (accessTokenValidUntil != null && accessTokenValidUntil.isAfter(Instant.now())) {
                    return this.queryAccessTokenModelPerDomain.get(dataDomainCloud);
                }
            }

            ApiClient apiClient = new ApiClient();
            apiClient.setBasePath(this.dqoCloudConfigurationProperties.getRestApiBaseUrl());
            apiClient.setApiKey(lookerStudioQueryApiKey);
            apiClient.getAuthentication("api_key");
            AccessTokenIssueApi accessTokenIssueApi = new AccessTokenIssueApi(apiClient);
            TenantQueryAccessTokenModel tenantQueryAccessTokenModel = accessTokenIssueApi.issueTenantDataROQueryAccessToken(
                    Objects.equals(UserDomainIdentity.ROOT_DATA_DOMAIN, dataDomainCloud) ? null : dataDomainCloud,
                    userPrincipal.getDataDomainIdentity().getTenantOwner(), userPrincipal.getDataDomainIdentity().getTenantId());

            Instant expiresAt = Instant.now().plus(1, ChronoUnit.HOURS);
            try {
                expiresAt = Instant.parse(tenantQueryAccessTokenModel.getExpiresAt());
            } catch (DateTimeParseException pe) {
                throw new DqoRuntimeException("Cannot parse expires at: " + tenantQueryAccessTokenModel.getExpiresAt(), pe);
            }

            synchronized (this.lock) {
                this.accessTokenValidUntilPerDomain.put(dataDomainCloud, expiresAt.minus(30, ChronoUnit.MINUTES));
                this.queryAccessTokenModelPerDomain.put(dataDomainCloud, tenantQueryAccessTokenModel);
            }

            return tenantQueryAccessTokenModel;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoRuntimeException("Failed to receive an access token from DQOps Cloud", httpClientErrorException);
        }
    }

    /**
     * Creates an authenticated URL for a looker studio dashboard.
     * @param dashboardSpec Dashboard specification.
     * @param dqoWindowLocationOrigin URL to the DQOps instance (the window.location.origin value).
     * @param userPrincipal Calling user principal, identifies the data domain.
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    @Override
    public String makeAuthenticatedDashboardUrl(DashboardSpec dashboardSpec, String dqoWindowLocationOrigin, DqoUserPrincipal userPrincipal) {
        String refreshToken = this.getLookerStudioQueryApiKey(userPrincipal);
        TenantQueryAccessTokenModel lookerStudioAccessToken = this.getLookerStudioAccessToken(userPrincipal);
        String dataDomainCloud = userPrincipal.getDataDomainIdentity().getDataDomainCloud();
        String token = refreshToken +
                ",t=" + lookerStudioAccessToken.getAccessToken() +
                ",p=" + lookerStudioAccessToken.getBillingProjectId() +
                ",d=" + lookerStudioAccessToken.getDataSetName() +
                ",e=" + this.accessTokenValidUntilPerDomain.get(dataDomainCloud).toEpochMilli();

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
     * @param dqoWindowLocationOrigin URL to the DQOps instance (the window.location.origin value).
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
