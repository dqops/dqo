/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.rules;

import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Rule documentation generator that generates documentation for rules.
 */
public interface RuleDocumentationGenerator {
    /**
     * Renders documentation for all rules as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param dqoHome         DQOps home.
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderRuleDocumentation(Path projectRootPath, LinkageStore<Class<?>> linkageStore, DqoHome dqoHome);
}
