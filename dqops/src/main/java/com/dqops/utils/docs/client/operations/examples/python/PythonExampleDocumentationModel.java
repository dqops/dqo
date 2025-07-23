/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.operations.examples.python;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Data;

import java.util.List;

/**
 * Documentation model to be used in Python example templates to render Python examples,
 * which then will be fed into operation's documentation page.
 */
@Data
public class PythonExampleDocumentationModel {
    /**
     * Expected HTTP status.
     */
    private HttpResponseStatus httpStatus;

    /**
     * Python module name.
     */
    private String moduleName;

    /**
     * API method name.
     */
    private String apiMethodName;

    /**
     * Flag for when authorized client is used.
     */
    private Boolean auth;

    /**
     * Flag for when asyncio is used.
     */
    private Boolean async;

    /**
     * Sample values for the subsequent path parameters.
     */
    private List<String> pathParameters;

    /**
     * Request body object constructed Python-style.
     */
    private String requestBody;

    /**
     * Additional models to import from dqops.client.models.
     */
    private List<String> modelImports;
}
