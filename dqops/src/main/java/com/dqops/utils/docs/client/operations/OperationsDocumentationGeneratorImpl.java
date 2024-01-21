/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
import java.util.stream.Collectors;

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
        mainPageModel.getConnectingDocumentationModel().setConnectingExamples(selectConnectingExamplesForClientMainPage(operationsSuperiorObjectDocumentationModels));

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

        // Jobs
        OperationsSuperiorObjectDocumentationModel jobsController = operationsSuperiorModels.stream()
                .filter(controller -> controller.getSuperiorClassSimpleName().equals("jobs"))
                .findAny().get();

        OperationsOperationDocumentationModel runChecksOperation = jobsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().equals("run_checks"))
                .findAny().get();
        OperationUsageExampleDocumentationModel runChecksExample = getUsageExampleFromOperationModel(runChecksOperation);
        runChecksExample.setExampleName("Run checks");
        runChecksExample.setExampleDescription(runChecksOperation.getOperationDescription());
        runChecksExample.setExampleFooter(runChecksOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(runChecksExample);

        List<OperationsOperationDocumentationModel> collectStatisticsOperations = jobsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().contains("collect_statistics"))
                .collect(Collectors.toList());
        OperationsOperationDocumentationModel collectStatisticsOnTablesOperation = collectStatisticsOperations.stream()
                .filter(operation -> operation.getOperationPythonName().contains("table"))
                .findAny().get();
        OperationUsageExampleDocumentationModel collectStatisticsOnTablesExample = getUsageExampleFromOperationModel(collectStatisticsOnTablesOperation);
        collectStatisticsOnTablesExample.setExampleName("Collect statistics on tables");
        collectStatisticsOnTablesExample.setExampleDescription(collectStatisticsOnTablesOperation.getOperationDescription());
        collectStatisticsOnTablesExample.setExampleFooter(collectStatisticsOnTablesOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(collectStatisticsOnTablesExample);

        OperationsOperationDocumentationModel collectStatisticsOnDataGroupsOperation = collectStatisticsOperations.stream()
                .filter(operation -> operation.getOperationPythonName().contains("data_groups"))
                .findAny().get();
        OperationUsageExampleDocumentationModel collectStatisticsOnDataGroupsExample = getUsageExampleFromOperationModel(collectStatisticsOnDataGroupsOperation);
        collectStatisticsOnDataGroupsExample.setExampleName("Collect statistics on data groups");
        collectStatisticsOnDataGroupsExample.setExampleDescription(collectStatisticsOnDataGroupsOperation.getOperationDescription());
        collectStatisticsOnDataGroupsExample.setExampleFooter(collectStatisticsOnDataGroupsOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(collectStatisticsOnDataGroupsExample);

        OperationsOperationDocumentationModel importTablesOperation = jobsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().equals("import_tables"))
                .findAny().get();
        OperationUsageExampleDocumentationModel importTablesExample = getUsageExampleFromOperationModel(importTablesOperation);
        importTablesExample.setExampleName("Import tables");
        importTablesExample.setExampleDescription(importTablesOperation.getOperationDescription());
        importTablesExample.setExampleFooter(importTablesOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(importTablesExample);

        // Check results
        OperationsSuperiorObjectDocumentationModel checkResultsController = operationsSuperiorModels.stream()
                .filter(controller -> controller.getSuperiorClassSimpleName().equals("check_results"))
                .findAny().get();
        OperationsOperationDocumentationModel getTableDataQualityStatusOperation = checkResultsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().equals("get_table_data_quality_status"))
                .findAny().get();
        OperationUsageExampleDocumentationModel getTableDataQualityStatusExample = getUsageExampleFromOperationModel(getTableDataQualityStatusOperation);
        getTableDataQualityStatusExample.setExampleName("Get table data quality status");
        getTableDataQualityStatusExample.setExampleDescription(getTableDataQualityStatusOperation.getOperationDescription());
        getTableDataQualityStatusExample.setExampleFooter(getTableDataQualityStatusOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(getTableDataQualityStatusExample);

        // Jobs (continuation)
        OperationsOperationDocumentationModel waitForJobOperation = jobsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().equals("wait_for_job"))
                .findAny().get();
        OperationUsageExampleDocumentationModel waitForJobExample = getUsageExampleFromOperationModel(waitForJobOperation);
        waitForJobExample.setExampleName("Wait for job");
        waitForJobExample.setExampleDescription(waitForJobOperation.getOperationDescription());
        waitForJobExample.setExampleFooter(waitForJobOperation.getReturnValueSamplePython());
        selectedUsageExamples.add(waitForJobExample);

        return selectedUsageExamples;
    }

    protected List<OperationUsageExampleDocumentationModel> selectConnectingExamplesForClientMainPage(
            List<OperationsSuperiorObjectDocumentationModel> operationsSuperiorModels) {
        List<OperationUsageExampleDocumentationModel> selectedConnectingExamples = new ArrayList<>();

        OperationsSuperiorObjectDocumentationModel connectionsController = operationsSuperiorModels.stream()
                .filter(controller -> controller.getSuperiorClassSimpleName().equals("connections"))
                .findAny().get();
        OperationsOperationDocumentationModel getAllConnectionsOperation = connectionsController.getOperationObjects().stream()
                .filter(operation -> operation.getOperationPythonName().equals("get_all_connections"))
                .findAny().get();

        OperationUsageExampleDocumentationModel curlExample = getAllConnectionsOperation.getUsageExamples().stream()
                .filter(operation -> operation.getExecutionMethod() == OperationExecutionMethod.curl)
                .findAny().get().clone();
        curlExample.setExampleDescription("Using curl you can communicate with the REST API by sending HTTP requests."
                + " It's a simple but effective way of trying out REST API functionality."
                + "\nIt can also easily be used as a part of CI/CD pipelines, which lets you validate data on the fly.");
        selectedConnectingExamples.add(curlExample);

        OperationUsageExampleDocumentationModel pythonSyncExample = getAllConnectionsOperation.getUsageExamples().stream()
                .filter(operation -> operation.getExecutionMethod() == OperationExecutionMethod.python_sync)
                .findAny().get().clone();
        pythonSyncExample.setExampleDescription("This connecting method uses the synchronous interface offered by the unauthorized Python client."
                + "\nUnauthorized client sends plain requests without an `Authorization` header, therefore no API Key is required."
                + "\nMethods of the synchronous interface wait until a response from the REST API is captured before execution of the program will resume."
                + "\nREST APIs that are accessible from a larger network and/or are accessed by many users should require requests to be authorized."
                + " Trying to access them with an unauthorized will result in failure (`401 Unauthorized` error).");
        selectedConnectingExamples.add(pythonSyncExample);

        OperationUsageExampleDocumentationModel pythonAsyncExample = getAllConnectionsOperation.getUsageExamples().stream()
                .filter(operation -> operation.getExecutionMethod() == OperationExecutionMethod.python_async)
                .findAny().get().clone();
        pythonAsyncExample.setExampleDescription("This connecting method uses the asynchronous interface offered by the unauthorized Python client."
                + "\n\nUnauthorized client sends plain requests without an `Authorization` header, therefore no API Key is required."
                + "\n\nMethods of the asynchronous interface send a request to the REST API and immediately resume the execution of the program."
                + "\nTo collect results, you have to explicitly await for the result."
                + "\n\n*Learn more on how to use Python's asyncio API [here](https://docs.python.org/3/library/asyncio-task.html).*"
                + "\n\nREST APIs that are accessible from a larger network and/or are accessed by many users should require requests to be authorized."
                + "\nTrying to access them with an unauthorized will result in failure (`401 Unauthorized` error).");
        selectedConnectingExamples.add(pythonAsyncExample);

        OperationUsageExampleDocumentationModel pythonAuthSyncExample = getAllConnectionsOperation.getUsageExamples().stream()
                .filter(operation -> operation.getExecutionMethod() == OperationExecutionMethod.auth_python_sync)
                .findAny().get().clone();
        pythonAuthSyncExample.setExampleDescription("This connecting method uses the synchronous interface offered by the authorized Python client."
                + "\n\nAuthorized client, along each request, sends an API Key as a `Bearer` token in the `Authorization` header."
                + "\nIf you haven't already, go [here](#getting-the-personal-api-key) to generate your API Key."
                + "\n\nMethods of the synchronous interface wait until a response from the REST API is captured before execution of the program will resume.");
        selectedConnectingExamples.add(pythonAuthSyncExample);

        OperationUsageExampleDocumentationModel pythonAuthAsyncExample = getAllConnectionsOperation.getUsageExamples().stream()
                .filter(operation -> operation.getExecutionMethod() == OperationExecutionMethod.auth_python_async)
                .findAny().get().clone();
        pythonAuthAsyncExample.setExampleDescription("This connecting method uses the asynchronous interface offered by the authorized Python client."
                + "\n\nAuthorized client, along each request, sends an API Key as a `Bearer` token in the `Authorization` header."
                + "\nIf you haven't already, go [here](#getting-the-personal-api-key) to generate your API Key."
                + "\n\nMethods of the asynchronous interface send a request to the REST API and immediately resume the execution of the program."
                + "\nTo collect results, you have to explicitly await for the result."
                + "\n\n*Learn more on how to use Python's asyncio API [here](https://docs.python.org/3/library/asyncio-task.html).*");
        selectedConnectingExamples.add(pythonAuthAsyncExample);

        return selectedConnectingExamples;
    }

    protected OperationUsageExampleDocumentationModel getUsageExampleFromOperationModel(OperationsOperationDocumentationModel operationDocumentationModel) {
        Optional<OperationUsageExampleDocumentationModel> usageExample = operationDocumentationModel.getUsageExamples().stream()
                .filter(example -> example.getExecutionMethod() == OperationExecutionMethod.auth_python_sync)
                .findAny();
        return usageExample.get().clone();
    }
}
