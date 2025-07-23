/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.parquetfiles;

import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

public interface ParquetFilesDocumentationGenerator {
    /**
     * Renders documentation for all parquet classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderParquetDocumentation(Path projectRootPath);
}
