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

import com.dqops.metadata.fields.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
@Component
public class ReflectionServiceImpl implements ReflectionService {
    private static final Map<Class<?>, ParameterDataType> KNOWN_DATA_TYPES = new LinkedHashMap<>() {{
        put(String.class, ParameterDataType.string_type);
        put(Integer.class, ParameterDataType.integer_type);
        put(int.class, ParameterDataType.integer_type);
        put(Long.class, ParameterDataType.long_type);
        put(long.class, ParameterDataType.long_type);
        put(Boolean.class, ParameterDataType.boolean_type);
        put(boolean.class, ParameterDataType.boolean_type);
        put(Double.class, ParameterDataType.double_type);
        put(double.class, ParameterDataType.double_type);
        put(LocalDate.class, ParameterDataType.date_type);
        put(LocalDateTime.class, ParameterDataType.datetime_type);
    }};

    private static final Map<Class<?>, Object> DEFAULT_VALUES = new LinkedHashMap<>() {{
        put(int.class, 0);
        put(long.class, 0L);
        put(boolean.class, false);
        put(double.class, 0.0);
    }};

    private Map<Class<?>, ClassInfo> reflectedClasses = new LinkedHashMap<>(); // accessed in a synchronized scope

    /**
     * Returns a cached reflection info for a given class type.
     * @param targetClass Target class type to introspect for fields.
     * @return Class info with a list of fields that will be serialized to YAML.
     */
    @Override
    public synchronized ClassInfo getClassInfoForClass(Class<?> targetClass) {
        ClassInfo classInfo = reflectedClasses.get(targetClass);
        if (classInfo == null) {
            classInfo = reflectClass(targetClass);
            reflectedClasses.put(targetClass, classInfo);
        }

        return classInfo;
    }

    /**
     * Introspects a given class using reflection. Finds all fields on the class.
     * @param targetClass Target class.
     * @return Reflection info of all fields that will be used for JSON serialization.
     */
    protected ClassInfo reflectClass(Class<?> targetClass) {
        Constructor<?>[] constructors = targetClass.getDeclaredConstructors();
        Optional<Constructor<?>> parameterlessConstructor = Arrays.stream(constructors)
                .filter(c -> c.getParameterCount() == 0)
                .findFirst();
        String metaDescription = targetClass.isAnnotationPresent(MetaDescription.class) ?
                targetClass.getAnnotation(MetaDescription.class).values() : null;

        ClassInfo classInfo = new ClassInfo(targetClass, parameterlessConstructor.orElse(null), metaDescription);

        Class<?> reflectedClass = targetClass;
        while (reflectedClass != Object.class) {
            Field[] fields = reflectedClass.getDeclaredFields();
            for (Field field : fields) {
                FieldInfo fieldInfo = makeFieldInfo(targetClass, field);
                if (fieldInfo != null) {
                    classInfo.getFields().add(fieldInfo);
                }
            }
            reflectedClass = reflectedClass.getSuperclass();
        }

        return classInfo;
    }

    /**
     * Creates a field info from a given field, when the field is yaml serializable.
     * @param targetClass Target class (superclass).
     * @param field Field object received from the java reflection api.
     * @return Field info for valid fields or null.
     */
    protected FieldInfo makeFieldInfo(Class<?> targetClass, Field field) {
        FieldInfo fieldInfo = getFieldInfoForField(field);
        if (fieldInfo != null) {
            fieldInfo.setDirectField(targetClass == field.getDeclaringClass());
        }

        return fieldInfo;
    }

    /**
     * Creates a field info from a given field, when the field is yaml serializable.
     * @param field Field object received from the java reflection api.
     * @return Field info for valid fields or null.
     */
    public FieldInfo getFieldInfoForField(Field field) {
        if (field.isSynthetic()) {
            return null;
        }

        if (Modifier.isStatic(field.getModifiers())) {
            return null;
        }

        if (field.isAnnotationPresent(JsonIgnore.class)) {
            // skipping this field
            return null;
        }

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        String helpText = field.isAnnotationPresent(JsonPropertyDescription.class) ?
                field.getAnnotation(JsonPropertyDescription.class).value() : null;
        String displayName = field.isAnnotationPresent(DisplayName.class) ?
                field.getAnnotation(DisplayName.class).value() : null;
        DisplayHint displayHint = field.isAnnotationPresent(ControlDisplayHint.class) ?
                field.getAnnotation(ControlDisplayHint.class).value() : null;
        String[] sampleValues = field.isAnnotationPresent(SampleValues.class) ?
                field.getAnnotation(SampleValues.class).values() : null;
        boolean requiredAnnotationPresent = field.isAnnotationPresent(RequiredField.class);
        boolean notNullableField = fieldType == boolean.class || fieldType == int.class || fieldType == double.class;

        String yamlFieldName;
        if (field.isAnnotationPresent(JsonProperty.class)) {
            yamlFieldName = field.getAnnotation(JsonProperty.class).value();
        } else {
            PropertyNamingStrategies.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategies.SnakeCaseStrategy();
            yamlFieldName = snakeCaseStrategy.translate(fieldName);
        }

        FieldInfo fieldInfo = new FieldInfo() {{
            setClazz(fieldType);
            setClassFieldName(fieldName);
            setYamlFieldName(yamlFieldName);
            setDisplayName(displayName != null ? displayName : yamlFieldName);
            setHelpText(helpText);
            setDefaultValue(DEFAULT_VALUES.getOrDefault(fieldType, null));
            setDisplayHint(displayHint);
            setSampleValues(sampleValues);
            setRequiredOrNotNullable(requiredAnnotationPresent || notNullableField);
        }};

        ParameterDataType parameterDataType = field.isAnnotationPresent(ControlType.class) ?
                field.getAnnotation(ControlType.class).value() : null;

        if (parameterDataType == null) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                parameterDataType = determineParameterDataType(fieldType, (ParameterizedType) genericType, fieldInfo);
            } else {
                parameterDataType = determineParameterDataType(fieldType, null, fieldInfo);
            }

        }
        fieldInfo.setDataType(parameterDataType);

        String getterMethodPrefix = fieldType == boolean.class ? "is" : "get";
        String getterSetterSuffix = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String getterMethodName = getterMethodPrefix + getterSetterSuffix;
        String setterMethodName = "set" + getterSetterSuffix;

        Class<?> declaringClass = field.getDeclaringClass();

        try {
            Method getterMethod = declaringClass.getMethod(getterMethodName);
            getterMethod.setAccessible(true);
            fieldInfo.setGetterMethod(getterMethod);
        }
        catch (NoSuchMethodException nex) {
            // ignoring
        }

        try {
            Method setterMethod = declaringClass.getMethod(setterMethodName, fieldType);
            setterMethod.setAccessible(true);
            fieldInfo.setSetterMethod(setterMethod);
        }
        catch (NoSuchMethodException nex) {
            // ignoring
        }

        return fieldInfo;
    }

    /**
     * Determine parameter data type represented in <code>fieldType</code>.
     * @param fieldType Class with the field type to be determined.
     * @param genericType Optional type if <code>fieldType</code> is a generic wrapper.
     * @param fieldInfo Field info to be modified with additional info about the determined type.
     * @return Determined parameter type.
     */
    @Override
    public ParameterDataType determineParameterDataType(Class<?> fieldType, ParameterizedType genericType, FieldInfo fieldInfo) {
        ParameterDataType parameterDataType = null;
        if (KNOWN_DATA_TYPES.containsKey(fieldType)) {
            parameterDataType = KNOWN_DATA_TYPES.get(fieldType);
        } else if (fieldType.isEnum()) {
            parameterDataType = ParameterDataType.enum_type;
            fieldInfo.setEnumValuesByName(getEnumValuesMap((Class<? extends Enum<?>>) fieldType));
        } else if (isClassList(fieldType) && isJavaClass(fieldType)) {
            Type listParameterType = genericType != null ? genericType.getActualTypeArguments()[0] : Object.class;
            if (listParameterType == String.class) {
                parameterDataType = ParameterDataType.string_list_type;
                try {
                    Constructor<?> emptyConstructor = ArrayList.class.getDeclaredConstructor();
                    fieldInfo.setConstructor(emptyConstructor);
                } catch (NoSuchMethodException e) {
                }
            }
            else if (listParameterType == Long.class) {
                parameterDataType = ParameterDataType.integer_list_type;
                try {
                    Constructor<?> emptyConstructor = ArrayList.class.getDeclaredConstructor();
                    fieldInfo.setConstructor(emptyConstructor);
                } catch (NoSuchMethodException e) {
                }
            }
            else {
                parameterDataType = ParameterDataType.object_type;
                fieldInfo.setObjectDataType(ObjectDataType.list_type);
            }
        } else if (isClassMap(fieldType) && isJavaClass(fieldType)) {
            parameterDataType = ParameterDataType.object_type;
            fieldInfo.setObjectDataType(ObjectDataType.map_type);
        } else {
            parameterDataType = ParameterDataType.object_type;
            fieldInfo.setObjectDataType(ObjectDataType.object_type);
            Constructor<?>[] constructors = fieldType.getDeclaredConstructors();
            Optional<Constructor<?>> parameterlessConstructor = Arrays.stream(constructors)
                    .filter(c -> c.getParameterCount() == 0)
                    .findFirst();
            parameterlessConstructor.ifPresent(fieldInfo::setConstructor);
        }

        fieldInfo.setGenericDataType(genericType);
        return parameterDataType;
    }

    private boolean isJavaClass(Class<?> clazz) {
        return clazz.getClassLoader() == null;
    }

    private boolean isClassList(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    private boolean isClassMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * Retrieves an enum info for a single enum value.
     * @param enumInstance Enum value.
     * @return Enum information.
     */
    protected EnumValueInfo createEnumValue(Enum<?> enumInstance) {
        assert enumInstance.getClass().isEnum();

        String name = enumInstance.name();
        EnumValueInfo enumInfo = new EnumValueInfo();
        enumInfo.setEnumInstance(enumInstance);
        enumInfo.setJavaName(name);

        try {
            Field enumField = enumInstance.getClass().getDeclaredField(name);
            String jsonProperty = enumField.isAnnotationPresent(JsonProperty.class) ?
                    enumField.getAnnotation(JsonProperty.class).value() : null;
            String displayName = enumField.isAnnotationPresent(DisplayName.class) ?
                    enumField.getAnnotation(DisplayName.class).value() : null;

            String yamlName = jsonProperty != null ? jsonProperty : name;
            enumInfo.setYamlName(yamlName);
            enumInfo.setDisplayName(displayName != null ? displayName : yamlName);
        }
        catch (NoSuchFieldException fex) {
            return null;
        }

        return enumInfo;
    }

    @Override
    public Map<String, EnumValueInfo> getEnumValuesMap(Class<? extends Enum> targetEnum) {
        Object[] enumConstants = targetEnum.getEnumConstants();
        return Arrays.stream(enumConstants)
                .map(e -> createEnumValue((Enum<?>) e))
                .collect(Collectors.toMap(EnumValueInfo::getYamlName, Function.identity()));
    }
}
