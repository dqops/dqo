/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client;

import com.dqops.utils.docs.DocumentationResourceFileLoader;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.Map;

/**
 * Python client documentation generator that generate main page documentation.
 */
public class MainPageClientDocumentationGeneratorImpl implements MainPageClientDocumentationGenerator {
    private final DocumentationResourceFileLoader documentationResourceFileLoader;

    public MainPageClientDocumentationGeneratorImpl(DocumentationResourceFileLoader documentationResourceFileLoader) {
        this.documentationResourceFileLoader = documentationResourceFileLoader;
    }

    @Override
    public DocumentationMarkdownFile renderMainPageDocumentation(MainPageClientDocumentationModel mainPageModel) {
        Template mainPageGuideTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/main_page_guide");
        String renderedGuide = HandlebarsDocumentationUtilities.renderTemplate(mainPageGuideTemplate, mainPageModel.getGuideDocumentationModel());
        Template mainPageIndexTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/main_page_index");
        String renderedIndex = HandlebarsDocumentationUtilities.renderTemplate(mainPageIndexTemplate, mainPageModel.getIndexDocumentationModel());
        Template mainPageConnectingTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/main_page_connecting");
        String renderedConnecting = HandlebarsDocumentationUtilities.renderTemplate(mainPageConnectingTemplate, mainPageModel.getConnectingDocumentationModel());

        DocumentationMarkdownFile mainPageFile = documentationResourceFileLoader.loadFileAndReplaceTokens(
                Path.of("client", "index.md"),
                Map.of(
                        "<!-- INCLUDE CLIENT_INDEX -->", renderedIndex,
                        "<!-- INCLUDE CLIENT_GUIDE -->", renderedGuide,
                        "<!-- INCLUDE CLIENT_CONNECTING -->", renderedConnecting
                )
        );

        mainPageFile.setRenderContext(mainPageModel);
        return mainPageFile;
    }
}
