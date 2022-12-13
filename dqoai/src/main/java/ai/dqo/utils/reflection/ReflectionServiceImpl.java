/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.reflection;

import ai.dqo.metadata.fields.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
@Component
public class ReflectionServiceImpl implements ReflectionService {
    private static final Map<Class<?>, ParameterDataType> KNOWN_DATA_TYPES = new HashMap<>() {{
        put(String.class, ParameterDataType.string_type);
        put(Integer.class, ParameterDataType.integer_type);
        put(int.class, ParameterDataType.integer_type);
        put(Long.class, ParameterDataType.long_type);
        put(long.class, ParameterDataType.long_type);
        put(Boolean.class, ParameterDataType.boolean_type);
        put(boolean.class, ParameterDataType.boolean_type);
        put(Double.class, ParameterDataType.double_type);
        put(double.class, ParameterDataType.double_type);
        put(Instant.class, ParameterDataType.instant_type);
        put(LocalDate.class, ParameterDataType.date_type);
    }};

    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>() {{
        put(int.class, 0);
        put(long.class, 0L);
        put(boolean.class, false);
        put(double.class, 0.0);
    }};

    private Map<Class<?>, ClassInfo> reflectedClasses = new HashMap<>(); // accessed in a synchronized scope

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
        ClassInfo classInfo = new ClassInfo(targetClass);

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

        PropertyNamingStrategies.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategies.SnakeCaseStrategy();
        String yamlFieldName = snakeCaseStrategy.translate(fieldName);

        FieldInfo fieldInfo = new FieldInfo() {{
            setClazz(fieldType);
            setClassFieldName(fieldName);
            setYamlFieldName(yamlFieldName);
            setDisplayName(displayName != null ? displayName : yamlFieldName);
            setHelpText(helpText);
            setDirectField(targetClass == field.getDeclaringClass());
            setDefaultValue(DEFAULT_VALUES.getOrDefault(fieldType, null));
            setDisplayHint(displayHint);
        }};

        ParameterDataType parameterDataType = field.isAnnotationPresent(ControlType.class) ?
                field.getAnnotation(ControlType.class).value() : null;

        if (parameterDataType == null) {
            if (KNOWN_DATA_TYPES.containsKey(fieldType)) {
                parameterDataType = KNOWN_DATA_TYPES.get(fieldType);
            } else if (fieldType.isEnum()) {
                parameterDataType = ParameterDataType.enum_type;
                Object[] enumConstants = fieldType.getEnumConstants();
                Map<String, EnumValueInfo> enumDictionary = Arrays.stream(enumConstants)
                        .map(e -> createEnumValue((Enum<?>) e))
                        .collect(Collectors.toMap(e -> e.getJavaName(), e -> e));
                fieldInfo.setEnumValuesByName(enumDictionary);
            } else if (fieldType == List.class) {
                Type listParameterType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
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
            }
            else {
                parameterDataType = ParameterDataType.object_type;
                Constructor<?>[] constructors = fieldType.getDeclaredConstructors();
                Optional<Constructor<?>> parameterlessConstructor = Arrays.stream(constructors)
                        .filter(c -> c.getParameterCount() == 0)
                        .findFirst();
                parameterlessConstructor.ifPresent(fieldInfo::setConstructor);
            }
        }
        fieldInfo.setDataType(parameterDataType);

        String getterMethodPrefix = fieldType == Boolean.class || fieldType == boolean.class ? "is" : "get";
        String getterSetterSuffix = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String getterMethodName = getterMethodPrefix + getterSetterSuffix;
        String setterMethodName = "set" + getterSetterSuffix;

        Class<?> declaringClass = field.getDeclaringClass();

        try {
            Method getterMethod = declaringClass.getMethod(getterMethodName);
            fieldInfo.setGetterMethod(getterMethod);
        }
        catch (NoSuchMethodException nex) {
            // ignoring
        }

        try {
            Method setterMethod = declaringClass.getMethod(setterMethodName, fieldType);
            fieldInfo.setSetterMethod(setterMethod);
        }
        catch (NoSuchMethodException nex) {
            // ignoring
        }

        return fieldInfo;
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
}
