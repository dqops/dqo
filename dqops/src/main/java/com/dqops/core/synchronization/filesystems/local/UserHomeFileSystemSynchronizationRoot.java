/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.filesystems.local;

import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.FileSystemSynchronizationRoot;

import java.nio.file.Path;

/**
 * File system root for folders in the user home.
 */
public class UserHomeFileSystemSynchronizationRoot extends FileSystemSynchronizationRoot {
    /**
     * Creates a root file system.
     *
     * @param rootPath Absolute path to a local file system.
     * @param rootType Root folder type.
     */
    public UserHomeFileSystemSynchronizationRoot(Path rootPath, DqoRoot rootType) {
        super(rootPath.toAbsolutePath().normalize(), rootType);
        assert rootPath != null && rootPath.isAbsolute();
    }
}
