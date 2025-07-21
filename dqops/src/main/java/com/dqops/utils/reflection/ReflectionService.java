/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.reflection;

import com.dqops.metadata.fields.ParameterDataType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
public interface ReflectionService {
    /**
     * Returns a cached reflection info for a given class type.
     *
     * @param targetClass Target class type to introspect for fields.
     * @return Class info with a list of fields that will be serialized to YAML.
     */
    ClassInfo getClassInfoForClass(Class<?> targetClass);

    /**
     * Creates a field info from a given field, when the field is yaml serializable.
     * @param field Field object received from the java reflection api.
     * @return Field info for valid fields or null.
     */
    FieldInfo getFieldInfoForField(Field field);

    /**
     * Determine parameter data type represented in <code>fieldType</code>.
     * @param fieldType Class with the field type to be determined.
     * @param genericType Optional type if <code>fieldType</code> is a generic wrapper.
     * @param fieldInfo Field info to be modified with additional info about the determined type.
     * @return Determined parameter type.
     */
    ParameterDataType determineParameterDataType(Class<?> fieldType, ParameterizedType genericType, FieldInfo fieldInfo);

    /**
     * Returns a cached reflection info for a given enum containing its values.
     *
     * @param targetEnum Target enum type to introspect.
     * @return Map containing info regarding values of the enum.
     */
    Map<String, EnumValueInfo> getEnumValuesMap(Class<? extends Enum> targetEnum);
}
