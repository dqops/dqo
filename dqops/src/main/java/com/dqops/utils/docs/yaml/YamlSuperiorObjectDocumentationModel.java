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

import com.dqops.utils.docs.generators.TypeModel;
import lombok.Data;

import java.util.List;

/**
 * Yaml superior class description model. Contains info about superior class and the objects it comprises.
 */
@Data
public class YamlSuperiorObjectDocumentationModel {
    /**
     * Superior class full name.
     */
    private String superiorClassFullName;
    /**
     * Superior class simple name.
     */
    private String superiorClassSimpleName;
    /**
     * Superior class file path.
     */
    private String superiorClassFilePath;
    /**
     * Superior class.
     */
    private Class<?> reflectedSuperiorClass;
    /**
     * Superior class data-type.
     */
    private TypeModel reflectedSuperiorDataType;

    /**
     * List of all superior class fields.
     */
    private List<YamlObjectDocumentationModel> classObjects;

    public String getLocationFilePath() {
        if (this.superiorClassFilePath == null) {
            return null;
        }

        return this.superiorClassFilePath + ".md";
    }

}
