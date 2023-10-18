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
package com.dqops.core.dqocloud.buckets;

import com.dqops.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentials;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentialsException;
import com.dqops.core.dqocloud.accesskey.DqoCloudOAuth2BucketRWRefreshHandler;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.OAuth2CredentialsWithRefresh;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQOps Cloud bucket credentials provider. Creates a Google storage client to access the bucket with the tenant's data.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoCloudBucketAccessProviderImpl implements DqoCloudBucketAccessProvider {
    private DqoCloudAccessTokenCache accessTokenCache;

    /**
     * Default injection constructor.
     * @param accessTokenCache DQOps Cloud credentials provider (access token provider).
     */
    @Autowired
    public DqoCloudBucketAccessProviderImpl(DqoCloudAccessTokenCache accessTokenCache) {
        this.accessTokenCache = accessTokenCache;
    }

    /**
     * Creates a configured Google storage client to access a tenant' folder in a DQOps Cloud bucket.
     * @param rootType Bucket type (sensor readouts, rule results, etc.)
     * @return Configured bucket access with a {@link Storage} client to access the data with downscoped credentials.
     */
    public DqoCloudRemoteBucket getRemoteBucketClientRW(DqoRoot rootType) {
        try {
            DqoCloudOAuth2BucketRWRefreshHandler refreshHandler = new DqoCloudOAuth2BucketRWRefreshHandler(rootType, this.accessTokenCache);
            DqoCloudCredentials dqoCloudCredentials = this.accessTokenCache.getCredentials(rootType);
            if (dqoCloudCredentials == null) {
                return null;
            }

            OAuth2CredentialsWithRefresh credentials =
                    OAuth2CredentialsWithRefresh.newBuilder()
                            .setAccessToken(dqoCloudCredentials.getAccessToken())
                            .setRefreshHandler(refreshHandler)
                            .build();

            StorageOptions options = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build();
            Storage storage = options.getService();

            DqoCloudRemoteBucket dqoCloudRemoteBucket = new DqoCloudRemoteBucket(rootType,
                    dqoCloudCredentials.getTenantAccessTokenModel().getBucketName(),
                    dqoCloudCredentials.getTenantAccessTokenModel().getBucketPathPrefix(),
                    storage);

            return dqoCloudRemoteBucket;
        }
        catch (Exception ex) {
            throw new DqoCloudCredentialsException(ex.getMessage(), ex);
        }
    }
}
