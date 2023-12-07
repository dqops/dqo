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
package com.dqops.core.filesystem.virtual.utility;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FolderName;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.DqoUserIdentity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Utility class for interactions with {@link HomeFolderPath}.
 */
public class HomeFolderPathUtility {
    /**
     * Factory method for {@link HomeFolderPath}, converting a path from filesystem to the virtual concept.
     * @param homeRelativePath Path to convert, relative to user's home folder.
     * @return {@link HomeFolderPath} equivalent of the {@code homeRelativePath}
     */
    public static HomeFolderPath createFromFilesystemPath(Path homeRelativePath) {
        ArrayList<FolderName> homeRelativeFoldersList = new ArrayList<>();
        for (Path fileSystemName : homeRelativePath) {
            homeRelativeFoldersList.add(
                    FolderName.fromFileSystemName(fileSystemName.toString())
            );
        }

        String dataDomain = DqoUserIdentity.DEFAULT_DATA_DOMAIN;
        if (!homeRelativeFoldersList.isEmpty()) {
            if (Objects.equals(homeRelativeFoldersList.get(0).getObjectName(), BuiltInFolderNames.DATA_DOMAINS)) {
                dataDomain = homeRelativeFoldersList.get(0).getObjectName();
                homeRelativeFoldersList.remove(1);
                homeRelativeFoldersList.remove(0);
            }
        }

        return new HomeFolderPath(dataDomain, homeRelativeFoldersList.toArray(FolderName[]::new));
    }
}
