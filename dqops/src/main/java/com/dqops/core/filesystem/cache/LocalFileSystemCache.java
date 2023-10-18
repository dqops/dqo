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

package com.dqops.core.filesystem.cache;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.data.storage.LoadedMonthlyPartition;
import org.springframework.beans.factory.DisposableBean;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

/**
 * Local file system cache.
 */
public interface LocalFileSystemCache extends DisposableBean {
    /**
     * Retrieves a list of folders, calling the load callback to load the data if it is missing in the cache.
     *
     * @param key             Cache key.
     * @param listingFunction Callback that is called to load the content of the folder.
     * @return Cached or loaded list of folders.
     */
    List<HomeFolderPath> getListOfFolders(Path key, Function<Path, List<HomeFolderPath>> listingFunction);

    /**
     * Retrieves a list of files in a folder, calling the load callback to load the data if it is missing in the cache.
     *
     * @param key             Cache key.
     * @param listingFunction Callback called to load a list of files in a folder.
     * @return Cached or loaded list of folders.
     */
    List<HomeFilePath> getListOfFiles(Path key, Function<Path, List<HomeFilePath>> listingFunction);

    /**
     * Reads a cached or real file content, calling the load callback to load the data if it is missing in the cache.
     * @param key Cache key.
     * @return Cached or loaded file content.
     */
    FileContent loadFileContent(Path key, Function<Path, FileContent> readFunction);

    /**
     * Stores a content of a file in the cache. Also invalidates a cached list of files from the parent folder.
     *
     * @param key         Cache key.
     * @param fileContent File content.
     */
    void storeFile(Path key, FileContent fileContent);

    /**
     * Retrieves a cached parquet file.
     * @param filePath Cache key.
     * @return Cached parquet file or null when the file was not found in the cache.
     */
    LoadedMonthlyPartition getParquetFile(Path filePath);

    /**
     * Stores a content of a Parquet file in the cache. Also invalidates a cached list of files from the parent folder.
     * @param filePath Cache key (file path).
     * @param table Parquet file content loaded to a table.
     */
    void storeParquetFile(Path filePath, LoadedMonthlyPartition table);

    /**
     * Removes a file from the cache. Invalidates the cached list of files from the parent folder.
     *
     * @param key Cache key.
     */
    void removeFile(Path key);

    /**
     * Removes a folder from the cache. Invalidates the cached list of folders from the parent folder.
     *
     * @param key Cache key.
     */
    void removeFolder(Path key);

    /**
     * Invalidates all entries in the cache.
     */
    void invalidateAll();

    /**
     * Invalidates a list of files and folders in a given folder.
     * @param folderPath Folder path.
     */
    void invalidateFolder(Path folderPath);

    /**
     * Invalidates a file and a list of files in the parent folder.
     * @param filePath File path.
     */
    void invalidateFile(Path filePath);

    /**
     * Process file changes that happened since the last time.
     * @param force When true, the changes are processed even if the delay has not passed yet.
     */
    void processFileChanges(boolean force);
}
