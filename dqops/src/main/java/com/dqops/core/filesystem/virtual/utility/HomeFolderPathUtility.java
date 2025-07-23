/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.virtual.utility;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FolderName;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;

import java.nio.file.Path;
import java.util.ArrayList;
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

        String dataDomain = UserDomainIdentity.ROOT_DATA_DOMAIN;
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
