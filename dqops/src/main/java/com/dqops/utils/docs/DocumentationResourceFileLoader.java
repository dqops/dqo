/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs;

import com.dqops.utils.docs.files.DocumentationMarkdownFile;

import java.nio.file.Path;
import java.util.Map;

/**
 * Loads the content of a markdown file in "docs/resources", replacing tokens contained within.
 * Used for separating the manual and the generated parts of the docs.
 */
public interface DocumentationResourceFileLoader {
    /**
     * Loads the docs/resources file and replaces the content between for each token in <code>tokenReplacementMap</code>.
     * @param pathToResource Path to the markdown file, relative to docs/resources.
     * @param tokenReplacementMap Token replacement map. Key in the map is the token, value is what to put in the place of this token.
     * @return Detached markdown file representing the file being loaded with values put in place of its tokens.
     */
    DocumentationMarkdownFile loadFileAndReplaceTokens(Path pathToResource, Map<String, String> tokenReplacementMap);
}
