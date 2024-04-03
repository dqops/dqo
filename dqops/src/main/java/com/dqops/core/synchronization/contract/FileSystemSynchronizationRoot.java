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
