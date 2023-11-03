package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.operations.OperationsOperationDocumentationModel;

public interface UsageExampleModelFactory {
    OperationUsageExampleDocumentationModel createOperationUsageExample(OperationExecutionMethod executionMethod,
                                                                        OperationsOperationDocumentationModel operationsOperationDocumentationModel);
}
