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

import java.nio.file.Path;

@SpringBootTest
public class HomeFilePathTests extends BaseTest {
    @Test
    void constructor_whenFolderGivenAndFileName_thenReturnsObject() {
        HomeFolderPath folder = new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN, FolderName.fromObjectName("first"));
        HomeFilePath sut = new HomeFilePath(folder, "filename.txt");

        Assertions.assertSame(folder, sut.getFolder());
        Assertions.assertEquals("filename.txt", sut.getFileName());
    }

    @Test
    void toRelativePath_whenFileInRootFolder_thenReturnsPathWithFileNameOnly() {
        HomeFilePath sut = new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "filename.txt");

        Path path = sut.toRelativePath();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("filename.txt", path.toString());
    }

    @Test
    void toRelativePath_whenFileInSubSubFolder_thenReturnsPathCombinedWithFolderPath() {
        HomeFolderPath folder = new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN, FolderName.fromObjectName("first"), FolderName.fromObjectName("second&"));
        HomeFilePath sut = new HomeFilePath(folder, "filename.txt");

        Path path = sut.toRelativePath();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("first/second%26/filename.txt", path.toString().replace('\\', '/'));
    }

    @Test
    void toRelativePath_whenFileInSubFolder_thenReturnsPathCombinedWithFolderPath() {
        HomeFolderPath folder = new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN, FolderName.fromObjectName("first"));
        HomeFilePath sut = new HomeFilePath(folder, "filename.txt");

        Path path = sut.toRelativePath();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("first/filename.txt", path.toString().replace('\\', '/'));
    }

    @Test
    void toString_whenFileInRootFolder_thenReturnsPathWithFileNameOnlyAndDataDomainName() {
        HomeFilePath sut = new HomeFilePath(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN), "filename.txt");

        String path = sut.toString();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("[]/filename.txt", path);
    }

    @Test
    void toString_whenFileInSubSubFolder_thenReturnsPathCombinedWithFolderPath() {
        HomeFolderPath folder = new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN, FolderName.fromObjectName("first"), FolderName.fromObjectName("second&"));
        HomeFilePath sut = new HomeFilePath(folder, "filename.txt");

        String path = sut.toString();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("[]/first/second%26/filename.txt", path);
    }

    @Test
    void toString_whenFileInSubSubFolderAndDifferentDataDomain_thenReturnsPathCombinedWithFolderPath() {
        HomeFolderPath folder = new HomeFolderPath("sales", FolderName.fromObjectName("first"), FolderName.fromObjectName("second&"));
        HomeFilePath sut = new HomeFilePath(folder, "filename.txt");

        String path = sut.toString();
        Assertions.assertNotNull(path);
        Assertions.assertEquals("[sales]/first/second%26/filename.txt", path);
    }

    @Test
    void fromFilePath_whenJustFileNameGiven_thenCreatesPathWithNoFolder() {
        HomeFilePath homeFilePath = HomeFilePath.fromFilePath(UserDomainIdentity.ROOT_DATA_DOMAIN, "filename.txt");
        Assertions.assertEquals(0, homeFilePath.getFolder().size());
        Assertions.assertEquals("filename.txt", homeFilePath.getFileName());
    }

    @Test
    void fromFilePath_whenTwoFoldersOnPath_thenCreatesThoseFoldersAsParents() {
        HomeFilePath homeFilePath = HomeFilePath.fromFilePath(UserDomainIdentity.ROOT_DATA_DOMAIN, "dir1/dir2/filename.txt");
        Assertions.assertEquals(2, homeFilePath.getFolder().size());
        Assertions.assertEquals("dir1", homeFilePath.getFolder().get(0).getFileSystemName());
        Assertions.assertEquals("dir2", homeFilePath.getFolder().get(1).getFileSystemName());
        Assertions.assertEquals("filename.txt", homeFilePath.getFileName());
    }
}
