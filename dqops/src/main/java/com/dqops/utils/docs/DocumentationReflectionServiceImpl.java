/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.utils.docs;

import com.dqops.metadata.fields.DisplayHint;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.reflection.ReflectionService;
import com.google.inject.internal.MoreTypes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DocumentationReflectionServiceImpl implements DocumentationReflectionService {
    private static final String REFLECTIVE_FIELD_NAME = "self";
    private final ReflectionService reflectionService;
    private final ClassInfo reflectedList;
    private final ClassInfo reflectedMap;

    public DocumentationReflectionServiceImpl(ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
        this.reflectedList = reflectionService.getClassInfoForClass(AbstractList.class);
        this.reflectedMap = reflectionService.getClassInfoForClass(LinkedHashMap.class);
    }

    @Override
    public ClassInfo getClassInfoForClass(Class<?> targetClass) {
        ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
        if (Map.class.isAssignableFrom(targetClass)) {
            sanitizeMapClassInfo(targetClass, classInfo);
        } else if (List.class.isAssignableFrom(targetClass)) {
            sanitizeListClassInfo(targetClass, classInfo);
        }
        return classInfo;
    }

    protected void sanitizeListClassInfo(Class<?> clazz, ClassInfo classInfo) {
        sanitizeClassInfoFieldListByClassFieldName(reflectedList.getFields(), classInfo);
        FieldInfo thisField = new FieldInfo() {{
            setClazz(clazz);
            setClassFieldName(REFLECTIVE_FIELD_NAME);
            setYamlFieldName(REFLECTIVE_FIELD_NAME);
            setDisplayName(REFLECTIVE_FIELD_NAME);
            setDisplayHint(DisplayHint.textarea);
        }};

        if (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType parameterizedSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
        ParameterizedType javaSuperclass = getJavaParameterizedSuperclass(parameterizedSuperclass);
        ParameterDataType parameterDataType = reflectionService.determineParameterDataType((Class<?>) javaSuperclass.getRawType(),
                javaSuperclass,
                thisField);
        thisField.setDataType(parameterDataType);
        classInfo.getFields().add(thisField);
    }

    protected void sanitizeMapClassInfo(Class<?> clazz, ClassInfo classInfo) {
        sanitizeClassInfoFieldListByClassFieldName(reflectedMap.getFields(), classInfo);
        FieldInfo thisField = new FieldInfo() {{
            setClazz(clazz);
            setClassFieldName(REFLECTIVE_FIELD_NAME);
            setYamlFieldName(REFLECTIVE_FIELD_NAME);
            setDisplayName(REFLECTIVE_FIELD_NAME);
            setDisplayHint(DisplayHint.textarea);
        }};

        if (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType parameterizedSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
        ParameterizedType javaSuperclass = getJavaParameterizedSuperclass(parameterizedSuperclass);
        ParameterDataType parameterDataType = reflectionService.determineParameterDataType((Class<?>) javaSuperclass.getRawType(),
                javaSuperclass,
                thisField);
        thisField.setDataType(parameterDataType);
        classInfo.getFields().add(thisField);
    }

    private ParameterizedType getJavaParameterizedSuperclass(ParameterizedType type) {
        ParameterizedType parameterizedType = type;
        while (parameterizedType != null && !parameterizedType.getTypeName().toLowerCase().contains("java.")) {
            parameterizedType = (ParameterizedType) ((Class<?>) parameterizedType.getRawType()).getGenericSuperclass();
        }
        Type[] typeArguments = new Type[parameterizedType.getActualTypeArguments().length];
        Iterator<Type> genericTypeArguments = Arrays.stream(type.getActualTypeArguments()).iterator();
        for (int i = 0; i < parameterizedType.getActualTypeArguments().length; ++i) {
            Type parameterizedTypeArgument = parameterizedType.getActualTypeArguments()[i];
            if (parameterizedTypeArgument instanceof TypeVariable) {
                typeArguments[i] = genericTypeArguments.next();
            } else {
                typeArguments[i] = parameterizedTypeArgument;
            }
        }

        ParameterizedType result = new MoreTypes.ParameterizedTypeImpl(
                parameterizedType == null ? null : parameterizedType.getOwnerType(),
                parameterizedType == null ? null : parameterizedType.getRawType(),
                typeArguments);
        return result;
    }

    private void sanitizeClassInfoFieldListByClassFieldName(List<FieldInfo> fieldList, ClassInfo classInfo) {
        List<FieldInfo> classInfoFields = classInfo.getFields().stream()
                .filter(f -> fieldList.stream()
                        .anyMatch(ff -> ff.getClassFieldName().equals(f.getClassFieldName()))
                ).collect(Collectors.toList());
        classInfo.getFields().removeAll(classInfoFields);
    }

    @Override
    public TypeModel getObjectsTypeModel(Type type, Function<Class<?>, String> objectLinkAccessor) {
        TypeModel typeModel = new TypeModel();

        Class<?> clazz;
        ParameterizedType genericType;
        FieldInfo fieldInfoContainer = new FieldInfo();

        if (type instanceof ParameterizedType) {
            genericType = (ParameterizedType) type;
            clazz = (Class<?>) genericType.getRawType();
        } else {
            clazz = (Class<?>) type;
            genericType = null;

            String link = objectLinkAccessor.apply(clazz);
            typeModel.setClassUsedOnTheFieldPath(link);
        }

        ParameterDataType parameterDataType = reflectionService.determineParameterDataType(clazz, genericType, fieldInfoContainer);

        String classSimpleName = clazz.getSimpleName();
        typeModel.setClassNameUsedOnTheField(classSimpleName);
        typeModel.setClazz(clazz);
        typeModel.setDataType(parameterDataType);
        typeModel.setObjectDataType(fieldInfoContainer.getObjectDataType());

        if (parameterDataType == ParameterDataType.object_type ||
                (parameterDataType == ParameterDataType.enum_type && typeModel.getClassUsedOnTheFieldPath() != null)) {
            ParameterizedType parameterizedType = fieldInfoContainer.getGenericDataType();
            ObjectDataType objectDataType = Objects.requireNonNullElse(fieldInfoContainer.getObjectDataType(), ObjectDataType.object_type);
            switch (objectDataType) {
                case object_type:
                    break;

                case list_type:
                    Type listType = parameterizedType.getActualTypeArguments()[0];
                    TypeModel listTypeModel = getObjectsTypeModel(listType, objectLinkAccessor);
                    typeModel.setGenericKeyType(listTypeModel);
                    break;

                case map_type:
                    Type keyType = parameterizedType.getActualTypeArguments()[0];
                    TypeModel keyTypeModel = getObjectsTypeModel(keyType, objectLinkAccessor);
                    typeModel.setGenericKeyType(keyTypeModel);

                    Type valueType = parameterizedType.getActualTypeArguments()[1];
                    TypeModel valueTypeModel = getObjectsTypeModel(valueType, objectLinkAccessor);
                    typeModel.setGenericValueType(valueTypeModel);
                    break;

                default:
                    throw new RuntimeException(new NoSuchFieldException("Enum value is not present"));
            }
        }

        return typeModel;
    }

    @Override
    public ReflectionService getReflectionService() {
        return this.reflectionService;
    }
}
