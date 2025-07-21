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

import com.dqops.core.principal.UserDomainIdentity;
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
    private UserDomainIdentity userIdentity;

    /**
     * Creates a DQOps Cloud remote bucket summary object.
     * @param rootType Root type (identifies the purpose of the bucket).
     * @param bucketName Google storage bucket name.
     * @param objectPrefix Object prefix to be used as tentant's root folder.
     * @param storage Google Storage client, configured with proper credentials.
     * @param userIdentity User identity that identifies the calling user and the data domain.
     */
    public DqoCloudRemoteBucket(DqoRoot rootType, String bucketName, String objectPrefix, Storage storage, UserDomainIdentity userIdentity) {
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
    public UserDomainIdentity getUserIdentity() {
        return userIdentity;
    }
}
