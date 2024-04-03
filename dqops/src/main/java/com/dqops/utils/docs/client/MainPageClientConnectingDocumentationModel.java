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

import com.dqops.utils.docs.client.operations.examples.OperationUsageExampleDocumentationModel;
import lombok.Data;

import java.util.List;

/**
 * Container object with a selected list of REST API usage examples for different ways to connect with the REST API.
 */
@Data
public class MainPageClientConnectingDocumentationModel {

    /**
     * Selected examples displayed on the main page.
     */
    private List<OperationUsageExampleDocumentationModel> connectingExamples;
}
