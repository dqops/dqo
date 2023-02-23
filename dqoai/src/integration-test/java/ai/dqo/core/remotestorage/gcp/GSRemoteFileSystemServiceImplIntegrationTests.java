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
package ai.dqo.core.remotestorage.gcp;

import ai.dqo.BaseIntegrationTest;
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import ai.dqo.core.dqocloud.filesystem.DqoCloudRemoteFileSystemServiceFactoryImpl;
import ai.dqo.core.filesystem.filesystemservice.contract.DownloadFileResponse;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.filesystemservice.contract.FileSystemService;
import ai.dqo.core.filesystem.filesystemservice.localfiles.DqoUserHomeFileSystemFactoryImpl;
import ai.dqo.core.filesystem.metadata.FileMetadata;
import ai.dqo.core.filesystem.metadata.FolderMetadata;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
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
public class GSRemoteFileSystemServiceImplIntegrationTests extends BaseIntegrationTest {
    private GSRemoteFileSystemServiceImpl sut;
    private DqoUserHomeFileSystemFactoryImpl userHomeFileSystemFactory;
    private UserHomeContext emptyUserHomeContext;
    private DqoCloudRemoteFileSystemServiceFactoryImpl gcpFileSystemFactory;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        this.sut = beanFactory.getBean(GSRemoteFileSystemServiceImpl.class);
        this.userHomeFileSystemFactory = beanFactory.getBean(DqoUserHomeFileSystemFactoryImpl.class);
        this.emptyUserHomeContext = UserHomeContextObjectMother.createDefaultHomeContext(true);
        this.gcpFileSystemFactory = beanFactory.getBean(DqoCloudRemoteFileSystemServiceFactoryImpl.class);
    }

    @Test
    void listFilesInFolder_whenEmptySourceFolder_thenNoFilesReturned() {
        DqoFileSystem remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources);
        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);

        Assertions.assertNotNull(folderMetadata);
        Assertions.assertEquals(0, folderMetadata.getFiles().size());
    }

    @Test
    void uploadFile_whenLocalFileUploaded_thenFileCreatedInBucket() {
        DqoFileSystem remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src1");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        DqoFileSystem localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources);
        Path relativeFilePath = Path.of("src1", "connection.dqoconnection.yaml");
        FileSystemService localFileSystemFileSystemService = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemService.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        InputStream localFileInputStream = localFileSystemFileSystemService.downloadFile(localFileSystem.getFileSystemRoot(), relativeFilePath);

        this.sut.uploadFile(remoteFileSystem.getFileSystemRoot(), relativeFilePath, localFileInputStream, localFileMetadata.getFileHash());

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
        Assertions.assertEquals(1, folderMetadata.getFolders().size());
        Assertions.assertEquals(1, folderMetadata.getFolders().get("src1").getFiles().size());

        this.sut.deleteFolder(remoteFileSystem.getFileSystemRoot(), null, true);

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
    }

    @Test
    void uploadFileAsync_whenLocalFileUploaded_thenFileCreatedInBucket() {
        DqoFileSystem remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src1");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        DqoFileSystem localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources);
        Path relativeFilePath = Path.of("src1", "connection.dqoconnection.yaml");
        FileSystemService localFileSystemFileSystemService = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemService.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        Mono<DownloadFileResponse> downloadLocalFileMono = localFileSystemFileSystemService.downloadFileAsync(localFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata);

        Mono<Void> uploadMono = downloadLocalFileMono.flatMap(localFileDownloadResponse -> {
            return this.sut.uploadFileAsync(remoteFileSystem.getFileSystemRoot(),
                    relativeFilePath, localFileDownloadResponse.getByteBufFlux(), localFileDownloadResponse.getMetadata());
        }).then();
        uploadMono.block();

        FileMetadata currentFileMetadata = this.sut.readFileMetadataAsync(remoteFileSystem.getFileSystemRoot(), relativeFilePath, null)
                .block();
        Assertions.assertEquals(localFileMetadata.getFileLength(), currentFileMetadata.getFileLength());
        Assertions.assertEquals(localFileMetadata.getFileName(), currentFileMetadata.getFileName());
        Assertions.assertEquals(localFileMetadata.getRelativePath(), currentFileMetadata.getRelativePath());
        Assertions.assertTrue(Arrays.equals(localFileMetadata.getFileHash(), currentFileMetadata.getFileHash()));

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
        Assertions.assertEquals(1, folderMetadata.getFolders().size());
        Assertions.assertEquals(1, folderMetadata.getFolders().get("src1").getFiles().size());

        this.sut.deleteFolderAsync(remoteFileSystem.getFileSystemRoot(), null, true).block();

        FolderMetadata folderMetadataAfterDelete = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadataAfterDelete);
        Assertions.assertEquals(0, folderMetadataAfterDelete.getFolders().size());
    }

    @Test
    void downloadFileAsync_whenLocalFileUploaded_thenFileCouldBeDownloaded() {
        DqoFileSystem remoteFileSystem = this.gcpFileSystemFactory.createRemoteDqoCloudFSRW(DqoRoot.sources);

        ConnectionWrapper initialConnWrapper = this.emptyUserHomeContext.getUserHome().getConnections().createAndAddNew("src2");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        emptyUserHomeContext.flush();

        DqoFileSystem localFileSystem = this.userHomeFileSystemFactory.createUserHomeFolderFileSystem(DqoRoot.sources);
        Path relativeFilePath = Path.of("src2", "connection.dqoconnection.yaml");
        FileSystemService localFileSystemFileSystemService = localFileSystem.getFileSystemService();
        FileMetadata localFileMetadata = localFileSystemFileSystemService.readFileMetadata(localFileSystem.getFileSystemRoot(), relativeFilePath, null);
        Assertions.assertNotNull(localFileMetadata);
        Mono<DownloadFileResponse> downloadLocalFileMono = localFileSystemFileSystemService.downloadFileAsync(localFileSystem.getFileSystemRoot(), relativeFilePath, localFileMetadata);

        Mono<Void> uploadMono = downloadLocalFileMono.flatMap(localFileDownloadResponse -> {
            return this.sut.uploadFileAsync(remoteFileSystem.getFileSystemRoot(),
                    relativeFilePath, localFileDownloadResponse.getByteBufFlux(), localFileDownloadResponse.getMetadata());
        }).then();
        uploadMono.block();

        FolderMetadata folderMetadata = this.sut.listFilesInFolder(remoteFileSystem.getFileSystemRoot(), null, null);
        Assertions.assertNotNull(folderMetadata);
        Assertions.assertEquals(1, folderMetadata.getFolders().size());
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
}
