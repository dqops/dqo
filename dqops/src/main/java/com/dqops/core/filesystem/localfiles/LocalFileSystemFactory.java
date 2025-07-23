/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
