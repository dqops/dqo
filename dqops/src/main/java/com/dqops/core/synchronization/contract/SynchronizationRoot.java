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
