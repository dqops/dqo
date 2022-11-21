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
package ai.dqo.core.filesystem.filesystemservice.contract;

import ai.dqo.core.filesystem.metadata.FileMetadata;
import ai.dqo.core.filesystem.metadata.FolderMetadata;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Interface implemented by file system service that are sensor readouts and writing files, listing files, etc.
 */
public interface FileSystemService {
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
    FileMetadata readFileMetadata(AbstractFileSystemRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata);

    /**
     * List files in a folder.
     * @param fileSystemRoot File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath Relative path inside the root folder that is listed.
     * @param lastKnownFolderMetadata Last known folder metadata that is stored in the .index folder.
     *                                This index may be used to look up hashes of files to avoid reading the files to hash them.
     * @return Folder with all nested files and folders.
     */
    FolderMetadata listFilesInFolder(AbstractFileSystemRoot fileSystemRoot, Path relativeFilePath, FolderMetadata lastKnownFolderMetadata);

    /**
     * Deletes a file.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    void deleteFile(AbstractFileSystemRoot fileSystemRoot, Path relativeFilePath);

    /**
     * Deletes a folder.
     * @param fileSystemRoot File system root.
     * @param relativeFolderPath Relative path to the folder that should be deleted.
     * @param deleteNonEmptyFolder When true, non-empty folders are also deleted including all nested files and sub folders.
     * @return true when the folder was deleted, false when the folder was not empty and cannot be deleted.
     */
    boolean deleteFolder(AbstractFileSystemRoot fileSystemRoot, Path relativeFolderPath, boolean deleteNonEmptyFolder);

    /**
     * Downloads a file and opens an input stream to the file.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     * @return Input stream to get the file content.
     */
    InputStream downloadFile(AbstractFileSystemRoot fileSystemRoot, Path relativeFilePath);

    /**
     * Uploads a stream to the file system.
     * @param fileSystemRoot File system root (with credentials).
     * @param relativeFilePath Relative file path inside the root file system.
     * @param sourceStream Source stream that will be uploaded. The method should close this stream after the upload finishes.
     * @param fileHash File hash that is expected.
     */
    void uploadFile(AbstractFileSystemRoot fileSystemRoot, Path relativeFilePath, InputStream sourceStream, byte[] fileHash);
}
