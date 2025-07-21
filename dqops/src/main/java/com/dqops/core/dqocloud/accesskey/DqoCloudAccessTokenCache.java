/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.accesskey;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;

/**
 * DQOps Cloud access token cache that creates new GCP access tokens when they are about to expire.
 */
public interface DqoCloudAccessTokenCache {
    /**
     * Returns a current GCP bucket access token used to perform read/write operations on a customer's storage bucket for a given root folder.
     *
     * @param dqoRoot DQOps Root folder.
     * @param userIdentity Calling user identity, used to identify the data domain.
     * @return Up-to-date access token.
     */
    DqoCloudCredentials getCredentials(DqoRoot dqoRoot, UserDomainIdentity userIdentity);

    /**
     * Invalidates the cache. Called when all the keys should be abandoned, because the API key has changed.
     */
    void invalidate();
}
