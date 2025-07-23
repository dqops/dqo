/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.cli;

import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Generates documentation about CLI commands.
 */
public interface CliCommandDocumentationGenerator {
    /**
     * Generates documentation of all commands.
     *
     * @param projectRootPath Project root path, used to identify the target file paths for generated documentation articles.
     * @return Documentation folder tree with generated documentation as markdown files.
     */
    DocumentationFolder generateDocumentationForCliCommands(Path projectRootPath);
}
