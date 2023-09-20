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
package com.dqops.utils.docs.client.controllers;

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.List;

/**
 * Python client documentation generator that generate documentation for controllers.
 */
public class ControllersDocumentationGeneratorImpl implements ControllersDocumentationGenerator {
    private ControllersDocumentationModelFactory controllersDocumentationModelFactory;

    public ControllersDocumentationGeneratorImpl(ControllersDocumentationModelFactory controllersDocumentationModelFactory) {
        this.controllersDocumentationModelFactory = controllersDocumentationModelFactory;
    }

    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath         Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param openAPIModel
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderControllersDocumentation(Path projectRootPath, OpenAPIModel openAPIModel) {
        DocumentationFolder controllersFolder = new DocumentationFolder();
        controllersFolder.setFolderName("client/controllers");
        controllersFolder.setLinkName("Controllers");
        controllersFolder.setDirectPath(projectRootPath.resolve("../docs/client/controllers").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("client/controllers/controllers_documentation");

        List<ControllersSuperiorObjectDocumentationModel> controllersSuperiorObjectDocumentationModels =
                controllersDocumentationModelFactory.createDocumentationForControllers(openAPIModel);

        for (ControllersSuperiorObjectDocumentationModel controllersSuperiorObjectDocumentationModel : controllersSuperiorObjectDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = controllersFolder.addNestedFile(controllersSuperiorObjectDocumentationModel.getLocationFilePath());
            documentationMarkdownFile.setRenderContext(controllersSuperiorObjectDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, controllersSuperiorObjectDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return controllersFolder;
    }
}
