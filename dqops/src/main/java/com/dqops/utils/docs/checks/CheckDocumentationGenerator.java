/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.utils.docs.checks;

import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Generates documentation about checks.
 */
public interface CheckDocumentationGenerator {
    /**
     * The name of the folder where the reference of the data quality checks is stored.
     */
    String CHECKS_FOLDER_NAME = "checks";

    /**
     * Generates documentation of all checks.
     *
     * @param projectRootPath Project root path, used to identify the target file paths for generated documentation articles.
     * @param currentRootFolder Current documentation that was read from files.
     * @return Documentation folder tree with generated documentation as markdown files.
     */
    DocumentationFolder renderCheckDocumentation(Path projectRootPath, DocumentationFolder currentRootFolder);
}
