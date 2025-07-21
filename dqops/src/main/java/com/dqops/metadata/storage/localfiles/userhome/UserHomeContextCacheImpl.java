/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.dqocloud.datadomains.CliCurrentDataDomainService;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
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
    private final CliCurrentDataDomainService cliCurrentDataDomainService;
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext cachedUserHomeContext;
    private Instant cachedAt;

    /**
     * The cache duration for a cached user home context (in seconds).
     */
    public static final int CACHE_DURATION_SECONDS = 30;

    /**
     * Default injection constructor.
     */
    @Autowired
    public UserHomeContextCacheImpl(CliCurrentDataDomainService cliCurrentDataDomainService,
                                    UserDomainIdentityFactory userDomainIdentityFactory) {
        this.cliCurrentDataDomainService = cliCurrentDataDomainService;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
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

        String currentRootMountedDataDomain = this.cliCurrentDataDomainService.getCurrentDataDomain();
        UserDomainIdentity dataDomainAdminIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(currentRootMountedDataDomain);

        UserHomeContext cachedUserHomeContext = this.userHomeContextFactory.openLocalUserHome(dataDomainAdminIdentity, true);
        this.cachedUserHomeContext = cachedUserHomeContext;
        this.cachedAt = Instant.now();

        return cachedUserHomeContext;
    }
}
