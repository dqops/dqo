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
package com.dqops.core.synchronization.filesystems.dqocloud;

import com.dqops.core.dqocloud.buckets.DqoCloudBucketAccessProvider;
import com.dqops.core.dqocloud.buckets.DqoCloudRemoteBucket;
import com.dqops.core.principal.DqoUserIdentity;
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
    public SynchronizationRoot createRemoteDqoCloudFSRW(DqoRoot rootType, DqoUserIdentity userIdentity) {
        DqoCloudRemoteBucket remoteBucketClient = this.dqoCloudBucketAccessProvider.getRemoteBucketClientRW(rootType, userIdentity);
        if (remoteBucketClient == null) {
            return null;
        }

        GSFileSystemSynchronizationRoot gsFileSystemRoot = new GSFileSystemSynchronizationRoot(
                Path.of(remoteBucketClient.getObjectPrefix()),
                remoteBucketClient.getStorage(),
                remoteBucketClient.getBucketName(),
                rootType);
        return new SynchronizationRoot(gsFileSystemRoot, this.remoteFileSystemService);
    }
}
