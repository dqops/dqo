/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.operations;

import com.dqops.utils.docs.client.MainPageClientDocumentationModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Yaml documentation generator that generate documentation for yaml.
 */
public interface OperationsDocumentationGenerator {
    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param openAPIModel
     * @param linkageStore
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderOperationsDocumentation(Path projectRootPath, OpenAPIModel openAPIModel, MainPageClientDocumentationModel mainPageModel);
}
