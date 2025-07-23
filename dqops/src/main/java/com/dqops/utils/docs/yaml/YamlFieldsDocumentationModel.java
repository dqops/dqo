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
import com.dqops.utils.reflection.EnumValueInfo;
import lombok.Data;

import java.util.Map;

/**
 * Yaml fields description model. Contains info about each object field.
 */
@Data
public class YamlFieldsDocumentationModel {
    /**
     * Field name.
     */
    private String classFieldName;
    /**
     * Yaml format field name.
     */
    private String yamlFieldName;
    /**
     * Display name.
     */
    private String displayName;
    /**
     * Field description.
     */
    private String helpText;
    /**
     * Field class type model.
     */
    private TypeModel typeModel;
    /**
     * Field enum values (if dataType is enum).
     */
    private Map<String, EnumValueInfo> enumValuesByName;
    /**
     * Field default value.
     */
    private Object defaultValue;
    /**
     * Field sample value.
     */
    private String[] sampleValues;
}
