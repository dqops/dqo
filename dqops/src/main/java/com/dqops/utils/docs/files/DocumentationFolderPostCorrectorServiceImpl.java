/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs.files;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Initiated after changes to documentation have been written onto disk. Idempotently corrects foul urls.
 */
public class DocumentationFolderPostCorrectorServiceImpl implements DocumentationFolderPostCorrectorService {
    Map<Path, Path> correctPathRemapping = null;
    Pattern oldLinkFormatPattern = Pattern.compile("\\((/docs(/[\\w\\-]+)*/?)(#[\\w\\-]+)?\\)");

    @Override
    public void postProcessCorrect(Path projectDir, DocumentationFolder toCorrect) {
        if (correctPathRemapping == null) {
            correctPathRemapping = createPathRemappingForFolder(projectDir.getParent(), toCorrect);
        }

        pathMappingCorrection(toCorrect);

    }

    protected Map<Path, Path> createPathRemappingForFolder(Path projectRootDirectory, DocumentationFolder documentationFolder) {
        Map<Path, Path> pathRemapping = new HashMap<>();
        createPathRemappingForFolderInternal(pathRemapping, documentationFolder, projectRootDirectory.relativize(documentationFolder.getDirectPath()));
        return pathRemapping;
    }

    private void createPathRemappingForFolderInternal(Map<Path, Path> acc,
                                                      DocumentationFolder currentFolder,
                                                      Path currentFolderPath) {
        for (DocumentationMarkdownFile file : currentFolder.getFiles()) {
            if (file.getFileName().equals("index.md")) {
                Path indexFilePath = currentFolderPath.resolve(file.getFileName());
                acc.put(currentFolderPath, indexFilePath);
            }
            else {
                Path filePath = currentFolderPath.resolve(file.getFileName());
                Path filePathLink = currentFolderPath.resolve(file.getLinkName());
                acc.put(filePathLink, filePath);
            }
        }

        for (DocumentationFolder folder : currentFolder.getSubFolders()) {
            createPathRemappingForFolderInternal(acc, folder, currentFolderPath.resolve(folder.getFolderName()));
        }
    }

    private void pathMappingCorrection(DocumentationFolder documentationFolder) {
        for (DocumentationMarkdownFile file : documentationFolder.getFiles()) {
            pathMappingCorrectionForFile(file);
        }
        for (DocumentationFolder folder : documentationFolder.getSubFolders()) {
            pathMappingCorrection(folder);
        }
    }

    protected void pathMappingCorrectionForFile(DocumentationMarkdownFile file) {
        String content = file.getFileContent();
        Matcher foulLinksMatcher = oldLinkFormatPattern.matcher(content);
        List<MatchResult> matches = foulLinksMatcher.results().collect(Collectors.toList());

        StringBuilder newContent = new StringBuilder();
        int i = 0;
        for (MatchResult match : matches) {
            newContent.append(content, i, match.start());

            Path oldPath = Path.of(match.group(1).substring(1));
            Path newPath = oldPath; // correctPathRemapping.get(oldPath);
            Path relativeNewPath = file.getDirectPath().relativize(newPath.toAbsolutePath());

            String matchString = match.group();
            String pathSubstitute = relativeNewPath.toString().replace('\\', '/');
            if (!pathSubstitute.isEmpty()) {
                pathSubstitute = pathSubstitute + "/";
            }
            String correctedMatch = matchString.replace(match.group(1), pathSubstitute);

            newContent.append(correctedMatch);
            i = match.end();
        }
        newContent.append(content, i, content.length());
        file.setFileContent(newContent.toString());
    }
}
