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
package ai.dqo.core.dqocloud.filesystem;

import ai.dqo.core.dqocloud.buckets.DqoCloudBucketAccessProvider;
import ai.dqo.core.dqocloud.buckets.DqoCloudRemoteBucket;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.remotestorage.gcp.GSFileSystemRoot;
import ai.dqo.core.remotestorage.gcp.GSRemoteFileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Factory for a remote DQO Cloud file system.
 */
@Component
public class DqoCloudRemoteFileSystemServiceFactoryImpl implements DqoCloudRemoteFileSystemServiceFactory {
    private GSRemoteFileSystemService remoteFileSystemService;
    private DqoCloudBucketAccessProvider dqoCloudBucketAccessProvider;

    /**
     * Default injection constructor.
     * @param remoteFileSystemService Remote file system service for the Google Cloud.
     * @param dqoCloudBucketAccessProvider DQO Cloud bucket access provider.
     */
    @Autowired
    public DqoCloudRemoteFileSystemServiceFactoryImpl(
            GSRemoteFileSystemService remoteFileSystemService,
            DqoCloudBucketAccessProvider dqoCloudBucketAccessProvider) {
        this.remoteFileSystemService = remoteFileSystemService;
        this.dqoCloudBucketAccessProvider = dqoCloudBucketAccessProvider;
    }

    /**
     * Creates a remote file system that accesses a remote DQO Cloud bucket to read and write the tenant's data.
     * @param rootType Root type.
     * @return DQO Cloud remote file system.
     */
    public DqoFileSystem createRemoteDqoCloudFSRW(DqoRoot rootType) {
        DqoCloudRemoteBucket remoteBucketClient = this.dqoCloudBucketAccessProvider.getRemoteBucketClientRW(rootType);
        GSFileSystemRoot gsFileSystemRoot = new GSFileSystemRoot(
                Path.of(remoteBucketClient.getObjectPrefix()),
                remoteBucketClient.getStorage(),
                remoteBucketClient.getBucketName(),
                rootType);
        return new DqoFileSystem(gsFileSystemRoot, this.remoteFileSystemService);
    }
}
