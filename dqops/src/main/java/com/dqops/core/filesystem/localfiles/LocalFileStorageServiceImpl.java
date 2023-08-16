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

import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FolderName;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.metadata.storage.localfiles.HomeType;
import org.springframework.beans.factory.BeanInitializationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Local folder access service, manages files in a given folder. Manages files on the disk in the given file system folder.
 */
public class LocalFileStorageServiceImpl implements LocalFileStorageService {
    private final String homeRootDirectory;
    private final Path homePath;
    private final HomeType homeType;
    private final LocalFileSystemCache localFileSystemCache;

    /**
     * Creates a local storage service that manages files in the given folder in the file system.
     * @param homeRootDirectory Path to the root file folder on the local machine that stores the files.
     * @param homeType DQO Home type (dqo system home or user home).
     */
    public LocalFileStorageServiceImpl(String homeRootDirectory,
                                       HomeType homeType,
                                       LocalFileSystemCache localFileSystemCache) {
        this.homeRootDirectory = homeRootDirectory;
        this.homePath = Path.of(homeRootDirectory).toAbsolutePath().normalize();
        this.homeType = homeType;
        this.localFileSystemCache = localFileSystemCache;
    }

    /**
     * Returns a path to the home folder on the local machine.
     * @return Path to the home folder.
     */
    public String getHomeRootDirectory() {
        return homeRootDirectory;
    }

    /**
     * Tries to initialize (create) a folder.
     *
     * @param path Path to the folder.
     */
    public void initializeEmptyFolder(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception ex) {
                throw new LocalFileSystemException("Cannot create a folder " + path, ex);
            }
        } else {
            if (!Files.isDirectory(path)) {
                throw new BeanInitializationException("Local home cannot be initialized because " + path + " already exists and it is a file, not a folder.");
            }
        }
    }

    /**
     * Checks if a file exist.
     *
     * @param filePath File path relative to the home root.
     * @return True when the file is present, false when the file is missing.
     */
    @Override
    public boolean fileExists(HomeFilePath filePath) {
        Path relativeFilePath = filePath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();

        return Files.exists(absolutePath) && !Files.isDirectory(absolutePath);
    }

    /**
     * Checks if a folder exists.
     *
     * @param folderPath Folder path relative to the home root.
     * @return True when the folder is present, false when the folder is missing.
     */
    @Override
    public boolean folderExists(HomeFolderPath folderPath) {
        Path relativeFilePath = folderPath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();

        return Files.exists(absolutePath) && Files.isDirectory(absolutePath);
    }

    /**
     * Tries to delete a folder.
     *
     * @param folderPath Relative folder path.
     * @return True when the folder was deleted, false when there were some issues (folder in use) or the folder does not exist.
     */
    @Override
    public boolean tryDeleteFolder(HomeFolderPath folderPath) {
        Path relativeFilePath = folderPath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();

        if (!Files.exists(absolutePath) || !Files.isDirectory(absolutePath)) {
            return false;
        }

        try {
            Files.delete(absolutePath);
            this.localFileSystemCache.removeFolder(absolutePath);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Reads a text file given the file name components.
     *
     * @param filePath File path relative to the home root.
     * @return File content or null when the file was not found.
     */
    @Override
    public FileContent readTextFile(HomeFilePath filePath) {
        Path relativeFilePath = filePath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();
        FileContent fileContent = this.localFileSystemCache.loadFileContent(absolutePath, key -> this.readTextFileDirect(absolutePath));
        return fileContent;
    }

    /**
     * Reads a text file given the file name components. Skips the cache.
     *
     * @param absolutePath File path relative to the home root.
     * @return File content or null when the file was not found.
     */
    public FileContent readTextFileDirect(Path absolutePath) {
        try {
            if (!Files.exists(absolutePath)) {
                return null;
            }

            String textContent = Files.readString(absolutePath, StandardCharsets.UTF_8);
            FileTime lastModifiedFileTime = Files.getLastModifiedTime(absolutePath);
            Instant lastModifiedInstant = lastModifiedFileTime.toInstant();
            return new FileContent(textContent, lastModifiedInstant);
        } catch (Exception ex) {
            throw new LocalFileSystemException("Cannot read a text file", ex);
        }
    }

    /**
     * Save a file to the user home folder.
     *
     * @param filePath    Relative file path inside the home folder.
     * @param fileContent File content.
     */
    @Override
    public void saveFile(HomeFilePath filePath, FileContent fileContent) {
        try {
            Path relativeFilePath = filePath.toRelativePath();
            Path absoluteFilePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();
            Path parentFolderPath = this.homePath.resolve(filePath.getFolder().toRelativePath()).toAbsolutePath();

            if (Files.notExists(parentFolderPath)) {
                Files.createDirectories(parentFolderPath);
            }

            String textContent = fileContent.getTextContent();
            if (textContent != null) {
                Files.writeString(absoluteFilePath, textContent, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                FileTime lastModifiedFileTime = Files.getLastModifiedTime(absoluteFilePath);
                Instant lastModifiedInstant = lastModifiedFileTime.toInstant();
                fileContent.setLastModified(lastModifiedInstant);

                this.localFileSystemCache.storeTextFile(absoluteFilePath, fileContent);
            } else {
                throw new LocalFileSystemException("File content type not supported");
            }
        } catch (Exception ex) {
            throw new LocalFileSystemException("Cannot write a file", ex);
        }
    }

    /**
     * Deletes a file given the path.
     *
     * @param filePath Relative file path inside the home folder.
     * @return True when the file was present and was deleted, false when the file was missing.
     */
    @Override
    public boolean deleteFile(HomeFilePath filePath) {
        try {
            Path relativeFilePath = filePath.toRelativePath();
            Path absolutePath = this.homePath.resolve(relativeFilePath).toAbsolutePath();
            boolean fileDeleted = Files.deleteIfExists(absolutePath);
            this.localFileSystemCache.removeFile(absolutePath);
            return fileDeleted;
        } catch (Exception ex) {
            throw new LocalFileSystemException("Cannot delete a file", ex);
        }
    }

    /**
     * Lists direct subfolders inside a given folder.
     *
     * @param folderPath Path elements to the folder whose content will be listed.
     * @return List of folder paths that are relative to the user home folder.
     */
    @Override
    public List<HomeFolderPath> listFolders(HomeFolderPath folderPath) {
        Path relativeFolderPath = folderPath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFolderPath).toAbsolutePath();

        List<HomeFolderPath> listOfFolders = this.localFileSystemCache.getListOfFolders(absolutePath, key -> this.listFoldersDirect(folderPath, key));
        return listOfFolders;
    }

    /**
     * Lists direct subfolders inside a given folder skipping the cache.
     *
     * @param folderPath Path elements the folder whose content will be listed.
     * @param absolutePath Path to the folder whose content will be listed.
     * @return List of folder paths that are relative to the user home folder.
     */
    public List<HomeFolderPath> listFoldersDirect(HomeFolderPath folderPath, Path absolutePath) {
        try {
            if (!Files.exists(absolutePath)) {
                return null;
            }

            try (Stream<Path> fileList = Files.list(absolutePath)) {
                List<HomeFolderPath> folders = fileList
                        .filter(path -> Files.isDirectory(path))
                        .map(path -> {
                            String fileSystemName = path.getFileName().toString();
                            FolderName subFolder = FolderName.fromFileSystemName(fileSystemName);
                            return folderPath.resolveSubfolder(subFolder);
                        })
                        .collect(Collectors.toList());

                return folders;
            }
        } catch (Exception ex) {
            throw new LocalFileSystemException("Cannot list a folder: " + folderPath.toString(), ex);
        }
    }

    /**
     * Lists direct files inside a given folder.
     *
     * @param folderPath Path to the folder that will be listed.
     * @return List of file paths that are relative to the user home folder.
     */
    @Override
    public List<HomeFilePath> listFiles(HomeFolderPath folderPath) {
        Path relativeFolderPath = folderPath.toRelativePath();
        Path absolutePath = this.homePath.resolve(relativeFolderPath).toAbsolutePath();
        List<HomeFilePath> listOfFiles = this.localFileSystemCache.getListOfFiles(absolutePath, key -> this.listFilesDirect(folderPath, key));
        return listOfFiles;
    }

    /**
     * Lists direct files inside a given folder directly from the disk, skipping the cache.
     *
     * @param folderPath Path to the folder that will be listed.
     * @return List of file paths that are relative to the user home folder.
     */
    public List<HomeFilePath> listFilesDirect(HomeFolderPath folderPath, Path absolutePath) {
        try {
            if (!Files.exists(absolutePath)) {
                return null;
            }

            try (Stream<Path> pathStream = Files.list(absolutePath)) {
                List<HomeFilePath> filePathsList = pathStream
                        .filter(path -> !Files.isDirectory(path))
                        .map(path -> {
                            String fileSystemName = path.getFileName().toString();
                            return folderPath.resolveFile(fileSystemName);
                        })
                        .collect(Collectors.toList());

                return filePathsList;
            }
        } catch (Exception ex) {
            throw new LocalFileSystemException("Cannot list a folder: " + folderPath.toString(), ex);
        }
    }
}
