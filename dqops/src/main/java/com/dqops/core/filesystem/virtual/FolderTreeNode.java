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
package com.dqops.core.filesystem.virtual;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.*;

/**
 * Folder node in a virtual file system tree.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class FolderTreeNode implements Cloneable {
    private HomeFolderPath folderPath;
    private List<FolderTreeNode> subFolders = new ArrayList<>();
    private List<FileTreeNode> files = new ArrayList<>();
    private FolderKind kind = FolderKind.UNKNOWN;
    private boolean deleteOnFlush;
    @JsonIgnore
    private boolean loaded;

    public FolderTreeNode() {
    }

    /**
     * Creates a folder node for a given virtual folder.
     * @param folderPath Virtual folder path.
     */
    public FolderTreeNode(HomeFolderPath folderPath) {
        this.folderPath = folderPath;

        if (folderPath.isEmpty()) {
			this.kind = FolderKind.HOME;
        }
        else {
			this.kind = FolderKind.UNKNOWN;
        }
    }

    /**
     * Cretes a folder node for a folder path and a provided folder kind.
     * @param folderPath Virtual folder path.
     * @param kind Folder kind.
     */
    public FolderTreeNode(HomeFolderPath folderPath, FolderKind kind) {
        this.folderPath = folderPath;
        this.kind = kind;
    }

    /**
     * Creates a folder three node for the root home folder.
     * @return Folder tree node (empty)
     */
    public static FolderTreeNode createRootFolderNode() {
        FolderTreeNode folderTreeNode = new FolderTreeNode(new HomeFolderPath(DqoUserIdentity.DEFAULT_DATA_DOMAIN));
        folderTreeNode.loadOnce();
        return folderTreeNode;
    }

    /**
     * Returns a list of subfolders. This method should be used by the serializer and deserializer only.
     * @return List of folder nodes.
     */
    public List<FolderTreeNode> getSubFolders() {
		this.loadOnce();
        return subFolders;
    }

    /**
     * Sets the list of subfolders. This method should be called only by the subclasses and the serializer.
     * @param subFolders New list of subfolders.
     */
    public void setSubFolders(List<FolderTreeNode> subFolders) {
        this.subFolders = subFolders;
    }

    /**
     * List all folders and deeply nested subfolders (at any depth) that contain any file with a given file extension.
     * Every folder (also deeply nested) that has at least one file whose name ends with the fileNameExtension is returned.
     * @param fileNameExtension File extension pattern, should be like ".dqoconn.yaml".
     * @param includeCurrentFolder True when the current folder should be also added to the results if it has files with a matching pattern.
     * @return List of subfolders with a file that match the file name extension.
     */
    public List<FolderTreeNode> findNestedSubFoldersWithFiles(String fileNameExtension, boolean includeCurrentFolder) {
        ArrayList<FolderTreeNode> folderTreeNodes = new ArrayList<>();

        if (includeCurrentFolder) {
            List<FileTreeNode> filesInFolder = this.getFiles();
            // check if the folder has a file that we are looking for
            for (FileTreeNode fileInSubFolder : filesInFolder) {
                if (fileInSubFolder.getFilePath().getFileName().endsWith(fileNameExtension)) {
                    // folder has a file that we were looking for
                    folderTreeNodes.add(this);
                    break;
                }
            }
        }

        for (FolderTreeNode subFolderNode : getSubFolders()) {
            // iterate sub folders
            List<FolderTreeNode> nestedSubFoldersWithFiles = subFolderNode.findNestedSubFoldersWithFiles(fileNameExtension, true);
            folderTreeNodes.addAll(nestedSubFoldersWithFiles);
        }

        return folderTreeNodes;
    }

    /**
     * Returns a list of files. This method should be used by subclases and the serializer.
     * @return List of files.
     */
    public List<FileTreeNode> getFiles() {
		this.loadOnce();
        return files;
    }

    /**
     * Sets a list of files.
     * @param files List of files.
     */
    public void setFiles(List<FileTreeNode> files) {
        this.files = files;
    }

    /**
     * Returns the folder kind.
     * @return Folder kind.
     */
    public FolderKind getKind() {
        return kind;
    }

    /**
     * Changes the folder kind. This method does not need to be called because the folder kind should be evaluated from the parent folder kind.
     * @param kind Folder kind.
     */
    public void setKind(FolderKind kind) {
        this.kind = kind;
    }

    /**
     * Gets the flag if the folder should be deleted on flush.
     * @return The folder should be deleted on flush.
     */
    public boolean isDeleteOnFlush() {
        return deleteOnFlush;
    }

    /**
     * Sets the flag that the folder should be deleted on flush.
     * @param deleteOnFlush True when the folder should be deleted on flush.
     */
    public void setDeleteOnFlush(boolean deleteOnFlush) {
        this.deleteOnFlush = deleteOnFlush;
    }

    /**
     * Returns the folder path that is relative to the root of the home folder.
     * @return Folder path.
     */
    public HomeFolderPath getFolderPath() {
        return folderPath;
    }

    /**
     * Sets the relative folder path for this node.
     * @param folderPath Folder path.
     */
    public void setFolderPath(HomeFolderPath folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * Returns the top most folder name of the current folder.
     * @return Folder name for the top most folder or null if it is the root home folder.
     */
    @JsonIgnore
    public FolderName getFolderName() {
        if (this.folderPath.size() == 0) {
            return null; // root folder
        }

        return this.folderPath.getTopFolder(); // returns the top most folder (the current folder) from the path
    }

    /**
     * Derives a folder kind for a subfolder that is specific to the folder kind of the current folder.
     * @param folderName Child folder name.
     * @return Child folder kind.
     */
    protected FolderKind deriveChildFolderKind(FolderName folderName) {
        switch (this.kind) {
            case HOME:
                if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.SOURCES)) {
                    return FolderKind.SOURCES;
                }
                else  if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.SENSORS)) {
                    return FolderKind.SENSORS;
                }
                else  if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.RULES)) {
                    return FolderKind.RULES;
                }
                else  if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.CHECKS)) {
                    return FolderKind.CHECKS;
                }
                else  if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.SETTINGS)) {
                    return FolderKind.SETTINGS;
                }
                else  if (Objects.equals(folderName.getFileSystemName(), BuiltInFolderNames.CREDENTIALS)) {
                    return FolderKind.CREDENTIALS;
                }
                return FolderKind.FOREIGN;
            case CHECKS:
                return FolderKind.CHECK_SUBFOLDER;
            case SOURCES:
                return FolderKind.SOURCE;
            case SOURCE:
                return FolderKind.SOURCE_SUBFOLDER;
            case RULES:
                return FolderKind.RULES_SUBFOLDER;
            case SETTINGS:
                return FolderKind.SETTINGS_SUBFOLDER;
            case CREDENTIALS:
                return FolderKind.CREDENTIALS_SUBFOLDER;
            default:
                return FolderKind.UNKNOWN;
        }
    }

    /**
     * Loads a list of subfolders and files in the current folder. This method calls the real {@link #loadChildFilesAndFolders()}
     * method and loads the list only on the first call.
     */
    public void loadOnce() {
        if (this.loaded) {
            return;
        }

		this.loaded = true;
		this.loadChildFilesAndFolders();
    }

    /**
     * Loads a list of folders and files on the file system. This method may be called multiple times because it will
     * scan the file system only on the first call. Derived classes should add their own implementation for scanning the file system.
     */
    public void loadChildFilesAndFolders() {
        if (this.subFolders == null) {
			this.subFolders = new ArrayList<>();
        }

        if (this.files == null) {
			this.files = new ArrayList<>();
        }
    }

    /**
     * Finds an existing folder name using an original (unsanitized) folder name.
     * @param objectName Unsafe (not sanitized for the file system) folder name.
     * @return Folder node when the folder exists or null when the folder must be registered by a call to {@link #getOrAddDirectFolder}.
     */
    public FolderTreeNode getChildFolder(String objectName) {
		this.loadOnce(); // load files and folders for the first time (list nested files and folders)

        Optional<FolderTreeNode> first = this.subFolders.stream()
                .filter(n -> Objects.equals(n.getFolderPath().getTopFolder().getObjectName(), objectName))
                .findFirst();

        if (first.isPresent()) {
            return first.get();
        }

        return null;
    }

    /**
     * Gets or adds a virtual child folder. The folder is not created on the file system until files are saved.
     * The folder name must be just a folder name.
     * @param subFolderName Unsafe folder name. The name will be sanitized before a folder is created in the file system.
     * @return Folder tree node.
     */
    public FolderTreeNode getOrAddDirectFolder(String subFolderName) {
        assert subFolderName.indexOf('/') < 0 && subFolderName.indexOf('\\') < 0; // folder separator not supported, use getOrAddFolderPath for folder paths

        Optional<FolderTreeNode> first = this.subFolders.stream()
                .filter(treeNode -> Objects.equals(treeNode.getFolderPath().getTopFolder().getObjectName(), subFolderName))
                .findFirst();

        if (first.isPresent()) {
           return first.get();
        }

        HomeFolderPath newFolderPath = this.folderPath.resolveSubfolder(FolderName.fromObjectName(subFolderName));
        FolderKind subFolderKind = deriveChildFolderKind(newFolderPath.getTopFolder());
        FolderTreeNode newFolderNode = createFolderTreeNode(newFolderPath, subFolderKind);
		this.subFolders.add(newFolderNode);
        return newFolderNode;
    }

    /**
     * Gets or adds a virtual child folder. The folder is not created on the file system until files are saved.
     * The folder name could be a path of subfolders like "parent/child/grandchild".
     * @param nestedFolderPath Unsafe folder path. The name will be sanitized before a folder is created in the file system.
     * @return Folder tree node.
     */
    public FolderTreeNode getOrAddFolderPath(String nestedFolderPath) {
        String[] pathComponents = StringUtils.split(nestedFolderPath, '/');
        FolderTreeNode folderNode = this;
        for (int i = 0; i < pathComponents.length; i++) {
            String pathComponent = pathComponents[i];
            folderNode = folderNode.getOrAddDirectFolder(pathComponent);
        }

        return folderNode;
    }

    /**
     * Creates a file system specific folder node for a new child folder. This method should be overridden in subclasses.
     * @param newFolderPath Folder path.
     * @param folderKind Folder kind.
     * @return Folder tree node.
     */
    protected FolderTreeNode createFolderTreeNode(HomeFolderPath newFolderPath, FolderKind folderKind) {
        return new FolderTreeNode(newFolderPath, folderKind);
    }

    /**
     * Finds an existing file in the folder given a physical (sanitized) file name.
     * @param fileName File name to be found.
     * @return File tree node when the file was found or null when it was missing.
     */
    public FileTreeNode getChildFileByFileName(String fileName) {
		this.loadOnce(); // load files and folders for the first time (list nested files and folders)

        Optional<FileTreeNode> first = this.files.stream()
                .filter(treeNode -> Objects.equals(treeNode.getFilePath().getFileName(), fileName))
                .findFirst();

        if (first.isPresent()) {
            return first.get();
        }

        return null;
    }

    /**
     * Adds a virtual file to the current folder. The file is not saved until a call to the {@link #flush()}.
     * The new file has a status {@link FileTreeNodeStatus#NEW} and is marked as a new file that must be saved to disk.
     * @param fileName Physical file name of the new file.
     * @param content File content.
     * @return Virtual file tree node.
     */
    public FileTreeNode addChildFile(String fileName, FileContent content) {
		this.loadOnce(); // load files and folders for the first time (list nested files and folders)

        Optional<FileTreeNode> first = this.files.stream()
                .filter(treeNode -> Objects.equals(treeNode.getFilePath().getFileName(), fileName))
                .findFirst();

        if (first.isPresent()) {
            FileTreeNode existingFileTreeNode = first.get();
            existingFileTreeNode.changeContent(content);
            return existingFileTreeNode;
        }

        HomeFilePath newFilePath = this.folderPath.resolveFile(fileName);
        final FileTreeNodeStatus fileStatus = FileTreeNodeStatus.NEW;
        FileTreeNode newFileNode = createFileTreeNode(newFilePath, content, fileStatus);
		this.files.add(newFileNode);
        return newFileNode;
    }

    /**
     * Creates a file system specific file node for a new child file. This method should be overridden in subclasses.
     * @param newFilePath File path to a child file.
     * @param content Initial file content.
     * @param status File status.
     * @return Folder tree node.
     */
    protected FileTreeNode createFileTreeNode(HomeFilePath newFilePath, FileContent content, FileTreeNodeStatus status) {
        return new FileTreeNode(newFilePath, content, status);
    }

    /**
     * Marks a file for a delayed deletion. Even if a file is not present, it will be added as a virtual note that is awaiting deletion.
     * @param fileName Physical file name of an existing file in the file system.
     * @return True when the file was present and was marked for deletion, false when it was missing or already deleted.
     */
    public boolean deleteChildFile(String fileName) {
		this.loadOnce(); // load files and folders for the first time (list nested files and folders)

        Optional<FileTreeNode> first = this.files.stream()
                .filter(treeNode -> Objects.equals(treeNode.getFilePath().getFileName(), fileName))
                .findFirst();

        if (!first.isPresent()) {
            // register a file for delayed deletion, but mark it as awaiting deletion
            HomeFilePath newFilePath = this.folderPath.resolveFile(fileName);
            final FileTreeNodeStatus fileStatus = FileTreeNodeStatus.TO_BE_DELETED;
            FileTreeNode deletedNode = createFileTreeNode(newFilePath, null, fileStatus);
			this.files.add(deletedNode);
            return false;
        }

        FileTreeNode existingFileNode = first.get();
        if (existingFileNode.getStatus() == FileTreeNodeStatus.DELETED ||
            existingFileNode.getStatus() == FileTreeNodeStatus.TO_BE_DELETED) {
            return false; // double deleted means deleted for sure
        }

        existingFileNode.markForDeletion();
        return true;
    }

    /**
     * Flushes all registered changes to the file system. Modified or new files are written, deleted files are deleted in the file system.
     */
    public void flush() {
        if (this.subFolders != null) {
            ListIterator<FolderTreeNode> folderIterator = this.subFolders.listIterator();
            while (folderIterator.hasNext()) {
                FolderTreeNode folderNode = folderIterator.next();
                folderNode.flush();

                if (folderNode.isDeleteOnFlush()) {
                    folderIterator.remove();
                }
            }
        }

        if (this.files != null) {
            for(FileTreeNode fileNode : this.files) {
                fileNode.flush();
            }

            // remove deleted files from the tree
            new ArrayList<>(this.files).stream()
                    .filter(node -> node.getStatus() == FileTreeNodeStatus.DELETED)
                    .forEach(node -> this.files.remove(node));
        }
    }

    /**
     * Checks if the folder three node is using a local file system (returns true) or only a virtual file system and all files are in memory.
     * Virtual file system does not have files stored on the disk and jinja2 templates cannot be evaluated by providing a path to the file.
     * @return True when the folder node is backed by a real file system (local disk). False when it is just a virtual, in-memory folder node without any disk storage.
     */
    public boolean isLocalFileSystem() {
        return false;
    }

    /**
     * Returns a physical path to the folder. Returns a non-null object only when the folder is using a real file system (local file system, disk based).
     * Returns null when the folder uses just an in-memory virtual file system.
     * @return Physical, absolute file path to the folder.
     */
    public Path getPhysicalAbsolutePath() {
        return null;
    }

    @Override
    public String toString() {
        return "FolderTreeNode{" +
                "folderPath=" + folderPath +
                '}';
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public FolderTreeNode clone() {
        try {
            FolderTreeNode cloned = (FolderTreeNode) super.clone();
            cloned.files = new ArrayList<>();

            if (this.files != null && this.files.size() > 0) {
                for (FileTreeNode fileNode : this.files) {
                    cloned.files.add(fileNode.clone());
                }
            }

            cloned.subFolders = new ArrayList<>();
            if (this.subFolders != null && this.subFolders.size() > 0) {
                for (FolderTreeNode folderTreeNode : this.subFolders) {
                    cloned.subFolders.add(folderTreeNode.clone());
                }
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
