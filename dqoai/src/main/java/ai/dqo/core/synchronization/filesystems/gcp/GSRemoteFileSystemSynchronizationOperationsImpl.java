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
package ai.dqo.core.synchronization.filesystems.gcp;

import ai.dqo.core.configuration.DqoStorageGcpConfigurationProperties;
import ai.dqo.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import ai.dqo.core.filesystem.metadata.FileMetadata;
import ai.dqo.core.filesystem.metadata.FolderMetadata;
import ai.dqo.core.synchronization.contract.*;
import ai.dqo.utils.exceptions.CloseableHelper;
import ai.dqo.utils.http.SharedHttpClientProvider;
import ai.dqo.utils.streams.ErrorInjectionInputStream;
import com.google.api.gax.paging.Page;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.common.io.BaseEncoding;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Remote file system for the google storage buckets.
 */
@Component
public class GSRemoteFileSystemSynchronizationOperationsImpl implements GSRemoteFileSystemSynchronizationOperations {
    private final DqoStorageGcpConfigurationProperties gcpConfigurationProperties;
    private final SharedHttpClientProvider sharedHttpClientProvider;
    private final DqoCloudAccessTokenCache dqoCloudAccessTokenCache;

    /**
     * Default injection constructor.
     * @param gcpConfigurationProperties Google Storage configuration properties.
     * @param sharedHttpClientProvider HTTP client provider that creates HTTP clients to be used to read and write files in a GCP storage bucket.
     * @param dqoCloudAccessTokenCache DQO Cloud access key cache. Returns the most current access token to access the google storage.
     */
    @Autowired
    public GSRemoteFileSystemSynchronizationOperationsImpl(DqoStorageGcpConfigurationProperties gcpConfigurationProperties,
                                                           SharedHttpClientProvider sharedHttpClientProvider,
                                                           DqoCloudAccessTokenCache dqoCloudAccessTokenCache) {
        this.gcpConfigurationProperties = gcpConfigurationProperties;
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.dqoCloudAccessTokenCache = dqoCloudAccessTokenCache;
    }

    /**
     * Returns true if the file system represents a local file system.
     *
     * @return True for a local file system, false for a remote file system.
     */
    @Override
    public boolean isLocalFileSystem() {
        return false;
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
        // TODO: we can check the timestamp of the last known file metadata (when it was checked) and if it is quite recent, we can avoid asking the cloud storage, but we need to configure the cache duration

        try {
            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            Storage storage = gsFileSystemRoot.getStorage();
            Path pathToFileInsideBlob = fileSystemRoot.getRootPath() != null ?
                    fileSystemRoot.getRootPath().resolve(relativeFilePath) :
                    relativeFilePath;

            String linuxStyleFilePath = pathToFileInsideBlob.toString().replace('\\', '/');
            BlobId blobId = BlobId.of(gsFileSystemRoot.getBucketName(), linuxStyleFilePath);
            Blob blob = storage.get(blobId);
            String md5Base64 = blob.getMd5();
            long updatedAt = blob.getUpdateTime();

            long statusCheckedAt = Instant.now().toEpochMilli();
            FileMetadata fileMetadata = new FileMetadata(relativeFilePath, updatedAt, md5Base64, statusCheckedAt, blob.getSize());
            return fileMetadata;
        }
        catch (Exception ex) {
            throw new FileMetadataReadException(relativeFilePath, ex.getMessage(), ex);
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
        GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
        Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                relativeFilePath;
        String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

        Mono<FileMetadata> fileMetadataMono = this.sharedHttpClientProvider.getHttpClientGcpStorage()
                .headers(httpHeaders -> httpHeaders
                        .add(HttpHeaderNames.AUTHORIZATION, "Bearer " + this.dqoCloudAccessTokenCache.getCredentials(gsFileSystemRoot.getRootType()).getAccessToken().getTokenValue())
                        .add(HttpHeaderNames.CONTENT_LENGTH, 0)
                )
                .head()
                .uri(String.format("https://%s.storage.googleapis.com/%s", gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket))
                .responseSingle((httpClientResponse, byteBufMono) -> {
                    try {
                        HttpHeaders headers = httpClientResponse.responseHeaders();
                        Integer fileLength = headers.getInt(HttpHeaderNames.CONTENT_LENGTH);
                        Long lastModified = headers.getTimeMillis(HttpHeaderNames.LAST_MODIFIED);
                        List<String> googleHashHeaderValues = headers.getAllAsString("x-goog-hash");
                        String md5Base64 = null;
                        for (String googleHashHeaderValue :  googleHashHeaderValues) {
                            String[] hashEntries = StringUtils.split(googleHashHeaderValue, ',');
                            for (String hashEntry : hashEntries) {
                                if (hashEntry.startsWith("md5=")) {
                                    md5Base64 = hashEntry.substring("md5=".length());
                                    break;
                                }
                            }
                        }

                        long statusCheckedAt = Instant.now().toEpochMilli();
                        FileMetadata fileMetadata = new FileMetadata(relativeFilePath, lastModified, md5Base64, statusCheckedAt, fileLength);
                        return byteBufMono.then(Mono.just(fileMetadata));
                    }
                    catch (Exception ex) {
                        return byteBufMono.then(Mono.error(new FileSystemChangeException(relativeFilePath, "Failed to read the metadata, error: " + ex.getMessage(), ex)));
                    }
                });

        return fileMetadataMono;
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
        // TODO: we can check the timestamp of the last known file metadata (when it was checked) and if it is quite recent, we can avoid asking the cloud storage, but we need to configure the cache duration

        try {
            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            Storage storage = gsFileSystemRoot.getStorage();
            Path fullPathToFolderInsideBucket = fileSystemRoot.getRootPath() != null ?
                    (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                    relativeFilePath;
            String linuxStyleFullFolderInBucket = fullPathToFolderInsideBucket != null ?
                    fullPathToFolderInsideBucket.toString().replace('\\', '/') : null;

            Page<Blob> blogPage = null;
            if (linuxStyleFullFolderInBucket != null) {
                if (linuxStyleFullFolderInBucket != null && !linuxStyleFullFolderInBucket.endsWith("/")) {
                    linuxStyleFullFolderInBucket += "/";
                }
                blogPage = storage.list(gsFileSystemRoot.getBucketName(), Storage.BlobListOption.prefix(linuxStyleFullFolderInBucket));
            } else {
                blogPage = storage.list(gsFileSystemRoot.getBucketName());
            }

            long now = Instant.now().toEpochMilli();
            FolderMetadata folderMetadata = new FolderMetadata(relativeFilePath, now, true);

            for (Blob blob : blogPage.iterateAll()) {
                String blobFileName = blob.getName();
                Path fullBlobFilePathInsideBucket = Path.of(blobFileName);
                Map<String, String> metadata = blob.getMetadata();
                if (metadata != null && Objects.equals(metadata.get("DQOFileType"), "empty-parquet")) {
                    // ignoring because it is a special empty file to ensure that the schema of an external table could be detected from a parquet file
                    continue;
                }

                String md5Base64 = blob.getMd5();

                long updatedAt = blob.getUpdateTime();
                Path blobPathRelativeToRoot = fullBlobFilePathInsideBucket;
                if (fileSystemRoot.getRootPath() != null) {
                    blobPathRelativeToRoot = fileSystemRoot.getRootPath().relativize(blobPathRelativeToRoot);
                }

                FileMetadata fileMetadata = new FileMetadata(blobPathRelativeToRoot, updatedAt, md5Base64, now, blob.getSize());
                folderMetadata.addFile(fileMetadata);
            }

            folderMetadata.makeAllChildFoldersScanned();
            return folderMetadata;
        }
        catch (Exception ex) {
            throw new FileMetadataReadException(relativeFilePath, ex.getMessage(), ex);
        }
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
        try {
            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            Storage storage = gsFileSystemRoot.getStorage();
            Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                    (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                    relativeFilePath;
            String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

            BlobId blobId = BlobId.of(gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket);
            Blob blob = storage.get(blobId);

            // TODO: use the file size as a hint to download the whole object in one call (when it is small)

            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
            ErrorInjectionInputStream errorInjectionInputStream = new ErrorInjectionInputStream(pipedInputStream);

            CompletableFuture.runAsync(() -> {
                try {
                    blob.downloadTo(pipedOutputStream);
                    pipedOutputStream.flush();
                    pipedOutputStream.close();
                }
                catch (Exception ex) {
                    // failed to download the file
                    errorInjectionInputStream.injectException(ex);
                }
            });

            return errorInjectionInputStream;
        }
        catch (Exception ex) {
            throw new FileSystemReadException(relativeFilePath, ex.getMessage(), ex);
        }
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
        try {
            Storage storage = null;
            BlobInfo blobInfo = null;

            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            storage = gsFileSystemRoot.getStorage();
            Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                    (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                    relativeFilePath;
            String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

            Path fileName = relativeFilePath.getFileName();
            String contentType = fileName.endsWith(".yaml") ? "application/vnd.dqo.spec.yml" :
                    fileName.endsWith(".parquet") ? "application/vnd.apache.parquet" :
                            "application/octet-stream";

            BlobId blobId = BlobId.of(gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket);
            blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(contentType)
                    .setMd5(fileHashMd5Base64)
                    .build();

            int firstBlockSize = this.gcpConfigurationProperties.getUploadBufferSize();
            byte[] block = sourceStream.readNBytes(firstBlockSize);  // TODO: use the file size as a hint to allocate a smaller buffer, allocate a buffer +1 bytes of the known size.. if we get more, then we have to load the whole file
            if (block.length < firstBlockSize) {
                storage.create(blobInfo, block);
            }
            else {
                Blob blob = storage.create(blobInfo);

                try {
                    WriteChannel blobWriter = blob.writer();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(block);
                    blobWriter.write(byteBuffer);

                    int readCount = 0;
                    while ((readCount = sourceStream.read(block)) > 0) {
                        byteBuffer.limit(readCount);
                        blobWriter.write(byteBuffer);
                    }
                }
                catch (Exception ex) {
                    // upload failed, but we already created a blob, we need to delete it
                    storage.delete(blobId);
                }
            }
        }
        catch (Exception ex) {
            throw new FileSystemChangeException(relativeFilePath, ex.getMessage(), ex);
        }
        finally {
            CloseableHelper.closeSilently(sourceStream);
        }
    }

    /**
     * Deletes a remote file.
     *
     * @param fileSystemRoot   File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    @Override
    public void deleteFile(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath) {
        try {
            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            Storage storage = gsFileSystemRoot.getStorage();
            Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                    (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                    relativeFilePath;
            String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

            BlobId blobId = BlobId.of(gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket);
            storage.delete(blobId);
        }
        catch (Exception ex) {
            throw new FileSystemChangeException(relativeFilePath, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a file asynchronously.
     *
     * @param fileSystemRoot   File system root (with credentials).
     * @param relativeFilePath Relative file path inside the remote root.
     */
    @Override
    public Mono<Path> deleteFileAsync(FileSystemSynchronizationRoot fileSystemRoot, Path relativeFilePath) {
        GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
        Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                relativeFilePath;
        String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

        Mono<Path> deleteFileMono = this.sharedHttpClientProvider.getHttpClientGcpStorage()
                .headers(httpHeaders -> httpHeaders
                        .add(HttpHeaderNames.AUTHORIZATION, "Bearer " + this.dqoCloudAccessTokenCache.getCredentials(gsFileSystemRoot.getRootType()).getAccessToken().getTokenValue())
                        .add(HttpHeaderNames.CONTENT_LENGTH, 0)
                )
                .delete()
                .uri(String.format("https://%s.storage.googleapis.com/%s", gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket))
                .response()
                .thenReturn(relativeFilePath);

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
        if (deleteNonEmptyFolder) {
            FolderMetadata folderMetadata = listFilesInFolder(fileSystemRoot, relativeFolderPath, null);
            Collection<FileMetadata> allFiles = folderMetadata.getAllFiles();
            for (FileMetadata fileMetadata : allFiles) {
                deleteFile(fileSystemRoot, fileMetadata.getRelativePath());
            }
        }

        return true;
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
        if (!deleteNonEmptyFolder) {
            return Mono.just(true);
        }

        FolderMetadata folderMetadata = listFilesInFolder(fileSystemRoot, relativeFolderPath, null);
        Collection<FileMetadata> allFiles = folderMetadata.getAllFiles();

        Mono<Void> deleteFilesMono = Flux.fromIterable(allFiles)
                .flatMap(fileMetadata -> deleteFileAsync(fileSystemRoot, fileMetadata.getRelativePath()),
                        this.gcpConfigurationProperties.getParallelDeleteOperations(), this.gcpConfigurationProperties.getParallelDeleteOperations())
                .then();

        return deleteFilesMono.thenReturn(true);
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
        GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
        Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                relativeFilePath;
        String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

        Mono<DownloadFileResponse> downloadFileMono = this.sharedHttpClientProvider.getHttpClientGcpStorage()
                .headers(httpHeaders -> httpHeaders
                        .add(HttpHeaderNames.AUTHORIZATION, "Bearer " + this.dqoCloudAccessTokenCache.getCredentials(gsFileSystemRoot.getRootType()).getAccessToken().getTokenValue())
                )
                .get()
                .uri(String.format("https://%s.storage.googleapis.com/%s", gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket))
//                .responseSingle(((httpClientResponse, byteBufMono) -> {
//                    if (httpClientResponse.status() == HttpResponseStatus.OK) {
//                        return byteBufMono.flatMap(byteBuf ->
//                                Mono.just(new DownloadFileResponse(lastKnownFileMetadata, ByteBufFlux.fromInbound(Mono.just(byteBuf)))));
//                    }
//                    else {
//                        return byteBufMono.then(Mono.error(new FileSystemChangeException(relativeFilePath,
//                                "Cannot download file " + linuxStyleFullFileInBucket + ", error: " + httpClientResponse.status().code())));
//                    }
//                }));

                .responseConnection(((httpClientResponse, connection) -> {
                    if (httpClientResponse.status() == HttpResponseStatus.OK) {
                        return Mono.just(new DownloadFileResponse(lastKnownFileMetadata, connection.inbound().receive()));
                    }
                    else {
                        return connection.inbound().receive().then(Mono.error(new FileSystemChangeException(relativeFilePath,
                                "Cannot download file " + linuxStyleFullFileInBucket + ", error: " + httpClientResponse.status().code())));
                    }
                }))
                .single();

        return downloadFileMono;
    }

    /**
     * Uploads a file to the file system as an asynchronous operation using Flux.
     *
     * @param fileSystemRoot   File system root.
     * @param relativeFilePath Relative path to the uploaded file.
     * @param bytesFlux        Source flux with byte buffers to be uploaded.
     * @param fileMetadata     File metadata with the file length and file content hash.
     * @return Mono returned when the file was fully uploaded.
     */
    @Override
    public Mono<Path> uploadFileAsync(FileSystemSynchronizationRoot fileSystemRoot,
                                      Path relativeFilePath,
                                      ByteBufFlux bytesFlux,
                                      FileMetadata fileMetadata) {
        try {
            GSFileSystemSynchronizationRoot gsFileSystemRoot = (GSFileSystemSynchronizationRoot) fileSystemRoot;
            Path fullPathToFileInsideBucket = fileSystemRoot.getRootPath() != null ?
                    (relativeFilePath != null ? fileSystemRoot.getRootPath().resolve(relativeFilePath) : fileSystemRoot.getRootPath()) :
                    relativeFilePath;
            String linuxStyleFullFileInBucket = fullPathToFileInsideBucket.toString().replace('\\', '/');

            Path fileName = relativeFilePath.getFileName();
            String contentType = fileName.endsWith(".yaml") ? "application/vnd.dqo.spec.yml" :
                    fileName.endsWith(".parquet") ? "application/vnd.apache.parquet" :
                            "application/octet-stream";

            Mono<HttpClientResponse> uploadFileMono = this.sharedHttpClientProvider.getHttpClientGcpStorage()
                    .headers(httpHeaders -> httpHeaders
                            .add(HttpHeaderNames.AUTHORIZATION, "Bearer " + this.dqoCloudAccessTokenCache.getCredentials(gsFileSystemRoot.getRootType()).getAccessToken().getTokenValue())
                            .add(HttpHeaderNames.CONTENT_TYPE, contentType)
                            .add(HttpHeaderNames.CONTENT_LENGTH, fileMetadata.getFileLength())
                            .add("x-goog-hash", "md5=" + fileMetadata.getMd5()))
                    .put()
                    .uri(String.format("https://%s.storage.googleapis.com/%s", gsFileSystemRoot.getBucketName(), linuxStyleFullFileInBucket))
                    .send(bytesFlux)
                    .response()
                    .flatMap(httpClientResponse -> {
                        if (httpClientResponse.status() == HttpResponseStatus.OK) {
                            return Mono.just(httpClientResponse);
                        }
                        else {
                            return Mono.error(new FileSystemChangeException(relativeFilePath,
                                    "Failed to upload a file to DQO Cloud, http error code: " + httpClientResponse.status().code()));
                        }
                    });

            return uploadFileMono.thenReturn(relativeFilePath);
        }
        catch (Exception ex) {
            throw new FileSystemChangeException(relativeFilePath, ex.getMessage(), ex);
        }
    }
}
