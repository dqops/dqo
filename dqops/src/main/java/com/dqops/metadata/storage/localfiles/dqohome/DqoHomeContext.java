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
