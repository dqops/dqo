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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.OAuth2CredentialsWithRefresh;

import java.io.IOException;

/**
 * OAuth2 access token refresh handler that contacts the DQOps Cloud to reissue an access token for R/W access to a storage bucket.
 */
public class DqoCloudOAuth2BucketRWRefreshHandler implements OAuth2CredentialsWithRefresh.OAuth2RefreshHandler {
    private final DqoRoot rootType;
    private final UserDomainIdentity userIdentity;
    private final DqoCloudAccessTokenCache accessTokenCache;

    /**
     * Creates an OAuth2 DQOps Cloud access token refresh handler.
     * @param rootType Root type to identify the token to use.
     * @param userIdentity User identity that identifies the data domain.
     * @param accessTokenCache DQOps Cloud parent credentials provider - to get the access token.
     */
    public DqoCloudOAuth2BucketRWRefreshHandler(DqoRoot rootType, UserDomainIdentity userIdentity, DqoCloudAccessTokenCache accessTokenCache) {
        this.rootType = rootType;
        this.userIdentity = userIdentity;
        this.accessTokenCache = accessTokenCache;
    }

    /**
     * Bucket root type.
     * @return Root type (bucket purpose).
     */
    public DqoRoot getRootType() {
        return rootType;
    }

    /**
     * Returns the user identity of the user, used mainly to identity the data domain.
     * @return Target data domain.
     */
    public UserDomainIdentity getUserIdentity() {
        return userIdentity;
    }

    /**
     * Returns a refreshed access token.
     * @return Refreshed access token.
     * @throws IOException When a token cannot be issued.
     */
    @Override
    public AccessToken refreshAccessToken() throws IOException {
        try {
            DqoCloudCredentials credentials = this.accessTokenCache.getCredentials(this.rootType, this.userIdentity);
            if (credentials == null) {
                return null;
            }
            AccessToken accessToken = credentials.getAccessToken();
            return accessToken;
        }
        catch (Exception ex) {
            throw new IOException("Refresh token for DQOps Cloud bucket access " + this.rootType + " cannot be retrieved.", ex);
        }
    }
}
