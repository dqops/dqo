/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.docs.files;

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
