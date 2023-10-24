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
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionService;
import com.google.inject.internal.MoreTypes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentationReflectionServiceImpl implements DocumentationReflectionService {
    private static final String REFLECTIVE_FIELD_NAME = "this";
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
    public ReflectionService getReflectionService() {
        return this.reflectionService;
    }
}
