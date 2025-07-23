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

import com.dqops.utils.exceptions.DqoRuntimeException;
import lombok.Data;

import java.nio.file.Path;

/**
 * Content of a single markdown file.
 */
@Data
public class DocumentationMarkdownFile implements Cloneable {
    /**
     * File name (without the folder path, but including the .md file extension).
     */
    private String fileName;

    /**
     * File link name to be generated in the MkDocs table of contents.
     */
    private String linkName;

    /**
     * Direct file system path to the folder.
     */
    private Path directPath;

    /**
     * File content.
     */
    private String fileContent;

    /**
     * Object passed to the Handlebars template. The model with the information to be rendered.
     */
    private Object renderContext;

    /**
     * Excludes this file from adding to the table of content in mkdocs.yml file.
     */
    private boolean excludeFromTableOfContent;

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DocumentationMarkdownFile clone() {
        try {
            return (DocumentationMarkdownFile) super.clone();
        }
        catch (CloneNotSupportedException cex) {
            throw new DqoRuntimeException("Clone not supported", cex);
        }
    }
}
