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
package com.dqops.core.filesystem.metadata;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudLimit;
import com.dqops.data.storage.HivePartitionPathUtility;
import com.dqops.utils.serialization.PathAsStringJsonDeserializer;
import com.dqops.utils.serialization.PathAsStringJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Folder metadata with a list of files and folders.
 */
public class FolderMetadata implements Cloneable {
    @JsonSerialize(using = PathAsStringJsonSerializer.class)
    @JsonDeserialize(using = PathAsStringJsonDeserializer.class)
    @JsonProperty("p")
    private Path relativePath;

    @JsonProperty("s")
    private long statusCheckedAt;

    @JsonProperty("a")
    private boolean allChildrenScanned;

    @JsonProperty("d")
    private FolderMetadataMap folders = new FolderMetadataMap();

    @JsonProperty("f")
    private FileMetadataMap files = new FileMetadataMap();

    @JsonProperty("h")
    private Long hash;

    @JsonIgnore
    private boolean frozen;

    /**
     * Default constructor - to be used by the deserializer.
     */
    public FolderMetadata() {
    }

    /**
     * Creates a folder metadata.
     * @param relativePath Folder relative path.
     * @param statusCheckedAt Timestamp when the status was checked.
     * @param allChildrenScanned All child nodes were scanned (folders and files) and are included in this object.
     */
    public FolderMetadata(Path relativePath,
                          long statusCheckedAt,
                          boolean allChildrenScanned) {
        this.relativePath = relativePath;
        this.statusCheckedAt = statusCheckedAt;
        this.allChildrenScanned = allChildrenScanned;
    }

    /**
     * Returns the folder name from the path (the last element of the full path).
     * @return Current folder name or null for a root folder.
     */
    @JsonIgnore
    public String getFolderName() {
        if (this.relativePath == null) {
            return null;
        }
        return this.relativePath.getName(this.relativePath.getNameCount() - 1).toString();
    }

    /**
     * Returns a relative path to the folder.
     * @return Relative path.
     */
    public Path getRelativePath() {
        return relativePath;
    }

    /**
     * Sets the path - a private setter to be used by the deserializer.
     * @param relativePath Relative path.
     */
    private void setRelativePath(Path relativePath) {
        this.relativePath = relativePath;
    }

    /**
     * The timestamp when the folder status was checked.
     * @return Timestamp of the time when the folder status was checked.
     */
    public long getStatusCheckedAt() {
        return statusCheckedAt;
    }

    /**
     * Private setter - used by the deserializer.
     * @param statusCheckedAt Status checked timestamp.
     */
    private void setStatusCheckedAt(long statusCheckedAt) {
        this.statusCheckedAt = statusCheckedAt;
    }

    /**
     * Gets the flag if the folder object includes all child subfolders and files (returns true).
     * If this property is false then not all subfolders were loaded and the current folder describes just a subset of child files.
     * @return True when all child folders and files are included, false when only a subset if files is included.
     */
    public boolean isAllChildrenScanned() {
        return allChildrenScanned;
    }

    /**
     * Private setter - for the deserializer.
     * @param allChildrenScanned True when all folders and files are included.
     */
    private void setAllChildrenScanned(boolean allChildrenScanned) {
        this.allChildrenScanned = allChildrenScanned;
    }

    /**
     * Retrieves or calculates a hash code. The hash code is stored and not calculated if the object is frozen.
     * @return 64-bit hash of all folders and files inside.
     */
    public Long getHash() {
        if (this.hash != null) {
            return this.hash;
        }

        String pathString = this.relativePath != null ? this.relativePath.toString().replace('\\', '/') : ".";
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        HashCode pathHash = hashFunction.hashString(pathString, StandardCharsets.UTF_8);

        HashCode hashCode = Hashing.combineUnordered(new ArrayList<HashCode>() {{
            add(pathHash);
            add(folders.calculateHash64());
            add(files.calculateHash64());
        }});

        if (this.frozen) {
            this.hash = hashCode.asLong(); // calculate it, probably called from the "freeze()" method.
        }

        return hashCode.asLong();
    }

    /**
     * Sets a 64-bit hash. It should be called only by the deserializer, that is why it is private.
     * @param hash New hash.
     */
    private void setHash(Long hash) {
        this.hash = hash;
    }

    /**
     * Dictionary of child folders.
     * @return Dictionary of child folders.
     */
    public FolderMetadataMap getFolders() {
        return folders;
    }

    /**
     * Dictionary of child files.
     * @return Child files.
     */
    public FileMetadataMap getFiles() {
        return files;
    }

    /**
     * Returns true if the folder is empty (the current folder has no child files and no sub folders).
     * @return True when the folder is empty, false if it has any files or sub folders.
     */
    @JsonIgnore
    public boolean isEmpty() {
        return this.files.isEmpty() && this.folders.isEmpty();
    }

    /**
     * Returns true if the folder metadata is frozen and child files or child folders cannot be added without cloning the object.
     * @return True when the folder metadata is frozen, false when changes are possible.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Freezes the current object and makes it fully read only.
     */
    public void freeze() {
        if (!this.frozen) {
            this.frozen = true;
            this.files.freeze();
            this.folders.freeze();
            this.getHash();  // calculates the hash
        }
    }

    /**
     * Returns a mutable file map. Clones the file map if it is already frozen.
     * @return Mutable file map.
     */
    @JsonIgnore
    public FileMetadataMap getMutableFiles() {
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Folder is frozen", this);
        }

        if (this.files.isFrozen()) {
            this.files = this.files.cloneUnfrozen();
        }

        return this.files;
    }

    /**
     * Returns a mutable folder map. Clones the folder map if it is already frozen.
     * @return Mutable folder map.
     */
    @JsonIgnore
    public FolderMetadataMap getMutableFolders() {
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Folder is frozen", this);
        }

        if (this.folders.isFrozen()) {
            this.folders = this.folders.cloneUnfrozen();
        }

        return this.folders;
    }

    /**
     * Adds a file to the metadata object. All missing folders are created. Frozen folder nodes are cloned as unfrozen.
     * @param fileMetadata File metadata that is added.
     */
    public void addFile(FileMetadata fileMetadata) {
        Path fileRelativePath = fileMetadata.getRelativePath();
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Trying to add a file: " + fileRelativePath.toString(), this);
        }

        assert this.relativePath == null || fileRelativePath.startsWith(this.relativePath);

        if (this.relativePath == null && fileRelativePath.getNameCount() == 1) {
            // direct child file, the current folder is an absolute root (null path)
            FileMetadataMap mutableFiles = this.getMutableFiles();
            mutableFiles.add(fileMetadata);
        }
        else if (this.relativePath != null && fileRelativePath.getNameCount() == this.relativePath.getNameCount() + 1) {
            // direct child file, the current folder is already nested
            FileMetadataMap mutableFiles = this.getMutableFiles();
            mutableFiles.add(fileMetadata);
        }
        else {
            // not direct file, we need to add missing folders
            FolderMetadataMap mutableFolders = this.getMutableFolders();
            String directChildFolderName = (this.relativePath == null ?
                    fileRelativePath.getName(0) :
                    fileRelativePath.getName(this.relativePath.getNameCount())
                ).toString();
            FolderMetadata directChildFolder = mutableFolders.getMutable(directChildFolderName);
            if (directChildFolder == null) {
                Path pathToDirectChildFolder = this.relativePath == null ? Path.of(directChildFolderName) :
                        this.relativePath.resolve(directChildFolderName);
                long statusCheckedAt = Instant.now().toEpochMilli();
                directChildFolder = new FolderMetadata(pathToDirectChildFolder, statusCheckedAt, false);
                mutableFolders.add(directChildFolder);
            }

            directChildFolder.addFile(fileMetadata);
        }
    }

    /**
     * Removes a file from the index.
     * @param fileRelativePath Relative file path.
     * @return True when the file was present (and was removed), false when the file was unknown.
     */
    public boolean removeFile(Path fileRelativePath) {
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Trying to remove a file: " + fileRelativePath.toString(), this);
        }

        assert this.relativePath == null || fileRelativePath.startsWith(this.relativePath);

        if (this.relativePath == null && fileRelativePath.getNameCount() == 1) {
            String fileName = fileRelativePath.toString();
            if (this.files.containsFileName(fileName)) {
                // direct child file, the current folder is an absolute root (null path)
                FileMetadataMap mutableFiles = this.getMutableFiles();
                mutableFiles.removeByFileName(fileName);
                return true;
            } else {
                return false;
            }
        }
        else if (this.relativePath != null && fileRelativePath.getNameCount() == this.relativePath.getNameCount() + 1) {
            // direct child file, the current folder is already nested
            String fileName = fileRelativePath.getName(fileRelativePath.getNameCount() - 1).toString();
            if (this.files.containsFileName(fileName)) {
                // direct child file, the current folder is an absolute root (null path)
                FileMetadataMap mutableFiles = this.getMutableFiles();
                mutableFiles.removeByFileName(fileName);
                return true;
            } else {
                return false;
            }
        }
        else {
            // not direct file, we need to go to the child folder
            FolderMetadataMap mutableFolders = this.getMutableFolders();
            String directChildFolderName = (this.relativePath == null ?
                    fileRelativePath.getName(0) :
                    fileRelativePath.getName(this.relativePath.getNameCount())
            ).toString();
            FolderMetadata directChildFolder = mutableFolders.getMutable(directChildFolderName);
            if (directChildFolder == null) {
                return false; // the file was not present, because the folder was missing
            }

            return directChildFolder.removeFile(fileRelativePath);
        }
    }

    /**
     * Applied a change in the file metadata. Required a mutable folder.
     * @param relativePath Relative path to the file that has changed (it is provided because the file may have been deleted and the <code>newFile</code> would be null).
     * @param newFile New file metadata. Could be null if the file was deleted.
     */
    public void applyChange(Path relativePath, FileMetadata newFile) {
        synchronized (this) {
            this.removeFile(relativePath);
            if (newFile != null) {
                this.addFile(newFile);
            }
        }
    }

    /**
     * Sets the flag on the folder and its child folders that all files and folders are stored in the index.
     * There are no more folders or files in the file system that are not included in the index.
     */
    public void makeAllChildFoldersScanned() {
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Cannot change a frozen object.", this);
        }

        this.allChildrenScanned = true;

        boolean allChildFoldersAreUnfrozen = this.folders.stream().noneMatch(FolderMetadata::isFrozen);
        if (allChildFoldersAreUnfrozen) {
            for (FolderMetadata childFolder : this.folders) {
                childFolder.makeAllChildFoldersScanned();
            }
        }
        else {
            boolean hasChildFoldersNotFullScanned = this.folders.stream().anyMatch(f -> !f.isAllChildrenScanned());
            if (hasChildFoldersNotFullScanned) {
                FolderMetadataMap newFolderMap = new FolderMetadataMap();

                for (FolderMetadata childFolder : this.folders) {
                    if (!childFolder.isAllChildrenScanned()) {
                        if (childFolder.isFrozen()) {
                            childFolder = childFolder.cloneUnfrozen();
                        }

                        childFolder.makeAllChildFoldersScanned();
                    }

                    newFolderMap.add(childFolder);
                }

                this.folders = newFolderMap;
            }
        }
    }

    /**
     * Clones the folder metadata as an unfrozen instance.
     * @return Unfrozen folder metadata, but the collections are still frozen and must be turned into mutable before making changes.
     */
    public FolderMetadata cloneUnfrozen() {
        assert this.frozen;

        try {
            FolderMetadata cloned = (FolderMetadata) this.clone();
            cloned.frozen = false;
            cloned.hash = null;
            cloned.statusCheckedAt = Instant.now().toEpochMilli();
            assert cloned.files.isFrozen();
            assert cloned.folders.isFrozen();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new FileSystemMetadataFrozenException("Cannot clone the object", e, this);
        }
    }

    /**
     * Finds a file in the current file list or nested folders. Returns the file metadata if the file was found
     * or returns null when the file is not in the index.
     * @param relativeFilePath Relative file path (relative to the root of the file system).
     * @return Found file metadata or null.
     */
    public FileMetadata findFile(Path relativeFilePath) {
        if (this.relativePath == null) {
            String firstNameElement = relativeFilePath.getName(0).toString();
            if (relativeFilePath.getNameCount() == 1) {
                return this.files.get(firstNameElement);
            } else {
                FolderMetadata childFolderFromRoot = this.folders.get(firstNameElement);
                if (childFolderFromRoot != null) {
                    return childFolderFromRoot.findFile(relativeFilePath);
                }
                return null; // the folder is not in the index
            }
        } else {
            int currentPathNameCount = this.relativePath.getNameCount();
            assert relativeFilePath.getNameCount() > currentPathNameCount && relativeFilePath.startsWith(this.relativePath);
            String nextNameElement = relativeFilePath.getName(currentPathNameCount).toString();
            if (relativeFilePath.getNameCount() == currentPathNameCount + 1) {
                return this.files.get(nextNameElement);
            } else {
                FolderMetadata childFolderFromRoot = this.folders.get(nextNameElement);
                if (childFolderFromRoot != null) {
                    return childFolderFromRoot.findFile(relativeFilePath);
                }
                return null; // the folder is not in the index
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderMetadata that = (FolderMetadata) o;

        return Objects.equals(relativePath, that.relativePath);
    }

    @Override
    public int hashCode() {
        return relativePath != null ? relativePath.hashCode() : 0;
    }

    /**
     * Collects all files, even from deeply nested sub folders.
     * @return Collection of files from on any depth or null when there are no child files.
     */
    @JsonIgnore
    public Collection<FileMetadata> getAllFiles() {
        if (this.isEmpty()) {
            return null;
        }

        ArrayList<FileMetadata> allFiles = new ArrayList<>();
        allFiles.addAll(this.files);
        for (FolderMetadata childFolder : this.folders) {
            Collection<FileMetadata> allFilesFromChildFolder = childFolder.getAllFiles();
            if (allFilesFromChildFolder != null) {
                allFiles.addAll(allFilesFromChildFolder);
            }
        }
        return allFiles;
    }

    /**
     * Compares this folder and the other folder to find any differences. Differences are returned as pairs of
     * files in the current folder tree and the other folder three. Files missing in the current three or the other tree are nulls in the pairs.
     * @param otherFolderMetadata Other folder node (with the sample relative path) to compare to.
     * @return Collection of file differences.
     */
    public Collection<FileDifference> findFileDifferences(FolderMetadata otherFolderMetadata) {
        assert Objects.equals(this.relativePath, otherFolderMetadata.relativePath);

        ArrayList<FileDifference> differences = null;
        for (FileMetadata myFile : this.files) {
            String fileName = myFile.getFileName();
            FileMetadata otherFile = otherFolderMetadata.files.get(fileName);

            if (otherFile == null || !Objects.equals(myFile.getMd5(), otherFile.getMd5())) {
                if (differences == null) {
                    differences = new ArrayList<>();
                }
                differences.add(new FileDifference(myFile, otherFile));
            }
        }

        for (FileMetadata otherFile : otherFolderMetadata.files) {
            String fileName = otherFile.getFileName();
            FileMetadata myFile = this.files.get(fileName);
            if (myFile == null) {
                if (differences == null) {
                    differences = new ArrayList<>();
                }
                differences.add(new FileDifference(null, otherFile)); // we don't have that file
            }
        }

        for (FolderMetadata myFolder : this.folders) {
            String folderName = myFolder.getFolderName();
            FolderMetadata otherFolder = otherFolderMetadata.folders.get(folderName);

            if (otherFolder == null) {
                Collection<FileMetadata> allFilesInMineChildFolder = myFolder.getAllFiles();

                if (allFilesInMineChildFolder == null) {
                    continue;
                }

                if (differences == null) {
                    differences = new ArrayList<>();
                }

                differences.addAll(allFilesInMineChildFolder.stream()
                        .map(f -> new FileDifference(f, null)).collect(Collectors.toList()));
            } else if (!myFolder.getHash().equals(otherFolder.getHash())) {
                Collection<FileDifference> childFolderDifferences = myFolder.findFileDifferences(otherFolder);
                if (childFolderDifferences != null) {
                    if (differences == null) {
                        differences = new ArrayList<>();
                    }

                    differences.addAll(childFolderDifferences);
                }
            }
        }

        for (FolderMetadata otherChildFolder : otherFolderMetadata.folders) {
            String folderName = otherChildFolder.getFolderName();
            FolderMetadata myFolder = this.folders.get(folderName);
            if (myFolder == null) {
                Collection<FileMetadata> allFilesInOtherChildFolder = otherChildFolder.getAllFiles();

                if (allFilesInOtherChildFolder == null) {
                    continue;
                }

                if (differences == null) {
                    differences = new ArrayList<>();
                }

                differences.addAll(allFilesInOtherChildFolder.stream()
                        .map(f -> new FileDifference(null, f)).collect(Collectors.toList()));
            }
        }

        return differences;
    }

    /**
     * Detaches empty child folders from the current folder node. Detaches folders recurrencially and returns a collection of folders
     * that were empty, so they can be deleted from the file system.
     * @return Optional collection of folders that are empty, were detached and could be deleted.
     */
    public Collection<FolderMetadata> detachEmptyFolders() {
        ArrayList<FolderMetadata> emptyFolders = null;

        if (!this.folders.isEmpty()) {
            if (this.folders.isFrozen() && hasEmptyNestedFolders()) {
                this.getMutableFolders(); // make the folder list unfrozen and mutable
            }

            for (FolderMetadata childFolder : new ArrayList<>(this.folders)) {
                if (childFolder.isFrozen() && childFolder.hasEmptyNestedFolders()) {
                    childFolder = this.folders.getMutable(childFolder.getFolderName());
                }

                Collection<FolderMetadata> childEmptyFolders = childFolder.detachEmptyFolders();
                if (childEmptyFolders != null) {
                    if (emptyFolders == null) {
                        emptyFolders = new ArrayList<>();
                    }
                    emptyFolders.addAll(childEmptyFolders);
                }

                if (childFolder.isEmpty()) {
                    if (emptyFolders == null) {
                        emptyFolders = new ArrayList<>();
                    }
                    emptyFolders.add(childFolder);
                    this.getMutableFolders().remove(childFolder);
                }
            }
        }

        return emptyFolders;
    }

    /**
     * Checks if the folder has any empty nested folders. It must be called to decide that we have to unfreeze it to make changes to the index.
     * @return True when there is at least one subfolder that is empty, false when all folders are unfrozen.
     */
    public boolean hasEmptyNestedFolders() {
        if (this.files.isEmpty()) {
            return true;
        }

        if (this.folders.isEmpty()) {
            return false;
        }

        for (FolderMetadata nestedFolder : this.folders) {
            if (nestedFolder.hasEmptyNestedFolders()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes connections and tables beyond the accepted limit, to match the license limitations.
     * @param apiKeyPayload API Key payload with the license limits.
     */
    public void truncateToLicenseLimits(DqoCloudApiKeyPayload apiKeyPayload) {
        Integer connectionsLimit = apiKeyPayload.getLimits().get(DqoCloudLimit.CONNECTIONS_LIMIT);
        if (connectionsLimit != null && this.folders.size() > connectionsLimit) {
            this.getMutableFolders().truncateToLimit(connectionsLimit,
                    HivePartitionPathUtility::validHivePartitionConnectionFolderName,
                    FolderTruncationMode.ASCENDING_SORTED_FIRST);
        }

        Integer tablesLimit = apiKeyPayload.getLimits().get(DqoCloudLimit.TABLES_LIMIT);
        Integer tablesPerConnectionLimit = apiKeyPayload.getLimits().get(DqoCloudLimit.CONNECTION_TABLES_LIMIT);
        if (tablesLimit != null || tablesPerConnectionLimit != null) {
            int tablesCount = 0;
            for (FolderMetadata connectionFolder : this.getMutableFolders()) {
                if (!HivePartitionPathUtility.validHivePartitionConnectionFolderName(connectionFolder.getFolderName())) {
                    continue;
                }

                if (tablesLimit != null && tablesCount + connectionFolder.folders.size() > tablesLimit) {
                    FolderMetadata mutableConnectionFolder = this.getMutableFolders().getMutable(connectionFolder.getFolderName());
                    mutableConnectionFolder.getMutableFolders().truncateToLimit(tablesLimit,
                            HivePartitionPathUtility::validHivePartitionTableFolderName,
                            FolderTruncationMode.ASCENDING_SORTED_FIRST);
                } else {
                    tablesCount += connectionFolder.folders.size();
                }

                if (tablesPerConnectionLimit != null) {
                    if (connectionFolder.getFolders().size() > tablesPerConnectionLimit) {
                        FolderMetadata mutableConnectionFolder = this.getMutableFolders().getMutable(connectionFolder.getFolderName());
                        mutableConnectionFolder.getMutableFolders().truncateToLimit(tablesPerConnectionLimit,
                                HivePartitionPathUtility::validHivePartitionTableFolderName,
                                FolderTruncationMode.ASCENDING_SORTED_FIRST);
                    }
                }
            }
        }

        Integer monthsLimit = apiKeyPayload.getLimits().get(DqoCloudLimit.MONTHS_LIMIT);
        if (monthsLimit != null) {
            for (FolderMetadata connectionFolder : this.getMutableFolders()) {
                if (!HivePartitionPathUtility.validHivePartitionConnectionFolderName(connectionFolder.getFolderName())) {
                    continue;
                }

                FolderMetadata mutableConnectionFolder = this.getMutableFolders().getMutable(connectionFolder.getFolderName());
                FolderMetadataMap tableFolders = mutableConnectionFolder.getMutableFolders();

                for (FolderMetadata tableFolder : tableFolders) {
                    if (!HivePartitionPathUtility.validHivePartitionTableFolderName(tableFolder.getFolderName())) {
                        continue;
                    }

                    if (tableFolder.getFolders().size() > monthsLimit) {
                        FolderMetadata mutableTableFolder = tableFolders.getMutable(tableFolder.getFolderName());
                        mutableTableFolder.getMutableFolders().truncateToLimit(monthsLimit,
                                HivePartitionPathUtility::validHivePartitionMonthFolderName,
                                FolderTruncationMode.DESCENDING_SORTED_FIRST);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "FolderMetadata{" +
                "relativePath=" + relativePath +
                '}';
    }
}
