/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.dqohome.DqoHome;

/**
 * File based user home context used to operate on the user home file based model.
 */
public class DqoHomeContext {
    private final FolderTreeNode homeRoot;
    private DqoHome dqoHome;

    /**
     * Creates a user home context given a virtual folder with the user home.
     * @param homeRoot User home root folder.
     */
    public DqoHomeContext(FolderTreeNode homeRoot) {
        this.homeRoot = homeRoot;
    }

    /**
     * Home file system root based on the file based implementation.
     * @return User home root.
     */
    public FolderTreeNode getHomeRoot() {
        return homeRoot;
    }

    /**
     * Returns a dqo home model that is used by this instance.
     * @return Dqo home model connected to the file system.
     */
    public DqoHome getDqoHome() {
        return dqoHome;
    }

    /**
     * Sets a reference to the dqo home model.
     * @param dqoHome Dqo home model
     */
    public void setDqoHome(DqoHome dqoHome) {
        assert dqoHome instanceof FileDqoHomeImpl && ((FileDqoHomeImpl)dqoHome).getHomeFolder() == this.homeRoot;
        this.dqoHome = dqoHome;
    }

    /**
     * Flushes changes to disk.
     */
    public void flush() {
		this.dqoHome.flush(); // flushes changes to the virtual file system
		this.homeRoot.flush(); // flushes changes to disk
    }
}
