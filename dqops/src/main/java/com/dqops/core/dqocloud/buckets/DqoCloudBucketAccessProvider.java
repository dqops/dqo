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
 * DQOps Cloud bucket credentials provider. Creates a Google storage client to access the bucket with the tenant's data.
 */
public interface DqoCloudBucketAccessProvider {
    /**
     * Creates a configured Google storage client to access a tenant' folder in a DQOps Cloud bucket.
     * @param rootType Bucket type (sensor_readouts, rule_results, etc.)
     * @param userIdentity Calling user identity used to identify the data domain. It can be an admin user, but the data domain must be selected.
     * @return Configured bucket access with a {@link Storage} client to access the data with downscoped credentials.
     */
    DqoCloudRemoteBucket getRemoteBucketClientRW(DqoRoot rootType, UserDomainIdentity userIdentity);
}
