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
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.principal.UserDomainIdentity;

/**
 * Factory class for the local file system. Creates the virtual file system that uses the local file system to access the home folder.
 */
public interface LocalFileSystemFactory {
    /**
     * Creates a local file system that is based on the real user home folder.
     * @param userDomainIdentity User identity and the data domain for which the user home is opened.
     * @return Local file system (root node) for the user's home folder.
     */
    LocalFolderTreeNode openLocalUserHome(UserDomainIdentity userDomainIdentity);

    /**
     * Creates a local file system that is based on the real DQO_HOME home folder.
     * @return Local file system (root node) for the DQO_HOME home folder.
     */
    LocalFolderTreeNode openLocalDqoHome();
}
