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
