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

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FolderName;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class LocalFileStorageServiceImplTests extends BaseTest {
    private LocalFileStorageService sut;
    private LocalHomeTestUtilities localUserHomeTestUtilities;

    @BeforeEach
    void setUp() {
        this.localUserHomeTestUtilities = BeanFactoryObjectMother.getBeanFactory().getBean(LocalHomeTestUtilities.class);
		this.sut = LocalHomeStorageServiceObjectMother.createLocalUserHomeStorageServiceForTestableHome(true);
    }

    @AfterEach
    void tearDown() {
		this.localUserHomeTestUtilities.recreateTestUserHome();
    }

    @Test
    void resolveService_whenInterfaceRequested_thenImplementationReturned() {
        Assertions.assertNotNull(sut);
        Assertions.assertInstanceOf(LocalFileStorageServiceImpl.class, sut);
    }

    @Test
    void listFiles_whenRootFolder_thenReturnsJustGitIgnore() {
        List<HomeFilePath> files = this.sut.listFiles(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), false);
        Assertions.assertEquals(3, files.size());
        files.sort(Comparator.comparing(HomeFilePath::getFileName));
        Assertions.assertEquals(".DQO_USER_HOME", files.get(0).getFileName());
        Assertions.assertEquals(".gitignore", files.get(1).getFileName());
        Assertions.assertEquals(".localsettings.dqosettings.yaml", files.get(2).getFileName());
    }

    @Test
    void listFolders_whenRootFolder_thenReturnsStandardFolders() {
        List<HomeFolderPath> folders = this.sut.listFolders(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), false);
        Assertions.assertEquals(11, folders.size());
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("sources")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("rules")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("sensors")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("checks")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("settings")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("patterns")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("dictionaries")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".data")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".index")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".credentials")));
    }

    @Test
    void saveFile_whenFileInRootFolderWritten_thenFileCanBeRead() {
        final String textContent = "file content\n";
        FileContent fileContent = new FileContent(textContent);
        HomeFilePath filePath = new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt");
		this.sut.saveFile(filePath, fileContent, false);
        FileContent restoredFile = this.sut.readFile(filePath, false);
        Assertions.assertNotNull(restoredFile);
        Assertions.assertEquals(textContent, restoredFile.getTextContent());
    }

    @Test
    void saveTextFile_whenFileInSubFolderWritten_thenFileCanBeRead() {
        final String textContent = "file content2\n";
        FileContent fileContent = new FileContent(textContent);
        FolderName[] folderNames = {FolderName.fromObjectName("conn1")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "file.txt");
		this.sut.saveFile(filePath, fileContent, false);

        FileContent restoredFile = this.sut.readFile(filePath, false);
        Assertions.assertNotNull(restoredFile);
        Assertions.assertEquals(textContent, restoredFile.getTextContent());
    }

    @Test
    void saveTextFile_whenFileInSubFolderWritten_thenFileCanBeListed() {
        final String textContent = "file content2\n";
        FileContent fileContent = new FileContent(textContent);
        FolderName[] folderNames = {FolderName.fromObjectName("conn2")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "file.txt");
		this.sut.saveFile(filePath, fileContent, false);

        List<HomeFilePath> files = this.sut.listFiles(folderPath, false);
        Assertions.assertEquals(1, files.size());
        Assertions.assertEquals("file.txt", files.get(0).getFileName());
    }

    @Test
    void readTextFile_whenFileMissingInRootFolder_thenReturnsNull() {
        FileContent restoredFile = this.sut.readFile(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "missing.txt"), false);
        Assertions.assertNull(restoredFile);
    }

    @Test
    void readTextFile_whenFileMissingInMissingSubfolder_thenReturnsNull() {
        FileContent restoredFile = this.sut.readFile(new HomeFilePath(
                new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, FolderName.fromObjectName("nosuchfolder")), "missing.txt"), false);
        Assertions.assertNull(restoredFile);
    }

    @Test
    void fileExists_whenFileMissing_thenReturnsFalse() {
        HomeFilePath filePath = new HomeFilePath(new HomeFolderPath(
                UserDomainIdentity.DEFAULT_DATA_DOMAIN, FolderName.fromObjectName("conn4")), "missing.txt");
        Assertions.assertFalse(this.sut.fileExists(filePath, false));
    }

    @Test
    void fileExists_whenFilePresent_thenReturnsTrue() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"), false);

        boolean result = this.sut.fileExists(filePath, false);
        Assertions.assertTrue(result);
    }

    @Test
    void deleteFile_whenFilePresent_thenFileIsDeleted() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn6")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"), false);
        Assertions.assertTrue(this.sut.fileExists(filePath, false));

        Assertions.assertTrue(this.sut.deleteFile(filePath, false));

        Assertions.assertFalse(this.sut.fileExists(filePath, false));
        FileContent restoredFile2= this.sut.readFile(filePath, false);
        Assertions.assertNull(restoredFile2);
    }

    @Test
    void tryDeleteFolder_whenFolderExists_thenDeleteFolder() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"), false);
		this.sut.deleteFile(filePath, false); // a folder that had a file
        Assertions.assertTrue(this.sut.folderExists(folderPath, false));

        Assertions.assertTrue(this.sut.tryDeleteFolder(folderPath, false));

        Assertions.assertFalse(this.sut.folderExists(folderPath, false));
    }

    @Test
    void tryDeleteFolder_whenFolderExistsAndHasFiles_thenReturnsFalseAndPreservesFolder() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"), false);
        Assertions.assertTrue(this.sut.folderExists(folderPath, false));

        Assertions.assertFalse(this.sut.tryDeleteFolder(folderPath, false));

        Assertions.assertTrue(this.sut.folderExists(folderPath, false));
    }
}
