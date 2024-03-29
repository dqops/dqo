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
