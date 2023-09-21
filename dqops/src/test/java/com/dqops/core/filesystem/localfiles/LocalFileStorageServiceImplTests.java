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

    @Autowired
    private LocalHomeTestUtilities localUserHomeTestUtilities;

    @BeforeEach
    void setUp() {
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
        List<HomeFilePath> files = this.sut.listFiles(new HomeFolderPath());
        Assertions.assertEquals(3, files.size());
        files.sort(Comparator.comparing(HomeFilePath::getFileName));
        Assertions.assertEquals(".DQO_USER_HOME", files.get(0).getFileName());
        Assertions.assertEquals(".gitignore", files.get(1).getFileName());
        Assertions.assertEquals(".localsettings.dqosettings.yaml", files.get(2).getFileName());
    }

    @Test
    void listFolders_whenRootFolder_thenReturnsStandardFolders() {
        List<HomeFolderPath> folders = this.sut.listFolders(new HomeFolderPath());
        Assertions.assertEquals(9, folders.size());
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("sources")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("rules")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("sensors")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("checks")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals("settings")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".data")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".index")));
        Assertions.assertTrue(folders.stream().anyMatch(f -> f.getTopFolder().getFileSystemName().equals(".credentials")));
    }

    @Test
    void saveFile_whenFileInRootFolderWritten_thenFileCanBeRead() {
        final String textContent = "file content\n";
        FileContent fileContent = new FileContent(textContent);
        HomeFilePath filePath = new HomeFilePath(new HomeFolderPath(), "file.txt");
		this.sut.saveFile(filePath, fileContent);
        FileContent restoredFile = this.sut.readTextFile(filePath);
        Assertions.assertNotNull(restoredFile);
        Assertions.assertEquals(textContent, restoredFile.getTextContent());
    }

    @Test
    void saveTextFile_whenFileInSubFolderWritten_thenFileCanBeRead() {
        final String textContent = "file content2\n";
        FileContent fileContent = new FileContent(textContent);
        FolderName[] folderNames = {FolderName.fromObjectName("conn1")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "file.txt");
		this.sut.saveFile(filePath, fileContent);

        FileContent restoredFile = this.sut.readTextFile(filePath);
        Assertions.assertNotNull(restoredFile);
        Assertions.assertEquals(textContent, restoredFile.getTextContent());
    }

    @Test
    void saveTextFile_whenFileInSubFolderWritten_thenFileCanBeListed() {
        final String textContent = "file content2\n";
        FileContent fileContent = new FileContent(textContent);
        FolderName[] folderNames = {FolderName.fromObjectName("conn2")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "file.txt");
		this.sut.saveFile(filePath, fileContent);

        List<HomeFilePath> files = this.sut.listFiles(folderPath);
        Assertions.assertEquals(1, files.size());
        Assertions.assertEquals("file.txt", files.get(0).getFileName());
    }

    @Test
    void readTextFile_whenFileMissingInRootFolder_thenReturnsNull() {
        FileContent restoredFile = this.sut.readTextFile(new HomeFilePath(new HomeFolderPath(), "missing.txt"));
        Assertions.assertNull(restoredFile);
    }

    @Test
    void readTextFile_whenFileMissingInMissingSubfolder_thenReturnsNull() {
        FileContent restoredFile = this.sut.readTextFile(new HomeFilePath(new HomeFolderPath(FolderName.fromObjectName("nosuchfolder")), "missing.txt"));
        Assertions.assertNull(restoredFile);
    }

    @Test
    void fileExists_whenFileMissing_thenReturnsFalse() {
        HomeFilePath filePath = new HomeFilePath(new HomeFolderPath(FolderName.fromObjectName("conn4")), "missing.txt");
        Assertions.assertFalse(this.sut.fileExists(filePath));
    }

    @Test
    void fileExists_whenFilePresent_thenReturnsTrue() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"));

        boolean result = this.sut.fileExists(filePath);
        Assertions.assertTrue(result);
    }

    @Test
    void deleteFile_whenFilePresent_thenFileIsDeleted() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn6")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"));
        Assertions.assertTrue(this.sut.fileExists(filePath));

        Assertions.assertTrue(this.sut.deleteFile(filePath));

        Assertions.assertFalse(this.sut.fileExists(filePath));
        FileContent restoredFile2= this.sut.readTextFile(filePath);
        Assertions.assertNull(restoredFile2);
    }

    @Test
    void tryDeleteFolder_whenFolderExists_thenDeleteFolder() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"));
		this.sut.deleteFile(filePath); // a folder that had a file
        Assertions.assertTrue(this.sut.folderExists(folderPath));

        Assertions.assertTrue(this.sut.tryDeleteFolder(folderPath));

        Assertions.assertFalse(this.sut.folderExists(folderPath));
    }

    @Test
    void tryDeleteFolder_whenFolderExistsAndHasFiles_thenReturnsFalseAndPreservesFolder() {
        FolderName[] folderNames = {FolderName.fromObjectName("conn5")};
        HomeFolderPath folderPath = new HomeFolderPath(folderNames);
        HomeFilePath filePath = new HomeFilePath(folderPath, "exist.txt");
		this.sut.saveFile(filePath, new FileContent("content"));
        Assertions.assertTrue(this.sut.folderExists(folderPath));

        Assertions.assertFalse(this.sut.tryDeleteFolder(folderPath));

        Assertions.assertTrue(this.sut.folderExists(folderPath));
    }
}
