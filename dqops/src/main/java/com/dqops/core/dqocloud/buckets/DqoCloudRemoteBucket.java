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

import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.cloud.storage.Storage;

/**
 * Credentials and the tenant's bucket object prefix to access the tenant's data on a bucket.
 */
public class DqoCloudRemoteBucket {
    private DqoRoot rootType;
    private String bucketName;
    private String objectPrefix;
    private Storage storage;
    private DqoUserIdentity userIdentity;

    /**
     * Creates a DQOps Cloud remote bucket summary object.
     * @param rootType Root type (identifies the purpose of the bucket).
     * @param bucketName Google storage bucket name.
     * @param objectPrefix Object prefix to be used as tentant's root folder.
     * @param storage Google Storage client, configured with proper credentials.
     * @param userIdentity User identity that identifies the calling user and the data domain.
     */
    public DqoCloudRemoteBucket(DqoRoot rootType, String bucketName, String objectPrefix, Storage storage, DqoUserIdentity userIdentity) {
        this.rootType = rootType;
        this.bucketName = bucketName;
        this.objectPrefix = objectPrefix;
        this.storage = storage;
        this.userIdentity = userIdentity;
    }

    /**
     * Returns the bucket type (sensor readouts, rule results, etc.)
     * @return Bucket type.
     */
    public DqoRoot getRootType() {
        return rootType;
    }

    /**
     * Returns the Google cloud bucket name.
     * @return Bucket name.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Returns the mandatory object prefix for all tenant's objects that are stored in the cloud.
     * @return Object prefix (followed by a '/' character).
     */
    public String getObjectPrefix() {
        return objectPrefix;
    }

    /**
     * Returns a configured Google Storage client with proper credentials.
     * @return Configured Google Storage client.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns the calling user identity, that identifies the data domain that will be synchronized.
     * @return User identity with the data domain.
     */
    public DqoUserIdentity getUserIdentity() {
        return userIdentity;
    }
}
