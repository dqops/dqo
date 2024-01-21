/*
 * Copyright © 2024 DQOps (support@dqops.com)
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
