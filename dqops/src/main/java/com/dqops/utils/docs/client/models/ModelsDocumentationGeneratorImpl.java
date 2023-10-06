/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.docs.client.models;

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * Python client documentation generator that generate documentation for models.
 */
public class ModelsDocumentationGeneratorImpl implements ModelsDocumentationGenerator {
    private final ModelsDocumentationModelFactory modelsDocumentationModelFactory;

    public ModelsDocumentationGeneratorImpl(ModelsDocumentationModelFactory modelsDocumentationModelFactory) {
        this.modelsDocumentationModelFactory = modelsDocumentationModelFactory;
    }

    @Override
    public DocumentationMarkdownFile renderModelsDocumentation(Path projectRootPath,
                                                         Collection<ComponentModel> componentModels,
                                                         LinkageStore<String> linkageStore) {
        Template template = HandlebarsDocumentationUtilities.compileTemplate("client/models/models_documentation");

        List<ModelsObjectDocumentationModel> modelsObjectDocumentationModels =
                modelsDocumentationModelFactory.createDocumentationForModels(componentModels, linkageStore);
        ModelsSuperiorObjectDocumentationModel modelsSuperiorObjectDocumentationModel = new ModelsSuperiorObjectDocumentationModel();
        modelsSuperiorObjectDocumentationModel.setClassObjects(modelsObjectDocumentationModels);

        DocumentationMarkdownFile documentationMarkdownFile = new DocumentationMarkdownFile();
        documentationMarkdownFile.setFileName("models.md");
        documentationMarkdownFile.setRenderContext(modelsSuperiorObjectDocumentationModel);

        String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, modelsSuperiorObjectDocumentationModel);
        documentationMarkdownFile.setFileContent(renderedDocument);
        return documentationMarkdownFile;
    }
}
