/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.files;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates a documentation folder by finding all markdown files and reading their content.
 * The loaded files (that are stored in the project) will be compared with the new rendered documentation.
 */
public class DocumentationFolderFactory {
    /**
     * Loads all markdown files from the given folder.
     * @param pathToDocumentationFolder Path to a documentation folder. It should point to the correct folder inside the repository's root with the documentation.
     * @return Loaded files.
     */
    public static DocumentationFolder loadCurrentFiles(Path pathToDocumentationFolder) {
        try {
            DocumentationFolder documentationFolder = new DocumentationFolder();
            documentationFolder.setFolderName(pathToDocumentationFolder.getFileName().toString());
            documentationFolder.setDirectPath(pathToDocumentationFolder);

            if (!Files.exists(pathToDocumentationFolder)) {
                return documentationFolder;
            }

            try (Stream<Path> pathStream = Files.walk(pathToDocumentationFolder)) {
                List<Path> pathsToMarkdownFiles = pathStream
                        .filter(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".md"))
                        .collect(Collectors.toList());

                for (Path markdownFilePath : pathsToMarkdownFiles) {
                    Path relativePath = pathToDocumentationFolder.relativize(markdownFilePath);
                    String filePathUnix = relativePath.toString().replace('\\', '/');

                    DocumentationMarkdownFile documentationMarkdownFile = documentationFolder.addNestedFile(filePathUnix);
                    documentationMarkdownFile.setFileContent(Files.readString(markdownFilePath, StandardCharsets.UTF_8));
                }
            }

            return documentationFolder;
        }
        catch (Exception ex) {
            throw new RuntimeException("Documentation files cannot be loaded from " + pathToDocumentationFolder);
        }
    }
}
