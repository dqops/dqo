/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
    private String objectClassPath;
    /**
     * Enum values for enum typed objects.
     */
    private List<String> enumValues;
    /**
     * List of all object fields.
     */
    private List<ModelsDocumentationModel> objectFields;

}
