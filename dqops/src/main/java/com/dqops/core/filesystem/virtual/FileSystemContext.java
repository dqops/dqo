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
