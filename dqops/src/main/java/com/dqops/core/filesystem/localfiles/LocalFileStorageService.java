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

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;

import java.util.List;

/**
 * Local folder access service, manages files in a given folder. Manages files on the disk in the given file system folder.
 */
public interface LocalFileStorageService {
    /**
     * Returns a path to the home folder on the local machine.
     * @return Path to the home folder.
     */
    String getHomeRootDirectory();

    /**
     * Checks if a file exists.
     * @param filePath File path relative to the home root.
     * @return True when the file is present, false when the file is missing.
     */
    boolean fileExists(HomeFilePath filePath);

    /**
     * Checks if a folder exists.
     * @param folderPath Folder path relative to the home root.
     * @return True when the folder is present, false when the folder is missing.
     */
    boolean folderExists(HomeFolderPath folderPath);

    /**
     * Reads a text file given the file name components.
     * @param filePath File path relative to the home root.
     * @return File content or null when the file was not found.
     */
    FileContent readFile(HomeFilePath filePath);

    /**
     * Save a file to the user home folder.
     * @param filePath Relative file path inside the home folder.
     * @param fileContent File content.
     */
    void saveFile(HomeFilePath filePath, FileContent fileContent);

    /**
     * Deletes a file given the path.
     * @param filePath Relative file path inside the home folder.
     * @return True when the file was present and was deleted, false when the file was missing.
     */
    boolean deleteFile(HomeFilePath filePath);

    /**
     * Lists direct subfolders inside a given folder.
     *
     * @param folderPath Path elements to the folder whose content will be listed.
     * @return List of directory names inside the directory. Names are relative to their containing directory and are decoded (unsanitized). Null if directory doesn't exist.
     */
    List<HomeFolderPath> listFolders(HomeFolderPath folderPath);

    /**
     * Lists direct files inside a given folder.
     *
     * @param folderPath Path to the folder that will be listed.
     * @return List of file paths that are relative to the user home folder.
     */
    List<HomeFilePath> listFiles(HomeFolderPath folderPath);

    /**
     * Tries to delete a folder.
     * @param folderPath Relative folder path.
     * @return True when the folder was deleted, false when there were some issues (folder in use) or the folder does not exist.
     */
    boolean tryDeleteFolder(HomeFolderPath folderPath);
}
