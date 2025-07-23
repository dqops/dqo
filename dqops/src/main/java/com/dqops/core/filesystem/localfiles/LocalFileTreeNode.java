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

import com.dqops.core.filesystem.virtual.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;

/**
 * Virtual file tree node in the virtual file system.
 */
public class LocalFileTreeNode extends FileTreeNode {
    @JsonIgnore
    private FileSystemContext context;

    public LocalFileTreeNode() {
    }

    /**
     * Creates a file tree node given a file system context and a file path.
     * @param context
     * @param filePath
     */
    public LocalFileTreeNode(FileSystemContext context, HomeFilePath filePath) {
        super(filePath);
        this.context = context;
    }

    /**
     * Creates a file tree node with a file content and file kind.
     * @param context File system context to read and save the file content.
     * @param filePath Virtual file path.
     * @param content File content.
     * @param status File status.
     */
    public LocalFileTreeNode(FileSystemContext context, HomeFilePath filePath, FileContent content, FileTreeNodeStatus status) {
        super(filePath, content, status);
        this.context = context;
    }

    /**
     * Retrieves the file content. A local file system implementation will load the file from the file system.
     * The file content is cached in the node for any following calls.
     * @return File content loaded from the file.
     */
    @Override
    public FileContent getContent() {
        if (super.getContent() == null) {
            LocalFileStorageService storageService = this.context.getStorageService();
            FileContent fileContent = storageService.readFile(this.getFilePath(), true);
			this.setContent(fileContent);
			this.setStatus(FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        }
        return super.getContent();
    }

    /**
     * Flushes any changes (file added, modified, deleted) to the file system.
     */
    @Override
    public void flush() {
        switch (this.getStatus()) {
            case NOT_LOADED:
            case LOADED_NOT_MODIFIED:
                break;
            case NEW:
            case MODIFIED:
				this.context.getStorageService().saveFile(this.getFilePath(), this.getContent(), true);
				this.setStatus(FileTreeNodeStatus.LOADED_NOT_MODIFIED);
                break;
            case TO_BE_DELETED:
				this.context.getStorageService().deleteFile(this.getFilePath(), true);
				this.setContent(null);
				this.setStatus(FileTreeNodeStatus.DELETED);
                break;
        }
    }

    /**
     * Checks if the file three node is using a local file system (returns true) or only a virtual file system and all files are in memory.
     * Virtual file system does not have files stored on the disk and jinja2 templates cannot be evaluated by providing a path to the file.
     *
     * @return True when the file node is backed by a real file system (local disk). False when it is just a virtual, in-memory file node without any disk storage.
     */
    @Override
    public boolean isLocalFileSystem() {
        return true;
    }

    /**
     * Returns a physical path to the file. Returns a non-null object only when the file is using a real file system (local file system, disk based).
     * Returns null when the file uses just an in-memory virtual file system.
     *
     * @return Physical, absolute file path to the file.
     */
    @Override
    public Path getPhysicalAbsolutePath() {
        String homePath = this.context.getStorageService().getHomeRootDirectory();
        if (homePath == null) {
            return null;
        }

        Path absolutePath = Path.of(homePath).resolve(this.getFilePath().toRelativePath()).toAbsolutePath();
        return absolutePath;
    }
}
