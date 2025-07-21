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

import lombok.Data;

/**
 * Container object with subcomponents used to generate REST API index page.
 */
@Data
public class MainPageClientDocumentationModel {
    /**
     * Documentation model for the index (listing of operations and models) of the REST API index page.
     */
    private MainPageClientIndexDocumentationModel indexDocumentationModel;

    /**
     * Documentation model for the Python client guide. Contains selected examples on how to use the `dqops` package in Python.
     */
    private MainPageClientGuideDocumentationModel guideDocumentationModel;

    /**
     * Documentation model for the connecting methods. Contains selected examples on how to connect with the REST API.
     */
    private MainPageClientConnectingDocumentationModel connectingDocumentationModel;
}
