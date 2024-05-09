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
package com.dqops.core.dqocloud.accesskey;

import com.dqops.cloud.rest.api.AccessTokenIssueApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.TenantAccessTokenModel;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * DQOps Cloud bucket credentials provider.
 */
@Component
public class DqoCloudCredentialsProviderImpl implements DqoCloudCredentialsProvider {
    private DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Default injection constructor.
     * @param dqoCloudApiClientFactory DQOps Cloud API Client factory.
     */
    @Autowired
    public DqoCloudCredentialsProviderImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Issues a tenant access token to access the bucket.
     * @param rootType Root type.
     * @param userIdentity Calling user identity.
     * @return Tenant access token.
     */
    @Override
    public TenantAccessTokenModel issueTenantAccessToken(DqoRoot rootType, UserDomainIdentity userIdentity) {
        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userIdentity);
        AccessTokenIssueApi accessTokenIssueApi = new AccessTokenIssueApi(authenticatedClient);
        String tenantIdFull = userIdentity.getTenantId() + "/" + userIdentity.getTenantGroupId();
        switch (rootType) {
            case data_sensor_readouts:
                return accessTokenIssueApi.issueBucketSensorReadoutsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case data_check_results:
                return accessTokenIssueApi.issueBucketCheckResultsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case data_errors:
                return accessTokenIssueApi.issueBucketErrorsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case data_statistics:
                return accessTokenIssueApi.issueBucketStatisticsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case data_incidents:
                return accessTokenIssueApi.issueBucketIncidentsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case sources:
                return accessTokenIssueApi.issueBucketSourcesRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case sensors:
                return accessTokenIssueApi.issueBucketSensorsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case rules:
                return accessTokenIssueApi.issueBucketRulesRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case checks:
                return accessTokenIssueApi.issueBucketChecksRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case settings:
                return accessTokenIssueApi.issueBucketSettingsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case credentials:
                return accessTokenIssueApi.issueBucketCredentialsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case dictionaries:
                return accessTokenIssueApi.issueBucketDictionariesRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            case patterns:
                return accessTokenIssueApi.issueBucketPatternsRWAccessToken(userIdentity.getDataDomainCloud(), userIdentity.getTenantOwner(), tenantIdFull);

            default:
                throw new RuntimeException("Unknown root: " + rootType);
        }
    }

    /**
     * Creates an OAuth2 access token.
     * @param tenantAccessTokenModel Tenant access token model that was returned from the engine.
     * @return Access token.
     */
    public AccessToken createAccessToken(TenantAccessTokenModel tenantAccessTokenModel) {
        Instant accessTokenExpiresAt = Instant.parse(tenantAccessTokenModel.getExpiresAt());
        Instant expiresAtWithMargin = accessTokenExpiresAt.minus(1, ChronoUnit.MINUTES);
        Date expirationDate = new Date(expiresAtWithMargin.toEpochMilli());
        return new AccessToken(tenantAccessTokenModel.getAccessToken(), expirationDate);
    }
}
