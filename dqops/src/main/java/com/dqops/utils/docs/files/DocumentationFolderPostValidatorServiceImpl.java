/*
 * Copyright © 2024 DQOps (support@dqops.com)
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
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DocumentationFolderPostValidatorServiceImpl implements DocumentationFolderPostValidatorService {
    // REGEXPs to ensure correctness.
    /**
     * General pattern for discerning links. Matches all Markdown links without "http://", "https://".
     */
    private final Pattern generalInternalLink = Pattern.compile("[\\w\\s]]\\((?!https?://)[^)\\[\\]]*\\)");

    /**
     * Pattern for ensuring a correct links format.
     */
    private final Pattern correctLinkFormat = Pattern.compile("[\\w\\s]]\\((?!\\))(((\\.\\./)*|\\./)(([a-zA-Z0-9]+[\\-_])*[a-zA-Z0-9]+/)*([a-zA-Z0-9]+[\\-_])*[a-zA-Z0-9]+\\.(md|png))?(#(([a-zA-Z0-9]+[\\-_])*[a-zA-Z0-9]+|--?[\\w.][\\w.\\-]*))?( \".+\")?\\)");

    private final Path projectRoot;

    public DocumentationFolderPostValidatorServiceImpl(Path projectRoot) {
        this.projectRoot = projectRoot.toAbsolutePath();
    }

    @Override
    public int postProcessValidate(DocumentationFolder toValidate) {
        int incorrectLinksCount = 0;

        for (DocumentationMarkdownFile file : toValidate.getFiles()) {
            incorrectLinksCount += postProcessValidateForFile(file);
        }
        for (DocumentationFolder folder : toValidate.getSubFolders()) {
            incorrectLinksCount += postProcessValidate(folder);
        }

        if (incorrectLinksCount != 0) {
            System.err.println("Folder " + projectRoot.relativize(toValidate.getDirectPath().toAbsolutePath()) + " contains " + incorrectLinksCount + " incorrect links.\n");
        }
        return incorrectLinksCount;
    }

    protected int postProcessValidateForFile(DocumentationMarkdownFile file) {
        String content = file.getFileContent();

        List<String> incorrectLinks = generalInternalLink.matcher(content).results()
                .map(MatchResult::group)
                .filter(Predicate.not(correctLinkFormat.asMatchPredicate()))
                .filter(line -> !line.contains("(+1)")) // some PII checks
                .collect(Collectors.toList());

        if (!incorrectLinks.isEmpty()) {
            System.err.println("File " + projectRoot.relativize(file.getDirectPath().toAbsolutePath()) + " contains " + incorrectLinks.size() + " incorrect links:");
            for (String incorrectLink : incorrectLinks) {
                System.err.println("\t" + incorrectLink);
            }
            System.err.println();
        }

        return incorrectLinks.size();
    }
}
