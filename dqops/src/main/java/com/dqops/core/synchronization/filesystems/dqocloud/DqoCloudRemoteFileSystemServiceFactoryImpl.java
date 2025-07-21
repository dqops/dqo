/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.filesystems.dqocloud;

import com.dqops.core.dqocloud.buckets.DqoCloudBucketAccessProvider;
import com.dqops.core.dqocloud.buckets.DqoCloudRemoteBucket;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import com.dqops.core.synchronization.filesystems.gcp.GSFileSystemSynchronizationRoot;
import com.dqops.core.synchronization.filesystems.gcp.GSRemoteFileSystemSynchronizationOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Factory for a remote DQOps Cloud file system.
 */
@Component
public class DqoCloudRemoteFileSystemServiceFactoryImpl implements DqoCloudRemoteFileSystemServiceFactory {
    private GSRemoteFileSystemSynchronizationOperations remoteFileSystemService;
    private DqoCloudBucketAccessProvider dqoCloudBucketAccessProvider;

    /**
     * Default injection constructor.
     * @param remoteFileSystemService Remote file system service for the Google Cloud.
     * @param dqoCloudBucketAccessProvider DQOps Cloud bucket access provider.
     */
    @Autowired
    public DqoCloudRemoteFileSystemServiceFactoryImpl(
            GSRemoteFileSystemSynchronizationOperations remoteFileSystemService,
            DqoCloudBucketAccessProvider dqoCloudBucketAccessProvider) {
        this.remoteFileSystemService = remoteFileSystemService;
        this.dqoCloudBucketAccessProvider = dqoCloudBucketAccessProvider;
    }

    /**
     * Creates a remote file system that accesses a remote DQOps Cloud bucket to read and write the tenant's data.
     * @param rootType Root type.
     * @param userIdentity User identity.
     * @return DQOps Cloud remote file system.
     */
    @Override
    public SynchronizationRoot createRemoteDqoCloudFSRW(DqoRoot rootType, UserDomainIdentity userIdentity) {
        DqoCloudRemoteBucket remoteBucketClient = this.dqoCloudBucketAccessProvider.getRemoteBucketClientRW(rootType, userIdentity);
        if (remoteBucketClient == null) {
            return null;
        }

        GSFileSystemSynchronizationRoot gsFileSystemRoot = new GSFileSystemSynchronizationRoot(
                Path.of(remoteBucketClient.getObjectPrefix()),
                remoteBucketClient.getStorage(),
                remoteBucketClient.getBucketName(),
                rootType,
                userIdentity);
        return new SynchronizationRoot(gsFileSystemRoot, this.remoteFileSystemService);
    }
}
