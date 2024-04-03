/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.docs.client.models;

import lombok.Data;

import java.util.List;

/**
 * Models superior class description model. Contains info about superior class and the objects it comprises.
 */
@Data
public class ModelsSuperiorObjectDocumentationModel {
    /**
     * The name of a group of models, it is the "common" or a controller name.
     */
    private String modelsGroupName;

    /**
     * List of all superior class fields.
     */
    private List<ModelsObjectDocumentationModel> classObjects;

    /**
     * Location of the file relative to "docs/client/models" directory.
     */
    private String locationFilePath;
}
