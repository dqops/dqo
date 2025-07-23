/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
        Assertions.assertEquals(11, this.sut.getSubFolders().size());
    }

    @Test
    void loadChildFilesAndFolders_whenDefaultFilesAndFoldersPresent_thenReturnsThoseFiles() {
		this.sut.loadChildFilesAndFolders();
        Assertions.assertEquals(3, this.sut.getFiles().size());
        Assertions.assertEquals(11, this.sut.getSubFolders().size());
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
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath(), true));

        this.sut.flush();

        Assertions.assertFalse(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath(), true));
    }

    @Test
    void flush_whenSubFolderMarkedForDeletion_thenDeletesSubFolderAndRemovesFromSubFolders() {
        FolderTreeNode subfolder = this.sut.getOrAddDirectFolder("subfolder");
        subfolder.addChildFile("newfile.txt", new FileContent("content"));
		sut.flush();
        Assertions.assertNotNull(sut.getChildFolder("subfolder"));
        subfolder.deleteChildFile("newfile.txt");
        subfolder.setDeleteOnFlush(true);
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath(), true));
        Assertions.assertSame(subfolder, sut.getChildFolder("subfolder"));
        Assertions.assertEquals(12, sut.getSubFolders().size());

		sut.flush();

        Assertions.assertFalse(this.sut.getContext().getStorageService().folderExists(subfolder.getFolderPath(), true));
        Assertions.assertTrue(this.sut.getContext().getStorageService().folderExists(sut.getFolderPath(), true));
        Assertions.assertEquals(11, sut.getSubFolders().size());
    }
}
