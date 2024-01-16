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
import com.dqops.utils.docs.client.MainPageClientDocumentationModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.operations.examples.OperationExecutionMethod;
import com.dqops.utils.docs.client.operations.examples.OperationUsageExampleDocumentationModel;
import com.dqops.utils.docs.client.operations.examples.UsageExampleModelFactory;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Python client documentation generator that generate documentation for operations.
 */
public class OperationsDocumentationGeneratorImpl implements OperationsDocumentationGenerator {
    private final OperationsDocumentationModelFactory operationsDocumentationModelFactory;
    private final UsageExampleModelFactory usageExampleModelFactory;

    public OperationsDocumentationGeneratorImpl(OperationsDocumentationModelFactory operationsDocumentationModelFactory,
                                                UsageExampleModelFactory usageExampleModelFactory) {
        this.operationsDocumentationModelFactory = operationsDocumentationModelFactory;
        this.usageExampleModelFactory = usageExampleModelFactory;
    }

    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param openAPIModel
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderOperationsDocumentation(Path projectRootPath,
                                                             OpenAPIModel openAPIModel,
                                                             MainPageClientDocumentationModel mainPageModel) {
        DocumentationFolder operationsFolder = new DocumentationFolder();
        operationsFolder.setFolderName("client/operations");
        operationsFolder.setLinkName("Operations");
        operationsFolder.setDirectPath(projectRootPath.resolve("../docs/client/operations").toAbsolutePath().normalize());

        List<OperationsSuperiorObjectDocumentationModel> operationsSuperiorObjectDocumentationModels =
                operationsDocumentationModelFactory.createDocumentationForOperations(openAPIModel);
        for (OperationsSuperiorObjectDocumentationModel operationsSuperiorDocumentationModel : operationsSuperiorObjectDocumentationModels) {
            for (OperationsOperationDocumentationModel operationDocumentationModel : operationsSuperiorDocumentationModel.getOperationObjects()) {
                for (OperationExecutionMethod operationExecutionMethod : OperationExecutionMethod.values()) {
                    OperationUsageExampleDocumentationModel usageExampleDocumentationModel = usageExampleModelFactory.createOperationUsageExample(
                            operationExecutionMethod,
                            operationDocumentationModel
                    );
                    usageExampleDocumentationModel.setRenderedExample(usageExampleDocumentationModel.getRenderedExample()
                            .replace("\n", "\n\t"));
                    operationDocumentationModel.getUsageExamples().add(usageExampleDocumentationModel);
                }
            }
        }

        operationsSuperiorObjectDocumentationModels.sort(Comparator.comparing(OperationsSuperiorObjectDocumentationModel::getSuperiorClassSimpleName));
        mainPageModel.getIndexDocumentationModel().setOperations(operationsSuperiorObjectDocumentationModels);
        mainPageModel.getGuideDocumentationModel().setSelectedExamples(selectUsageExamplesForClientMainPage(operationsSuperiorObjectDocumentationModels));

        MainPageOperationsDocumentationModel mainPageOperationsDocumentationModel = new MainPageOperationsDocumentationModel();
        mainPageOperationsDocumentationModel.setControllerOperations(operationsSuperiorObjectDocumentationModels);

        Template mainPageTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/operations/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = operationsFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageOperationsDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, mainPageOperationsDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);

        Template template = HandlebarsDocumentationUtilities.compileTemplate("client/operations/operations_documentation");

        for (OperationsSuperiorObjectDocumentationModel operationsSuperiorObjectDocumentationModel : operationsSuperiorObjectDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = operationsFolder.addNestedFile(operationsSuperiorObjectDocumentationModel.getLocationFilePath());
            documentationMarkdownFile.setRenderContext(operationsSuperiorObjectDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, operationsSuperiorObjectDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return operationsFolder;
    }

    protected List<OperationUsageExampleDocumentationModel> selectUsageExamplesForClientMainPage(
            List<OperationsSuperiorObjectDocumentationModel> operationsSuperiorModels) {
        List<OperationUsageExampleDocumentationModel> selectedUsageExamples = new ArrayList<>();

        OperationsOperationDocumentationModel runChecksOperation = operationsSuperiorModels.stream()
                .filter(controller -> controller.getSuperiorClassSimpleName().equals("jobs"))
                .flatMap(controller -> controller.getOperationObjects().stream())
                .filter(operation -> operation.getOperationPythonName().equals("run_checks"))
                .findAny().get();
        OperationUsageExampleDocumentationModel runChecksExample = getUsageExampleFromOperationModel(runChecksOperation);

        runChecksExample.setExampleName("Run checks");
        runChecksExample.setExampleDescription(runChecksOperation.getOperationDescription());
        selectedUsageExamples.add(runChecksExample);

        return selectedUsageExamples;
    }

    protected OperationUsageExampleDocumentationModel getUsageExampleFromOperationModel(OperationsOperationDocumentationModel operationDocumentationModel) {
        Optional<OperationUsageExampleDocumentationModel> usageExample = operationDocumentationModel.getUsageExamples().stream()
                .filter(example -> example.getExecutionMethod() == OperationExecutionMethod.auth_python_sync)
                .findAny();
        return usageExample.get();
    }
}
