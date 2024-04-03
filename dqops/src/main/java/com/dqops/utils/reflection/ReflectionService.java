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
