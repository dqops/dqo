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
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class LocalFolderTreeNodeTests extends BaseTest {
    private LocalFolderTreeNode sut;

    @BeforeEach
    void setUp() {
		this.sut = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
    }

    @Test
    void isLocalFileSystem_whenCalled_thenReturnsTrue() {
        Assertions.assertTrue(this.sut.isLocalFileSystem());
    }

    @Test
    void getPhysicalAbsolutePath_whenCalledForRootFolder_thenReturnsHomePath() {
        Path physicalAbsolutePath = this.sut.getPhysicalAbsolutePath();
        Assertions.assertNotNull(physicalAbsolutePath);
        Assertions.assertEquals(this.sut.getContext().getStorageService().getHomeRootDirectory().replace('\\', '/'),
                physicalAbsolutePath.toString().replace('\\', '/'));
    }

    @Test
    void getPhysicalAbsolutePath_whenCalledForSubFolderFolder_thenReturnsPathToFolder() {
        FolderTreeNode subfolder = this.sut.getOrAddDirectFolder("subfolder");
        Path physicalAbsolutePath = subfolder.getPhysicalAbsolutePath();
        Assertions.assertNotNull(physicalAbsolutePath);
        Assertions.assertEquals(this.sut.getContext().getStorageService().getHomeRootDirectory().replace('\\', '/') + "/subfolder",
                physicalAbsolutePath.toString().replace('\\', '/'));
    }

    @Test
    void getFiles_whenCalledBeforeAddingFiles_thenReturnsJustLoadedList() {
        Assertions.assertNotNull(this.sut.getFiles());
        Assertions.assertEquals(3, this.sut.getFiles().size());
    }

    @Test
    void getSubFolders_whenCalledOnNewDirectory_thenReturnsLoadedFolders() {
        Assertions.assertNotNull(this.sut.getSubFolders());
        Assertions.assertEquals(9, this.sut.getSubFolders().size());
    }

    @Test
    void loadChildFilesAndFolders_whenDefaultFilesAndFoldersPresent_thenReturnsThoseFiles() {
		this.sut.loadChildFilesAndFolders();
        Assertions.assertEquals(3, this.sut.getFiles().size());
        Assertions.assertEquals(9, this.sut.getSubFolders().size());
        Assertions.assertNotNull(this.sut.getChildFileByFileName(".gitignore"));
    }

    @Test
    void flush_whenFileSaved_thenFileIsFoundInNewTreeNode() {
		this.sut.addChildFile("newfile.txt", new FileContent("content"));
		this.sut.flush();

        LocalFolderTreeNode sut2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode fileReloaded = sut2.getChildFileByFileName("newfile.txt");
        Assertions.assertNotNull(fileReloaded);
        Assertions.assertEquals("content", fileReloaded.getContent().getTextContent());
    }

    @Test
    void flush_whenFileSavedInSubFolder_thenFileIsFoundInNewTreeNode() {
        FolderTreeNode subfolder = this.sut.getOrAddDirectFolder("subfolder");
        subfolder.addChildFile("newfile.txt", new FileContent("content"));
		this.sut.flush();

        LocalFolderTreeNode sut2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FolderTreeNode subfolder2 = sut2.getChildFolder("subfolder");
        FileTreeNode fileReloaded = subfolder2.getChildFileByFileName("newfile.txt");
        Assertions.assertNotNull(fileReloaded);
        Assertions.assertEquals("content", fileReloaded.getContent().getTextContent());
    }

    @Test
    void flush_whenFolderMarkedForDeletion_thenDeletesFolder() {
        FolderTreeNode sut = this.sut.getOrAddDirectFolder("subfolder");
        sut.addChildFile("newfile.txt", new FileContent("content"));
        this.sut.flush();
        sut.deleteChildFile("newfile.txt");
        sut.setDeleteOnFlush(true);
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath()));

        this.sut.flush();

        Assertions.assertFalse(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath()));
    }

    @Test
    void flush_whenSubFolderMarkedForDeletion_thenDeletesSubFolderAndRemovesFromSubFolders() {
        FolderTreeNode subfolder = this.sut.getOrAddDirectFolder("subfolder");
        subfolder.addChildFile("newfile.txt", new FileContent("content"));
		sut.flush();
        Assertions.assertNotNull(sut.getChildFolder("subfolder"));
        subfolder.deleteChildFile("newfile.txt");
        subfolder.setDeleteOnFlush(true);
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath()));
        Assertions.assertSame(subfolder, sut.getChildFolder("subfolder"));
        Assertions.assertEquals(10, sut.getSubFolders().size());

		sut.flush();

        Assertions.assertFalse(this.sut.getContext().getStorageService().folderExists(subfolder.getFolderPath()));
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath()));
        Assertions.assertEquals(9, sut.getSubFolders().size());
    }
}
