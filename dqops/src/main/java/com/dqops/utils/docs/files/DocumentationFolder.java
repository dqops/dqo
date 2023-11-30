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
package com.dqops.utils.docs.files;

import com.dqops.utils.collections.ListFindUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Describes a single documentation folder with markdown documentation files that is generated.
 */
@Data
public class DocumentationFolder {
    public static final String INDEX_FILE_NAME = "index.md";

    /**
     * Folder name.
     */
    private String folderName;

    /**
     * File link name to be generated in the MkDocs table of contents.
     */
    private String linkName;

    /**
     * Direct file system path to the folder.
     */
    private Path directPath;

    /**
     * List of subfolders.
     */
    private List<DocumentationFolder> subFolders = new ArrayList<>();

    /**
     * List of files at this level.
     */
    private List<DocumentationMarkdownFile> files = new ArrayList<>();

    /**
     * Adds a nested file.
     * @param fileName File name of a markdown file, preceded by the folder path. For example: tables/strings/some_string_sensor.md
     * @return Markdown file object that was added to the file tree.
     */
    public DocumentationMarkdownFile addNestedFile(String fileName) {
        String fileNameUnix = fileName.replace('\\', '/');
        String[] folderPath = StringUtils.split(fileNameUnix, '/');
        if (folderPath.length > 1) {
            DocumentationFolder subFolder = ListFindUtils.findElement(
                    this.subFolders, folder -> Objects.equals(folderPath[0], folder.getFolderName()));

            if (subFolder == null) {
                Path subFolderPath = this.directPath.resolve(folderPath[0]);
                subFolder = new DocumentationFolder() {{
                    setFolderName(folderPath[0]);
                    setLinkName(folderPath[0]);
                    setDirectPath(subFolderPath);
                }};
                this.subFolders.add(subFolder);
            }

            return subFolder.addNestedFile(fileNameUnix.substring(fileNameUnix.indexOf('/') + 1));
        }

        Path directFilePath = this.directPath.resolve(folderPath[0]);
        DocumentationMarkdownFile documentationMarkdownFile = new DocumentationMarkdownFile() {{
            setFileName(folderPath[0]);
            setLinkName(folderPath[0].substring(0, folderPath[0].length() - ".md".length()));
            setDirectPath(directFilePath);
        }};

        this.files.add(documentationMarkdownFile);
        return documentationMarkdownFile;
    }

    /**
     * Adds a nested file.
     * @param file Markdown file object to be added to the file tree. <code>fileName</code> needs to be set.
     * @return Markdown file object that was added to the file tree.
     */
    public DocumentationMarkdownFile addNestedFile(DocumentationMarkdownFile file) {
        String fileNameUnix = file.getFileName().replace('\\', '/');
        String[] folderPath = StringUtils.split(fileNameUnix, '/');
        if (folderPath.length > 1) {
            DocumentationFolder subFolder = ListFindUtils.findElement(
                    this.subFolders, folder -> Objects.equals(folderPath[0], folder.getFolderName()));

            if (subFolder == null) {
                Path subFolderPath = this.directPath.resolve(folderPath[0]);
                subFolder = new DocumentationFolder() {{
                    setFolderName(folderPath[0]);
                    setLinkName(folderPath[0]);
                    setDirectPath(subFolderPath);
                }};
                this.subFolders.add(subFolder);
            }

            file.setFileName(fileNameUnix.substring(fileNameUnix.indexOf('/') + 1));
            return subFolder.addNestedFile(file);
        }

        Path directFilePath = this.directPath.resolve(folderPath[0]);
        file.setFileName(folderPath[0]);
        file.setLinkName(folderPath[0].substring(0, folderPath[0].length() - ".md".length()));
        file.setDirectPath(directFilePath);

        this.files.add(file);
        return file;
    }

    /**
     * Generates the content of the navigation for MkDocs that references all files.
     * @param indentSpaces Number of spaces to use for indentation.
     * @return Rendered yaml content with the navigation tree.
     */
    public List<String> generateMkDocsNavigation(int indentSpaces) {
        return generateMkDocsNavigation(indentSpaces, "");
    }

    /**
     * Generates the content of the navigation for MkDocs that references all files.
     * @param indentSpaces Number of spaces to use for indentation.
     * @param folderNamePrefix Folder name prefix that is rendered before file names.
     * @return Rendered yaml content with the navigation tree.
     */
    private List<String> generateMkDocsNavigation(int indentSpaces, String folderNamePrefix) {
        List<String> resultLines = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        String indent = " ".repeat(indentSpaces);
        stringBuilder.append(indent);
        stringBuilder.append("- ");
        stringBuilder.append(this.linkName);
        stringBuilder.append(':');
        resultLines.add(stringBuilder.toString());

        List<DocumentationMarkdownFile> markdownFiles = this.files.stream()
                .filter(file -> !file.getFileName().equals(INDEX_FILE_NAME)).collect(Collectors.toList());
        if (markdownFiles.size() < files.size()) {
            StringBuilder fileLineBuilder = new StringBuilder();
            fileLineBuilder.append(indent); // base indent
            fileLineBuilder.append("  "); // file indent
            fileLineBuilder.append("- '");
            fileLineBuilder.append(folderNamePrefix);
            fileLineBuilder.append(this.folderName);
            fileLineBuilder.append('/');
            fileLineBuilder.append(INDEX_FILE_NAME);
            fileLineBuilder.append("'");

            resultLines.add(fileLineBuilder.toString());
        }

        for (DocumentationFolder subFolder : this.subFolders) {
            String subFolderPrefix = folderNamePrefix + this.folderName + "/";
            List<String> subfolderLines = subFolder.generateMkDocsNavigation(indentSpaces + 2, subFolderPrefix);
            resultLines.addAll(subfolderLines);
        }

        for (DocumentationMarkdownFile markdownFile : markdownFiles) {
            StringBuilder fileLineBuilder = new StringBuilder();
            fileLineBuilder.append(indent); // base indent
            fileLineBuilder.append("  "); // file indent
            fileLineBuilder.append("- ");
            fileLineBuilder.append(markdownFile.getLinkName().replace('-', ' '));
            fileLineBuilder.append(": '");
            fileLineBuilder.append(folderNamePrefix);
            fileLineBuilder.append(this.folderName);
            fileLineBuilder.append('/');
            fileLineBuilder.append(markdownFile.getFileName());
            fileLineBuilder.append("'");

            resultLines.add(fileLineBuilder.toString());
        }

        return resultLines;
    }

    /**
     * Writes all modified (different) files to disk. Compares the files with existing documentation files.
     * @param currentReferenceFiles A folder with loaded content of the current files. Used to detect files that are different (a new documentation was generated).
     */
    public void writeModifiedFiles(DocumentationFolder currentReferenceFiles) {
        if (!Files.exists(this.directPath)) {
            try {
                Files.createDirectories(this.directPath);
            }
            catch (IOException ex) {
                throw new RuntimeException("Cannot create folder " + this.directPath.toString() + ", error: " + ex.getMessage(), ex);
            }
        }

        for (DocumentationFolder subFolder : this.subFolders) {
            DocumentationFolder matchingCurrentSubFolder = currentReferenceFiles != null ?
                ListFindUtils.findElement(currentReferenceFiles.getSubFolders(), f -> Objects.equals(subFolder.folderName, f.folderName))
                    : null;

            subFolder.writeModifiedFiles(matchingCurrentSubFolder);
        }

        for (DocumentationMarkdownFile documentationFile : this.files){
            DocumentationMarkdownFile matchingCurrentFile = currentReferenceFiles != null ?
                    ListFindUtils.findElement(currentReferenceFiles.getFiles(), f -> Objects.equals(documentationFile.getFileName(), f.getFileName()))
                    : null;

            if (matchingCurrentFile != null && Objects.equals(matchingCurrentFile.getFileContent(), documentationFile.getFileContent())) {
                continue; // no changes to write, ignoring
            }

            try {
                Files.writeString(documentationFile.getDirectPath(), documentationFile.getFileContent(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            }
            catch (Exception ex) {
                System.err.println("Cannot write file " + documentationFile.getDirectPath() + ", error: " + ex.getMessage() + ", exception type: " + ex.getClass().toString());
            }
        }
    }

    /**
     * Change order of subdirectories and files according to the provided comparator (in-place).
     * @param comparator Comparator on subdirectory/file names.
     */
    public void sortByNameRecursive(Comparator<String> comparator) {
        this.subFolders.sort((f1, f2) -> comparator.compare(f1.getFolderName(), f2.getFolderName()));
        this.files.sort((f1, f2) -> comparator.compare(f1.getFileName(), f2.getFileName()));
        this.subFolders.forEach(f -> f.sortByNameRecursive(comparator));
    }
}
