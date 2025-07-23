/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.virtual;

import com.dqops.core.filesystem.localfiles.LocalFileStorageService;

/**
 * File system context that separates the virtual file tree from the system.
 */
public class FileSystemContext {
    private LocalFileStorageService storageService;

    /**
     * Creates a file system context that uses a given local file storage service.
     * @param storageService Local file storage service.
     */
    public FileSystemContext(LocalFileStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Local file system storage service.
     * @return Storage service.
     */
    public LocalFileStorageService getStorageService() {
        return storageService;
    }

    /**
     * Changes a reference to the file system storage service.
     * @param storageService Storage service.
     */
    public void setStorageService(LocalFileStorageService storageService) {
        this.storageService = storageService;
    }
}
