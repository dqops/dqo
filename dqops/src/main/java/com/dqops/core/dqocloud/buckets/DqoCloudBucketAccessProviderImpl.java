/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.buckets;

import com.dqops.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentials;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentialsException;
import com.dqops.core.dqocloud.accesskey.DqoCloudOAuth2BucketRWRefreshHandler;
import com.dqops.core.principal.UserDomainIdentity;
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
     * @param userIdentity Calling user identity.
     * @return Configured bucket access with a {@link Storage} client to access the data with downscoped credentials.
     */
    @Override
    public DqoCloudRemoteBucket getRemoteBucketClientRW(DqoRoot rootType, UserDomainIdentity userIdentity) {
        try {
            DqoCloudOAuth2BucketRWRefreshHandler refreshHandler = new DqoCloudOAuth2BucketRWRefreshHandler(rootType, userIdentity, this.accessTokenCache);
            DqoCloudCredentials dqoCloudCredentials = this.accessTokenCache.getCredentials(rootType, userIdentity);
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
                    storage,
                    userIdentity);

            return dqoCloudRemoteBucket;
        }
        catch (Exception ex) {
            throw new DqoCloudCredentialsException(ex.getMessage(), ex);
        }
    }
}
