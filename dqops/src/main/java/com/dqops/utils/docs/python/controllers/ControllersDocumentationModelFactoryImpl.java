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
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.google.common.base.CaseFormat;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllersDocumentationModelFactoryImpl implements ControllersDocumentationModelFactory {

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    private final LinkageStore<String> linkageStore;

    public ControllersDocumentationModelFactoryImpl(LinkageStore<String> linkageStore) {
        this.linkageStore = linkageStore;
    }

    @Override
    public List<ControllersSuperiorObjectDocumentationModel> createDocumentationForControllers(OpenAPI openAPI) {
        List<ControllersSuperiorObjectDocumentationModel> controllersDocumentation = new ArrayList<>();

        for (Tag tag : openAPI.getTags()) {
            ControllersSuperiorObjectDocumentationModel controllersSuperiorObjectDocumentationModel = new ControllersSuperiorObjectDocumentationModel();
            controllersSuperiorObjectDocumentationModel.setSuperiorClassFullName(tag.getName());
            controllersSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(getTagSimpleName(tag));

            Map<Class<?>, ControllersObjectDocumentationModel> yamlObjectDocumentationModels = new HashMap<>();

//            generateYamlObjectDocumentationModelRecursive(
//                    yamlClass, yamlObjectDocumentationModels);
//
//            controllersSuperiorObjectDocumentationModel.setReflectedSuperiorClass(yamlClass);
            controllersSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());
//
//            for (Map.Entry<Class<?>, ControllersObjectDocumentationModel> yamlObject : yamlObjectDocumentationModels.entrySet()) {
//                controllersSuperiorObjectDocumentationModel.getClassObjects().add(yamlObject.getValue());
//                linkageStore.put(yamlObject.getKey(), yamlObject.getValue().getObjectClassPath());
//            }

            controllersDocumentation.add(controllersSuperiorObjectDocumentationModel);
        }
        return controllersDocumentation;
    }

    private String getTagSimpleName(Tag tag) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tag.getName());
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private void generateYamlObjectDocumentationModelRecursive(Path superiorObjectFileName,
                                                               Class<?> targetClass,
                                                               Map<Class<?>, ControllersObjectDocumentationModel> visitedObjects) {
        if (!visitedObjects.containsKey(targetClass) && !linkageStore.containsKey(targetClass)) {
            visitedObjects.put(targetClass, null);

            ControllersObjectDocumentationModel controllersObjectDocumentationModel = new ControllersObjectDocumentationModel();
            List<ControllersDocumentationModel> controllersDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    controllersObjectDocumentationModel.setClassDescription(formattedClassComment);
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

            controllersObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            controllersObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            controllersObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            controllersObjectDocumentationModel.setObjectClassPath(
                    Path.of("/").resolve(superiorObjectFileName).resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, info.getClazz(), visitedObjects);
                }

                ControllersDocumentationModel controllersDocumentationModel = new ControllersDocumentationModel();
                controllersDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

                if (linkageStore.containsKey(info.getClazz())) {
                    Path infoClassPath = linkageStore.get(info.getClazz());
                    controllersDocumentationModel.setClassUsedOnTheFieldPath(infoClassPath.toString());
                } else {
                    controllersDocumentationModel.setClassUsedOnTheFieldPath(
                            "#" + controllersDocumentationModel.getClassNameUsedOnTheField());
                }

                controllersDocumentationModel.setClassFieldName(info.getClassFieldName());
                controllersDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                controllersDocumentationModel.setDisplayName(info.getDisplayName());
                controllersDocumentationModel.setHelpText(info.getHelpText());
                controllersDocumentationModel.setClazz(info.getClazz());
                controllersDocumentationModel.setDataType(info.getDataType());
                controllersDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                controllersDocumentationModel.setDefaultValue(info.getDefaultValue());
                controllersDocumentationModel.setSampleValues(info.getSampleValues());

                controllersDocumentationModels.add(controllersDocumentationModel);

            }
            controllersObjectDocumentationModel.setObjectFields(controllersDocumentationModels);
            visitedObjects.put(targetClass, controllersObjectDocumentationModel);
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
    private void processGenericTypes(Path superiorObjectFileName, Type type, Map<Class<?>, ControllersObjectDocumentationModel> visitedObjects) {
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

