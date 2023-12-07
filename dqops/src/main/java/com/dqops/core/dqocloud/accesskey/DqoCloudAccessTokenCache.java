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
package com.dqops.core.dqocloud.accesskey;

import com.dqops.core.principal.DqoUserIdentity;
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
    DqoCloudCredentials getCredentials(DqoRoot dqoRoot, DqoUserIdentity userIdentity);

    /**
     * Invalidates the cache. Called when all the keys should be abandoned, because the API key has changed.
     */
    void invalidate();
}
