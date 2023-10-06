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
package com.dqops.utils.docs.client.operations;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.google.common.base.CaseFormat;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;

public class OperationsDocumentationModelFactoryImpl implements OperationsDocumentationModelFactory {

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    @Override
    public List<OperationsSuperiorObjectDocumentationModel> createDocumentationForOperations(OpenAPIModel openAPIModel) {
        List<OperationsSuperiorObjectDocumentationModel> operationsDocumentation = new ArrayList<>();

//        for (Map.Entry<String, Set<OperationModel>> operation : openAPIModel.getOperationsMethods().entrySet()) {
//            String operationName = operation.getKey();
//            Set<OperationModel> operationMethods = operation.getValue();
//            OperationsSuperiorObjectDocumentationModel operationsSuperiorObjectDocumentationModel = new OperationsSuperiorObjectDocumentationModel();
//            operationsSuperiorObjectDocumentationModel.setSuperiorClassFullName(operationName);
//            operationsSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(getObjectSimpleName(operationName));
//
//            Map<Class<?>, OperationsObjectDocumentationModel> yamlObjectDocumentationModels = new HashMap<>();
//
////            generateYamlObjectDocumentationModelRecursive(
////                    yamlClass, yamlObjectDocumentationModels);
////
////            operationsSuperiorObjectDocumentationModel.setReflectedSuperiorClass(yamlClass);
//            operationsSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());
////
////            for (Map.Entry<Class<?>, OperationsObjectDocumentationModel> yamlObject : yamlObjectDocumentationModels.entrySet()) {
////                operationsSuperiorObjectDocumentationModel.getClassObjects().add(yamlObject.getValue());
////                linkageStore.put(yamlObject.getKey(), yamlObject.getValue().getObjectClassPath());
////            }
//
//            operationsDocumentation.add(operationsSuperiorObjectDocumentationModel);
//        }
        return operationsDocumentation;
    }

    private String getObjectSimpleName(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private void generateYamlObjectDocumentationModelRecursive(Path superiorObjectFileName,
                                                               Class<?> targetClass,
                                                               Map<Class<?>, OperationsObjectDocumentationModel> visitedObjects) {
        if (!visitedObjects.containsKey(targetClass)) {
            visitedObjects.put(targetClass, null);

            OperationsObjectDocumentationModel operationsObjectDocumentationModel = new OperationsObjectDocumentationModel();
            List<OperationsDocumentationModel> operationsDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    operationsObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    processGenericTypes(superiorObjectFileName, genericSuperclass, visitedObjects);
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            operationsObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            operationsObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            operationsObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            operationsObjectDocumentationModel.setObjectClassPath(
                    Path.of("/").resolve(superiorObjectFileName).resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, info.getClazz(), visitedObjects);
                }

                OperationsDocumentationModel operationsDocumentationModel = new OperationsDocumentationModel();
                operationsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

//                if (linkageStore.containsKey(info.getClazz())) {
//                    Path infoClassPath = linkageStore.get(info.getClazz());
//                    operationsDocumentationModel.setClassUsedOnTheFieldPath(infoClassPath.toString());
//                } else {
//                    operationsDocumentationModel.setClassUsedOnTheFieldPath(
//                            "#" + operationsDocumentationModel.getClassNameUsedOnTheField());
//                }

                operationsDocumentationModel.setClassFieldName(info.getClassFieldName());
                operationsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                operationsDocumentationModel.setDisplayName(info.getDisplayName());
                operationsDocumentationModel.setHelpText(info.getHelpText());
                operationsDocumentationModel.setClazz(info.getClazz());
                operationsDocumentationModel.setDataType(info.getDataType());
                operationsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                operationsDocumentationModel.setDefaultValue(info.getDefaultValue());
                operationsDocumentationModel.setSampleValues(info.getSampleValues());

                operationsDocumentationModels.add(operationsDocumentationModel);

            }
            operationsObjectDocumentationModel.setObjectFields(operationsDocumentationModels);
            visitedObjects.put(targetClass, operationsObjectDocumentationModel);
        }
    }

    /**
     * Checks if the specified class is a generic superclass.
     */
    private boolean isGenericSuperclass(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        return genericSuperclass instanceof ParameterizedType;
    }

    /**
     * Process and parse generic types, and then invoke appropriate actions on those types in a recursive.
     */
    private void processGenericTypes(Path superiorObjectFileName, Type type, Map<Class<?>, OperationsObjectDocumentationModel> visitedObjects) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, genericClass, visitedObjects);
                    }
                }
            }
        }
    }

    /**
     * Checks if the specified class is a java inbuilt class
     */
    private boolean isJavaClass(Class<?> clazz) {
        return clazz.getClassLoader() == null;
    }
}

