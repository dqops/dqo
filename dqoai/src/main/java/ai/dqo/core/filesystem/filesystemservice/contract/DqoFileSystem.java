/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.filesystem.filesystemservice.contract;

/**
 * DQO file system: a reference to the file system root and a file system service that can manage files in that root.
 */
public class DqoFileSystem {
    private AbstractFileSystemRoot fileSystemRoot;
    private FileSystemService fileSystemService;

    /**
     * Creates a DQO file system.
     * @param fileSystemRoot File system root.
     * @param fileSystemService File system management service that operates on the root.
     */
    public DqoFileSystem(AbstractFileSystemRoot fileSystemRoot, FileSystemService fileSystemService) {
        this.fileSystemRoot = fileSystemRoot;
        this.fileSystemService = fileSystemService;
    }

    /**
     * Returns the file system root.
     * @return File system root.
     */
    public AbstractFileSystemRoot getFileSystemRoot() {
        return fileSystemRoot;
    }

    /**
     * Returns the implementation of a file system service that manages the target file system.
     * @return File system service.
     */
    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }
}
