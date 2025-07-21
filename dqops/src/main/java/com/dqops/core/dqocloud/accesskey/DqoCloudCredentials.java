/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
