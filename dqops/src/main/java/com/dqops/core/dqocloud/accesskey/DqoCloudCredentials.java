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

import com.dqops.cloud.rest.model.TenantAccessTokenModel;
import com.google.auth.oauth2.AccessToken;

import java.time.Instant;

/**
 * Model object that stores the credentials model returned from the DQOps Cloud and a GCP API access token.
 */
public class DqoCloudCredentials {
    private TenantAccessTokenModel tenantAccessTokenModel;
    private AccessToken accessToken;
    private Instant createdAt = Instant.now();

    /**
     * Creates a pair of a full tenant access token (with the details of the google storage bucket) and a GCP API access token.
     * @param tenantAccessTokenModel Access token model with the storage bucket details.
     * @param accessToken Access token.
     */
    public DqoCloudCredentials(TenantAccessTokenModel tenantAccessTokenModel, AccessToken accessToken) {
        this.tenantAccessTokenModel = tenantAccessTokenModel;
        this.accessToken = accessToken;
    }

    /**
     * Returns the tentant's remote folder credentials model with the Google Storage bucket name and the path inside the bucket.
     * @return Tenant access token model.
     */
    public TenantAccessTokenModel getTenantAccessTokenModel() {
        return tenantAccessTokenModel;
    }

    /**
     * Returns the access token to be used in GCP API.
     * @return Access token.
     */
    public AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the timestamp when the object was created.
     * @return Timestamp when the object was created.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }
}
