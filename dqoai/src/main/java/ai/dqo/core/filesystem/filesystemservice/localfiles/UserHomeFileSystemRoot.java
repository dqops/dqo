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
package ai.dqo.core.filesystem.filesystemservice.localfiles;

import ai.dqo.core.filesystem.filesystemservice.contract.AbstractFileSystemRoot;

import java.nio.file.Path;

/**
 * File system root for folders in the user home.
 */
public class UserHomeFileSystemRoot extends AbstractFileSystemRoot {
    /**
     * Creates a root file system.
     *
     * @param rootPath Absolute path to a local file system.
     */
    public UserHomeFileSystemRoot(Path rootPath) {
        super(rootPath);
        assert rootPath != null && rootPath.isAbsolute();
    }
}
