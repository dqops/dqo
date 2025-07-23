/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
