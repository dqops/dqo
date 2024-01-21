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

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.client.operations.OperationsOperationDocumentationModel;
import com.github.jknack.handlebars.Template;

public class PythonExampleDocumentationGeneratorImpl implements PythonExampleDocumentationGenerator {
    private final PythonExampleDocumentationModelFactory pythonExampleDocumentationModelFactory;

    public PythonExampleDocumentationGeneratorImpl(PythonExampleDocumentationModelFactory pythonExampleDocumentationModelFactory) {
        this.pythonExampleDocumentationModelFactory = pythonExampleDocumentationModelFactory;
    }

    /**
     * Renders Python usage example into a string containing the code snippet.
     * @param operationModel
     * @param auth
     * @param async
     * @return Rendered Python code snippet.
     */
    @Override
    public String renderPythonOperationExample(OperationsOperationDocumentationModel operationModel,
                                               boolean auth,
                                               boolean async) {
        PythonExampleDocumentationModel pythonExampleDocumentationModel = pythonExampleDocumentationModelFactory.createPythonExampleDocumentationModel(
                operationModel.getOperationModel(),
                operationModel.getRequestBodyField(),
                operationModel.getParametersFields(),
                auth,
                async
        );

        Template pythonExampleTemplate = HandlebarsDocumentationUtilities.compileTemplate("client/operations/examples/python/python_example_documentation");
        String renderedPythonOperationExample = HandlebarsDocumentationUtilities.renderTemplate(pythonExampleTemplate, pythonExampleDocumentationModel);
        return renderedPythonOperationExample;
    }
}
