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

import java.nio.file.Path;

public interface DocsModelLinkageService {
    /**
     * Gets docs linkage for a certain class, if this class can be linked to external pages in the documentation.
     * @param modelClassName Searched class name.
     * @return Path to the class's documentation if it can be found. Null otherwise.
     */
    Path findDocsLinkage(String modelClassName);
}
