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
