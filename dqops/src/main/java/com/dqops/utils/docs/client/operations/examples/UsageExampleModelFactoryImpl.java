package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsOperationDocumentationModel;
import com.dqops.utils.docs.client.operations.examples.python.PythonExampleDocumentationGenerator;
import com.dqops.utils.docs.generators.GeneratorUtility;
import io.swagger.v3.oas.models.PathItem;

import java.util.List;

public class UsageExampleModelFactoryImpl implements UsageExampleModelFactory {
    private final PythonExampleDocumentationGenerator pythonExampleDocumentationGenerator;
    public UsageExampleModelFactoryImpl(PythonExampleDocumentationGenerator pythonExampleDocumentationGenerator) {
        this.pythonExampleDocumentationGenerator = pythonExampleDocumentationGenerator;
    }

    @Override
    public OperationUsageExampleDocumentationModel createOperationUsageExample(OperationExecutionMethod executionMethod,
                                                                               OperationsOperationDocumentationModel operationsOperationDocumentationModel) {
        OperationModel operationModel = operationsOperationDocumentationModel.getOperationModel();
        List<OperationParameterDocumentationModel> operationParameters = operationsOperationDocumentationModel.getParametersFields();

        OperationUsageExampleDocumentationModel model = new OperationUsageExampleDocumentationModel();
        model.setExecutionMethod(executionMethod);
        model.setOperation(operationModel);

        String renderedExample = renderUsageExample(executionMethod, operationsOperationDocumentationModel);
        model.setRenderedExample(renderedExample);
        return model;
    }

    protected String renderUsageExample(OperationExecutionMethod executionMethod, OperationsOperationDocumentationModel operationDocumentationModel) {
        switch (executionMethod) {
            case python_sync:
                return pythonExampleDocumentationGenerator.renderPythonOperationExample(operationDocumentationModel,
                        false, false);
            case python_async:
                return pythonExampleDocumentationGenerator.renderPythonOperationExample(operationDocumentationModel,
                        false, true);
            case auth_python_sync:
                return pythonExampleDocumentationGenerator.renderPythonOperationExample(operationDocumentationModel,
                        true, false);
            case auth_python_async:
                return pythonExampleDocumentationGenerator.renderPythonOperationExample(operationDocumentationModel,
                        true, true);
            case curl:
                return renderCurlUsageExample(operationDocumentationModel.getOperationModel(),
                        operationDocumentationModel.getRequestBodyField(),
                        operationDocumentationModel.getParametersFields());
            default:
                return "The example is in another castle...";
        }
    }

    protected String renderCurlUsageExample(OperationModel operationModel,
                                            OperationParameterDocumentationModel requestBody,
                                            List<OperationParameterDocumentationModel> operationParameters) {
        final String newLine = "^\n\t\t";
        StringBuilder renderedExample = new StringBuilder("curl");

        if (operationModel.getHttpMethod().equals(PathItem.HttpMethod.HEAD)) {
            renderedExample.append(" -I");
        } else if (!operationModel.getHttpMethod().equals(PathItem.HttpMethod.GET)) {
            renderedExample.append(" -X ");
            renderedExample.append(operationModel.getHttpMethod());
        }

        String callUrl = PathParameterFillerUtility.getSampleCallPath(operationModel.getPath(), operationParameters);
        renderedExample.append(" ").append(callUrl);
        renderedExample.append(newLine)
                .append("-H \"Accept: application/json\"");

        if (requestBody != null) {
            renderedExample.append(newLine)
                    .append("-H \"Content-Type: application/json\"").append(newLine)
                    .append("-d").append(newLine);

            String payload = GeneratorUtility.generateJsonSampleFromTypeModel(requestBody.getTypeModel(), false);
            String payloadPadded = payload.replace("\"", "\\\"")
                    .replace(System.lineSeparator(), " ");
            renderedExample.append("\"")
                    .append(payloadPadded)
                    .append("\"");
        }
        renderedExample.append("\n");

        return renderedExample.toString();
    }
}
