/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
