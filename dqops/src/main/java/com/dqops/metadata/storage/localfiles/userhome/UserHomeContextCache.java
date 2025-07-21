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

/**
 * User home context local cache, used by the cli command completers to speed up parsing.
 */
public interface UserHomeContextCache {
    /**
     * Notifies the factory that a cached copy of a user home context should be invalidated because a change was written to the user home.
     */
    void invalidateCache();

    /**
     * Returns a cached user home context for the CLI user.
     * @return Cached user home context.
     */
    UserHomeContext getCachedLocalUserHome();

    /**
     * Attaches a reference to a DQOps user home context factory used by this cache.
     * @param userHomeContextFactory User home context factory.
     */
    void setUserHomeContextFactory(UserHomeContextFactory userHomeContextFactory);
}
