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

import com.dqops.cli.completion.completers.cache.CliCompletionCache;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.userhome.UserHome;

/**
 * File based user home context used to operate on the user home file based model.
 */
public class UserHomeContext {
    private final FolderTreeNode homeRoot;
    private final UserDomainIdentity userIdentity;
    private UserHome userHome;
    private UserHomeContextCache userHomeContextCache;

    /**
     * Creates a user home context given a virtual folder with the user home.
     * @param userIdentity User identity of the user who opened the user home context.
     * @param homeRoot User home root folder.
     */
    public UserHomeContext(FolderTreeNode homeRoot, UserDomainIdentity userIdentity) {
        this.homeRoot = homeRoot;
        this.userIdentity = userIdentity;
    }

    /**
     * Home file system root based on the file based implementation.
     * @return User home root.
     */
    public FolderTreeNode getHomeRoot() {
        return homeRoot;
    }

    /**
     * Returns the user identity of the user who opened the user home. Also identifies the data domain.
     * @return User identity.
     */
    public UserDomainIdentity getUserIdentity() {
        return userIdentity;
    }

    /**
     * Returns a user home model that is used by this instance.
     * @return User home model connected to the file system.
     */
    public UserHome getUserHome() {
        return userHome;
    }

    /**
     * Sets a reference to the user home model.
     * @param userHome User home model
     */
    public void setUserHome(UserHome userHome) {
        assert userHome instanceof FileUserHomeImpl &&  ((FileUserHomeImpl)userHome).getHomeFolder() == this.homeRoot;
        this.userHome = userHome;
    }

    /**
     * Flushes changes to disk.
     */
    public void flush() {
		this.userHome.flush(); // flushes changes to the virtual file system
		this.homeRoot.flush(); // flushes changes to disk

        CliCompletionCache.invalidateCache();

        if (this.userHomeContextCache != null) {
            this.userHomeContextCache.invalidateCache();
        }
    }

    /**
     * Associates the user home context with a user home context cache. The cache is flushed when the user context is persisted.
     */
    protected void setUserModelCache(UserHomeContextCache userHomeContextCache) {
        this.userHomeContextCache = userHomeContextCache;
    }
}
