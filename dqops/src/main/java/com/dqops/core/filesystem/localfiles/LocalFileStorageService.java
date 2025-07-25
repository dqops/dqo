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
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return True when the file is present, false when the file is missing.
     */
    boolean fileExists(HomeFilePath filePath, boolean useLocking);

    /**
     * Checks if a folder exists.
     * @param folderPath Folder path relative to the home root.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return True when the folder is present, false when the folder is missing.
     */
    boolean folderExists(HomeFolderPath folderPath, boolean useLocking);

    /**
     * Reads a text file given the file name components.
     * @param filePath File path relative to the home root.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return File content or null when the file was not found.
     */
    FileContent readFile(HomeFilePath filePath, boolean useLocking);

    /**
     * Save a file to the user home folder.
     * @param filePath Relative file path inside the home folder.
     * @param fileContent File content.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     */
    void saveFile(HomeFilePath filePath, FileContent fileContent, boolean useLocking);

    /**
     * Deletes a file given the path.
     * @param filePath Relative file path inside the home folder.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return True when the file was present and was deleted, false when the file was missing.
     */
    boolean deleteFile(HomeFilePath filePath, boolean useLocking);

    /**
     * Lists direct subfolders inside a given folder.
     *
     * @param folderPath Path elements to the folder whose content will be listed.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return List of directory names inside the directory. Names are relative to their containing directory and are decoded (unsanitized). Null if directory doesn't exist.
     */
    List<HomeFolderPath> listFolders(HomeFolderPath folderPath, boolean useLocking);

    /**
     * Lists direct files inside a given folder.
     *
     * @param folderPath Path to the folder that will be listed.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return List of file paths that are relative to the user home folder.
     */
    List<HomeFilePath> listFiles(HomeFolderPath folderPath, boolean useLocking);

    /**
     * Tries to delete a folder.
     * @param folderPath Relative folder path.
     * @param useLocking Use locking. Pass false when a lock was already acquired in an outer scope.
     * @return True when the folder was deleted, false when there were some issues (folder in use) or the folder does not exist.
     */
    boolean tryDeleteFolder(HomeFolderPath folderPath, boolean useLocking);
}
