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
package ai.dqo.core.dqocloud.buckets;

import ai.dqo.cloud.rest.model.TenantAccessTokenModel;
import ai.dqo.core.dqocloud.accesskey.DqoCloudCredentialsException;
import ai.dqo.core.dqocloud.accesskey.DqoCloudCredentialsProvider;
import ai.dqo.core.dqocloud.accesskey.DqoCloudOAuth2BucketRWRefreshHandler;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.OAuth2CredentialsWithRefresh;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DQO Cloud bucket credentials provider. Creates a Google storage client to access the bucket with the tenant's data.
 */
@Component
public class DqoCloudBucketAccessProviderImpl implements DqoCloudBucketAccessProvider {
    private DqoCloudCredentialsProvider dqoCloudCredentialsProvider;

    /**
     * Default injection constructor.
     * @param dqoCloudCredentialsProvider DQO Cloud credentials provider.
     */
    @Autowired
    public DqoCloudBucketAccessProviderImpl(DqoCloudCredentialsProvider dqoCloudCredentialsProvider) {
        this.dqoCloudCredentialsProvider = dqoCloudCredentialsProvider;
    }

    /**
     * Creates a configured Google storage client to access a tenant' folder in a DQO Cloud bucket.
     * @param rootType Bucket type (sensor readouts, rule results, etc.)
     * @return Configured bucket access with a {@link Storage} client to access the data with downscoped credentials.
     */
    public DqoCloudRemoteBucket getRemoteBucketClientRW(DqoRoot rootType) {
        try {
            TenantAccessTokenModel initialAccessToken = this.dqoCloudCredentialsProvider.issueTenantAccessToken(rootType);
            DqoCloudOAuth2BucketRWRefreshHandler refreshHandler = new DqoCloudOAuth2BucketRWRefreshHandler(rootType, this.dqoCloudCredentialsProvider);
            AccessToken tenantAccessToken = this.dqoCloudCredentialsProvider.createAccessToken(initialAccessToken);

            OAuth2CredentialsWithRefresh credentials =
                    OAuth2CredentialsWithRefresh.newBuilder()
                            .setAccessToken(tenantAccessToken)
                            .setRefreshHandler(refreshHandler)
                            .build();

            StorageOptions options = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build();
            Storage storage = options.getService();

            DqoCloudRemoteBucket dqoCloudRemoteBucket = new DqoCloudRemoteBucket(rootType,
                    initialAccessToken.getBucketName(),
                    initialAccessToken.getBucketPathPrefix(),
                    storage);

            return dqoCloudRemoteBucket;
        }
        catch (Exception ex) {
            throw new DqoCloudCredentialsException(ex.getMessage(), ex);
        }
    }
}
