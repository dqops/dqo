/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.generators;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.reflection.ObjectDataType;
import lombok.Data;

@Data
public class TypeModel {
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
     * Object field data type.
     */
    private ObjectDataType objectDataType;
    /**
     * Key type
     */
    private TypeModel genericKeyType;
    /**
     * Value type
     */
    private TypeModel genericValueType;
}
