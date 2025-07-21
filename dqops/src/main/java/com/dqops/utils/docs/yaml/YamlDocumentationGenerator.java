/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.yaml;

import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;
import java.util.List;

/**
 * Yaml documentation generator that generate documentation for yaml.
 */
public interface YamlDocumentationGenerator {
    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath         Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param yamlDocumentationSchema Yaml documentation schema, describing the layout of documentation for YAML.
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderYamlDocumentation(Path projectRootPath, LinkageStore<Class<?>> linkageStore, List<YamlDocumentationSchemaNode> yamlDocumentationSchema);
}
