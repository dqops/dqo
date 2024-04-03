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
package com.dqops.core.synchronization.filesystems.gcp;

import com.dqops.BaseIntegrationTest;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.synchronization.filesystems.dqocloud.DqoCloudRemoteFileSystemServiceFactoryImpl;
import com.dqops.core.synchronization.contract.DownloadFileResponse;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.FileSystemSynchronizationOperations;
import com.dqops.core.synchronization.filesystems.gcp.GSRemoteFileSystemSynchronizationOperationsImpl;
import com.dqops.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactoryImpl;
import com.dqops.core.filesystem.metadata.FileMetadata;
import com.dqops.core.filesystem.metadata.FolderMetadata;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

@SpringBootTest
public class GSRemoteFileSystemSynchronizationOperationsImplIntegrationTests extends BaseIntegrationTest {
    private GSRemoteFileSystemSynchronizationOperationsImpl sut;
    private LocalSynchronizationFileSystemFactoryImpl userHomeFileSystemFactory;
    private UserHomeContext emptyUserHomeContext;
    private DqoCloudRemoteFileSystemServiceFactoryImpl gcpFileSystemFactory;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        this.sut = beanFactory.getBean(GSRemoteFileSystemSynchronizationOperationsImpl.class);
        this.userHomeFileSystemFactory = beanFactory.getBean(LocalSynchronizationFileSystemFactoryImpl.class);
        this.emptyUserHomeContext = UserHomeContextObjectMother.createDefaultHomeContext(true);
        this.gcpFileSystemFactory = beanFactory.getBean(DqoCloudRemoteFileSystemServiceFactoryImpl.class);
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
    }

    @Test
    void listFilesInFolder_whenEmptySourceFolder_thenNoFilesReturned() {
        SynchronizationRoot remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources, this.userDomainIdentity);
        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);

        Assertions.assertNotNull(folderMetadata);
        Assertions.assertEquals(0, folderMetadata.getFiles().size());
    }

    @Test
    void uploadFile_whenLocalFileUploaded_thenFileCreatedInBucket() {
        SynchronizationRoot remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources, this.userDomainIdentity);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src1");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        SynchronizationRoot localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources, this.userDomainIdentity);
        Path relativeFilePath = Path.of("src1", "connection.dqoconnection.yaml");
        FileSystemSynchronizationOperations localFileSystemFileSystemSynchronizationOperations = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemSynchronizationOperations.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        InputStream localFileInputStream = localFileSystemFileSystemSynchronizationOperations.downloadFile(localFileSystem.getFileSystemRoot(), relativeFilePath);

        this.sut.uploadFile(remoteFileSystem.getFileSystemRoot(), relativeFilePath, localFileInputStream, localFileMetadata.getMd5());

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
//        Assertions.assertEquals(1, folderMetadata.getFolders().size());
        Assertions.assertEquals(1, folderMetadata.getFolders().get("src1").getFiles().size());

        this.sut.deleteFolder(remoteFileSystem.getFileSystemRoot(), null, true);

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
    }

    @Test
    void uploadFileAsync_whenLocalFileUploaded_thenFileCreatedInBucket() {
        SynchronizationRoot remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources, this.userDomainIdentity);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src1");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        SynchronizationRoot localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources, this.userDomainIdentity);
        Path relativeFilePath = Path.of("src1", "connection.dqoconnection.yaml");
        FileSystemSynchronizationOperations localFileSystemFileSystemSynchronizationOperations = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemSynchronizationOperations.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        Mono<DownloadFileResponse> downloadLocalFileMono = localFileSystemFileSystemSynchronizationOperations.downloadFileAsync(localFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata);

        Mono<FileMetadata> uploadMono = this.sut.uploadFileAsync(remoteFileSystem.getFileSystemRoot(),
                    relativeFilePath, downloadLocalFileMono);

        uploadMono.block();

        FileMetadata currentFileMetadata = this.sut.readFileMetadataAsync(remoteFileSystem.getFileSystemRoot(), relativeFilePath, null)
                .block();
        Assertions.assertEquals(localFileMetadata.getFileLength(), currentFileMetadata.getFileLength());
        Assertions.assertEquals(localFileMetadata.getFileName(), currentFileMetadata.getFileName());
        Assertions.assertEquals(localFileMetadata.getRelativePath(), currentFileMetadata.getRelativePath());
        Assertions.assertEquals(localFileMetadata.getMd5(), currentFileMetadata.getMd5());

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
//        Assertions.assertEquals(1, folderMetadata.getFolders().size());
        Assertions.assertEquals(1, folderMetadata.getFolders().get("src1").getFiles().size());

        this.sut.deleteFolderAsync(remoteFileSystem.getFileSystemRoot(), null, true).block();

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
    }

    @Test
    void downloadFileAsync_whenLocalFileUploaded_thenFileCouldBeDownloaded() {
        SynchronizationRoot remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources, this.userDomainIdentity);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src2");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        SynchronizationRoot localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources, this.userDomainIdentity);
        Path relativeFilePath = Path.of("src2", "connection.dqoconnection.yaml");
        FileSystemSynchronizationOperations localFileSystemFileSystemSynchronizationOperations = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemSynchronizationOperations.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        Mono<DownloadFileResponse> downloadLocalFileMono = localFileSystemFileSystemSynchronizationOperations.downloadFileAsync(localFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata);

        Mono<FileMetadata> uploadMono = this.sut.uploadFileAsync(remoteFileSystem.getFileSystemRoot(),
                    relativeFilePath, downloadLocalFileMono);
        uploadMono.block();

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
//        Assertions.assertEquals(1, folderMetadata.getFolders().size());
        Assertions.assertEquals(1, folderMetadata.getFolders().get("src2").getFiles().size());

        Mono<ByteBuf> fileResult = this.sut.downloadFileAsync(remoteFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata)
                .flatMap(downloadFileResponse -> {
                    return downloadFileResponse.getByteBufFlux().aggregate();
                });

        ByteBuf downloadedContent = fileResult.block();
        Assertions.assertEquals(localFileMetadata.getFileLength(), (long)downloadedContent.capacity());

        this.sut.deleteFolderAsync(remoteFileSystem.getFileSystemRoot(), null, true).block();

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
    }

    @Test
    void downloadFileAsync_whenLocalFileUploadedAndFileHasSpacesAndSpecialCharacters_thenFileCouldBeDownloaded() {
        SynchronizationRoot remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.credentials, this.userDomainIdentity);

        SharedCredentialWrapper credentialWrapper = this.emptyUserHomeContext.getUserHome().getCredentials().createAndAddNew("Some credential %?.txt");
        credentialWrapper.setObject(new FileContent("Text content"));
        emptyUserHomeContext.flush();

        SynchronizationRoot localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.credentials, this.userDomainIdentity);
        Path relativeFilePath = Path.of("Some credential %25%3F.txt");
        Assertions.assertTrue(localFileSystem.getFileSystemRoot().getRootPath().resolve(relativeFilePath).toFile().exists());

        FileSystemSynchronizationOperations localFileSystemFileSystemSynchronizationOperations = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemSynchronizationOperations.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        Mono<DownloadFileResponse> downloadLocalFileMono = localFileSystemFileSystemSynchronizationOperations.downloadFileAsync(localFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata);

        Mono<FileMetadata> uploadMono = this.sut.uploadFileAsync(remoteFileSystem.getFileSystemRoot(),
                relativeFilePath, downloadLocalFileMono);
        uploadMono.block();

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
        Assertions.assertNotNull(folderMetadata.getFiles().get("Some credential %25%3F.txt"));

        Mono<ByteBuf> fileResult = this.sut.downloadFileAsync(remoteFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata)
                .flatMap(downloadFileResponse -> {
                    return downloadFileResponse.getByteBufFlux().aggregate();
                });

        ByteBuf downloadedContent = fileResult.block();
        Assertions.assertEquals(localFileMetadata.getFileLength(), (long)downloadedContent.capacity());

        this.sut.deleteFolderAsync(remoteFileSystem.getFileSystemRoot(), null, true).block();

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFiles().size());
    }
}
