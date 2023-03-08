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
package ai.dqo.core.synchronization.filesystems.local;

import ai.dqo.core.filesystem.metadata.FileMetadata;
import ai.dqo.core.filesystem.metadata.FolderMetadata;
import ai.dqo.core.synchronization.contract.*;
import ai.dqo.utils.exceptions.CloseableHelper;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.io.*;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Local file system implementation.
 */
@Component
@Slf4j
public class LocalFileSystemSynchronizationOperationsImpl implements LocalFileSystemSynchronizationOperations {
    /**
     * Returns true if the file system represents a local file system.
     *
     * @return True for a local file system, false for a remote file system.
     */
    @Override
    public boolean isLocalFileSystem() {
        return true;
    }

    /**
     * Read the metadata of a single file.
     *
     * @param fileSystemRoot        File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath      Relative file path in the file system.
     * @param lastKnownFileMetadata Optional last known file metadata.
     *                              If the last known file metadata is not null and the last file modification
     *                              has not changed then the hash could be copied (and not calculated).
     * @return File metadata.
     */
    @Override
    public FileMetadata readFileMetadata(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata) {
        Path rootFileSystemPath = fileSystemRoot.getRootPath();
        Path fullPathToFile = rootFileSystemPath.resolve(relativeFilePath);

        try {
            File file = fullPathToFile.toFile();
            if (!file.exists()) {
                return null;
            }

            long lastModifiedMillis = file.lastModified();
            String fileHashMd5Base64 = null;

            if (lastKnownFileMetadata != null && lastModifiedMillis == lastKnownFileMetadata.getLastModifiedAt()) {
                fileHashMd5Base64 = lastKnownFileMetadata.getMd5();
            } else {
                HashCode fileHashCode = com.google.common.io.Files.asByteSource(file).hash(Hashing.md5());
                fileHashMd5Base64 = BaseEncoding.base64().encode(fileHashCode.asBytes());
            }

            long now = Instant.now().toEpochMilli();
            return new FileMetadata(relativeFilePath, lastModifiedMillis, fileHashMd5Base64, now, file.length());
        } catch (IOException ex) {
            throw new FileMetadataReadException(fullPathToFile, ex.getMessage(), ex);
        }
    }

    /**
     * Read the metadata of a single file asynchronously.
     *
     * @param fileSystemRoot        File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath      Relative file path in the file system.
     * @param lastKnownFileMetadata Optional last known file metadata.
     *                              If the last known file metadata is not null and the last file modification
     *                              has not changed then the hash could be copied (and not calculated).
     * @return File metadata.
     */
    @Override
    public Mono<FileMetadata> readFileMetadataAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FileMetadata lastKnownFileMetadata) {
        return Mono.fromCallable(() -> readFileMetadata(fileSystemRoot, relativeFilePath, lastKnownFileMetadata));
    }

    /**
     * List files in a folder.
     *
     * @param fileSystemRoot          File system root information. May contain credentials to access a remote file system.
     * @param relativeFilePath        Relative path inside the root folder that is listed.
     * @param lastKnownFolderMetadata Last known folder metadata that is stored in the .index folder.
     *                                This index may be used to look up hashes of files to avoid reading the files to hash them.
     * @return Folder with all nested files and folders.
     */
    @Override
    public FolderMetadata listFilesInFolder(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, FolderMetadata lastKnownFolderMetadata) {
        Path rootFileSystemPath = fileSystemRoot.getRootPath();
        Path fullPathToFolder = relativeFilePath != null ? rootFileSystemPath.resolve(relativeFilePath) : rootFileSystemPath;

        try {
            long statusCheckedAt = Instant.now().toEpochMilli();
            final FolderMetadata resultFolderMetadata = new FolderMetadata(relativeFilePath, statusCheckedAt, true);

            if (fullPathToFolder.toFile().exists()) {
                Stream<Path> fileListStream = Files.list(fullPathToFolder);
                try {
                    fileListStream
                            .parallel()
                            .forEach(childPath -> {
                                Path childRelativePath = rootFileSystemPath.relativize(childPath);
                                String fileName = childPath.getFileName().toString();

                                if (Files.isDirectory(childPath)) {
                                    if (Objects.equals("__pycache__", fileName)) {
                                        return; // we ignore python cache folders
                                    }

                                    FolderMetadata lastKnownChildFolder = lastKnownFolderMetadata != null ?
                                            lastKnownFolderMetadata.getFolders().get(fileName) : null;
                                    FolderMetadata childFolderMetadata = this.listFilesInFolder(fileSystemRoot, childRelativePath, lastKnownChildFolder);
                                    resultFolderMetadata.getFolders().add(childFolderMetadata);
                                } else {
                                    if (fileName.startsWith(".") && fileName.endsWith(".parquet.crc")) {
                                        return; // crc files are ignored, they are generated by parquet-mr, we are storing the .parquet.crc file content as a hash of the file instead of calculating the hash directly
                                    }

                                    FileMetadata lastKnownFileMetadata = lastKnownFolderMetadata != null ?
                                            lastKnownFolderMetadata.getFiles().get(fileName) : null;
                                    FileMetadata fileMetadata = this.readFileMetadata(fileSystemRoot, childRelativePath, lastKnownFileMetadata);
                                    resultFolderMetadata.getFiles().add(fileMetadata);
                                }
                            });
                } finally {
                    fileListStream.close(); // file descriptor leak in Java, file stream must be closed
                }
            }
            return resultFolderMetadata;
        } catch (IOException ex) {
            throw new FileMetadataReadException(fullPathToFolder, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a file.
     *
     * @param fileSystemRoot   Remote file system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    @Override
    public void deleteFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath) {
        Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFilePath);
        File file = fullPathToFile.toFile();
        if (!file.exists()) {
            return;
        }

        file.delete();
    }

    /**
     * Deletes a file asynchronously.
     *
     * @param fileSystemRoot   File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    @Override
    public Mono<Path> deleteFileAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath) {
        Mono<Path> deleteFileMono = Mono.fromRunnable(() -> {
            deleteFile(fileSystemRoot, relativeFilePath);
        }).thenReturn(relativeFilePath);

        return deleteFileMono;
    }

    /**
     * Deletes a folder.
     *
     * @param fileSystemRoot       File system root.
     * @param relativeFolderPath   Relative path to the folder that should be deleted.
     * @param deleteNonEmptyFolder When true, non-empty folders are also deleted including all nested files and sub folders.
     * @return true when the folder was deleted, false when the folder was not empty and cannot be deleted.
     */
    @Override
    public boolean deleteFolder(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFolderPath, boolean deleteNonEmptyFolder) {
        Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFolderPath);
        File file = fullPathToFile.toFile();
        if (!file.exists()) {
            return true;
        }

        assert file.isDirectory();

        try {
            FileUtils.deleteDirectory(file);
            return true;
        } catch (IOException ex) {
            throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a folder asynchronously.
     *
     * @param fileSystemRoot       File system root.
     * @param relativeFolderPath   Relative path to the folder that should be deleted.
     * @param deleteNonEmptyFolder When true, non-empty folders are also deleted including all nested files and sub folders.
     * @return true when the folder was deleted, false when the folder was not empty and cannot be deleted.
     */
    @Override
    public Mono<Boolean> deleteFolderAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFolderPath, boolean deleteNonEmptyFolder) {
        Mono<Boolean> deleteFolderMono = Mono.fromCallable(() -> {
            return deleteFolder(fileSystemRoot, relativeFolderPath, deleteNonEmptyFolder);
        });

        return deleteFolderMono;
    }

    /**
     * Downloads a file and opens an input stream to the file.
     *
     * @param fileSystemRoot   File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     * @return Input stream to get the file content.
     */
    @Override
    public InputStream downloadFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath) {
        Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFilePath);

        try {
            FileInputStream fileInputStream = new FileInputStream(fullPathToFile.toFile());
            return fileInputStream;
        } catch (Exception ex) {
            throw new FileSystemReadException(fullPathToFile, ex.getMessage(), ex);
        }
    }

    /**
     * Downloads a file asynchronously, returning a flux of file content blocks.
     *
     * @param fileSystemRoot        File system root (with credentials).
     * @param relativeFilePath      Relative file path inside the remote root.
     * @param lastKnownFileMetadata Last known file metadata. Could be used for verification or skipping an extra hashing.
     * @return File download response with the flux of file content and the metadata.
     */
    @Override
    public Mono<DownloadFileResponse> downloadFileAsync(FileSystemSynchronizationRoot fileSystemRoot,
                                                        Path relativeFilePath,
                                                        FileMetadata lastKnownFileMetadata) {
        Mono<ByteBufFlux> byteBufFluxMono = Mono.fromCallable(() -> {
            Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFilePath);
            ByteBufFlux byteBufFlux = ByteBufFlux.fromPath(fullPathToFile);
            return byteBufFlux;
        });

        Mono<DownloadFileResponse> resultMono = byteBufFluxMono
                .map(byteBufFlux -> new DownloadFileResponse(lastKnownFileMetadata, byteBufFlux));
        return resultMono;
    }

    /**
     * Uploads a stream to the file system.
     *
     * @param fileSystemRoot    File system root (with credentials).
     * @param relativeFilePath  Relative file path inside the root file system.
     * @param sourceStream      Source stream that will be uploaded. The method should close this stream after the upload finishes.
     * @param fileHashMd5Base64 MD5 file hash that is expected.
     */
    @Override
    public void uploadFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath, InputStream sourceStream, String fileHashMd5Base64) {
        Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFilePath);

        try {
            Path parentFolderPath = fullPathToFile.getParent();
            if (!Files.exists(parentFolderPath)) {
                try {
                    Files.createDirectories(parentFolderPath);
                } catch (Exception ex) {
                    // ignore, probably a race condition
                }
            }

            try (OutputStream fileOutputStream = Files.newOutputStream(fullPathToFile)) {
                sourceStream.transferTo(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                sourceStream.close();
            }

            assert fullPathToFile.toFile().length() > 0;
        } catch (Exception ex) {
            throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
        } finally {
            CloseableHelper.closeSilently(sourceStream);
        }
    }

    /**
     * Uploads a file to the file system as an asynchronous operation using Flux.
     *
     * @param fileSystemRoot           File system root.
     * @param relativeFilePath         Relative path to the uploaded file.
     * @param downloadFileResponseMono Mono that has a response with a downloaded file
     * @return Mono returned when the file was fully uploaded.
     */
    @Override
    public Mono<Path> uploadFileAsync(FileSystemSynchronizationRoot fileSystemRoot,
                                      Path relativeFilePath,
                                      Mono<DownloadFileResponse> downloadFileResponseMono) {
        Mono<Path> uploadFinishMono = downloadFileResponseMono
                .flatMap((DownloadFileResponse downloadResponse) -> {
                    ByteBufFlux bytesFlux = downloadResponse.getByteBufFlux();
                    FileMetadata fileMetadata = downloadResponse.getMetadata();

                    Path fullPathToFile = fileSystemRoot.getRootPath().resolve(relativeFilePath);

                    try {
                        Path parentFolderPath = fullPathToFile.getParent();
                        if (!Files.exists(parentFolderPath)) {
                            try {
                                Files.createDirectories(parentFolderPath);
                            } catch (IOException ex) {
                                // ignore, this could be just a race condition that another thread is trying to create the same folder
                            }
                        }

                        final FileOutputStream fileOutputStream = new FileOutputStream(fullPathToFile.toFile());
                        final ByteChannel outputByteChannel = fileOutputStream.getChannel();
                        return bytesFlux.doOnEach(byteBufSignal -> {
                            switch (byteBufSignal.getType()) {
                                case ON_NEXT: {
                                    try {
                                        ByteBuf byteBuf = byteBufSignal.get();
                                        outputByteChannel.write(byteBuf.nioBuffer());
                                    } catch (Exception ex) {
                                        throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
                                    }
                                    break;
                                }

                                case ON_ERROR: {
                                    try {
                                        outputByteChannel.close();
                                        fileOutputStream.close();
                                        Files.delete(fullPathToFile);

                                        // cleans old parquet-mr .crc files, to be deleted... it is here only for cleaning
                                        Path fileName = relativeFilePath.getFileName();
                                        if (fileName.getFileName().toString().endsWith(".parquet")) {
                                            Path crcFilePath = parentFolderPath.resolve("." + fileName + ".crc");
                                            if (Files.exists(crcFilePath)) {
                                                Files.delete(crcFilePath);
                                            }
                                        }
                                    } catch (Exception ex) {
                                        throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
                                    }

                                    break;
                                }

                                case ON_COMPLETE: {
                                    try {
                                        outputByteChannel.close();
                                        fileOutputStream.close();
                                    } catch (Exception ex) {
                                        throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
                                    }
                                    break;
                                }
                            }
                        }).then(Mono.just(relativeFilePath));
                    } catch (Exception ex) {
                        throw new FileSystemChangeException(fullPathToFile, ex.getMessage(), ex);
                    }
                });

        return uploadFinishMono;
    }
}
