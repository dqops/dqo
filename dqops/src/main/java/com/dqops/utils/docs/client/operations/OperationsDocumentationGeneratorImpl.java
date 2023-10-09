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
package com.dqops.utils.docs.client.operations;

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.List;

/**
 * Python client documentation generator that generate documentation for operations.
 */
public class OperationsDocumentationGeneratorImpl implements OperationsDocumentationGenerator {
    private final OperationsDocumentationModelFactory operationsDocumentationModelFactory;

    public OperationsDocumentationGeneratorImpl(OperationsDocumentationModelFactory operationsDocumentationModelFactory) {
        this.operationsDocumentationModelFactory = operationsDocumentationModelFactory;
    }

    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param openAPIModel
     * @param linkageStore
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderOperationsDocumentation(Path projectRootPath, OpenAPIModel openAPIModel, LinkageStore<String> linkageStore) {
        DocumentationFolder operationsFolder = new DocumentationFolder();
        operationsFolder.setFolderName("client/operations");
        operationsFolder.setLinkName("Operations");
        operationsFolder.setDirectPath(projectRootPath.resolve("../docs/client/operations").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("client/operations/operations_documentation");

        List<OperationsSuperiorObjectDocumentationModel> operationsSuperiorObjectDocumentationModels =
                operationsDocumentationModelFactory.createDocumentationForOperations(openAPIModel, linkageStore);

        for (OperationsSuperiorObjectDocumentationModel operationsSuperiorObjectDocumentationModel : operationsSuperiorObjectDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = operationsFolder.addNestedFile(operationsSuperiorObjectDocumentationModel.getLocationFilePath());
            documentationMarkdownFile.setRenderContext(operationsSuperiorObjectDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, operationsSuperiorObjectDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return operationsFolder;
    }
}
