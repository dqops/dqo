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

import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.examples.OperationUsageExampleDocumentationModel;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Yaml class object description model. Contains info about object and list of their fields.
 */
@Data
public class OperationsOperationDocumentationModel {
    /**
     * Operation model.
     */
    private OperationModel operationModel;

    /**
     * Object class full name.
     */
    private String operationJavaName;
    /**
     * Object class simple name.
     */
    private String operationPythonName;
    /**
     * Object class description.
     */
    private String operationDescription;

    /**
     * Source code
     */
    private String clientSourceUrl;

    /**
     * REST api URL.
     */
    private String apiCallUrl;
    /**
     * HTTP Method
     */
    private HttpMethod apiCallMethod;

    /**
     * Return value parameter
     */
    private OperationsDocumentationModel returnValueField;
    /**
     * List of operation parameters fields.
     */
    private List<OperationParameterDocumentationModel> parametersFields;
    /**
     * Body parameter field.
     */
    private OperationParameterDocumentationModel requestBodyField;

    /**
     * Usage examples list.
     */
    private List<OperationUsageExampleDocumentationModel> usageExamples = new ArrayList<>();

    /**
     * Sample return value.
     */
    private String returnValueSample;
}
