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
package ai.dqo.utils.docs;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Replaces the content of a section of the MkDocs yml file (the navigation). Used to import pages to the navigation.
 */
public class MkDocsIndexReplaceUtility {
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
            int indexOfBegin = allYamlLines.indexOf(beginMarker);
            if (indexOfBegin < 0) {
                throw new RuntimeException("Marker " + beginMarker + " not found in mkdocs.yml");
            }

            int indexOfEnd = allYamlLines.indexOf(endMarker);
            if (indexOfEnd < 0) {
                throw new RuntimeException("Marker " + endMarker + " not found in mkdocs.yml");
            }

            List<String> currentLines = allYamlLines.stream()
                    .skip(indexOfBegin + 1)
                    .limit(indexOfEnd - indexOfBegin - 1)
                    .collect(Collectors.toList());

            if (currentLines.equals(newLines)) {
                return; // no changes
            }

            List<String> newYamlLines = new ArrayList<>();
            newYamlLines.addAll(allYamlLines.subList(0, indexOfBegin + 1));
            newYamlLines.addAll(newLines);
            newYamlLines.addAll(allYamlLines.subList(indexOfEnd, allYamlLines.size()));

            String newYamlContent = String.join(System.lineSeparator(), newYamlLines) + System.lineSeparator();

            Files.writeString(pathToMkDocsYaml, newYamlContent, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (Exception ex) {
            throw new RuntimeException("Cannot modify mkdocs.yml file: " + ex.getMessage(), ex);
        }
    }
}
