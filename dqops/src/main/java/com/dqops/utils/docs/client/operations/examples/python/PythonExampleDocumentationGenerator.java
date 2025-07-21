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

import com.dqops.utils.docs.client.operations.OperationsOperationDocumentationModel;

/**
 * Python usage example generator that generates usage examples of client operations to be used in the documentation.
 */
public interface PythonExampleDocumentationGenerator {
    /**
     * Renders Python usage example into a string containing the code snippet.
     * @param operationModel
     * @param auth
     * @param async
     * @return Rendered Python code snippet.
     */
    String renderPythonOperationExample(OperationsOperationDocumentationModel operationModel,
                                        boolean auth,
                                        boolean async);
}
