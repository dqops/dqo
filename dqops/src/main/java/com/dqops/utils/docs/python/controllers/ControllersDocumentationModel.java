/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.docs.python.controllers;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.reflection.EnumValueInfo;
import lombok.Data;

import java.util.Map;

/**
 * Yaml fields description model. Contains info about each object field.
 */
@Data
public class ControllersDocumentationModel {
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
     * Field class.
     */
    private Class<?> clazz;
    /**
     * Field class name.
     */
    private String classNameUsedOnTheField;
    /**
     * Field class link.
     */
    private String classUsedOnTheFieldPath;
    /**
     * Field data type.
     */
    private ParameterDataType dataType;
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
