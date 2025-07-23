/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.filesystems.gcp;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.FileSystemSynchronizationRoot;
import com.google.cloud.storage.Storage;

import java.nio.file.Path;

/**
 * File system root for a google storage bucket.
 */
public class GSFileSystemSynchronizationRoot extends FileSystemSynchronizationRoot {
    private final Storage storage;
    private final String bucketName;
    private final UserDomainIdentity userIdentity;

    /**
     * Creates a root file system.
     *
     * @param rootPath Root file system path. The path is just relative to the bucket. The path may be null if the root is the root folder of the bucket.
     * @param storage Configured google storage service with credentials.
     * @param bucketName Bucket name.
     * @param userIdentity User identity of the user initiating synchronization, with the data domain.
     */
    public GSFileSystemSynchronizationRoot(Path rootPath, Storage storage, String bucketName, DqoRoot rootType, UserDomainIdentity userIdentity) {
        super(rootPath, rootType);
        this.storage = storage;
        this.bucketName = bucketName;
        this.userIdentity = userIdentity;
    }

    /**
     * Returns the remote bucket name.
     * @return Remote bucket name.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Configured google storage service, associated with credentials.
     * @return Google storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns the user identity of the user who manages the instance, specifies the data domain that is synchronized.
     * @return User identity with the data domain.
     */
    public UserDomainIdentity getUserIdentity() {
        return userIdentity;
    }
}
