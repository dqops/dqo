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

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.List;

/**
 * Yaml documentation generator that generate documentation for yaml.
 */
public class YamlDocumentationGeneratorImpl implements YamlDocumentationGenerator {
    private YamlDocumentationModelFactory yamlDocumentationModelFactory;

    public YamlDocumentationGeneratorImpl(YamlDocumentationModelFactory yamlDocumentationModelFactory) {
        this.yamlDocumentationModelFactory = yamlDocumentationModelFactory;
    }

    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath         Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param yamlDocumentationSchema Yaml documentation schema, describing the layout of documentation for YAML.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderYamlDocumentation(Path projectRootPath, LinkageStore<Class<?>> linkageStore, List<YamlDocumentationSchemaNode> yamlDocumentationSchema) {
        DocumentationFolder yamlFolder = new DocumentationFolder();
        yamlFolder.setFolderName("reference/yaml");
        yamlFolder.setLinkName("Yaml");
        yamlFolder.setDirectPath(projectRootPath.resolve("../docs/reference/yaml").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("yaml/yaml_documentation");

        List<YamlSuperiorObjectDocumentationModel> yamlSuperiorObjectDocumentationModels =
                yamlDocumentationModelFactory.createDocumentationForYaml(yamlDocumentationSchema);

        for (YamlSuperiorObjectDocumentationModel yamlSuperiorObjectDocumentationModel : yamlSuperiorObjectDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = yamlFolder.addNestedFile(yamlSuperiorObjectDocumentationModel.getLocationFilePath());
            documentationMarkdownFile.setRenderContext(yamlSuperiorObjectDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, yamlSuperiorObjectDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return yamlFolder;
    }
}
