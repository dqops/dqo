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
