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

        LocalFolderTreeNode homeFolder = new LocalFolderTreeNode(new FileSystemContext(localHomeStorageService), new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN));
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

        LocalFolderTreeNode homeFolder = new LocalFolderTreeNode(new FileSystemContext(localHomeStorageService), new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN));
        return homeFolder;
    }
}
