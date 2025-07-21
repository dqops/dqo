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

import com.dqops.utils.docs.files.DocumentationMarkdownFile;

/**
 * Yaml documentation generator that generate documentation for yaml.
 */
public interface MainPageClientDocumentationGenerator {
    DocumentationMarkdownFile renderMainPageDocumentation(MainPageClientDocumentationModel mainPageModel);
}
