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
