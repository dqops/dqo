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

import java.nio.file.Path;
import java.util.ArrayList;

@SpringBootTest
public class HomeFolderPathTests extends BaseTest {
    @Test
    void constructor_whenEmpty_thenCreatesObject() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);
        Assertions.assertEquals(0, sut.size());
    }

    @Test
    void constructor_whenCollectionGiven_thenReturnsFolderPath() {
        ArrayList<FolderName> list = new ArrayList<>();
        list.add(FolderName.fromObjectName("first"));
        list.add(FolderName.fromObjectName("second"));
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, list);
        Assertions.assertEquals(2, sut.size());
        Assertions.assertSame(list.get(0), sut.get(0));
        Assertions.assertSame(list.get(1), sut.get(1));
    }

    @Test
    void constructor_whenFolderNamesGiven_thenPathIsConstructed() {
        FolderName first = FolderName.fromObjectName("first");
        FolderName second = FolderName.fromObjectName("second");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);
        Assertions.assertEquals(2, sut.size());
        Assertions.assertSame(first, sut.get(0));
        Assertions.assertSame(second, sut.get(1));
    }

    @Test
    void getTopFolder_whenEmptyPathForRootFolder_thenReturnsNull() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);
        Assertions.assertNull(sut.getTopFolder());
    }

    @Test
    void getTopFolder_whenOneFolder_thenThisFolderIsReturned() {
        FolderName first = FolderName.fromObjectName("first");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first);
        FolderName topFolder = sut.getTopFolder();
        Assertions.assertSame(first, topFolder);
    }

    @Test
    void getTopFolder_whenTwoFolders_thenSecondFolderIsReturned() {
        FolderName first = FolderName.fromObjectName("first");
        FolderName second = FolderName.fromObjectName("second");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);
        FolderName topFolder = sut.getTopFolder();
        Assertions.assertSame(second, topFolder);
    }

    @Test
    void clone_whenPathCloned_thenNewPathHasDeeplyClonedObjects() {
        FolderName first = FolderName.fromObjectName("first");
        FolderName second = FolderName.fromObjectName("second");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);

        HomeFolderPath clone = sut.clone();
        Assertions.assertEquals(2, clone.size());
        Assertions.assertNotSame(sut.get(0), clone.get(0));
        Assertions.assertNotSame(sut.get(1), clone.get(1));
        Assertions.assertEquals(sut.get(0), clone.get(0));
        Assertions.assertEquals(sut.get(1), clone.get(1));
    }

    @Test
    void toRelativePath_whenRootFolderWithNoFolderNames_thenReturnsDotFolder() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);

        Path path = sut.toRelativePath();

        Assertions.assertNotNull(path);
        Assertions.assertEquals(".", path.toString().replace('\\', '/'));
    }

    @Test
    void toRelativePath_whenOneFolder_thenReturnsOneFolderUsingSanitizedFileSystemNames() {
        FolderName first = FolderName.fromObjectName("first&");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first);

        Path path = sut.toRelativePath();

        Assertions.assertNotNull(path);
        Assertions.assertEquals("first%26", path.toString());
    }

    @Test
    void toRelativePath_whenTwoFolders_thenReturnsMultiPartPathUsingSanitizedFileSystemNames() {
        FolderName first = FolderName.fromObjectName("first&");
        FolderName second = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);

        Path path = sut.toRelativePath();

        Assertions.assertNotNull(path);
        Assertions.assertEquals("first%26/second%3E", path.toString().replace('\\', '/'));
    }

    @Test
    void toRelativePath_whenThreeFolders_thenReturnsMultiPartPathUsingSanitizedFileSystemNames() {
        FolderName first = FolderName.fromObjectName("first&");
        FolderName second = FolderName.fromObjectName("second>");
        FolderName third = FolderName.fromObjectName("third");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second, third);

        Path path = sut.toRelativePath();

        Assertions.assertNotNull(path);
        Assertions.assertEquals("first%26/second%3E/third", path.toString().replace('\\', '/'));
    }

    @Test
    void toPhysicalPathElements_whenMultiPartPath_thenReturnsArrayOfFileSystemNames() {
        FolderName first = FolderName.fromObjectName("first&");
        FolderName second = FolderName.fromObjectName("second>");
        FolderName third = FolderName.fromObjectName("third");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second, third);

        String[] pathElements = sut.toPhysicalPathElements();

        Assertions.assertEquals(3, pathElements.length);
        Assertions.assertEquals("first%26", pathElements[0]);
        Assertions.assertEquals("second%3E", pathElements[1]);
        Assertions.assertEquals("third", pathElements[2]);
    }

    @Test
    void resolveSubfolder_whenRootFolder_thenCreatesOneLevelFolderPath() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);
        FolderName first = FolderName.fromObjectName("first&");

        HomeFolderPath result = sut.resolveSubfolder(first);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(first, result.get(0));
    }

    @Test
    void resolveSubfolder_whenFolderWithSubFolders_thenCreatesOneLevelDeeperFolderPath() {
        FolderName first = FolderName.fromObjectName("first&");
        FolderName second = FolderName.fromObjectName("second>");
        FolderName third = FolderName.fromObjectName("third");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);

        HomeFolderPath result = sut.resolveSubfolder(third);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(first, result.get(0));
        Assertions.assertNotSame(first, result.get(0));
        Assertions.assertEquals(second, result.get(1));
        Assertions.assertNotSame(second, result.get(1));
        Assertions.assertEquals(third, result.get(2));
    }

    @Test
    void resolveFile_whenRootFileSystem_thenCreatesFilePathInRoot() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);

        HomeFilePath filePath = sut.resolveFile("filename.txt");

        Assertions.assertNotNull(filePath);
        Assertions.assertNotSame(sut, filePath.getFolder());
        Assertions.assertEquals(sut, filePath.getFolder());
        Assertions.assertEquals("filename.txt", filePath.getFileName());
    }

    @Test
    void resolveFile_whenRootFileSystemAndSimpleDomainName_thenCreatesFilePathInRootButInsideDomainFolder() {
        HomeFolderPath sut = new HomeFolderPath("hr");

        HomeFilePath filePath = sut.resolveFile("filename.txt");

        Assertions.assertNotNull(filePath);
        Assertions.assertNotSame(sut, filePath.getFolder());
        Assertions.assertEquals(sut, filePath.getFolder());
        Assertions.assertEquals("domains/hr/filename.txt", filePath.toRelativePath().toString().replace('\\', '/'));
        Assertions.assertEquals("filename.txt", filePath.getFileName());
    }

    @Test
    void resolveFile_whenChildFolder_thenCreatesFilePathInClonedFolder() {
        FolderName first = FolderName.fromObjectName("first");
        FolderName second = FolderName.fromObjectName("second");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);

        HomeFilePath filePath = sut.resolveFile("filename.txt");

        Assertions.assertNotNull(filePath);
        Assertions.assertNotSame(sut, filePath.getFolder());
        Assertions.assertEquals(sut, filePath.getFolder());
        Assertions.assertEquals("filename.txt", filePath.getFileName());
    }

    @Test
    void toString_whenRootFolder_thenReturnsEmptyDot() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN);

        Assertions.assertEquals("[]/", sut.toString());
    }

    @Test
    void toString_whenFolder_thenReturnsFolderPath() {
        FolderName first = FolderName.fromObjectName("first&");
        FolderName second = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first, second);

        Assertions.assertEquals("[]/first%26/second%3E/", sut.toString());
    }

    @Test
    void equals_whenObjectAreDifferentInstancesButAreEqual_thenReturnsTrue() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);

        FolderName first2 = FolderName.fromObjectName("first&");
        FolderName second2 = FolderName.fromObjectName("second>");
        HomeFolderPath other = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first2, second2);

        Assertions.assertTrue(sut.equals(other));
    }

    @Test
    void equals_whenObjectAreDifferentDepth_thenReturnsFalse() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);

        FolderName first2 = FolderName.fromObjectName("first&");
        HomeFolderPath other = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first2);

        Assertions.assertFalse(sut.equals(other));
    }

    @Test
    void equals_whenObjectAreDifferentByOneElement_thenReturnsFalse() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);

        FolderName first2 = FolderName.fromObjectName("first&");
        FolderName second2 = FolderName.fromObjectName("other");
        HomeFolderPath other = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first2, second2);

        Assertions.assertFalse(sut.equals(other));
    }

    @Test
    void hashCode_whenTwoObjectsAreDifferentInstancesButAreEqual_thenReturnsTheSameHash() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);

        FolderName first2 = FolderName.fromObjectName("first&");
        FolderName second2 = FolderName.fromObjectName("second>");
        HomeFolderPath other = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first2, second2);

        Assertions.assertTrue(sut.equals(other));
        Assertions.assertNotEquals(0, sut.hashCode());
        Assertions.assertEquals(sut.hashCode(), other.hashCode());
    }

    @Test
    void hashCode_whenTwoObjectsAreDifferentInstancesAndDifferByOneElement_thenReturnsDifferentHashes() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);

        FolderName first2 = FolderName.fromObjectName("first&");
        FolderName second2 = FolderName.fromObjectName("OTHER");
        HomeFolderPath other = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first2, second2);

        Assertions.assertFalse(sut.equals(other));
        Assertions.assertNotEquals(0, sut.hashCode());
        Assertions.assertNotEquals(sut.hashCode(), other.hashCode());
    }

    @Test
    void extractSubFolderAt_whenFirstFolderSkipped_thenReturnsModifiedFolderPath() {
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, FolderName.fromObjectName("first"), FolderName.fromObjectName("second"), FolderName.fromObjectName("third"));
        HomeFolderPath subFolder = sut.extractSubFolderAt(1);
        Assertions.assertEquals("[]/second/third/", subFolder.toString());
    }

    @Test
    void getFullObjectName_whenCalledOnPathWithSpecialCharacters_thenReturnsUnsanitizedPath() {
        FolderName first1 = FolderName.fromObjectName("first&");
        FolderName second1 = FolderName.fromObjectName("second>");
        HomeFolderPath sut = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, first1, second1);
        String fullObjectName = sut.getFullObjectName();
        Assertions.assertEquals("first&/second>", fullObjectName);
    }
}
