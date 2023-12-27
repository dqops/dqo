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
package com.dqops.utils.docs.yaml;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

/**
 * Yaml documentation model factory that creates a yaml documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class YamlDocumentationModelFactoryImpl implements YamlDocumentationModelFactory {

    private final DocumentationReflectionService reflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    private final LinkageStore<Class<?>> linkageStore;

    public YamlDocumentationModelFactoryImpl(LinkageStore<Class<?>> linkageStore) {
        this.linkageStore = linkageStore;
    }

    /**
     * Create a yaml documentation models.
     * @param yamlDocumentationSchema Yaml documentation schema.
     * @return Yaml superior documentation models.
     */
    @Override
    public List<YamlSuperiorObjectDocumentationModel> createDocumentationForYaml(List<YamlDocumentationSchemaNode> yamlDocumentationSchema) {
        List<YamlSuperiorObjectDocumentationModel> yamlDocumentation = new ArrayList<>();

        for (YamlDocumentationSchemaNode yamlDocumentationNode : yamlDocumentationSchema) {
            Class<?> yamlClass = yamlDocumentationNode.getClazz();
            YamlSuperiorObjectDocumentationModel yamlSuperiorObjectDocumentationModel = new YamlSuperiorObjectDocumentationModel();
            yamlSuperiorObjectDocumentationModel.setSuperiorClassFullName(yamlClass.getName());
            yamlSuperiorObjectDocumentationModel.setSuperiorClassFilePath(yamlDocumentationNode.getPathToFile().toString());
            yamlSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(yamlDocumentationNode.getPathToFile().getFileName().toString());

            Map<Class<?>, YamlObjectDocumentationModel> yamlObjectDocumentationModels = new HashMap<>();

            generateYamlObjectDocumentationModelRecursive(
                    Path.of("docs", "reference", "yaml",
                            yamlSuperiorObjectDocumentationModel.getSuperiorClassFilePath()),
                    yamlClass, yamlObjectDocumentationModels);

            yamlSuperiorObjectDocumentationModel.setReflectedSuperiorClass(yamlClass);
            TypeModel yamlTypeModel = getObjectsTypeModel(yamlClass, yamlObjectDocumentationModels);
            yamlSuperiorObjectDocumentationModel.setReflectedSuperiorDataType(yamlTypeModel);
            yamlSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());

            for (Map.Entry<Class<?>, YamlObjectDocumentationModel> yamlObject : yamlObjectDocumentationModels.entrySet()) {
                yamlSuperiorObjectDocumentationModel.getClassObjects().add(yamlObject.getValue());
                linkageStore.put(yamlObject.getKey(), yamlObject.getValue().getObjectClassPath());
            }

            yamlDocumentation.add(yamlSuperiorObjectDocumentationModel);
        }
        return yamlDocumentation;
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private void generateYamlObjectDocumentationModelRecursive(Path superiorObjectFileName,
                                                               Class<?> targetClass,
                                                               Map<Class<?>, YamlObjectDocumentationModel> visitedObjects) {
        if (targetClass != null && !visitedObjects.containsKey(targetClass) && !linkageStore.containsKey(targetClass)) {
            YamlObjectDocumentationModel yamlObjectDocumentationModel = new YamlObjectDocumentationModel();
            visitedObjects.put(targetClass, yamlObjectDocumentationModel);
            List<YamlFieldsDocumentationModel> yamlFieldsDocumentationModels = new ArrayList<>();
            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            yamlObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            yamlObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            yamlObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            yamlObjectDocumentationModel.setObjectClassPath(
                    Path.of("/").resolve(superiorObjectFileName).resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    yamlObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    processGenericTypes(superiorObjectFileName, genericSuperclass, visitedObjects);
                }
            }

            for (FieldInfo info : infoFields) {
                if (info.getDataType() == null) {
                    continue;
                }

                YamlFieldsDocumentationModel yamlFieldsDocumentationModel = new YamlFieldsDocumentationModel();

                Type infoType = Objects.requireNonNullElse(info.getGenericDataType(), info.getClazz());
                TypeModel infoTypeModel = getObjectsTypeModel(infoType, visitedObjects);

                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("float")) {
                        // Float values
                        continue;
                    } else if (!info.getClazz().getName().contains("java.")) {
                        // Custom objects
                        generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, info.getClazz(), visitedObjects);
                    } else if (info.getObjectDataType() == ObjectDataType.list_type) {
                        // Objects that can be rendered as lists
                        TypeModel genericType = infoTypeModel.getGenericKeyType();
                        if (genericType.getDataType().equals(ParameterDataType.object_type)) {
                            generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, genericType.getClazz(), visitedObjects);
                        }
                    } else if (info.getObjectDataType() == ObjectDataType.map_type) {
                        // Objects that can be rendered as maps
                        TypeModel genericTypeK = infoTypeModel.getGenericKeyType();
                        if (genericTypeK.getDataType().equals(ParameterDataType.object_type)) {
                            generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, genericTypeK.getClazz(), visitedObjects);
                        }

                        TypeModel genericTypeV = infoTypeModel.getGenericValueType();
                        if (genericTypeV.getDataType().equals(ParameterDataType.object_type)) {
                            generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, genericTypeV.getClazz(), visitedObjects);
                        }
                    } else {
                        // Java std objects that cannot be rendered as maps
                        continue;
                    }
                }

                infoTypeModel = getObjectsTypeModel(infoType, visitedObjects);
                yamlFieldsDocumentationModel.setTypeModel(infoTypeModel);
                yamlFieldsDocumentationModel.setClassFieldName(info.getClassFieldName());
                yamlFieldsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                yamlFieldsDocumentationModel.setDisplayName(info.getDisplayName());
                yamlFieldsDocumentationModel.setHelpText(info.getHelpText());
                yamlFieldsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                yamlFieldsDocumentationModel.setDefaultValue(info.getDefaultValue());
                yamlFieldsDocumentationModel.setSampleValues(info.getSampleValues());

                yamlFieldsDocumentationModels.add(yamlFieldsDocumentationModel);

            }
            yamlObjectDocumentationModel.setObjectFields(yamlFieldsDocumentationModels);
            visitedObjects.put(targetClass, yamlObjectDocumentationModel);
        }
    }

    private TypeModel getObjectsTypeModel(Type type, Map<Class<?>, YamlObjectDocumentationModel> visitedObjects) {
        Function<Class<?>, String> linkAccessor = (clazz) -> {
            if (linkageStore.containsKey(clazz)) {
                Path infoClassPath = linkageStore.get(clazz);
                return infoClassPath.toString().replace('\\', '/');
            } else if (visitedObjects.containsKey(clazz)) {
                YamlObjectDocumentationModel visitedObject = visitedObjects.get(clazz);
                return visitedObject.getObjectClassPath().toString().replace('\\', '/');
            } else {
                return null;
            }
        };

        return reflectionService.getObjectsTypeModel(type, linkAccessor);
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
    private void processGenericTypes(Path superiorObjectFileName, Type type, Map<Class<?>, YamlObjectDocumentationModel> visitedObjects) {
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

