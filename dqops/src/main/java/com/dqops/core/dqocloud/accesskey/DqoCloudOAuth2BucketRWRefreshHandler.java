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

import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.OAuth2CredentialsWithRefresh;

import java.io.IOException;

/**
 * OAuth2 access token refresh handler that contacts the DQO Cloud to reissue an access token for R/W access to a storage bucket.
 */
public class DqoCloudOAuth2BucketRWRefreshHandler implements OAuth2CredentialsWithRefresh.OAuth2RefreshHandler {
    private DqoRoot rootType;
    private DqoCloudAccessTokenCache accessTokenCache;

    /**
     * Creates an OAuth2 DQO Cloud access token refresh handler.
     * @param rootType Root type to identify the token to use.
     * @param accessTokenCache DQO Cloud parent credentials provider - to get the access token.
     */
    public DqoCloudOAuth2BucketRWRefreshHandler(DqoRoot rootType, DqoCloudAccessTokenCache accessTokenCache) {
        this.rootType = rootType;
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
     * Returns a refreshed access token.
     * @return Refreshed access token.
     * @throws IOException When a token cannot be issued.
     */
    @Override
    public AccessToken refreshAccessToken() throws IOException {
        try {
            DqoCloudCredentials credentials = this.accessTokenCache.getCredentials(this.rootType);
            if (credentials == null) {
                return null;
            }
            AccessToken accessToken = credentials.getAccessToken();
            return accessToken;
        }
        catch (Exception ex) {
            throw new IOException("Refresh token for DQO Cloud bucket access " + this.rootType + " cannot be retrieved.", ex);
        }
    }
}
