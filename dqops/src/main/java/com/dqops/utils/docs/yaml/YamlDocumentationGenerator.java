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
