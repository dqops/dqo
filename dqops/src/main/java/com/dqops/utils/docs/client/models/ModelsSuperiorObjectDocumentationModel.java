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
