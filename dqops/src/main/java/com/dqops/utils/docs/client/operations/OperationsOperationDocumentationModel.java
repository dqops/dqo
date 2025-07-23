/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * Sample return value JSON.
     */
    private String returnValueSampleJson;
    /**
     * Sample return value in Python.
     */
    private String returnValueSamplePython;
}
