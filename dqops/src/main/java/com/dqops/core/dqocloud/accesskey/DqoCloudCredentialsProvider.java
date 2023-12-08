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
