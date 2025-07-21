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
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;

/**
 * DQOps Cloud bucket credentials provider.
 */
public interface DqoCloudCredentialsProvider {
    /**
     * Issues a downscoped access token to access the bucket.
     * @param rootType Root type.
     * @param userIdentity Calling user identity.
     * @return Downscoped access token.
     */
    TenantAccessTokenModel issueTenantAccessToken(DqoRoot rootType, UserDomainIdentity userIdentity);

    /**
     * Creates an OAuth2 access token.
     * @param tenantAccessTokenModel Downscoped access token model that was returned from the engine.
     * @return Access token.
     */
    AccessToken createAccessToken(TenantAccessTokenModel tenantAccessTokenModel);
}
