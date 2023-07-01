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
