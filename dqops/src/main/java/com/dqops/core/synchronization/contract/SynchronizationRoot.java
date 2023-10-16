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

/**
 * DQOps file system: a reference to the file system root and a file system service that can manage files in that root.
 */
public class SynchronizationRoot {
    private FileSystemSynchronizationRoot fileSystemRoot;
    private FileSystemSynchronizationOperations fileSystemSynchronizationOperations;

    /**
     * Creates a DQOps file system.
     * @param fileSystemRoot File system root.
     * @param fileSystemSynchronizationOperations File system management service that operates on the root.
     */
    public SynchronizationRoot(FileSystemSynchronizationRoot fileSystemRoot, FileSystemSynchronizationOperations fileSystemSynchronizationOperations) {
        this.fileSystemRoot = fileSystemRoot;
        this.fileSystemSynchronizationOperations = fileSystemSynchronizationOperations;
    }

    /**
     * Returns the file system root.
     * @return File system root.
     */
    public FileSystemSynchronizationRoot getFileSystemRoot() {
        return fileSystemRoot;
    }

    /**
     * Returns the implementation of a file system service that manages the target file system.
     * @return File system service.
     */
    public FileSystemSynchronizationOperations getFileSystemService() {
        return fileSystemSynchronizationOperations;
    }
}
