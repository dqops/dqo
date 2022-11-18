/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.filesystem.localfiles;

import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.core.filesystem.virtual.FolderName;
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import org.springframework.beans.factory.BeanInitializationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Local folder access service, manages files in a given folder. Manages files in the on the disk in the given file system folder.
 */
public class LocalFileStorageServiceImpl implements LocalFileStorageService {
    private final String homePath;

    /**
     * Creates a local storage service that manages files in the given folder in the file system.
     * @param homePath Path to the root file folder on the local machine that stores the files.
     */
    public LocalFileStorageServiceImpl(String homePath) {
        this.homePath = homePath;
    }

    /**
     * Returns a path to the home folder on the local machine.
     * @return Path to the home folder.
     */
    public String getHomePath() {
        return homePath;
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
        Path homePath = Path.of(this.homePath);
        Path absolutePath = homePath.resolve(relativeFilePath).toAbsolutePath();

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
        Path homePath = Path.of(this.homePath);
        Path absolutePath = homePath.resolve(relativeFilePath).toAbsolutePath();

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
        Path homePath = Path.of(this.homePath);
        Path absolutePath = homePath.resolve(relativeFilePath).toAbsolutePath();

        if( !Files.exists(absolutePath) || !Files.isDirectory(absolutePath)) {
            return false;
        }

        try {
            Files.delete(absolutePath);
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
        try {
            Path relativeFilePath = filePath.toRelativePath();
            Path homePath = Path.of(this.homePath);
            Path absolutePath = homePath.resolve(relativeFilePath).toAbsolutePath();

            if (!Files.exists(absolutePath)) {
                return null;
            }

            String textContent = Files.readString(absolutePath, StandardCharsets.UTF_8);
            return new FileContent(textContent);
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
            Path homePath = Path.of(this.homePath);
            Path absoluteFilePath = homePath.resolve(relativeFilePath).toAbsolutePath();
            Path parentFolderPath = homePath.resolve(filePath.getFolder().toRelativePath()).toAbsolutePath();

            if (Files.notExists(parentFolderPath)) {
                Files.createDirectories(parentFolderPath);
            }

            String textContent = fileContent.getTextContent();
            if (textContent != null) {
                Files.writeString(absoluteFilePath, textContent, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
            Path homePath = Path.of(this.homePath);
            Path absolutePath = homePath.resolve(relativeFilePath).toAbsolutePath();
            return Files.deleteIfExists(absolutePath);
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
        try {
            Path relativeFolderPath = folderPath.toRelativePath();
            Path homePath = Path.of(this.homePath);
            Path absolutePath = homePath.resolve(relativeFolderPath).toAbsolutePath();

            if (!Files.exists(absolutePath)) {
                return null;
            }

            try(Stream<Path> fileList = Files.list(absolutePath)) {
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
        try {
            Path relativeFolderPath = folderPath.toRelativePath();
            Path homePath = Path.of(this.homePath);
            Path absolutePath = homePath.resolve(relativeFolderPath).toAbsolutePath();

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
