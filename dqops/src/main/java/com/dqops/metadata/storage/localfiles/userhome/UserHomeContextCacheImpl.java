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
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * User home context local cache, used by the cli command completers to speed up parsing.
 */
@Component
@Lazy(false)
public class UserHomeContextCacheImpl implements UserHomeContextCache {
    private final DqoUserPrincipalProvider dqoUserPrincipalProvider;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext cachedUserHomeContext;
    private Instant cachedAt;

    /**
     * The cache duration for a cached user home context (in seconds).
     */
    public static final int CACHE_DURATION_SECONDS = 30;

    /**
     * Default injection constructor.
     * @param dqoUserPrincipalProvider User principal provider.
     */
    @Autowired
    public UserHomeContextCacheImpl(DqoUserPrincipalProvider dqoUserPrincipalProvider) {
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
    }

    /**
     * Attaches a reference to a DQOps user home context factory used by this cache.
     *
     * @param userHomeContextFactory User home context factory.
     */
    @Override
    public void setUserHomeContextFactory(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Notifies the factory that a cached copy of a user home context should be invalidated because a change was written to the user home.
     */
    @Override
    public void invalidateCache() {
        this.cachedUserHomeContext = null;
        this.cachedAt = null;
    }

    /**
     * Returns a cached user home context for the CLI user.
     * @return Cached user home context.
     */
    @Override
    public UserHomeContext getCachedLocalUserHome() {
        if (this.cachedAt != null && this.cachedUserHomeContext != null &&
                this.cachedAt.plus(CACHE_DURATION_SECONDS, ChronoUnit.SECONDS).isAfter(Instant.now())) {
            return this.cachedUserHomeContext;
        }

        DqoUserPrincipal userPrincipalForDqopsOperator = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity operatorIdentity = userPrincipalForDqopsOperator.createIdentity();
        UserHomeContext cachedUserHomeContext = this.userHomeContextFactory.openLocalUserHome(operatorIdentity);
        this.cachedUserHomeContext = cachedUserHomeContext;
        this.cachedAt = Instant.now();

        return cachedUserHomeContext;
    }
}
