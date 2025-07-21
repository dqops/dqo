/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.NEW);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenLoadedNotModified_thenMarkedForDeletion() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenModified_thenMarkedForDeletion() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.MODIFIED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, sut.getStatus());
    }

    @Test
    void markForDeletion_whenDeleted_thenPreservesDeleted() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("aaa"), FileTreeNodeStatus.DELETED);
        sut.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, sut.getStatus());
    }

    @Test
    void changeContent_whenNotLoaded_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                null, FileTreeNodeStatus.NOT_LOADED);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenLoadedNotModified_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenNew_thenSetsContentAndStatusIsNew() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.NEW, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenStatusToBeDeleted_thenSetsContentAndStatusIsModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        FileContent newContent = new FileContent("new");
        sut.changeContent(newContent);
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        Assertions.assertSame(newContent, sut.getContent());
    }

    @Test
    void changeContent_whenStatusDeleted_thenSetsContentAndStatusIsNew() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
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
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.markForDeletion();
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, sut.getStatus());
    }

    @Test
    void flush_whenStatusModified_thenChangesToLoadedNotModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.LOADED_NOT_MODIFIED);
        sut.changeContent(new FileContent("new"));
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, sut.getStatus());
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());
    }

    @Test
    void flush_whenStatusNew_thenChangesToLoadedNotModified() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, sut.getStatus());
    }

    @Test
    void isLocalFileSystem_whenDefaultMethodCalled_thenReturnsFalseBecauseItIsVirtual() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        Assertions.assertFalse(sut.isLocalFileSystem());
    }

    @Test
    void getPhysicalAbsolutePath_whenDefaultMethodCalled_thenReturnsFalseBecauseItIsVirtual() {
        FileTreeNode sut = new FileTreeNode(new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "file.txt"),
                new FileContent("old"), FileTreeNodeStatus.NEW);
        Assertions.assertNull(sut.getPhysicalAbsolutePath());
    }
}
