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

import com.dqops.core.filesystem.virtual.FileSystemContext;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;

/**
 * Object mother that creates a local folder tree node using an empty, temporary user home for testing.
 */
public final class LocalFolderTreeNodeObjectMother {
    /**
     * Creates a new local home tree node using the target/temporary-user-home that is cleared before the call.
     * @param recreateHomeDirectory Recreate the home folder (delete an existing folder and create a default one).
     * @return Temporary user home folder.
     */
    public static LocalFolderTreeNode createEmptyTemporaryUserHome(boolean recreateHomeDirectory) {
        LocalFileStorageServiceImpl localHomeStorageService =
                LocalHomeStorageServiceObjectMother.createLocalUserHomeStorageServiceForTestableHome(recreateHomeDirectory);

        LocalFolderTreeNode homeFolder = new LocalFolderTreeNode(new FileSystemContext(localHomeStorageService), new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN));
        return homeFolder;
    }

    /**
     * Creates a new local home tree node using the target/test-user-home that is cleared before the call.
     * @param recreateHomeDirectory Recreate the home folder (delete an existing folder and create a default one).
     * @return Temporary user home folder.
     */
    public static LocalFolderTreeNode createDefaultUserHome(boolean recreateHomeDirectory) {
        LocalFileStorageServiceImpl localHomeStorageService =
                LocalHomeStorageServiceObjectMother.createDefaultHomeStorageService(recreateHomeDirectory);

        LocalFolderTreeNode homeFolder = new LocalFolderTreeNode(new FileSystemContext(localHomeStorageService), new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN));
        return homeFolder;
    }
}
