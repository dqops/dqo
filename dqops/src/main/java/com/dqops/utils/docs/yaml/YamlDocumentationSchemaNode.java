/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs.yaml;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

/**
 * Object describing a single autonomous node of the YAML documentation.
 */
@Data
@AllArgsConstructor
public class YamlDocumentationSchemaNode {
    private Class<?> clazz;
    private Path pathToFile;

    public static YamlDocumentationSchemaNode fromClass(Class<?> clazz) {
        return new YamlDocumentationSchemaNode(
                clazz,
                Path.of(clazz.getSimpleName())
        );
    }
}
