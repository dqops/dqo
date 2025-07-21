/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
