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
