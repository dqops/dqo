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
package ai.dqo.core.dqocloud.accesskey;

import ai.dqo.cloud.rest.api.AccessTokenIssueApi;
import ai.dqo.cloud.rest.handler.ApiClient;
import ai.dqo.cloud.rest.model.TenantAccessTokenModel;
import ai.dqo.core.dqocloud.client.DqoCloudApiClientFactory;
import ai.dqo.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * DQO Cloud bucket credentials provider.
 */
@Component
public class DqoCloudCredentialsProviderImpl implements DqoCloudCredentialsProvider {
    private DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Default injection constructor.
     * @param dqoCloudApiClientFactory DQO Cloud API Client factory.
     */
    @Autowired
    public DqoCloudCredentialsProviderImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Issues a tenant access token to access the bucket.
     * @param rootType Root type.
     * @return Tenant access token.
     */
    public TenantAccessTokenModel issueTenantAccessToken(DqoRoot rootType) {
        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient();
        AccessTokenIssueApi accessTokenIssueApi = new AccessTokenIssueApi(authenticatedClient);
        switch (rootType) {
            case data_sensor_readouts:
                return accessTokenIssueApi.issueBucketSensorReadoutsRWAccessToken();

            case data_rule_results:
                return accessTokenIssueApi.issueBucketRuleResultsRWAccessToken();

            case data_errors:
                return accessTokenIssueApi.issueBucketErrorsRWAccessToken();

            case data_statistics:
                return accessTokenIssueApi.issueBucketStatisticsRWAccessToken();

            case sources:
                return accessTokenIssueApi.issueBucketSourcesRWAccessToken();

            case sensors:
                return accessTokenIssueApi.issueBucketSensorsRWAccessToken();

            case rules:
                return accessTokenIssueApi.issueBucketRulesRWAccessToken();

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
