package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import io.swagger.v3.oas.models.PathItem;

import java.util.List;

public class UsageExampleModelFactoryImpl implements UsageExampleModelFactory {
    @Override
    public OperationUsageExampleDocumentationModel createOperationUsageExample(OperationExecutionMethod executionMethod,
                                                                               OperationModel operationModel,
                                                                               List<OperationParameterDocumentationModel> operationParameters) {
        OperationUsageExampleDocumentationModel model = new OperationUsageExampleDocumentationModel();
        model.setExecutionMethod(executionMethod);
        model.setOperation(operationModel);

        String renderedExample;
        switch (executionMethod) {
            case curl:
                renderedExample = renderedCurlUsageExample(operationModel, operationParameters);
                break;
            default:
                renderedExample = "No render";
                break;
        }

        model.setRenderedExample(renderedExample);
        return model;
    }

    protected String renderedCurlUsageExample(OperationModel operationModel,
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
        renderedExample.append("\n\t\t");
        renderedExample.append("-H \"Accept: application/json\"");

        return renderedExample.toString();
    }
}
