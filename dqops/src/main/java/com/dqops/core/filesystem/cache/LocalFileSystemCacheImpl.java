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

import com.dqops.core.configuration.DqoCacheSpecConfigurationProperties;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
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
public class LocalFileSystemCacheImpl implements LocalFileSystemCache {
    private final Cache<Path, List<HomeFolderPath>> foldersCache;
    private final Cache<Path, List<HomeFilePath>> filesCache;
    private final Cache<Path, FileContent> textFilesCache;
    private final WatchService watchService;
    private final Map<Path, WatchKey> directoryWatchers = new LinkedHashMap<>();
    private final Object directoryWatchersLock = new Object();
    private final DqoCacheSpecConfigurationProperties dqoCacheSpecConfigurationProperties;
    private Instant nextFileChangeDetectionAt = Instant.now().minus(100L, ChronoUnit.MILLIS);

    @Autowired
    public LocalFileSystemCacheImpl(DqoCacheSpecConfigurationProperties dqoCacheSpecConfigurationProperties) {
        this.dqoCacheSpecConfigurationProperties = dqoCacheSpecConfigurationProperties;

        WatchService newWatchService = null;
        if (dqoCacheSpecConfigurationProperties.isWatchFileSystemChanges()) {
            try {
                newWatchService = FileSystems.getDefault().newWatchService();
            } catch (IOException ioe) {
                throw new DqoRuntimeException("Cannot create a watch service.");
            }
        }
        this.watchService = newWatchService;

        this.foldersCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheSpecConfigurationProperties.getMaximumSize())
                .expireAfterWrite(dqoCacheSpecConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();
        this.filesCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheSpecConfigurationProperties.getMaximumSize())
                .expireAfterWrite(dqoCacheSpecConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();
        this.textFilesCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheSpecConfigurationProperties.getMaximumSize())
                .expireAfterWrite(dqoCacheSpecConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * Starts a folder change watcher for the given folder. Does nothing if the folder is already monitored.
     * @param folderPath Folder path.
     */
    public void startFolderWatcher(Path folderPath) {
        if (!this.dqoCacheSpecConfigurationProperties.isWatchFileSystemChanges()) {
            return;
        }

        synchronized (this.directoryWatchersLock) {
            if (this.directoryWatchers.containsKey(folderPath)) {
                return;
            }

            try {
                if (!Files.exists(folderPath) || !Files.isDirectory(folderPath)) {
                    return;
                }

                WatchKey watchKey = folderPath.register(this.watchService,
                        StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                this.directoryWatchers.put(folderPath, watchKey);
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
        if (!this.dqoCacheSpecConfigurationProperties.isWatchFileSystemChanges()) {
            return;
        }

        synchronized (this.directoryWatchersLock) {
            if (!this.directoryWatchers.containsKey(folderPath)) {
                return;
            }

            WatchKey watchKey = this.directoryWatchers.get(folderPath);
            this.directoryWatchers.remove(folderPath);
            watchKey.cancel();
        }
    }

    /**
     * Process file changes that happened since the last time.
     */
    public void processFileChanges() {
        if (!this.dqoCacheSpecConfigurationProperties.isWatchFileSystemChanges() || this.watchService == null) {
            return;
        }

        Instant nextFileChangeDetectionAt;
        synchronized (this.directoryWatchersLock) {
            nextFileChangeDetectionAt = this.nextFileChangeDetectionAt;
        }

        if (nextFileChangeDetectionAt.isAfter(Instant.now())) {
            return;
        }

        WatchKey watchKey;
        while ((watchKey = this.watchService.poll()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                Path filePath = (Path) event.context();
                invalidateFile(filePath);
                invalidateFolder(filePath); // we don't know if it is a file or a folder
            }
            watchKey.reset();
        }

        synchronized (this.directoryWatchersLock) {
            this.nextFileChangeDetectionAt = Instant.now().plus(
                    this.dqoCacheSpecConfigurationProperties.getProcessFileChangesDelayMs(), ChronoUnit.MILLIS);
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
        if (this.dqoCacheSpecConfigurationProperties.isEnable()) {
            this.startFolderWatcher(folderPath);
            this.processFileChanges();
            return this.foldersCache.get(folderPath, listingFunction);
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
        if (this.dqoCacheSpecConfigurationProperties.isEnable()) {
            this.startFolderWatcher(folderPath);
            this.processFileChanges();
            return this.filesCache.get(folderPath, listingFunction);
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
        if (this.dqoCacheSpecConfigurationProperties.isEnable()) {
            this.startFolderWatcher(filePath.getParent());
            this.processFileChanges();
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
        if (!this.dqoCacheSpecConfigurationProperties.isEnable()) {
            return;
        }

        this.startFolderWatcher(filePath.getParent());
        this.textFilesCache.put(filePath, fileContent);
        Path parentFolderPath = filePath.getParent();
        this.filesCache.invalidate(parentFolderPath);
    }

    /**
     * Removes a file from the cache. Invalidates the cached list of files from the parent folder.
     * @param key Cache key.
     */
    @Override
    public void removeFile(Path key) {
        this.textFilesCache.invalidate(key);
        Path parentFolderPath = key.getParent();
        this.filesCache.invalidate(parentFolderPath);
    }

    /**
     * Removes a folder from the cache. Invalidates the cached list of folders from the parent folder.
     * @param folderPath Cache key.
     */
    @Override
    public void removeFolder(Path folderPath) {
        this.foldersCache.invalidate(folderPath);
        this.filesCache.invalidate(folderPath);

        this.stopFolderWatcher(folderPath);

        if (folderPath.getNameCount() > 1) {
            Path parentFolderPath = folderPath.getParent();
            this.filesCache.invalidate(parentFolderPath);
        }
    }

    /**
     * Invalidates a list of files and folders in a given folder.
     * @param folderPath Folder path.
     */
    @Override
    public void invalidateFolder(Path folderPath) {
        this.foldersCache.invalidate(folderPath);
        this.filesCache.invalidate(folderPath);
    }

    /**
     * Invalidates a file and a list of files in the parent folder.
     * @param filePath File path.
     */
    @Override
    public void invalidateFile(Path filePath) {
        this.textFilesCache.invalidate(filePath);

        Path folderPath = filePath.getParent();
        if (folderPath != null) {
            this.foldersCache.invalidate(folderPath);
            this.filesCache.invalidate(folderPath);
        }
    }

    /**
     * Invalidates all entries in the cache.
     */
    @Override
    public void invalidateAll() {
        this.foldersCache.invalidateAll();
        this.filesCache.invalidateAll();
        this.textFilesCache.invalidateAll();

        if (this.dqoCacheSpecConfigurationProperties.isWatchFileSystemChanges() && this.watchService != null) {
            synchronized (this.directoryWatchersLock) {
                for (WatchKey watchKey : this.directoryWatchers.values()) {
                    watchKey.cancel();
                }

                this.directoryWatchers.clear();
            }
        }
    }
}
