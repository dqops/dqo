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

import com.dqops.core.configuration.DqoCacheConfigurationProperties;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.ObjectMemorySizeUtility;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Local file system cache.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LocalFileSystemCacheImpl implements LocalFileSystemCache, DisposableBean {
    private final Cache<Path, List<HomeFolderPath>> folderListsCache;
    private final Cache<Path, List<HomeFilePath>> fileListsCache;
    private final Cache<Path, FileContent> textFilesCache;
    private final Cache<Path, LoadedMonthlyPartition> parquetFilesCache;
    private WatchService watchService;
    private final Map<Path, WatchKey> directoryWatchersByPath = new LinkedHashMap<>();
    private final Map<WatchKey, Path> watchedDirectoriesByWatchKey = new LinkedHashMap<>();
    private final Object directoryWatchersLock = new Object();
    private final DqoCacheConfigurationProperties dqoCacheConfigurationProperties;
    private Instant nextFileChangeDetectionAt = Instant.now().minus(100L, ChronoUnit.MILLIS);

    @Autowired
    public LocalFileSystemCacheImpl(DqoCacheConfigurationProperties dqoCacheConfigurationProperties) {
        this.dqoCacheConfigurationProperties = dqoCacheConfigurationProperties;

        WatchService newWatchService = null;
        if (dqoCacheConfigurationProperties.isEnable() && dqoCacheConfigurationProperties.isWatchFileSystemChanges()) {
            try {
                newWatchService = FileSystems.getDefault().newWatchService();
            } catch (IOException ioe) {
                throw new DqoRuntimeException("Cannot create a watch service.");
            }
        }
        this.watchService = newWatchService;

        this.folderListsCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheConfigurationProperties.getYamlFilesLimit())
                .expireAfterWrite(dqoCacheConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();
        this.fileListsCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheConfigurationProperties.getFileListsLimit())
                .expireAfterWrite(dqoCacheConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();
        this.textFilesCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheConfigurationProperties.getFileListsLimit())
                .expireAfterWrite(dqoCacheConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();

        long heapMemoryAvailableForCache = Runtime.getRuntime().maxMemory() - this.dqoCacheConfigurationProperties.getReservedHeapMemoryBytes();
        long parquetCacheBytes = (long)(heapMemoryAvailableForCache * this.dqoCacheConfigurationProperties.getParquetCacheMemoryFraction());

        this.parquetFilesCache = Caffeine.newBuilder()
                .maximumWeight(Math.max(1L, parquetCacheBytes))
                .expireAfterWrite(dqoCacheConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .weigher((Path path, LoadedMonthlyPartition table) -> (int)ObjectMemorySizeUtility.measureDeep(table))
                .build();
    }

    /**
     * Called when the object is destroyed. Stops the watcher.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (this.watchService != null) {
            this.watchService.close();
            this.watchService = null;
        }
    }

    /**
     * Starts a folder change watcher for the given folder. Does nothing if the folder is already monitored.
     * @param folderPath Folder path.
     */
    public void startFolderWatcher(Path folderPath) {
        if (!this.dqoCacheConfigurationProperties.isWatchFileSystemChanges()) {
            return;
        }

        synchronized (this.directoryWatchersLock) {
            if (this.directoryWatchersByPath.containsKey(folderPath)) {
                return;
            }

            try {
                if (!Files.exists(folderPath) || !Files.isDirectory(folderPath)) {
                    return;
                }

                WatchKey watchKey = folderPath.register(this.watchService,
                        StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                this.directoryWatchersByPath.put(folderPath, watchKey);
                this.watchedDirectoriesByWatchKey.put(watchKey, folderPath);
            }
            catch (IOException ioe) {
                throw new DqoRuntimeException("Cannot start watching changes in the folder " + folderPath, ioe);
            }
        }
    }

    /**
     * Stops a folder change watcher for the given folder. Does nothing if the folder is not monitored.
     * @param folderPath Folder path.
     */
    public void stopFolderWatcher(Path folderPath) {
        if (!this.dqoCacheConfigurationProperties.isWatchFileSystemChanges()) {
            return;
        }

        synchronized (this.directoryWatchersLock) {
            if (!this.directoryWatchersByPath.containsKey(folderPath)) {
                return;
            }

            WatchKey watchKey = this.directoryWatchersByPath.get(folderPath);
            this.directoryWatchersByPath.remove(folderPath);
            this.watchedDirectoriesByWatchKey.remove(watchKey);
            watchKey.cancel();
        }
    }

    /**
     * Process file changes that happened since the last time.
     * @param force When true, the changes are processed even if the delay has not passed yet.
     */
    public void processFileChanges(boolean force) {
        if (!this.dqoCacheConfigurationProperties.isWatchFileSystemChanges() || this.watchService == null) {
            return;
        }

        Instant nextFileChangeDetectionAt;
        synchronized (this.directoryWatchersLock) {
            nextFileChangeDetectionAt = this.nextFileChangeDetectionAt;
        }

        if (!force && nextFileChangeDetectionAt.isAfter(Instant.now())) {
            return;
        }

        WatchKey watchKey;
        while ((watchKey = this.watchService.poll()) != null) {
            Path directoryPath;
            synchronized (this.directoryWatchersLock) {
                directoryPath = this.watchedDirectoriesByWatchKey.get(watchKey);
            }

            if (directoryPath != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    Path filePath = (Path) event.context();
                    Path absoluteFilePath = directoryPath.resolve(filePath).toAbsolutePath().normalize();
                    invalidateFile(absoluteFilePath);
                    invalidateFolder(absoluteFilePath); // we don't know if it is a file or a folder
                    invalidateFolder(directoryPath);
                }
            }
            watchKey.reset();
        }

        synchronized (this.directoryWatchersLock) {
            this.nextFileChangeDetectionAt = Instant.now().plus(
                    this.dqoCacheConfigurationProperties.getProcessFileChangesDelayMillis(), ChronoUnit.MILLIS);
        }
    }

    /**
     * Retrieves a list of folders, calling the load callback to load the data if it is missing in the cache.
     * @param folderPath Cache folderPath.
     * @param listingFunction Callback that is called to load the content of the folder.
     * @return Cached or loaded list of folders.
     */
    @Override
    public List<HomeFolderPath> getListOfFolders(Path folderPath, Function<Path, List<HomeFolderPath>> listingFunction) {
        if (this.dqoCacheConfigurationProperties.isEnable()) {
            this.startFolderWatcher(folderPath);
            this.processFileChanges(false);
            return this.folderListsCache.get(folderPath, listingFunction);
        }

        return listingFunction.apply(folderPath);
    }

    /**
     * Retrieves a list of files in a folder, calling the load callback to load the data if it is missing in the cache.
     * @param folderPath Cache folderPath.
     * @param listingFunction Callback called to load a list of files in a folder.
     * @return Cached or loaded list of folders.
     */
    @Override
    public List<HomeFilePath> getListOfFiles(Path folderPath, Function<Path, List<HomeFilePath>> listingFunction) {
        if (this.dqoCacheConfigurationProperties.isEnable()) {
            this.startFolderWatcher(folderPath);
            this.processFileChanges(false);
            return this.fileListsCache.get(folderPath, listingFunction);
        }

        return listingFunction.apply(folderPath);
    }

    /**
     * Reads a cached or real file content, calling the load callback to load the data if it is missing in the cache.
     * @param filePath Cache key.
     * @return Cached or loaded file content.
     */
    @Override
    public FileContent loadFileContent(Path filePath, Function<Path, FileContent> readFunction) {
        if (this.dqoCacheConfigurationProperties.isEnable()) {
            this.startFolderWatcher(filePath.getParent());
            this.processFileChanges(false);
            return this.textFilesCache.get(filePath, readFunction);
        }

        return readFunction.apply(filePath);
    }

    /**
     * Stores a content of a text file in the cache. Also invalidates a cached list of files from the parent folder.
     * @param filePath Cache key (file path).
     * @param fileContent File content.
     */
    @Override
    public void storeTextFile(Path filePath, FileContent fileContent) {
        if (!this.dqoCacheConfigurationProperties.isEnable()) {
            return;
        }

        this.startFolderWatcher(filePath.getParent());
        this.textFilesCache.put(filePath, fileContent);

        Path parentFolderPath = filePath.getParent();
        this.fileListsCache.invalidate(parentFolderPath);
        for (; parentFolderPath != null; parentFolderPath = parentFolderPath.getParent()) {
            this.folderListsCache.invalidate(parentFolderPath);
        }
    }

    /**
     * Retrieves a cached parquet file.
     * @param filePath Cache key.
     * @return Cached parquet file or null when the file was not found in the cache.
     */
    @Override
    public LoadedMonthlyPartition getParquetFile(Path filePath) {
        if (this.dqoCacheConfigurationProperties.isEnable()) {
            this.startFolderWatcher(filePath.getParent());
            this.processFileChanges(false);
            return this.parquetFilesCache.getIfPresent(filePath);
        }

        return null;
    }

    /**
     * Stores a content of a Parquet file in the cache. Also invalidates a cached list of files from the parent folder.
     * @param filePath Cache key (file path).
     * @param table Parquet file content loaded to a table.
     */
    @Override
    public void storeParquetFile(Path filePath, LoadedMonthlyPartition table) {
        if (!this.dqoCacheConfigurationProperties.isEnable()) {
            return;
        }

        this.startFolderWatcher(filePath.getParent());
        this.parquetFilesCache.put(filePath, table);

        Path parentFolderPath = filePath.getParent();
        this.fileListsCache.invalidate(parentFolderPath);
        for (; parentFolderPath != null; parentFolderPath = parentFolderPath.getParent()) {
            this.folderListsCache.invalidate(parentFolderPath);
        }
    }

    /**
     * Removes a file from the cache. Invalidates the cached list of files from the parent folder.
     * @param filePath Cache key.
     */
    @Override
    public void removeFile(Path filePath) {
        this.textFilesCache.invalidate(filePath);
        this.parquetFilesCache.invalidate(filePath);

        Path parentFolderPath = filePath.getParent();
        this.fileListsCache.invalidate(parentFolderPath);
        for (; parentFolderPath != null; parentFolderPath = parentFolderPath.getParent()) {
            this.folderListsCache.invalidate(parentFolderPath);
        }

    }

    /**
     * Removes a folder from the cache. Invalidates the cached list of folders from the parent folder.
     * @param folderPath Cache key.
     */
    @Override
    public void removeFolder(Path folderPath) {
        this.folderListsCache.invalidate(folderPath);
        this.fileListsCache.invalidate(folderPath);

        this.stopFolderWatcher(folderPath);

        if (folderPath.getNameCount() > 1) {
            Path parentFolderPath = folderPath.getParent();
            this.fileListsCache.invalidate(parentFolderPath);
        }
    }

    /**
     * Invalidates a list of files and folders in a given folder.
     * @param folderPath Folder path.
     */
    @Override
    public void invalidateFolder(Path folderPath) {
        this.folderListsCache.invalidate(folderPath);
        this.fileListsCache.invalidate(folderPath);
    }

    /**
     * Invalidates a file and a list of files in the parent folder.
     * @param filePath File path.
     */
    @Override
    public void invalidateFile(Path filePath) {
        this.textFilesCache.invalidate(filePath);
        this.parquetFilesCache.invalidate(filePath);

        Path folderPath = filePath.getParent();
        if (folderPath != null) {
            this.folderListsCache.invalidate(folderPath);
            this.fileListsCache.invalidate(folderPath);
        }
    }

    /**
     * Invalidates all entries in the cache.
     */
    @Override
    public void invalidateAll() {
        this.folderListsCache.invalidateAll();
        this.fileListsCache.invalidateAll();
        this.textFilesCache.invalidateAll();
        this.parquetFilesCache.invalidateAll();

        if (this.dqoCacheConfigurationProperties.isWatchFileSystemChanges() && this.watchService != null) {
            synchronized (this.directoryWatchersLock) {
                for (WatchKey watchKey : this.directoryWatchersByPath.values()) {
                    watchKey.cancel();
                }

                this.directoryWatchersByPath.clear();
                this.watchedDirectoriesByWatchKey.clear();
            }
        }
    }
}
