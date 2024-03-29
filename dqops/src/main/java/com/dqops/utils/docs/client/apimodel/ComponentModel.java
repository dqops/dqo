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

package com.dqops.utils.docs.client.apimodel;

import io.swagger.v3.oas.models.media.Schema;
import lombok.Data;

import java.nio.file.Path;

@Data
public class ComponentModel {
    private final String className;
    private final Schema<?> objectSchema;
    private final Class<?> reflectedClass;
    private Path docsLink;

    public ComponentModel(String className, Schema<?> objectSchema, Class<?> reflectedClass) {
        this.className = className;
        this.objectSchema = objectSchema;
        this.reflectedClass = reflectedClass;
    }
}
