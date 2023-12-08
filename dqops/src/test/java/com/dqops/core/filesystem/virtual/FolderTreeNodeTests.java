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
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.principal.UserDomainIdentity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class FolderTreeNodeTests extends BaseTest {
    @Test
    void getSubFolders_whenNewObject_thenIsEmpty() {
        FolderTreeNode sut = new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN));
        Assertions.assertNotNull(sut.getSubFolders());
        Assertions.assertEquals(0, sut.getSubFolders().size());
    }

    @Test
    void getFiles_whenNewObject_thenIsEmpty() {
        FolderTreeNode sut = new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN));
        Assertions.assertNotNull(sut.getFiles());
        Assertions.assertEquals(0, sut.getFiles().size());
    }

    @Test
    void getKind_whenNewObjectFromDefaultConstructor_thenIsUnknown() {
        FolderTreeNode sut = new FolderTreeNode();
        Assertions.assertEquals(FolderKind.UNKNOWN, sut.getKind());
    }

    @Test
    void getKind_whenNewObjectForRootFolder_thenIsHome() {
        FolderTreeNode sut = new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN));
        Assertions.assertEquals(FolderKind.HOME, sut.getKind());
    }

    @Test
    void getKind_whenNewObjectForRootFolder_thenIsUnknown() {
        FolderTreeNode sut = new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, FolderName.fromObjectName("name")));
        Assertions.assertEquals(FolderKind.UNKNOWN, sut.getKind());
    }

    @Test
    void constructor_whenNameAndKindGiven_thenBothStored() {
        HomeFolderPath folderPath = new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN, FolderName.fromObjectName(BuiltInFolderNames.SOURCES), FolderName.fromObjectName("src1"));
        FolderTreeNode sut = new FolderTreeNode(folderPath, FolderKind.SOURCE);
        Assertions.assertSame(folderPath, sut.getFolderPath());
        Assertions.assertEquals(FolderKind.SOURCE, sut.getKind());
    }

    @Test
    void createRootFolderNode_whenCalled_thenReturnsHomeRoot() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        Assertions.assertEquals(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN), home.getFolderPath());
        Assertions.assertEquals(FolderKind.HOME, home.getKind());
    }

    @Test
    void getFolderName_whenRootHomeFolder_thenReturnsNull() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderName folderName = sut.getFolderName();
        Assertions.assertEquals(null, folderName);
    }

    @Test
    void getFolderName_whenFirstLevelFolder_thenReturnsFolderName() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);

        FolderName folderName = sut.getFolderName();
        Assertions.assertNotNull(folderName);
        Assertions.assertSame(sut.getFolderPath().getTopFolder(), folderName);
    }

    @Test
    void getFolderName_whenSecondLevelFolder_thenReturnsTopFolderName() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sources = home.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderTreeNode sut = sources.getOrAddDirectFolder("src1");

        FolderName folderName = sut.getFolderName();
        Assertions.assertNotNull(folderName);
        Assertions.assertSame(sut.getFolderPath().getTopFolder(), folderName);
    }

    @Test
    void deriveChildFolderKind_whenHomeFolderAndAskingForSources_thenReturnsSources() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName(BuiltInFolderNames.SOURCES));
        Assertions.assertEquals(FolderKind.SOURCES, folderKind);
    }

    @Test
    void deriveChildFolderKind_whenHomeFolderAndAskingForChecks_thenReturnsChecks() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName(BuiltInFolderNames.CHECKS));
        Assertions.assertEquals(FolderKind.CHECKS, folderKind);
    }

    @Test
    void deriveChildFolderKind_whenHomeFolderAndAskingForSensors_thenReturnsSensors() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName(BuiltInFolderNames.SENSORS));
        Assertions.assertEquals(FolderKind.SENSORS, folderKind);
    }

    @Test
    void deriveChildFolderKind_whenHomeFolderAndAskingForRules_thenReturnsRules() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName(BuiltInFolderNames.RULES));
        Assertions.assertEquals(FolderKind.RULES, folderKind);
    }

    @Test
    void deriveChildFolderKind_whenSourcesFolderAndAskingChild_thenReturnsSource() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName("src1"));
        Assertions.assertEquals(FolderKind.SOURCE, folderKind);
    }

    @Test
    void deriveChildFolderKind_whenRulesFolderAndAskingChild_thenReturnsRules() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderKind folderKind = sut.deriveChildFolderKind(FolderName.fromObjectName("rule"));
        Assertions.assertEquals(FolderKind.RULES_SUBFOLDER, folderKind);
    }

    // TODO: add more tests for specific sub-subfolders.

    @Test
    void loadChildFilesAndFolders_whenCalledOnNewObject_thenDoesNothingAndDoesNotReplaceCollections() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        List<FolderTreeNode> subFolders = sut.getSubFolders();
        List<FileTreeNode> files = sut.getFiles();
        sut.loadChildFilesAndFolders();
        Assertions.assertSame(subFolders, sut.getSubFolders());
        Assertions.assertSame(files, sut.getFiles());
    }

    @Test
    void getChildFolder_whenSourcesFolderNotExistsInHomeFolder_thenReturnsNull() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getChildFolder(BuiltInFolderNames.SOURCES);

        Assertions.assertNull(result);
    }

    @Test
    void getChildFolder_whenSourcesFolderExistsInHomeFolder_thenReturnsNode() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode expected = sut.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);

        FolderTreeNode result = sut.getChildFolder(BuiltInFolderNames.SOURCES);
        Assertions.assertNotNull(result);
        Assertions.assertSame(expected, result);
    }

    @Test
    void getChildFolder_whenSourceFolderExistsInSourcesFolderAndRequiresSanitization_thenFindsByObjectName() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderTreeNode expected = sut.getOrAddDirectFolder("src1&>");

        FolderTreeNode result = sut.getChildFolder("src1&>");
        Assertions.assertNotNull(result);
        Assertions.assertSame(expected, result);
    }

    @Test
    void getOrAddDirectFolder_whenRootFolderAndAskingForSources_thenReturnsSourcesWithCorrectKind() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);

        FolderTreeNode found = sut.getChildFolder(BuiltInFolderNames.SOURCES);
        Assertions.assertNotNull(result);
        Assertions.assertSame(found, result);
        Assertions.assertEquals(BuiltInFolderNames.SOURCES, result.getFolderName().getObjectName());
        Assertions.assertEquals(FolderKind.SOURCES, result.getKind());
    }

    @Test
    void getOrAddDirectFolder_whenSourcesFolderAndAskingForSource_thenReturnsSourceWithCorrectKind() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderTreeNode result = sut.getOrAddDirectFolder("Src");

        FolderTreeNode found = sut.getChildFolder("Src");
        Assertions.assertNotNull(result);
        Assertions.assertSame(found, result);
        Assertions.assertEquals("Src", result.getFolderName().getObjectName());
        Assertions.assertEquals(FolderKind.SOURCE, result.getKind());
    }

    @Test
    void getOrAddDirectFolder_whenRootFolderAndAddingSourcesAgain_thenReturnsExistingFolder() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);

        FolderTreeNode second = sut.getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        Assertions.assertSame(result, second);
    }

    @Test
    void getOrAddFolderPath_whenDirectFolderThatExists_thenReturnsThisFolder() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getOrAddFolderPath(BuiltInFolderNames.SOURCES);

        FolderTreeNode second = sut.getOrAddFolderPath(BuiltInFolderNames.SOURCES);
        Assertions.assertSame(result, second);
    }

    @Test
    void getOrAddFolderPath_whenFolderPathWithSubFolder_thenReturnsRequestedSubFolder() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getOrAddFolderPath(BuiltInFolderNames.SOURCES + "/src1");

        Assertions.assertEquals(2, result.getFolderPath().size());
        Assertions.assertEquals(BuiltInFolderNames.SOURCES, result.getFolderPath().get(0).getFileSystemName());
        Assertions.assertEquals("src1", result.getFolderPath().get(1).getFileSystemName());
    }

    @Test
    void getOrAddFolderPath_whenFolderPathWithSubFolderAndRequiresEncoding_thenReturnsRequestedSubFolderEncoded() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FolderTreeNode result = sut.getOrAddFolderPath(BuiltInFolderNames.SOURCES + "/src1>");

        Assertions.assertEquals(2, result.getFolderPath().size());
        Assertions.assertEquals(BuiltInFolderNames.SOURCES, result.getFolderPath().get(0).getFileSystemName());
        Assertions.assertEquals("src1%3E", result.getFolderPath().get(1).getFileSystemName());
        Assertions.assertEquals("src1>", result.getFolderPath().get(1).getObjectName());
    }

    @Test
    void createFolderTreeNode_whenCalledJust_thenReturnsFolderNodeRequested() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        HomeFolderPath folderPath = sut.getFolderPath().resolveSubfolder(FolderName.fromObjectName(BuiltInFolderNames.SOURCES));
        FolderTreeNode result = sut.createFolderTreeNode(folderPath, FolderKind.SOURCES);
        Assertions.assertSame(folderPath, result.getFolderPath());
        Assertions.assertSame(FolderKind.SOURCES, result.getKind());
    }

    @Test
    void getChildFileByFileName_whenFileNotPresent_thenReturnsNull() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode result = sut.getChildFileByFileName("missing.txt");
        Assertions.assertNull(result);
    }

    @Test
    void getChildFileByFileName_whenFilePresent_thenReturnsFileNode() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode childFile = sut.addChildFile("file.txt", new FileContent("content"));
        FileTreeNode result = sut.getChildFileByFileName("file.txt");
        Assertions.assertNotNull(result);
        Assertions.assertSame(childFile, result);
    }

    @Test
    void addChildFile_whenFileNotPresentAndAddingToRootFolder_thenFileIsAdded() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode result = sut.addChildFile("file.txt", new FileContent("content"));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sut.getFolderPath(), result.getFilePath().getFolder());
        Assertions.assertEquals("file.txt", result.getFilePath().getFileName());
        Assertions.assertEquals(FileTreeNodeStatus.NEW, result.getStatus());
        FileTreeNode found = sut.getChildFileByFileName("file.txt");
        Assertions.assertNotNull(found);
    }

    @Test
    void addChildFile_whenFilePresentAndAddingToRootFolder_thenReplacesContent() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode first = sut.addChildFile("file.txt", new FileContent("content"));

        FileTreeNode second = sut.addChildFile("file.txt", new FileContent("content2"));
        FileTreeNode found = sut.getChildFileByFileName("file.txt");
        Assertions.assertEquals("content2", found.getContent().getTextContent());
    }

    @Test
    void addChildFile_whenFileWasAddedAndDeletedAndAddingToRootFolderAgain_thenReplacesContent() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode first = sut.addChildFile("file.txt", new FileContent("content"));
        first.markForDeletion();

        FileTreeNode second = sut.addChildFile("file.txt", new FileContent("content2"));
        FileTreeNode found = sut.getChildFileByFileName("file.txt");
        Assertions.assertEquals("content2", found.getContent().getTextContent());
        Assertions.assertEquals(FileTreeNodeStatus.MODIFIED, found.getStatus());
    }

    @Test
    void deleteChildFile_whenFileStatusIsNew_thenMarksForDeletion() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));

        Assertions.assertTrue(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());
    }

    @Test
    void deleteChildFile_whenFileStatusIsLoadedNotChanged_thenMarksForDeletion() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        file.setStatus(FileTreeNodeStatus.LOADED_NOT_MODIFIED);

        Assertions.assertTrue(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());
    }

    @Test
    void deleteChildFile_whenFileStatusIsModified_thenMarksForDeletion() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        file.setStatus(FileTreeNodeStatus.MODIFIED);

        Assertions.assertTrue(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());
    }

    @Test
    void deleteChildFile_whenFileStatusIsAwaitingDeletion_thenReturnsFalse() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        Assertions.assertTrue(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());

        Assertions.assertFalse(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());
    }

    @Test
    void deleteChildFile_whenFileStatusIsDeleted_thenReturnsFalse() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        Assertions.assertTrue(sut.deleteChildFile("file.txt"));
        file.flush();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, file.getStatus());

        Assertions.assertFalse(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, file.getStatus());
    }

    @Test
    void deleteChildFile_whenFileNotPresent_thenAddsVirtualNodeWithToBeDeletedStaus() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();

        Assertions.assertFalse(sut.deleteChildFile("file.txt"));
        Assertions.assertEquals(1, sut.getFiles().size());
        FileTreeNode file = sut.getChildFileByFileName("file.txt");
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());
    }

    @Test
    void flush_whenEmpty_thenDoesNothing() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        sut.flush();
    }

    @Test
    void flush_whenDirectFileAsLoadedNotModified_thenDoesNothing() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        file.setStatusAsUnmodified();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, file.getStatus());

        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, file.getStatus());
        Assertions.assertSame(file, sut.getChildFileByFileName("file.txt"));
    }

    @Test
    void flush_whenDirectFileAsNew_thenStatusChangedToLoadedNotModified() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        Assertions.assertEquals(FileTreeNodeStatus.NEW, file.getStatus());

        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, file.getStatus());
        Assertions.assertSame(file, sut.getChildFileByFileName("file.txt"));
    }

    @Test
    void flush_whenDirectFileAsModified_thenStatusChangedToLoadedNotModified() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        file.setStatus(FileTreeNodeStatus.MODIFIED);

        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, file.getStatus());
        Assertions.assertSame(file, sut.getChildFileByFileName("file.txt"));
    }

    @Test
    void flush_whenDirectFileAsToBeDeleted_thenStatusChangedToDeletedAndObjectRemoved() {
        FolderTreeNode sut = FolderTreeNode.createRootFolderNode();
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        file.markForDeletion();
        Assertions.assertEquals(FileTreeNodeStatus.TO_BE_DELETED, file.getStatus());

        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.DELETED, file.getStatus());
        Assertions.assertNull(sut.getChildFileByFileName("file.txt"));
        Assertions.assertEquals(0, sut.getFiles().size());
    }

    @Test
    void flush_whenFileInSubfolder_thenFlushIsDelegatedToSubFolders() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.SENSORS);
        FileTreeNode file = sut.addChildFile("file.txt", new FileContent("content"));
        Assertions.assertEquals(FileTreeNodeStatus.NEW, file.getStatus());

        sut.flush();
        Assertions.assertEquals(FileTreeNodeStatus.LOADED_NOT_MODIFIED, file.getStatus());
        Assertions.assertSame(file, sut.getChildFileByFileName("file.txt"));
    }

    @Test
    void findNestedSubFoldersWithFiles_whenNoSubFolders_thenReturnsEmptyList() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", false);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void findNestedSubFoldersWithFiles_whenDirectSubFoldersButWithoutRequiredFile_thenReturnsEmptyList() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode rule1Folder = sut.getOrAddDirectFolder("rule1");
        FolderTreeNode rule2Folder = sut.getOrAddDirectFolder("rule2");

        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", false);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void findNestedSubFoldersWithFiles_whenDirectSubFoldersAndOneFolderHasRequiredFile_thenReturnsMatchingFolder() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode rule1Folder = sut.getOrAddDirectFolder("rule1");
        FolderTreeNode rule2Folder = sut.getOrAddDirectFolder("rule2");
        FileTreeNode rule1File = rule1Folder.addChildFile("rule.dqorule.yaml", new FileContent("content"));

        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", false);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("rules/rule1", result.get(0).getFolderPath().toString());
    }

    @Test
    void findNestedSubFoldersWithFiles_whenFileFoundInOneDirectAndOneChildChildFolder_thenReturnsMatchingFolders() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode rule1Folder = sut.getOrAddDirectFolder("rule1");
        FolderTreeNode rule2Folder = sut.getOrAddDirectFolder("rule2");
        FolderTreeNode rule21Folder = rule2Folder.getOrAddDirectFolder("rule21");
        FileTreeNode rule1File = rule1Folder.addChildFile("rule.dqorule.yaml", new FileContent("content"));
        FileTreeNode rule21File = rule21Folder.addChildFile("rule.dqorule.yaml", new FileContent("content"));

        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", false);
        result.sort(Comparator.comparing(f -> f.getFolderPath().toString()));
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("rules/rule1", result.get(0).getFolderPath().toString());
        Assertions.assertEquals("rules/rule2/rule21", result.get(1).getFolderPath().toString());
    }

    @Test
    void findNestedSubFoldersWithFiles_whenMatchingFileInCurrentFolderAndIncludeCurrentIsTrue_thenReturnsSelf() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode rule1Folder = sut.getOrAddDirectFolder("rule1");
        FileTreeNode rule1File = sut.addChildFile("rule.dqorule.yaml", new FileContent("content"));

        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", true);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("rules", result.get(0).getFolderPath().toString());
    }

    @Test
    void findNestedSubFoldersWithFiles_whenMatchingFileInCurrentFolderAndIncludeCurrentIsFalse_thenDoesNotReturn() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode rule1Folder = sut.getOrAddDirectFolder("rule1");
        FileTreeNode rule1File = sut.addChildFile("rule.dqorule.yaml", new FileContent("content"));

        List<FolderTreeNode> result = sut.findNestedSubFoldersWithFiles(".dqorule.yaml", false);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void isLocalFileSystem_whenDefaultMethodCalled_thenReturnsFalseBecauseItIsVirtual() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        Assertions.assertFalse(sut.isLocalFileSystem());
    }

    @Test
    void getPhysicalAbsolutePath_whenCalled_thenReturnsNull() {
        FolderTreeNode home = FolderTreeNode.createRootFolderNode();
        FolderTreeNode sut = home.getOrAddDirectFolder(BuiltInFolderNames.RULES);
        Assertions.assertNull(sut.getPhysicalAbsolutePath());
    }
}
