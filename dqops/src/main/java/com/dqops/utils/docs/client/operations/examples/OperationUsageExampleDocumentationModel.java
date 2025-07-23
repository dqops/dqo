/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.client.apimodel.OperationModel;
import lombok.Data;

/**
 * Cloneable model for usage examples for a particular connecting method for a specific operation of the REST API client.
 * Cloning creates a shallow copy, so that every clone can have different descriptions, but ultimately use the same OperationModel.
 */
@Data
public class OperationUsageExampleDocumentationModel implements Cloneable {
    /**
     * Name of the example snippet.
     */
    private String exampleName;

    /**
     * Description of the example snippet.
     */
    private String exampleDescription;

    /**
     * Execution method.
     */
    private OperationExecutionMethod executionMethod;

    /**
     * Operation model.
     */
    private OperationModel operation;

    /**
     * Rendered example.
     */
    private String renderedExample;

    /**
     * Description of the example snippet to be included in a footer.
     */
    private String exampleFooter;

    @Override
    public OperationUsageExampleDocumentationModel clone() {
        try {
            return (OperationUsageExampleDocumentationModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
