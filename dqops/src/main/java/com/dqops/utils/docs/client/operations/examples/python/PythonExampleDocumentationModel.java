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
