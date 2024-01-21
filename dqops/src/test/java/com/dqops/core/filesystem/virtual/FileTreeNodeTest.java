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
package com.dqops.core.filesystem.virtual;

import com.dqops.BaseTest;
import com.dqops.core.principal.UserDomainIdentity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTreeNodeTest extends BaseTest {
    @Test
    void constructor_whenNoParameters_thenStatusIsNotLoaded() {
        FileTreeNode sut = new FileTreeNode();
        Assertions.assertEquals(FileTreeNodeStatus.NOT_LOADED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenNew_thenMarkedForDeletion() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.NEW);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenLoadedNotModified_thenMarkedForDeletion() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenModified_thenMarkedForDeletion() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.MODIFIED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenDeleted_thenPreservesDeleted() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.DELETED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, sut.getStatus());
    }

    @Test
    void changeContent_whenNotLoaded_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                null, FileTreeNodeStatus.NOT_LOADED);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenLoadedNotModified_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenNew_thenSetsContentAndStatusIsNew() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.NEW, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenStatusToBeDeleted_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenStatusDeleted_thenSetsContentAndStatusIsNew() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        sut.flush();
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.NEW, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void flush_whenToBeDeleted_thenChangesToDeleted() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, sut.getStatus());
    }

    @Test
    void flush_whenStatusModified_thenChangesToLoadedNotModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.changeContent(new FileContent("new"));
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());
    }

    @Test
    void flush_whenStatusNew_thenChangesToLoadedNotModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());
    }

    @Test
    void isLocalFileSystem_whenDefaultMethodCalled_thenReturnsFalseBecauseItIsVirtual() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        Assertions.assertFalse(sut.isLocalFileSystem());
    }

    @Test
    void getPhysicalAbsolutePath_whenDefaultMethodCalled_thenReturnsFalseBecauseItIsVirtual() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        Assertions.assertNull(sut.getPhysicalAbsolutePath());
    }
}
