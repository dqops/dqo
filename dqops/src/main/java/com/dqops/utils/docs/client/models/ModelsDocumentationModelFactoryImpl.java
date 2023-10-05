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
package com.dqops.utils.docs.client.models;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.apimodel.OperationModel;
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
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelsDocumentationModelFactoryImpl implements ModelsDocumentationModelFactory {

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    @Override
    public List<ModelsObjectDocumentationModel> createDocumentationForModels(Collection<ComponentModel> componentModels,
                                                                             LinkageStore<String> linkageStore) {
        List<ModelsObjectDocumentationModel> modelsDocumentation = new ArrayList<>();

        Map<String, ComponentModel> componentModelMap = componentModels.stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));
        List<ComponentModel> topLevelModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() != null && componentModel.getDocsLink().toString().contains("models"))
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        for (ComponentModel model : topLevelModels) {
            String modelName = model.getClassName();
            ModelsObjectDocumentationModel modelsObjectDocumentationModel = new ModelsObjectDocumentationModel();
            modelsObjectDocumentationModel.setClassFullName(modelName);
            modelsObjectDocumentationModel.setClassSimpleName(getObjectSimpleName(modelName));

            Map<Class<?>, ModelsObjectDocumentationModel> renderedModelObjects = new HashMap<>();

            generateModelObjectDocumentationModelRecursive(model.getReflectedClass(), renderedModelObjects, linkageStore);

            for (Map.Entry<Class<?>, ModelsObjectDocumentationModel> renderedModelObjectEntry : renderedModelObjects.entrySet()) {
                ComponentModel correspondingComponent = componentModelMap.get(renderedModelObjectEntry.getKey().getSimpleName());
                if (correspondingComponent != null && (correspondingComponent.getDocsLink() == null || correspondingComponent == model)) {
                    modelsDocumentation.add(renderedModelObjectEntry.getValue());
                    linkageStore.putIfAbsent(
                            renderedModelObjectEntry.getKey().getSimpleName(),
                            renderedModelObjectEntry.getValue().getObjectClassPath()
                    );
                }
            }
        }

        return modelsDocumentation;
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
    private void generateModelObjectDocumentationModelRecursive(Class<?> targetClass,
                                                                Map<Class<?>, ModelsObjectDocumentationModel> visitedObjects,
                                                                LinkageStore<String> linkageStore) {
        if (targetClass != null && !visitedObjects.containsKey(targetClass)) {
            visitedObjects.put(targetClass, null);

            ModelsObjectDocumentationModel modelsObjectDocumentationModel = new ModelsObjectDocumentationModel();
            List<ModelsDocumentationModel> modelsDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    modelsObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    processGenericTypes(genericSuperclass, visitedObjects, linkageStore);
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            modelsObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            modelsObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            modelsObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            modelsObjectDocumentationModel.setObjectClassPath(
                    Path.of("/docs/client/models/").resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType() == null) {
                    continue;
                }

                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    generateModelObjectDocumentationModelRecursive(info.getClazz(), visitedObjects, linkageStore);
                }

                ModelsDocumentationModel modelsDocumentationModel = new ModelsDocumentationModel();
                modelsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

                if (linkageStore.containsKey(info.getClazz().getSimpleName())) {
                    Path infoClassPath = linkageStore.get(info.getClazz().getSimpleName());
                    modelsDocumentationModel.setClassUsedOnTheFieldPath(infoClassPath.toString());
                } else {
                    modelsDocumentationModel.setClassUsedOnTheFieldPath(
                            "#" + modelsDocumentationModel.getClassNameUsedOnTheField());
                }

                modelsDocumentationModel.setClassFieldName(info.getClassFieldName());
                modelsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                modelsDocumentationModel.setDisplayName(info.getDisplayName());
                modelsDocumentationModel.setHelpText(info.getHelpText());
                modelsDocumentationModel.setClazz(info.getClazz());
                modelsDocumentationModel.setDataType(info.getDataType());
                modelsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                modelsDocumentationModel.setDefaultValue(info.getDefaultValue());
                modelsDocumentationModel.setSampleValues(info.getSampleValues());

                modelsDocumentationModels.add(modelsDocumentationModel);

            }
            modelsObjectDocumentationModel.setObjectFields(modelsDocumentationModels);
            visitedObjects.put(targetClass, modelsObjectDocumentationModel);
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
    private void processGenericTypes(Type type, Map<Class<?>, ModelsObjectDocumentationModel> visitedObjects, LinkageStore<String> linkageStore) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        generateModelObjectDocumentationModelRecursive(genericClass, visitedObjects, linkageStore);
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

