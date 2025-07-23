/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.contract;

import java.nio.file.Path;

/**
 * Base token for the root file system. Identifies the root file system. Derived classes (for cloud data access)
 * may also store access keys needed to access the remote files.
 */
public abstract class FileSystemSynchronizationRoot {
    private Path rootPath;
    private DqoRoot rootType;

    /**
     * Creates a root file system.
     * @param rootPath Root file system path.
     * @param rootType Root type.
     */
    public FileSystemSynchronizationRoot(Path rootPath, DqoRoot rootType) {
        this.rootPath = rootPath;
        this.rootType = rootType;
    }

    /**
     * Returns the root file system path. It may be an absolute path to a local folder.
     * @return Root file system path.
     */
    public Path getRootPath() {
        return rootPath;
    }

    /**
     * Returns the type of the root folder.
     * @return Root folder type.
     */
    public DqoRoot getRootType() {
        return rootType;
    }
}
