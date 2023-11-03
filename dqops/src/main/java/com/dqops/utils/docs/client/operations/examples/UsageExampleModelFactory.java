package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;

import java.util.List;

public interface UsageExampleModelFactory {
    OperationUsageExampleDocumentationModel createOperationUsageExample(OperationExecutionMethod executionMethod,
                                                                        OperationModel operationModel,
                                                                        List<OperationParameterDocumentationModel> operationParameters);
}
