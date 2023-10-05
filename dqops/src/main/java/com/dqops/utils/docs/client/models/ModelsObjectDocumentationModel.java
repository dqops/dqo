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

import java.nio.file.Path;
import java.util.List;

/**
 * Yaml class object description model. Contains info about object and list of their fields.
 */
@Data
public class ModelsObjectDocumentationModel {
    /**
     * Object class full name.
     */
    private String classFullName;
    /**
     * Object class simple name.
     */
    private String classSimpleName;
    /**
     * Object class description.
     */
    private String classDescription;
    /**
     * Object class.
     */
    private Class<?> reflectedClass;
    /**
     * Object class path.
     */
    private Path objectClassPath;
    /**
     * List of all object fields.
     */
    private List<ModelsDocumentationModel> objectFields;

}
