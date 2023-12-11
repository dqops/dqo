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

import com.dqops.core.filesystem.virtual.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.parquet.Strings;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Folder node in a virtual file system tree. This implementation performs real I/O operations on the home folder.
 */
public class LocalFolderTreeNode extends FolderTreeNode {
    @JsonIgnore
    private FileSystemContext context;

    public LocalFolderTreeNode() {
    }

    public LocalFolderTreeNode(FileSystemContext context, HomeFolderPath folderPath) {
        super(folderPath);
        this.context = context;
    }

    public LocalFolderTreeNode(FileSystemContext context, HomeFolderPath folderPath, FolderKind kind) {
        super(folderPath, kind);
        this.context = context;
    }

    /**
     * Returns a file system context that separates the implementation from the file system I/O code.
     * @return File system context.
     */
    public FileSystemContext getContext() {
        return context;
    }

    /**
     * Setsa file system context.
     * @param context New file system context.
     */
    public void setContext(FileSystemContext context) {
        this.context = context;
    }

    /**
     * Loads a list of folders and files on the file system. This method may be called multiple times because it will
     * scan the file system only on the first call.
     */
    @Override
    public void loadChildFilesAndFolders() {
        super.loadChildFilesAndFolders();

        List<HomeFolderPath> subFolders = this.context.getStorageService().listFolders(this.getFolderPath());
        List<HomeFilePath> files = this.context.getStorageService().listFiles(this.getFolderPath());

        if (subFolders != null && subFolders.size() > 0) {
            Set<String> existingSubFolders = this.getSubFolders().stream().map(f -> f.getFolderName().getObjectName()).collect(Collectors.toSet());

			this.getSubFolders().addAll(subFolders.stream()
                    .filter(path -> !existingSubFolders.contains(path.getTopFolder().getObjectName()))
                    .map(path -> new LocalFolderTreeNode(this.context, path, deriveChildFolderKind(path.getTopFolder())))
                    .collect(Collectors.toList()));
        }

        if (files != null && files.size() > 0) {
            Set<String> existingFiles = this.getFiles().stream().map(f -> f.getFilePath().getFileName()).collect(Collectors.toSet());

			this.getFiles().addAll(files.stream()
                    .filter(path -> !existingFiles.contains(path.getFileName()))
                    .map(path -> new LocalFileTreeNode(this.context, path))
                    .collect(Collectors.toList()));
        }
    }

    /**
     * Creates a file system specific folder node for a new child folder. This method should be overridden in subclasses.
     *
     * @param newFolderPath Folder path.
     * @param folderKind    Folder kind.
     * @return Folder tree node.
     */
    @Override
    protected FolderTreeNode createFolderTreeNode(HomeFolderPath newFolderPath, FolderKind folderKind) {
        return new LocalFolderTreeNode(this.context, newFolderPath, folderKind);
    }

    /**
     * Creates a file system specific file node for a new child file. This method should be overridden in subclasses.
     *
     * @param newFilePath File path to a child file.
     * @param content     Initial file content.
     * @param status      File status.
     * @return Folder tree node.
     */
    @Override
    protected FileTreeNode createFileTreeNode(HomeFilePath newFilePath, FileContent content, FileTreeNodeStatus status) {
        return new LocalFileTreeNode(this.context, newFilePath, content, status);
    }

    /**
     * Flushes all registered changes to the file system. Modified or new files are written, deleted files are deleted in the file system.
     */
    @Override
    public void flush() {
        super.flush();
        if (this.isDeleteOnFlush()) {
			this.context.getStorageService().tryDeleteFolder(this.getFolderPath());
        }
    }

    /**
     * Checks if the folder three node is using a local file system (returns true) or only a virtual file system and all files are in memory.
     * Virtual file system does not have files stored on the disk and jinja2 templates cannot be evaluated by providing a path to the file.
     *
     * @return True when the folder node is backed by a real file system (local disk). False when it is just a virtual, in-memory folder node without any disk storage.
     */
    @Override
    public boolean isLocalFileSystem() {
        return true;
    }

    /**
     * Returns a physical path to the folder. Returns a non-null object only when the folder is using a real file system (local file system, disk based).
     * Returns null when the folder uses just an in-memory virtual file system.
     *
     * @return Physical, absolute file path to the folder.
     */
    @Override
    public Path getPhysicalAbsolutePath() {
        String homePath = this.context.getStorageService().getHomeRootDirectory();
        if (homePath == null) {
            return null;
        }

        Path directPath = Path.of(homePath);
        if (!this.getFolderPath().isEmpty() || !Strings.isNullOrEmpty(this.getFolderPath().getDataDomain())) {
            directPath = directPath.resolve(this.getFolderPath().toRelativePath());
        }

        return directPath.toAbsolutePath();
    }
}
