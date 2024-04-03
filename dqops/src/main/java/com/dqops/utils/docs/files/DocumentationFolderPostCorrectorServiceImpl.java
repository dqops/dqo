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

import com.google.common.collect.Streams;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Initiated after changes to documentation have been written onto disk. Idempotently corrects foul urls.
 */
public class DocumentationFolderPostCorrectorServiceImpl implements DocumentationFolderPostCorrectorService {
    private Map<Path, Path> correctPathRemapping = null;

    // REGEXPs to find and replace foul links.
    /**
     * Links that use '\' instead of '/' as a path delimiter.
     */
    private final Pattern windowsStylePathDelimiterPattern = Pattern.compile("\\(\\\\?([.\\w\\-]+(\\.md)?\\\\?)*(#[\\w\\-]+)?\\)");

    /**
     * Absolute links, with projectRoot (dqops directory) as root.
     */
    private final Pattern absoluteLinksPattern = Pattern.compile("\\((/docs(/[\\w\\-]+)*(\\.md)?/?)(#[\\w\\-]+)?\\)");

    /**
     * Links to files that don't include the ".md" extension.
     */
    private final Pattern referenceWithoutFilePattern = Pattern.compile("]\\((([\\w\\-]+|\\.\\.)/?)+(#[\\w\\-]+)?\\)");

    /**
     * Links with '/' after filename, usually between ".md" and "#".
     */
    private final Pattern redundantSlashPattern = Pattern.compile("]\\(([^(\\[\\]](?!\\.com))*\\.md(/)([^(\\[\\]](?!\\.com))*\\)");

    private final Path projectRootDirectory;

    public DocumentationFolderPostCorrectorServiceImpl(Path projectRootDirectory) {
        this.projectRootDirectory = projectRootDirectory;
    }

    @Override
    public void postProcessCorrect(DocumentationFolder toCorrect) {
        if (correctPathRemapping == null) {
            correctPathRemapping = createPathRemappingForFolder(toCorrect);
        }

        pathMappingCorrection(toCorrect);
    }

    protected Map<Path, Path> createPathRemappingForFolder(DocumentationFolder documentationFolder) {
        Map<Path, Path> pathRemapping = new LinkedHashMap<>();
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
        Path fileDirectPath = file.getDirectPath();
        Path workingPath = fileDirectPath.getParent();


        Deque<Function<String, String>> contentModifiers = new ArrayDeque<>();
        contentModifiers.add(this::pathDelimiterStyleCorrection);

        Function<Path, Function<String, String>> partialTransform = p ->
                toModify -> pathReferenceLevelCorrection(p, toModify);
        contentModifiers.add(partialTransform.apply(workingPath));

        contentModifiers.add(this::pathToFileReferenceCorrection);
        contentModifiers.add(this::redundantSlashCorrection);

        Function<String, String> compoundModifiers = Streams.stream(contentModifiers.descendingIterator())
                .reduce(Function::compose).get();
        String modifiedContent = compoundModifiers.apply(content);
        file.setFileContent(modifiedContent);
    }

    protected String pathDelimiterStyleCorrection(String fileContent) {
        Matcher windowsDelimiterLinksMatcher = windowsStylePathDelimiterPattern.matcher(fileContent);
        List<MatchResult> matches = windowsDelimiterLinksMatcher.results().collect(Collectors.toList());

        StringBuilder newContent = new StringBuilder();
        int i = 0;
        for (MatchResult match : matches) {
            newContent.append(fileContent, i, match.start());

            String matchedLink = match.group();
            newContent.append(matchedLink.replace("\\", "/"));
            i = match.end();
        }
        newContent.append(fileContent, i, fileContent.length());
        return newContent.toString();
    }

    protected String pathReferenceLevelCorrection(Path workingPath, String fileContent) {
        Matcher foulLinksMatcher = absoluteLinksPattern.matcher(fileContent);
        List<MatchResult> matches = foulLinksMatcher.results().collect(Collectors.toList());

        StringBuilder newContent = new StringBuilder();
        int i = 0;
        for (MatchResult match : matches) {
            newContent.append(fileContent, i, match.start());

            Path oldPath = projectRootDirectory.relativize(
                    Path.of(match.group(1).substring(1)).toAbsolutePath()
            );

            Path newPath = Objects.requireNonNullElse(correctPathRemapping.get(oldPath), oldPath);
            Path relativeNewPath = workingPath.relativize(newPath.toAbsolutePath());

            String matchString = match.group();
            String pathSubstitute = relativeNewPath.toString().replace('\\', '/');
            if (!pathSubstitute.startsWith("../")) {
                pathSubstitute = "./" + pathSubstitute;
            }
            String correctedMatch = matchString.replace(match.group(1), pathSubstitute);

            newContent.append(correctedMatch);
            i = match.end();
        }
        newContent.append(fileContent, i, fileContent.length());
        return newContent.toString();
    }

    protected String pathToFileReferenceCorrection(String fileContent) {
        Matcher noFileLinksMatcher = referenceWithoutFilePattern.matcher(fileContent);
        List<MatchResult> matches = noFileLinksMatcher.results().collect(Collectors.toList());

        StringBuilder newContent = new StringBuilder();
        int i = 0;
        for (MatchResult match : matches) {
            newContent.append(fileContent, i, match.start(1));

            String matchedReference = match.group(1);
            if (matchedReference.charAt(matchedReference.length() - 1) == '/') {
                matchedReference = matchedReference.substring(0, matchedReference.length() - 1);
            }

            newContent.append(matchedReference).append(".md");
            i = match.end(1);
        }
        newContent.append(fileContent, i, fileContent.length());
        return newContent.toString();
    }

    protected String redundantSlashCorrection(String fileContent) {
        Matcher extraSlashLinksMatcher = redundantSlashPattern.matcher(fileContent);
        List<MatchResult> matches = extraSlashLinksMatcher.results().collect(Collectors.toList());

        StringBuilder newContent = new StringBuilder();
        int i = 0;
        for (MatchResult match : matches) {
            newContent.append(fileContent, i, match.start(2));
            i = match.end(2);
        }
        newContent.append(fileContent, i, fileContent.length());
        return newContent.toString();
    }
}
