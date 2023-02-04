/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.core.filesystem.virtual.utility;

import ai.dqo.core.filesystem.virtual.FolderName;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;

import java.nio.file.Path;
import java.util.LinkedList;

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
        LinkedList<FolderName> homeRelativeFoldersList = new LinkedList<>();
        for (Path fileSystemName : homeRelativePath) {
            homeRelativeFoldersList.add(
                    FolderName.fromFileSystemName(fileSystemName.toString())
            );
        }

        return new HomeFolderPath(homeRelativeFoldersList.toArray(FolderName[]::new));
    }
}
