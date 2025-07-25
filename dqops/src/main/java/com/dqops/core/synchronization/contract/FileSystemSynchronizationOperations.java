/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.contract;

import com.dqops.core.filesystem.metadata.FileMetadata;
import com.dqops.core.filesystem.metadata.FolderMetadata;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Interface implemented by file system service that are sensor readouts and writing files, listing files, etc.
 */
public interface FileSystemSynchronizationOperations {
    /**
     * Returns true if the file system represents a local file system.
     * @return True for a local file system, false for a remote file system.
     */
    boolean isLocalFileSystem();

    /**
     * Read the metadata of a single file.
     * @param fileSystemRoot File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath Relative file path in the file system.
     * @param lastKnownFileMetadata Optional last known file metadata.
     *                              If the last known file metadata is not null and the last file modification
     *                              has not changed then the hash could be copied (and not calculated).
     * @return File metadata.
     */
    FileMetadata readFileMetadata(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata);

    /**
     * Read the metadata of a single file asynchronously.
     * @param fileSystemRoot File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath Relative file path in the file system.
     * @param lastKnownFileMetadata Optional last known file metadata.
     *                              If the last known file metadata is not null and the last file modification
     *                              has not changed then the hash could be copied (and not calculated).
     * @return File metadata.
     */
    Mono<FileMetadata> readFileMetadataAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata);

    /**
     * List files in a folder.
     * @param fileSystemRoot File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath Relative path inside the root folder that is listed.
     * @param lastKnownFolderMetadata Last known folder metadata that is stored in the .index folder.
     *                                This index may be used to look up hashes of files to avoid reading the files to hash them.
     * @return Folder with all nested files and folders.
     */
    FolderMetadata listFilesInFolder(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FolderMetadata lastKnownFolderMetadata);

    /**
     * Deletes a file.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    void deleteFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath);

    /**
     * Deletes a file asynchronously.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    Mono<FileMetadata> deleteFileAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath);

    /**
     * Deletes a folder.
     * @param fileSystemRoot File system root.
     * @param relativeFolderPath Relative path to the folder that should be deleted.
     * @param deleteNonEmptyFolder When true, non-empty folders are also deleted including all nested files and sub folders.
     * @return true when the folder was deleted, false when the folder was not empty and cannot be deleted.
     */
    boolean deleteFolder(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFolderPath, boolean deleteNonEmptyFolder);

    /**
     * Deletes a folder asynchronously.
     * @param fileSystemRoot File system root.
     * @param relativeFolderPath Relative path to the folder that should be deleted.
     * @param deleteNonEmptyFolder When true, non-empty folders are also deleted including all nested files and sub folders.
     * @return true when the folder was deleted, false when the folder was not empty and cannot be deleted.
     */
    Mono<Boolean> deleteFolderAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFolderPath, boolean deleteNonEmptyFolder);

    /**
     * Downloads a file and opens an input stream to the file.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     * @return Input stream to get the file content.
     */
    InputStream downloadFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath);

    /**
     * Uploads a stream to the file system.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the root file system.
     * @param sourceStream Source stream that will be uploaded. The method should close this stream after the upload finishes.
     * @param fileHashMd5Base64 MD5 file hash that is expected, encoded as a base64 string.
     */
    void uploadFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, InputStream sourceStream, String fileHashMd5Base64);

    /**
     * Downloads a file asynchronously, returning a flux of file content blocks.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     * @param lastKnownFileMetadata Last known file metadata. Could be used for verification or skipping an extra hashing.
     * @return File download response with the flux of file content and the metadata.
     */
    Mono<DownloadFileResponse> downloadFileAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata);

    /**
     * Uploads a file to the file system as an asynchronous operation using Flux.
     * @param fileSystemRoot File system root.
     * @param relativeFilePath Relative path to the uploaded file.
     * @param downloadFileResponseMono Mono that has a response with a downloaded file
     * @return Mono returned when the file was fully uploaded.
     */
    Mono<FileMetadata> uploadFileAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, Mono<DownloadFileResponse> downloadFileResponseMono);
}
