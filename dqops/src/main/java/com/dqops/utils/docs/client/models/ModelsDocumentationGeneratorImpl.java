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
import com.dqops.utils.docs.client.MainPageClientDocumentationModel;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;
import com.google.common.collect.Streams;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Python client documentation generator that generate documentation for models.
 */
public class ModelsDocumentationGeneratorImpl implements ModelsDocumentationGenerator {
    private final ModelsDocumentationModelFactory modelsDocumentationModelFactory;

    public ModelsDocumentationGeneratorImpl(ModelsDocumentationModelFactory modelsDocumentationModelFactory) {
        this.modelsDocumentationModelFactory = modelsDocumentationModelFactory;
    }

    @Override
    public DocumentationFolder renderModelsDocumentation(Path projectRootPath,
                                                         Collection<ComponentModel> componentModels,
                                                         MainPageClientDocumentationModel mainPageModel) {
        DocumentationFolder modelsFolder = new DocumentationFolder();
        modelsFolder.setFolderName("client/models");
        modelsFolder.setLinkName("Models");
        modelsFolder.setDirectPath(projectRootPath.resolve("../docs/client/models").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("client/models/models_documentation");

        ModelsSuperiorObjectDocumentationModel sharedModelsDocumentationModel =
                modelsDocumentationModelFactory.createDocumentationForSharedModels(componentModels);
        if (sharedModelsDocumentationModel != null) {
            DocumentationMarkdownFile documentationMarkdownFile = modelsFolder.addNestedFile(
                    ModelsDocumentationModelFactoryImpl.SHARED_MODELS_IDENTIFIER + ".md");
            documentationMarkdownFile.setRenderContext(sharedModelsDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, sharedModelsDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }

        List<ModelsSuperiorObjectDocumentationModel> modelsSuperiorObjectDocumentationModels =
                modelsDocumentationModelFactory.createDocumentationForModels(componentModels);

        mainPageModel.getIndexDocumentationModel().setModels(Streams.concat(
                Stream.of(sharedModelsDocumentationModel),
                modelsSuperiorObjectDocumentationModels.stream()
        ).collect(Collectors.toList()));

        for (ModelsSuperiorObjectDocumentationModel modelsSuperiorObjectDocumentationModel
                : modelsSuperiorObjectDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = modelsFolder.addNestedFile(modelsSuperiorObjectDocumentationModel.getLocationFilePath());
            documentationMarkdownFile.setRenderContext(modelsSuperiorObjectDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, modelsSuperiorObjectDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }

        MainPageModelsDocumentationModel mainPageModelsDocumentationModel = new MainPageModelsDocumentationModel();
        mainPageModelsDocumentationModel.getModelsSuperiorModels().add(sharedModelsDocumentationModel);
        mainPageModelsDocumentationModel.getModelsSuperiorModels().addAll(modelsSuperiorObjectDocumentationModels);;

        Template mainPageTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/models/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = modelsFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageDocumentationMarkdownFile);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, mainPageModelsDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);

        return modelsFolder;
    }
}
