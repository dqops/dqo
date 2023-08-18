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
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FileTreeNodeStatus;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class LocalFileTreeNodeTests extends BaseTest {
    private LocalFolderTreeNode folder;

    @BeforeEach
    void setUp() {
		this.folder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
    }

    @Test
    void isLocalFileSystem_whenCalled_thenReturnsTrue() {
        FileTreeNode sut = this.folder.addChildFile("file1.txt", new FileContent("content"));
        Assertions.assertTrue(sut.isLocalFileSystem());
    }

    @Test
    void getPhysicalAbsolutePath_whenCalledForFile_thenReturnsDirectPathToFile() {
        FileTreeNode sut = this.folder.addChildFile("file1.txt", new FileContent("content"));
        Path physicalAbsolutePath = sut.getPhysicalAbsolutePath();
        Assertions.assertNotNull(physicalAbsolutePath);
        Assertions.assertEquals(this.folder.getContext().getStorageService().getHomeRootDirectory().replace('\\', '/') + "/file1.txt",
                physicalAbsolutePath.toString().replace('\\', '/'));
    }

    @Test
    void getContent_whenFilePresentAndCalledForTheFirstTime_thenLoadsContent() {
		folder.addChildFile("file1.txt", new FileContent("content"));
		folder.flush();

        LocalFolderTreeNode home2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode sut = home2.getChildFileByFileName("file1.txt");
        FileContent content = sut.getContent();
        Assertions.assertNotNull(content);
        Assertions.assertEquals("content", content.getTextContent());
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());
    }

    @Test
    void getContent_whenFilePresentAndCalledForTheSecondTime_thenReturnsAlreadyLoadedContent() {
		folder.addChildFile("file1.txt", new FileContent("content"));
		folder.flush();

        LocalFolderTreeNode home2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode sut = home2.getChildFileByFileName("file1.txt");
        FileContent content = sut.getContent();
        Assertions.assertNotNull(content);
        Assertions.assertSame(content, sut.getContent());
        Assertions.assertEquals("content", content.getTextContent());
    }

    @Test
    void flush_whenNewNode_thenFileIsWritten() {
		folder.addChildFile("file1.txt", new FileContent("content"));
		folder.flush();

        LocalFolderTreeNode home2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode sut = home2.getChildFileByFileName("file1.txt");
        FileContent content = sut.getContent();
        Assertions.assertNotNull(content);
        Assertions.assertEquals("content", content.getTextContent());
    }

    @Test
    void flush_whenExistingNodeIsModified_thenFileIsWritten() {
        FileTreeNode sut = this.folder.addChildFile("file1.txt", new FileContent("content"));
		this.folder.flush();
        sut.changeContent(new FileContent("new"));
		this.folder.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());

        LocalFolderTreeNode home2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode reloaded = home2.getChildFileByFileName("file1.txt");
        FileContent content = reloaded.getContent();
        Assertions.assertNotNull(content);
        Assertions.assertEquals("new", content.getTextContent());
    }

    @Test
    void flush_whenExistingNodeDeleted_thenFileMissingAfterReload() {
        FileTreeNode sut = this.folder.addChildFile("file1.txt", new FileContent("content"));
		this.folder.flush();
        sut.markForDeletion();
		this.folder.flush();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, sut.getStatus());

        LocalFolderTreeNode home2 = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(false);
        FileTreeNode reloaded = home2.getChildFileByFileName("file1.txt");
        Assertions.assertNull(reloaded);
    }

    @Test
    void getOrAddFolder_whenAskingForExistingFolderAndThenLoadsAllFolders_thenReturnsVirtualFolderAndMergesWithPrevious() {
        FolderTreeNode sourcesFolder = this.folder.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
		this.folder.getSubFolders(); // forces load
        FolderTreeNode returned = this.folder.getChildFolder(BuiltInFolderNames.SOURCES);
        Assertions.assertSame(sourcesFolder, returned);
    }

    @Test
    void addFile_whenNewFileRegisteredAndThenOtherFilesListed_thenPreservesFile() {
        FileTreeNode file = this.folder.addChildFile("filename.txt", new FileContent("abc"));
		this.folder.getFiles();
        Assertions.assertSame(file, this.folder.getChildFileByFileName("filename.txt"));
    }
}
