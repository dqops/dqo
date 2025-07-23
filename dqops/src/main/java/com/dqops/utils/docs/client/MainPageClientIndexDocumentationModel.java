/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client;

import com.dqops.utils.docs.client.models.ModelsSuperiorObjectDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsSuperiorObjectDocumentationModel;
import lombok.Data;

import java.util.List;

/**
 * Container object with a list of all REST API methods and models.
 */
@Data
public class MainPageClientIndexDocumentationModel {
    /**
     * Operations superior object documentation models.
     */
    private List<OperationsSuperiorObjectDocumentationModel> operations;

    /**
     * Models superior object documentation models.
     */
    private List<ModelsSuperiorObjectDocumentationModel> models;
}
