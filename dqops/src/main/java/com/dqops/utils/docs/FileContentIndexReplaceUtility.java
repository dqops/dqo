/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Replaces the content of a file between two markers.
 * Used to replaces the content of a section of the MkDocs yml file (the navigation). Imports pages to the navigation.
 */
public class FileContentIndexReplaceUtility {
    /**
     * Replaces the content of the <code>originalFileContentLines</code> file between <code>beginMarker</code> and <code>endMarker</code>
     * with the new lines given in <code>newLines</code>.
     * @param originalFileContentLines A list of lines from the current file.
     * @param newLines List of new lines.
     * @param beginMarker Begin marker.
     * @param endMarker End marker.
     * @return The new file content, divided into lines.
     */
    public static List<String> replaceLinesBetweenMarkers(List<String> originalFileContentLines, List<String> newLines, String beginMarker, String endMarker) {
        Optional<String> beginMarkerLine = originalFileContentLines.stream().filter(l -> l.contains(beginMarker)).findFirst();
        if (beginMarkerLine.isEmpty()) {
            throw new RuntimeException("Marker " + beginMarker + " not found in the file (probably mkdocs.yml)");
        }
        int indexOfBegin = originalFileContentLines.indexOf(beginMarkerLine.get());

        Optional<String> endMarkerLine = originalFileContentLines.stream()
                .skip(indexOfBegin + 1)
                .filter(l -> l.contains(endMarker)).findFirst();
        if (endMarkerLine.isEmpty()) {
            throw new RuntimeException("Marker " + endMarker + " not found in the file (probably mkdocs.yml)");
        }

        List<String> contentAfterBeginMarker = originalFileContentLines.stream()
                .skip(indexOfBegin + 1)
                .collect(Collectors.toList());

        int indexOfEnd = contentAfterBeginMarker.indexOf(endMarkerLine.get()) + indexOfBegin + 1;

        List<String> currentLines = originalFileContentLines.stream()
                .skip(indexOfBegin + 1)
                .limit(indexOfEnd - indexOfBegin - 1)
                .collect(Collectors.toList());

        if (currentLines.equals(newLines)) {
            return null; // no changes
        }

        List<String> newYamlLines = new ArrayList<>();
        newYamlLines.addAll(originalFileContentLines.subList(0, indexOfBegin + 1));
        newYamlLines.addAll(newLines);
        newYamlLines.addAll(originalFileContentLines.subList(indexOfEnd, originalFileContentLines.size()));

        return newYamlLines;
    }

    /**
     * Loads the mkdocs.yml file and replaces the content between <code>beginMarker</code> and <code>endMarker</code>
     * with the new lines given in <code>newLines</code>.
     * @param pathToMkDocsYaml Path to the mkdocs.yml file.
     * @param newLines List of new lines.
     * @param beginMarker Begin marker.
     * @param endMarker End marker.
     */
    public static void replaceContentLines(Path pathToMkDocsYaml, List<String> newLines, String beginMarker, String endMarker) {
        try {
            List<String> allYamlLines = Files.readAllLines(pathToMkDocsYaml, StandardCharsets.UTF_8);
            List<String> newFullFileContent = replaceLinesBetweenMarkers(allYamlLines, newLines, beginMarker, endMarker);

            if (newFullFileContent == null) {
                return; // no file changes
            }

            String newYamlContent = String.join(System.lineSeparator(), newFullFileContent) + System.lineSeparator();

            Files.writeString(pathToMkDocsYaml, newYamlContent, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (Exception ex) {
            throw new RuntimeException("Cannot modify the mkdocs.yml file: " + ex.getMessage(), ex);
        }
    }
}
