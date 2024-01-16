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

        DocumentationMarkdownFile mainPageFile = documentationResourceFileLoader.loadFileAndReplaceTokens(
                Path.of("client", "index.md"),
                Map.of(
                        "<!-- INCLUDE CLIENT_INDEX -->", renderedIndex,
                        "<!-- INCLUDE CLIENT_GUIDE -->", renderedGuide
                )
        );

        mainPageFile.setRenderContext(mainPageModel);
        return mainPageFile;
    }
}
