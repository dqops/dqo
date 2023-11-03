package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsOperationDocumentationModel;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;

import java.util.List;

public class UsageExampleModelFactoryImpl implements UsageExampleModelFactory {
    @Override
    public OperationUsageExampleDocumentationModel createOperationUsageExample(OperationExecutionMethod executionMethod,
                                                                               OperationsOperationDocumentationModel operationsOperationDocumentationModel) {
        OperationModel operationModel = operationsOperationDocumentationModel.getOperationModel();
        List<OperationParameterDocumentationModel> operationParameters = operationsOperationDocumentationModel.getParametersFields();

        OperationUsageExampleDocumentationModel model = new OperationUsageExampleDocumentationModel();
        model.setExecutionMethod(executionMethod);
        model.setOperation(operationModel);

        String renderedExample;
        switch (executionMethod) {
            case curl:
                renderedExample = renderedCurlUsageExample(operationModel,
                        operationsOperationDocumentationModel.getRequestBodyField(),
                        operationParameters);
                model.setExecutionCodeFormatting("bash");
                break;
            default:
                renderedExample = "No render";
                break;
        }

        model.setRenderedExample(renderedExample);
        return model;
    }

    protected String renderedCurlUsageExample(OperationModel operationModel,
                                              OperationParameterDocumentationModel requestBody,
                                              List<OperationParameterDocumentationModel> operationParameters) {
        StringBuilder renderedExample = new StringBuilder("curl");

        if (operationModel.getHttpMethod().equals(PathItem.HttpMethod.HEAD)) {
            renderedExample.append(" -I");
        } else if (!operationModel.getHttpMethod().equals(PathItem.HttpMethod.GET)) {
            renderedExample.append(" -X ");
            renderedExample.append(operationModel.getHttpMethod());
        }

        String callUrl = PathParameterFillerUtility.getSampleCallPath(operationModel.getPath(), operationParameters);
        renderedExample.append(" ").append(callUrl);
        renderedExample.append("\n\t\t-H \"Accept: application/json\"");

        if (requestBody != null) {
            renderedExample.append("\n\t\t-H \"Content-Type: application/json\"");
            renderedExample.append("\n\t\t-d ");

            String payload = PathParameterFillerUtility.getSampleFromTypeModel(requestBody.getTypeModel());
            String payloadPadded = payload.replace("\n", "\n\t\t");
            renderedExample.append("'")
                    .append(payloadPadded)
                    .append("'");
        }

        return renderedExample.toString();
    }
}
