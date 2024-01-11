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

import com.dqops.utils.docs.client.models.ModelsSuperiorObjectDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsSuperiorObjectDocumentationModel;
import com.dqops.utils.docs.client.operations.examples.OperationUsageExampleDocumentationModel;
import lombok.Data;

import java.util.List;

/**
 * Container object with a list of all checks.
 */
@Data
public class MainPageClientDocumentationModel {
    /**
     * Operations superior object documentation models.
     */
    private List<OperationsSuperiorObjectDocumentationModel> operations;

    /**
     * Models superior object documentation models.
     */
    private List<ModelsSuperiorObjectDocumentationModel> models;

    /**
     * Selected examples displayed on the main page.
     */
    private List<OperationUsageExampleDocumentationModel> selectedExamples;
}
